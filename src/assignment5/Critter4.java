package assignment5;

import assignment5.Critter.CritterShape;

/**
 * Critter4 Class: Mommy Critter This Critter will reproduce whenever it can if
 * it has enough energy It will fight if it has had a child in the turn (which
 * it will almost always have) to protect it
 */
public class Critter4 extends Critter {

	private boolean hadChild;
	private boolean moved;

	/**
	 * Constructor
	 */
	public Critter4() {
		hadChild = false;
	}

	/**
	 * doTimeStep: Critter4 will always have a child if it has enough energy to do
	 * so
	 */
	@Override
	public void doTimeStep() {
		hadChild = false;
		moved = false;
		walk(getRandomInt(8));
		moved = true;
		if (getEnergy() >= Params.min_reproduce_energy) {
			Critter4 child = new Critter4();
			reproduce(child, getRandomInt(8));
			hadChild = true;
		}
	}

	/**
	 * fight: Critter4 will protect its child if needed it is also smart enough not
	 * to attempt to run if it has already moved
	 */
	@Override
	public boolean fight(String oponent) {
		if (oponent.equals("@")) {
			return true;
		}
		if (hadChild == false || moved == true) {
			return true;
		}
		run(getRandomInt(8));
		return false;
	}

	/**
	 * String representation of Critter4
	 */
	@Override
	public String toString() {
		return "4";
	}

	/**
	 * Shows the number of babies that Critter4s made last turn
	 * 
	 * @param critter4
	 */
	public static String runStats(java.util.List<Critter> critter4) {
		String ret = "";
		int hadChild = 0;
		for (Object obj : critter4) {
			Critter4 c = (Critter4) obj;
			if (c.hadChild) {
				hadChild++;
			}
		}
		hadChild = percentChild(critter4.size(), hadChild);

		ret += ("" + critter4.size() + " total Critter3s: " + hadChild + "% had babies") + '\n';
		return ret;
	}

	/**
	 * Helper function cause repeating code is annoying
	 * 
	 * @param size
	 *            total size
	 * @param child
	 *            Children made last turn to be turned into a percentage
	 * @return Has the percentage in integer form also keeps 0 so no divide by 0
	 *         error
	 */
	private static int percentChild(int size, int child) {
		if (child == 0) {
			return 0;
		}
		return child * 100 / size;
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.DIAMOND; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.FUCHSIA; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.FUCHSIA; }

}
