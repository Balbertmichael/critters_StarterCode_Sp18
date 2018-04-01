package assignment4;

import assignment4.Critter.CritterShape;

/**
 * Critter1 Class: Lazy Critter This Critter only moves if it has to, meaning it
 * will only move if it's energy is low so it can find food. Otherwise the
 * Critter will rest in each time step and never runs. If it is confronted in a
 * fight, it will walk away if possible
 *
 */
public class Critter1 extends Critter {

	// Private variable to add awareness on whether or not it has moved
	private boolean moved;

	/**
	 * Constructor
	 */
	public Critter1() {
		moved = false;
	}

	/**
	 * doTimeStep: Critter1 only rests during a time step It will only reproduce
	 * once it has energy twice as much as the starting energy
	 */
	@Override
	public void doTimeStep() {
		moved = false;
		if (getEnergy() < Params.start_energy / 2) {
			// Only walks if it knows it's about to die
			for (int i = 0; i < 8; ++i) {
				String lookVal = look(i, false);
				if (lookVal.equals("2") || lookVal.equals("C")) {
					walk((i + 4) % 8);
					moved = true;
				}
			}
			if (!moved) {
				walk(getRandomInt(8));
				moved = true;
			}
		} else {
			for (int i = 0; i < 8; ++i) {
				String lookVal = look(i, false);
				if (lookVal.equals("2") || lookVal.equals("C")) {
					walk((i + 4) % 8);
					moved = true;
				}
			}
			if (getEnergy() >= 2 * Params.start_energy) {
				Critter1 child = new Critter1();
				reproduce(child, getRandomInt(8));
			}
		}
	}

	/**
	 * fight: Critter1 never fights unless necessary it is smart enough to know if
	 * it already moved or not it also never runs, only walks
	 */
	@Override
	public boolean fight(String oponent) {
		if (oponent.equals("@")) {
			return true;
		}
		if (moved == true) {
			return true;
		}
		walk(getRandomInt(8));
		moved = true;
		return false;
	}

	/**
	 * String representation of Critter1
	 */
	@Override
	public String toString() {
		return "1";
	}

	/**
	 * Shows the lazy and the desperate Critter1s last turn
	 * 
	 * @param critter1
	 */
	public static String runStats(java.util.List<Critter> critter1) {
		String ret = "";
		int lazy = 0;
		int escapedFromDeath = 0;
		for (Object obj : critter1) {
			Critter1 c = (Critter1) obj;
			if (c.moved) {
				escapedFromDeath++;
			} else {
				lazy++;
			}
		}
		if (lazy != 0) {
			lazy = lazy * 100 / critter1.size();
		}
		if (escapedFromDeath != 0) {
			escapedFromDeath = ((escapedFromDeath * 100) / critter1.size());
		}
		ret += ("" + critter1.size() + " total Critter1s: " + lazy + "% are lazy, " + escapedFromDeath
				+ "% had a close brush with death.");
		return ret;
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.CIRCLE;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.DARKMAGENTA;
	}

	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.DARKMAGENTA;
	}

}
