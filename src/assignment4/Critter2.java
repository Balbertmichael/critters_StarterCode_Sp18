package assignment4;

import java.util.List;

import assignment4.Critter.CritterShape;

/**
 * Critter2 Class: Hotheaded Critter This Critter will never back away from a
 * fight. It is always will go towards a fight.
 */
public class Critter2 extends Critter {

	private static int fought = 0;

	/**
	 * Constructor
	 */
	public Critter2() {
	}

	/**
	 * doTimeStep: Critter2 will always move to fight
	 */
	@Override
	public void doTimeStep() {
		fought = 0;
		boolean moved = false;
		for(int i = 0; i < 8; ++i) {
			if(look(i, false) != null) {
				walk(i);
				moved = true;
			}
			else if(look(i, true) != null) {
				run(i);
				moved = true;
			}
		}
		if(!moved) {
			run(getRandomInt(8));
		}
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
	public static String runStats(List<Critter> critter4) {
		String ret = "";
		ret += (critter4.size() + " total Critter2s who fought " + fought + " times") + '\n';
		return ret;
	}

	@Override
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.RED; }

}
