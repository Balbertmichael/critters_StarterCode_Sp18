package assignment4;

public class Critter2 extends Critter {
	
	public Critter2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doTimeStep() {
		run(getRandomInt(8));
		if (getEnergy() >= Params.start_energy * 3) {
			Critter2 child = new Critter2();
			reproduce(child, getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String oponent) {
		return true;
	}
	
	@Override
	public String toString() {
		return "2";
	}

}
