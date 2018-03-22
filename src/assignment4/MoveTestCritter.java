package assignment4;

public class MoveTestCritter extends Critter.TestCritter {
	
	//final static int R = 0, UR = 1, U = 2, UL = 3, L = 4, DL = 5, D = 6, DR = 7;
	private int strNum = 0;
	private int direction = 0;
	
	public MoveTestCritter(int num) {
		strNum = num;
	}
	
	@Override
	public void doTimeStep() {
		walk(direction);
	}
	
	public void reproduce(int dir) {
		Critter child = new MoveTestCritter(dir);
		reproduce(child, dir);
	}
	
	public void changeDir(int dir) {
		direction = dir;
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	
	public String toString() {
		return Integer.toString(strNum);
	}
}
