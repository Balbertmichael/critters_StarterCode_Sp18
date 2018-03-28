package assignment4;

import java.util.List;

import assignment4.Critter.CritterShape;

/**
 * Critter2 Class: Hotheaded Critter This Critter will never back away from a
 * fight. It is always energetic and running. It never walks
 */
public class Critter2 extends Critter {

	private static int fought = 0;

	/**
	 * Constructor
	 */
	public Critter2() {
	}

	/**
	 * doTimeStep: Critter2 will always run randomly in whichever direction it wants
	 */
	@Override
	public void doTimeStep() {
		fought = 0;
		run(getRandomInt(8));
		if (getEnergy() >= Params.start_energy * 3) {
			Critter2 child = new Critter2();
			reproduce(child, getRandomInt(8));
		}
	}

	/**
	 * fight: Critter2 will always fight
	 */
	@Override
	public boolean fight(String oponent) {
		fought++;
		return true;
	}

	/**
	 * String representation of Critter2
	 */
	@Override
	public String toString() {
		return "2";
	}

	/**
	 * Shows the number of fights Critter2s had last turn
	 * 
	 * @param critter4
	 */
	public static void runStats(List<Critter> critter4) {
		System.out.println(critter4.size() + " total Critter2s who fought " + fought + " times");
	}

	@Override
	public CritterShape viewShape() { return CritterShape.SQUARE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }

}
