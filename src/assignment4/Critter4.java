package assignment4;

public class Critter4 extends Critter {
	
	private boolean hadChild;
	
	public Critter4() {
		hadChild = false;
	}
	
	@Override
	public void doTimeStep() {
		hadChild = false;
		walk(getRandomInt(8));
		if(getEnergy() >= Params.min_reproduce_energy) {
			Critter4 child = new Critter4();
			reproduce(child, getRandomInt(8));
			hadChild = true;
		}
	}

	@Override
	public boolean fight(String oponent) {
		return hadChild;
	}

	@Override
	public String toString() {
		return "4";
	}
}
