package assignment4;

import assignment4.Critter.CritterShape;

/**
 * Critter3 Class: Straight-laced Critter This Critter moves only completely
 * horizontally or completely vertically It never moves diagonally It can walk,
 * run, but never rests
 */
public class Critter3 extends Critter {

	private boolean moved;
	private int dir = 0;

	public Critter3() {
	}

	/**
	 * doTimeStep: Critter3 randomly chooses an action and goes with it However it
	 * will always move straight, never diagonally
	 */
	@Override
	public void doTimeStep() {
		moved = false;
		int chooseMove = getRandomInt(2);
		dir = 2 * getRandomInt(4);
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
	 * fight: Critter3 is smart enough to know if it has moved. so it will fight if
	 * it already moved. otherwise, it will try to run away
	 */
	@Override
	public boolean fight(String oponent) {
		if (oponent.equals("@")) {
			return true;
		}
		if (moved == true) {
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

	/**
	 * Shows the locked directions of Critter3 made last turn
	 * 
	 * @param critter3
	 */
	public static void runStats(java.util.List<Critter> critter3) {
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;
		for (Object obj : critter3) {
			Critter3 c = (Critter3) obj;
			if (c.dir == 0) {
				++right;
			} else if (c.dir == 2) {
				++up;
			} else if (c.dir == 4) {
				++left;
			} else if (c.dir == 6) {
				++down;
			}
		}
		int size = critter3.size();
		up = PercentDir(size, up);
		down = PercentDir(size, down);
		right = PercentDir(size, right);
		left = PercentDir(size, left);
		System.out.println("" + critter3.size() + " total Critter3s: " + up + "% Up, " + down + "% Down, " + left
				+ "% Left, " + right + "% right");
	}

	/**
	 * Helper function cause repeating code is annoying
	 * 
	 * @param size
	 *            total size
	 * @param dir
	 *            Direction that is to be made into a percentage
	 * @return Has the percentage in integer form also keeps 0 so no divide by 0
	 *         error
	 */
	private static int PercentDir(int size, int dir) {
		if (dir == 0) {
			return 0;
		}
		return dir * 100 / size;
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.GOLDENROD; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.GOLDENROD; }

}
