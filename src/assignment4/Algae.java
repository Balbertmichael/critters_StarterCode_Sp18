package assignment4;

public class Algae extends Critter {
	
	public Algae() {
	}
	
	@Override
	public String toString() {
		return "@";
	}
	
	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String oponent) {
		return true;
	}

}
