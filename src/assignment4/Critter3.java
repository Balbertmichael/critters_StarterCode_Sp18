package assignment4;
/**
 * Critter3 Class: Straight-laced Critter
 * This Critter moves only completely horizontally or completely vertically
 * It never moves diagonally
 * It can walk, run, but never rests
 */
public class Critter3 extends Critter {

	private boolean moved;

	public Critter3() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * doTimeStep: Critter3 randomly chooses an action and goes with it
	 * However it will always move straight, never diagonally
	 */
	@Override
	public void doTimeStep() {
		moved = false;
		int chooseMove = getRandomInt(3);
		int dir = 2 * getRandomInt(4);
		switch (chooseMove) {
		case (0):
			run(dir);
			moved = true;
			break;
		case (1):
			walk(dir);
			moved = true;
			break;
		default:
			break;
		}

		if (getEnergy() >= Params.start_energy * 1.5) {
			Critter3 child = new Critter3();
			reproduce(child, dir);
		}
	}

	/**
	 * fight: Critter3 is smart enough to know if it has moved.
	 * so it will fight if it already moved.
	 * otherwise, it will try to run away
	 */
	@Override
	public boolean fight(String oponent) {
		if (oponent.equals("@")) {
			return true;
		}
		if(moved == true) {
			return true;
		}
		run(getRandomInt(4) * 2);
		return false;
	}

	/**
	 * String representation of Critter3
	 */
	@Override
	public String toString() {
		return "3";
	}
}
