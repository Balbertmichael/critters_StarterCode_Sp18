package assignment4;
/**
 * Critter4 Class: Mommy Critter
 * This Critter will reproduce whenever it can if it has enough energy
 * It will fight if it has had a child in the turn (which it will almost always have)
 * to protect it
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
	 * doTimeStep: Critter4 will always have a child if it has enough energy to do so
	 */
	@Override
	public void doTimeStep() {
		hadChild = false;
		moved = false;
		walk(getRandomInt(8));
		moved = true;
		if(getEnergy() >= Params.min_reproduce_energy) {
			Critter4 child = new Critter4();
			reproduce(child, getRandomInt(8));
			hadChild = true;
		}
	}

	/**
	 * fight: Critter4 will protect its child if needed
	 * it is also smart enough not to attempt to run if it has already moved
	 */
	@Override
	public boolean fight(String oponent) {
		if (oponent.equals("@")) {
			return true;
		}
		if(hadChild == false || moved == true) {
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
}
