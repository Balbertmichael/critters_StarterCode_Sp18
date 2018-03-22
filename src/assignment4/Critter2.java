package assignment4;
/**
 * Critter2 Class: Hotheaded Critter
 * This Critter will never back away from a fight.
 * It is always energetic and running. It never walks
 */
public class Critter2 extends Critter {

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
		return true;
	}
	
	/**
	 * String representation of Critter2
	 */
	@Override
	public String toString() {
		return "2";
	}

}
