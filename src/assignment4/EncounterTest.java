package assignment4;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import assignment4.Critter.TestCritter;

public class EncounterTest {

	private static final boolean DEBUG = false;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Params.world_width = 20;
		Params.world_height = 20;
		Params.walk_energy_cost = 2;
		Params.run_energy_cost = 5;
		Params.rest_energy_cost = 1;
		Params.min_reproduce_energy = 20;
		Params.refresh_algae_count = 0;

		Params.photosynthesis_energy_amount = 1;
		Params.start_energy = 100;
		TestCritter.clearWorld();
	}

	@After
	public void tearDown() throws Exception {
	}

	// Utility method.
	/**
	 * Move n steps in the specified direction.
	 * 
	 * @param current
	 *            x, current y, direction to move, steps to move
	 * @return new co-ordinates.
	 */
	public static int[] moveStep(int x_coord, int y_coord, int direction, int n) {
		int w = Params.world_width;
		int h = Params.world_height;
		int newX = x_coord + w;
		int newY = y_coord + h;

		switch (direction) {
		case 0:
			newX = (newX += n);
			break;
		case 1:
			newX = (newX += n);
			newY = (newY -= n);
			break;
		case 2:
			newY = (newY -= n);
			break;
		case 3:
			newX = (newX -= n);
			newY = (newY -= n);
			break;
		case 4:
			newX = (newX -= n);
			break;
		case 5:
			newX = (newX -= n);
			newY = (newY += n);
			break;
		case 6:
			newY = (newY += n);
			break;
		case 7:
			newX = (newX += n);
			newY = (newY += n);
			break;
		}
		return new int[] { newX % w, newY % h };
	}

	/**
	 * 11. Creates two Critters in the same spot, one being a runner. Checks to see
	 * if runner moved, lost energy, and lived
	 * 
	 * @throws InvalidCritterException
	 * 
	 */
	@Test
	public void RunDuringFightTest() throws InvalidCritterException {
		int x = 0;
		int y = 0;
		int num = 2;
		Critter.makeCritter("MyCritter7");
		MyCritter7 fighter = (MyCritter7) Critter.getInstances("MyCritter7").get(0);
		Critter.makeCritter("MyCritter6");
		MyCritter6 runner = (MyCritter6) Critter.getInstances("MyCritter6").get(0);
		runner.setX_coord(x);
		runner.setY_coord(y);
		fighter.setX_coord(x);
		fighter.setY_coord(y);

		assertEquals(num, TestCritter.getPopulation().size());
		Critter.worldTimeStep();

		if (DEBUG) {
			Critter.displayWorld();
		}
		assertFalse(runner.getEnergy() <= 0);
		assertEquals(Params.start_energy - Params.rest_energy_cost - Params.run_energy_cost, runner.getEnergy());
		assertTrue(runner.getX_coord() != x || runner.getY_coord() != y);
		assertTrue(fighter.getX_coord() == x && fighter.getY_coord() == y);
	}

	@Test
	public void RunFailDuringFightTest() throws InvalidCritterException {
		Critter.clearWorld();
		String e1 = "EncounterTestCritter1", e2 = "EncounterTestCritter2", m1 = "MyCritter6";
		int x = 0;
		int y = 0;
		int num = 10;
		Critter.makeCritter(e2);
		EncounterTestCritter2 fighter = (EncounterTestCritter2) Critter.getInstances(e2).get(0);
		Critter.makeCritter(e1);
		EncounterTestCritter1 runner = (EncounterTestCritter1) Critter.getInstances(e1).get(0);
		for (int i = 0; i < 8; ++i) {
			Critter.makeCritter(m1);
		}
		List<Critter> c = Critter.getInstances(m1);
		MyCritter6[] blocker = new MyCritter6[c.size()];
		for (int i = 0; i < c.size(); ++i) {
			blocker[i] = (MyCritter6) c.get(i);
		}
		runner.setX_coord(x);
		runner.setY_coord(y);
		fighter.setX_coord(x);
		fighter.setY_coord(y);
		for (int i = 0; i < 8; ++i) {
			int[] dir = moveStep(x, y, i, 2);
			blocker[i].setX_coord(dir[0]);
			blocker[i].setY_coord(dir[1]);
		}

		Critter.displayWorld();

		assertEquals(num, TestCritter.getPopulation().size());
		Critter.worldTimeStep();

		Critter.displayWorld();

		if (DEBUG) {
			Critter.displayWorld();
		}
		// assertFalse(runner.getEnergy() <= 0);
		// assertEquals(Params.start_energy - Params.rest_energy_cost -
		// Params.walk_energy_cost, runner.getEnergy());

		assertEquals(num - 1, TestCritter.getPopulation().size());
	}

	@Test
	public void FightTest() throws InvalidCritterException {
		Critter.clearWorld();
		String e1 = "EncounterTestCritter1", e2 = "EncounterTestCritter2", m1 = "MyCritter6";
		int x = 0;
		int y = 0;
		int num = 10;
		Critter.makeCritter(e2);
		EncounterTestCritter2 fighter = (EncounterTestCritter2) Critter.getInstances(e2).get(0);

		Critter.makeCritter(e2);
		EncounterTestCritter2 runner = (EncounterTestCritter2) Critter.getInstances(e2).get(1);

		for (int i = 0; i < 8; ++i) {
			Critter.makeCritter(m1);
		}
		List<Critter> c = Critter.getInstances(m1);
		MyCritter6[] blocker = new MyCritter6[c.size()];
		for (int i = 0; i < c.size(); ++i) {
			blocker[i] = (MyCritter6) c.get(i);
		}
		runner.setX_coord(x);
		runner.setY_coord(y);
		fighter.setX_coord(x);
		fighter.setY_coord(y);
		for (int i = 0; i < 8; ++i) {
			int[] dir = moveStep(x, y, i, 2);
			blocker[i].setX_coord(dir[0]);
			blocker[i].setY_coord(dir[1]);
		}

		Critter.displayWorld();

		assertEquals(num, TestCritter.getPopulation().size());
		Critter.worldTimeStep();

		Critter.displayWorld();

		if (DEBUG) {
			Critter.displayWorld();
		}
		// assertFalse(runner.getEnergy() <= 0);
		// assertEquals(Params.start_energy - Params.rest_energy_cost -
		// Params.walk_energy_cost, runner.getEnergy());

		assertEquals(num - 1, TestCritter.getPopulation().size());
	}

}
