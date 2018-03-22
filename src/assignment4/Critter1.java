package assignment4;
/**
 * Critter1 Class: Lazy Critter
 * This Critter only moves if it has to, meaning it will only move if it's energy is low so it can find food.
 * Otherwise the Critter will rest in each time step and never runs.
 * If it is confronted in a fight, it will walk away if possible
 *
 */
public class Critter1 extends Critter {
	
	//Private variable to add awareness on whether or not it has moved
	private boolean moved;
	
	/**
	 * Constructor
	 */
	public Critter1() {
		moved = false;
	}
	
	/**
	 * doTimeStep: Critter1 only rests during a time step
	 * It will only reproduce once it has energy twice as much as the starting energy
	 */
	@Override
	public void doTimeStep() {
		moved = false;
		if(getEnergy() < Params.start_energy / 2) {
			//Only walks if it knows it's going to die
			walk(Critter.getRandomInt(8));
			moved = true;
		}
		else {
			if(getEnergy() >= 2 * Params.start_energy) {
				Critter1 child = new Critter1();
				reproduce(child, getRandomInt(8));
			}
		}
	}

	/**
	 * fight: Critter1 never fights unless necessary
	 * it is smart enough to know if it already moved or not
	 * it also never runs, only walks
	 */
	@Override
	public boolean fight(String oponent) {
		// A somewhat smart critter that only fights if it can't
		if(oponent.equals("@")) {
			return true;
		}
		if(moved == true) {
			return true;
		}
		walk(getRandomInt(8));
		return false;
	}
	
	/**
	 * String representation of Critter1
	 */
	@Override
	public String toString() {
		return "1";
	}

}
