package assignment4;

public class Critter3 extends Critter {

	public Critter3() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doTimeStep() {
		int chooseMove = getRandomInt(3);
		int dir = 2 * getRandomInt(4);
		switch (chooseMove) {
		case (0):
			run(dir);
			break;
		case (1):
			walk(dir);
			break;
		default:
			break;
		}
		
		if(getEnergy() >= Params.start_energy * 1.5) {
			Critter3 child = new Critter3();
			reproduce(child, dir);
		}
	}

	@Override
	public boolean fight(String oponent) {
		if(oponent.equals("@")) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "3";
	}
}
