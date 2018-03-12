package assignment4;

public class Critter1 extends Critter {
	
	//Private variable to add awareness on whether or not it has moved
	private boolean moved;
	
	public Critter1() {
		moved = false;
	}
	
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
	
	@Override
	public String toString() {
		return "1";
	}

}
