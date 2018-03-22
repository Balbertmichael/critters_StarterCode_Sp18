package assignment4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import assignment4.Critter.TestCritter;

import java.util.List;

public class MovementTest {

	final static int R = 0, UR = 1, U = 2, UL = 3, L = 4, DL = 5, D = 6, DR = 7;
	int yend, xend;

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
		xend = Params.world_width - 1;
		yend = Params.world_height - 1;

		Params.photosynthesis_energy_amount = 1;
		Params.start_energy = 100;
		TestCritter.clearWorld();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * 1. Use MakeCritter to create a Critter, and makes sure walk works for 1 step.
	 * 
	 * @throws InvalidCritterException
	 */
	public void testWalk() throws InvalidCritterException {

		MoveTestCritter c = new MoveTestCritter(8);
		c.setX_coord(0);
		c.setY_coord(0);
		c.changeDir(L);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == Params.world_width - 1 && c.getY_coord() == 0);

		c.setX_coord(0);
		c.setY_coord(0);
		c.changeDir(U);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == 0 && c.getY_coord() == yend);

		c.setX_coord(0);
		c.setY_coord(0);
		c.changeDir(UL);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == Params.world_width - 1 && c.getY_coord() == Params.world_height - 1);

		c.setX_coord(Params.world_width - 1);
		c.setY_coord(0);
		c.changeDir(R);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == 0 && c.getY_coord() == 0);

		c.setX_coord(Params.world_width - 1);
		c.setY_coord(0);
		c.changeDir(UR);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == 0 && c.getY_coord() == yend);

		c.setX_coord(xend);
		c.setY_coord(0);
		c.changeDir(U);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == Params.world_width - 1 && c.getY_coord() == yend);

		c.setX_coord(0);
		c.setY_coord(yend);
		c.changeDir(L);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == xend && c.getY_coord() == yend);

		c.setX_coord(0);
		c.setY_coord(yend);
		c.changeDir(DL);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == Params.world_width - 1 && c.getY_coord() == 0);

		c.setX_coord(0);
		c.setY_coord(yend);
		c.changeDir(D);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == 0 && c.getY_coord() == 0);

		c.setX_coord(xend);
		c.setY_coord(yend);
		c.changeDir(R);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == 0 && c.getY_coord() == yend);

		c.setX_coord(xend);
		c.setY_coord(yend);
		c.changeDir(DR);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == 0 && c.getY_coord() == 0);

		c.setX_coord(xend);
		c.setY_coord(yend);
		c.changeDir(D);
		c.doTimeStep();
		System.out.println("X: " + c.getX_coord());
		System.out.println("Y: " + c.getY_coord());
		assertTrue(c.getX_coord() == Params.world_width - 1 && c.getY_coord() == 0);

	}

	@Test
	public void ReproduceTest() {
		MoveTestCritter c = new MoveTestCritter(8);
		c.setX_coord(0);
		c.setY_coord(0);
		for (int i = 0; i < 8; ++i) {
			c.setEnergy(1000);
			c.reproduce(i);
		}
		List<Critter> baby = TestCritter.getBabies();
		System.out.println(baby.size());
		List<Critter> pop = TestCritter.getPopulation();
		for (Critter b : baby) {
			c.setEnergy(1000);
			pop.add(b);
		}
		Critter.displayWorld();
		TestCritter.clearWorld();

		c.setX_coord(xend);
		c.setY_coord(0);
		for (int i = 0; i < 8; ++i) {
			c.setEnergy(1000);
			c.reproduce(i);
		}
		baby = TestCritter.getBabies();
		pop = TestCritter.getPopulation();
		for (Critter b : baby) {
			c.setEnergy(1000);
			pop.add(b);
		}
		Critter.displayWorld();
		TestCritter.clearWorld();

		c.setX_coord(0);
		c.setY_coord(yend);
		for (int i = 0; i < 8; ++i) {
			c.setEnergy(1000);
			c.reproduce(i);
		}
		baby = TestCritter.getBabies();
		pop = TestCritter.getPopulation();
		for (Critter b : baby) {
			c.setEnergy(1000);
			pop.add(b);
		}
		Critter.displayWorld();
		TestCritter.clearWorld();

		c.setX_coord(xend);
		c.setY_coord(yend);
		for (int i = 0; i < 8; ++i) {
			c.setEnergy(1000);
			c.reproduce(i);
		}
		baby = TestCritter.getBabies();
		pop = TestCritter.getPopulation();
		for (Critter b : baby) {
			c.setEnergy(1000);
			pop.add(b);
		}
		Critter.displayWorld();
		TestCritter.clearWorld();
	}
}