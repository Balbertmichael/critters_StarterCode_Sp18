package assignment4;

public class Critter4 extends Critter {
	
	private boolean hadChild;
	private boolean moved;
	
	public Critter4() {
		hadChild = false;
	}
	
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

	@Override
	public String toString() {
		return "4";
	}
}
