package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Angelique Bautista>
 * <Student1 ab54429>
 * <Student1 15465>
 * <Student2 Albert Bautista>
 * <Student2 abb2639>
 * <Student2 15505>
 * Slip days used: <0>
 * Spring 2018
 */

import java.io.File;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

/**
 * Critter class is the base abstract class It holds the basic functionality of
 * all Critters in the Critter world It also contains the entire Critter world
 * population
 */
public abstract class Critter {

	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE, SQUARE, TRIANGLE, DIAMOND, STAR
	}

	/*
	 * the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the
	 * default color to be the same as you background
	 * 
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a
	 * non-filled shape, at least, that's the intent. You can edit these default
	 * methods however you need to, but please preserve that intent as you implement
	 * them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor() {
		return viewColor();
	}

	public javafx.scene.paint.Color viewFillColor() {
		return viewColor();
	}

	public abstract CritterShape viewShape();

	private static String myPackage;
	private static String pkgLoc;

	private static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name and finds the location of the package. The usage
	// assumes that Critter and its subclasses are
	// all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
		pkgLoc = Paths.get("").toAbsolutePath().toString().replace("\\", "\\\\") + "\\\\" + "bin\\\\" + myPackage;
		if (!Files.exists(Paths.get(pkgLoc))) {
			pkgLoc = Paths.get("").toAbsolutePath().toString().replace("\\", "\\\\") + "\\\\" + myPackage;
		}
	}

	private static java.util.Random rand = new java.util.Random();

	/**
	 * Returns a random integer
	 * 
	 * @param max
	 *            maximum amount the integer can be
	 * @return random integer between 0 and max
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	/**
	 * Sets the seed for the random integer Will allow for more predictable testing
	 * 
	 * @param new_seed
	 *            value of seed
	 */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}

	/*
	 * a one-character long string that visually depicts your critter in the ASCII
	 * interface
	 */
	public String toString() {
		return "";
	}

	private int energy = 0;

	protected int getEnergy() {
		return energy;
	}

	private int x_coord;
	private int y_coord;

	protected final void walk(int direction) {
		move(1, direction);
		energy -= Params.walk_energy_cost;
	}

	protected final void run(int direction) {
		move(2, direction);
		energy -= Params.run_energy_cost;
	}

	// Direction is 0-7
	private final void move(int speed, int direction) {
		switch (direction) {
		case (0):
			x_coord = negModulo(x_coord + speed, Params.world_width);
			break;
		case (1):
			x_coord = negModulo(x_coord + speed, Params.world_width);
			y_coord = negModulo(y_coord - speed, Params.world_height);
			break;
		case (2):
			y_coord = negModulo(y_coord - speed, Params.world_height);
			break;
		case (3):
			x_coord = negModulo(x_coord - speed, Params.world_width);
			y_coord = negModulo(y_coord - speed, Params.world_height);
			break;
		case (4):
			x_coord = negModulo(x_coord - speed, Params.world_width);
			break;
		case (5):
			x_coord = negModulo(x_coord - speed, Params.world_width);
			y_coord = negModulo(y_coord + speed, Params.world_height);
			break;
		case (6):
			y_coord = negModulo(y_coord + speed, Params.world_height);
			break;
		case (7):
			x_coord = negModulo(x_coord + speed, Params.world_width);
			y_coord = negModulo(y_coord + speed, Params.world_height);
			break;
		}
	}

	protected final void reproduce(Critter offspring, int direction) {
		// Can only reproduce if energy is higher than minimum reproduction energy
		if (energy < Params.min_reproduce_energy) {
			return;
		}
		offspring.energy = energy / 2;
		energy /= 2;
		switch (direction) {
		case (0):
			offspring.x_coord = negModulo(x_coord + 1, Params.world_width);
			offspring.y_coord = y_coord % Params.world_height;
			break;
		case (1):
			offspring.x_coord = negModulo(x_coord + 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord - 1, Params.world_height);
			break;
		case (2):
			offspring.x_coord = (x_coord) % Params.world_width;
			offspring.y_coord = negModulo(y_coord - 1, Params.world_height);
			break;
		case (3):
			offspring.x_coord = negModulo(x_coord - 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord - 1, Params.world_height);
			break;
		case (4):
			offspring.x_coord = negModulo(x_coord - 1, Params.world_width);
			offspring.y_coord = y_coord % Params.world_height;
			break;
		case (5):
			offspring.x_coord = negModulo(x_coord - 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord + 1, Params.world_height);
			break;
		case (6):
			offspring.x_coord = x_coord % Params.world_width;
			offspring.y_coord = negModulo(y_coord + 1, Params.world_height);
			break;
		case (7):
			offspring.x_coord = negModulo(x_coord + 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord + 1, Params.world_height);
			break;
		}
		babies.add(offspring);
	}

	private int negModulo(int div, int mod) {
		while (div < 0) {
			div += mod;
		}
		return div % mod;
	}

	/**
	 * Contains an individual Critter's actions during one world step
	 */
	public abstract void doTimeStep();

	/**
	 * Contains how an individual Critter fights when faced with an encounter
	 * 
	 * @param oponent
	 *            the string representation of the Critter's fighting opponent
	 * @return true if Critter is willing to fight; false if Critter is not
	 */
	public abstract boolean fight(String oponent);

	/**
	 * create and initialize a Critter subclass. critter_class_name must be the
	 * unqualified name of a concrete subclass of Critter, if not, an
	 * InvalidCritterException must be thrown. (Java weirdness: Exception throwing
	 * does not work properly if the parameter has lower-case instead of upper. For
	 * example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * 
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class<?> critter_class = Class.forName(myPackage + '.' + critter_class_name);
			Critter c = (Critter) critter_class.newInstance();

			// Randomly placing the critter in the world
			c.x_coord = getRandomInt(Params.world_width);
			c.y_coord = getRandomInt(Params.world_height);
			c.energy = Params.start_energy;

			population.add(c);
		} catch (IllegalAccessException | InstantiationException | ClassNotFoundException | ClassCastException e) {
			throw (new InvalidCritterException(critter_class_name));
		}
	}

	/**
	 * Gets a list of critters of a specific type.
	 * 
	 * @param critter_class_name
	 *            What kind of Critter is to be listed. Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class<?> critter_class = Class.forName(myPackage + '.' + critter_class_name);
			Critter getC = (Critter) critter_class.newInstance();
			for (Critter c : population) {
				// TODO Fix for Critter
				if (c.toString().equals(getC.toString())) {
					result.add(c);
				}
			}
		} catch (IllegalAccessException | InstantiationException | ClassNotFoundException | ClassCastException e) {
			throw (new InvalidCritterException(critter_class_name));
		}
		return result;
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * 
	 * @param critters
	 *            List of Critters.
	 */
	public static String runStats(List<Critter> critters) {
		String ret = "";
		ret += "" + critters.size() + " critters as follows -- ";
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string, 1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			ret += (prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		ret += '\n';
		//System.out.print(ret);
		return ret;
	}

	/*
	 * the TestCritter class allows some critters to "cheat". If you want to create
	 * tests of your Critter model, you can create subclasses of this class and then
	 * use the setter functions contained here.
	 * 
	 * NOTE: you must make sure that the setter functions work with your
	 * implementation of Critter. That means, if you're recording the positions of
	 * your critters using some sort of external grid or some other data structure
	 * in addition to the x_coord and y_coord functions, then you MUST update these
	 * setter functions so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		/**
		 * Set Critter energy level
		 * 
		 * @param new_energy_value
		 */
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		/**
		 * Sets Critter's x location in the Critter world
		 * 
		 * @param new_x_coord
		 */
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		/**
		 * Sets Critter's y location in the Critter world
		 * 
		 * @param new_y_coord
		 */
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		/**
		 * Gets Critter's x location
		 * 
		 * @return Critter's x coordinate
		 */
		protected int getX_coord() {
			return super.x_coord;
		}

		/**
		 * Gets Critter's y location
		 * 
		 * @return Critter's y coordinate
		 */
		protected int getY_coord() {
			return super.y_coord;
		}

		/*
		 * This method getPopulation has to be modified by you if you are not using the
		 * population ArrayList that has been provided in the starter code. In any case,
		 * it has to be implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the
		 * babies ArrayList that has been provided in the starter code. In any case, it
		 * has to be implemented for grading tests to work. Babies should be added to
		 * the general population at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}

	/**
	 * Progresses the entire Critter World by one time step in the following order
	 * *Invoke every critter's doTimeStep() *Resolve encounters *Update energy
	 * (subtract rest energy) *Generate algae *Move any babies born during the time
	 * step to the general Critter population
	 */
	public static void worldTimeStep() {

		// DoTimeSteps for photosynthesis
		for (Critter c : population) {
			c.doTimeStep();
		}

		// Encounters
		// Checks each member of population against every other member of population not
		// yet checked
		// Determine if both Critters are at the same position
		for (int i = 0; i < population.size(); ++i) {
			Critter refC = population.get(i);
			if (refC.energy <= 0) {
				continue;
			}
			for (int k = i + 1; k < population.size(); ++k) {
				Critter othC = population.get(k);
				if (othC.energy <= 0) {
					continue;
				}
				// If Critters occupy the same location, resolve encounter
				if (refC.x_coord == othC.x_coord && refC.y_coord == othC.y_coord) {
					Critter winner = critterEncounter(refC, othC);
					if (winner == null) {
						continue;
					} else if (winner.equals(refC)) {
						--k; // Account for changes in indexing once Critter is removed
					} else if (winner.equals(othC)) {
						--i; // Account for changes in indexing once Critter is removed
						break; // Since refC no longer exists, move onto next Critter
					}
				}
			}
		}
		// Update rest energy
		// Iterator Implementation
		Iterator<Critter> critIter = population.iterator();
		while (critIter.hasNext()) {
			Critter c = critIter.next();
			c.energy -= Params.rest_energy_cost;
			if (c.energy <= 0) {
				critIter.remove();
			}
		}

		// Refresh Algae count
		for (int i = 0; i < Params.refresh_algae_count; ++i) {
			try {
				makeCritter("Algae");
			} catch (InvalidCritterException e) {
				// Should never happen
			}
		}

		// Babies turn into critters
		// Iterator implementation
		Iterator<Critter> babyIter = babies.iterator();
		while (babyIter.hasNext()) {
			Critter c = babyIter.next();
			population.add(c);
			babyIter.remove();
		}
	}

	/**
	 * Helper function to deal with the Encounter of two critters on the same space
	 * (Moved mostly cause this is a pretty long section)
	 * 
	 * @param refC
	 *            First Critter (Reference Critter)
	 * @param othC
	 *            Second Critter (Other Critter)
	 */
	private static Critter critterEncounter(Critter refC, Critter othC) {

		// Save current location in case Critters attempt to run to invalid locations
		int oldX = refC.x_coord;
		int oldY = refC.y_coord;

		// Give both Critters one chance to run away if desired; if destination is
		// occupied = cannot run, must fight
		// First Critter choosing to fight
		boolean refCFight = refC.fight(othC.toString());
		if (refCFight == false) {
			if (locOccupied(refC.x_coord, refC.y_coord, refC)) {
				// Invalid location: return position to original position, and force it to fight
				refC.x_coord = oldX;
				refC.y_coord = oldY;
			}
		}

		// Second Critter choosing to fight
		boolean othCFight = othC.fight(refC.toString());
		if (othCFight == false) {
			if (locOccupied(othC.x_coord, othC.y_coord, othC)) {
				// Invalid location: return position to original position, and force it to fight
				othC.x_coord = oldX;
				othC.y_coord = oldY;
			}
		}

		// Remove from population if Critters had used up all energy to run
		if (refC.energy <= 0 || othC.energy <= 0) {
			if (refC.energy < 0) {
				population.remove(refC);
				return othC;
			}
			if (othC.energy < 0) {
				population.remove(othC);
				return refC;
			}
		}

		// If both Critters are still in the same location despite having the chance to
		// run away
		if (refC.x_coord == othC.x_coord && refC.y_coord == othC.y_coord) {

			Critter winner;
			Critter loser;

			// The Critter that tried to run away loses
			if (refCFight && !othCFight) {
				winner = refC;
				loser = othC;

			} else if (othCFight && !refCFight) {
				winner = othC;
				loser = refC;

			}

			// If both tried to run away but could not, or both wanted to fight
			else {

				int refCRoll = getRandomInt(refC.energy);
				int othCRoll = getRandomInt(othC.energy);

				// Check if Algae is one of the two critters; Critter will always win over Algae

				if (refC instanceof Algae) {
					winner = othC;
					loser = refC;
				} else if (othC instanceof Algae) {
					winner = refC;
					loser = othC;
				}

				// Critters Fight
				if (refCRoll >= othCRoll) {
					winner = refC;
					loser = othC;
				} else {
					winner = othC;
					loser = refC;
				}
			}
			winner.energy += loser.energy / 2; // Winner gets half of loser's energy
			population.remove(loser); // Loser dies
			return winner;
		}
		return null;
	}

	/**
	 * Method to allow Critters to see other Critters in the world and make choices
	 * based on sight
	 * 
	 * @param direction The direction to look at relative to the current x and y position
	 * @param willRun Basically determines how far to look: 1 or 2 spaces around
	 * @return Returns the first critter that is in the spot that is being looked at
	 */
	protected final String look(int direction, boolean willRun) {
		energy -= Params.look_energy_cost;
		int chkX = x_coord;
		int chkY = y_coord;
		int speed = 1;
		if (willRun) {
			speed = 2;
		}
		switch (direction) {
		case (0):
			chkX = negModulo(x_coord + speed, Params.world_width);
			break;
		case (1):
			chkX = negModulo(x_coord + speed, Params.world_width);
			chkY = negModulo(y_coord - speed, Params.world_height);
			break;
		case (2):
			chkY = negModulo(y_coord - speed, Params.world_height);
			break;
		case (3):
			chkX = negModulo(x_coord - speed, Params.world_width);
			chkY = negModulo(y_coord - speed, Params.world_height);
			break;
		case (4):
			chkX = negModulo(x_coord - speed, Params.world_width);
			break;
		case (5):
			chkX = negModulo(x_coord - speed, Params.world_width);
			chkY = negModulo(y_coord + speed, Params.world_height);
			break;
		case (6):
			chkY = negModulo(y_coord + speed, Params.world_height);
			break;
		case (7):
			chkX = negModulo(x_coord + speed, Params.world_width);
			chkY = negModulo(y_coord + speed, Params.world_height);
			break;
		}
		for (Critter c : population) {
			if ((c.x_coord == chkX) && (c.y_coord == chkY)) {
				return c.toString();
			}
		}
		return null;
	}

	/**
	 * Helper function to determine if location is occupied by a critter
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return true if location is occupied; false if not
	 */
	private static boolean locOccupied(int x, int y, Critter me) {
		for (Critter c : population) {
			if (c.equals(me)) {
				continue;
			}
			if ((c.x_coord == x) && (c.y_coord == y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Basically a way for the model to push it's values onto the CritterWorldView
	 * 
	 * @param world
	 *            The view to utilize to display the critters in the population
	 */
	public static void displayWorld(CritterWorldView world) {
		world.redrawGrid();
		double x;
		double y;

		for (Critter c : population) {
			x = world.convertColToY(c.x_coord);
			y = world.convertRowToX(c.y_coord);
			world.paintCritter(c, x, y);
		}
	}

	/**
	 * Makes an observable list of critters in the package for the drop down menu
	 * 
	 * @return A List of Strings to be used in the ChoiceBox
	 */
	public static ObservableList<String> makeCritterList() {
		File f = new File(pkgLoc);
		String[] subFiles = f.list();
		List<String> findClasses = new ArrayList<String>();
		List<String> findCritters = new ArrayList<String>();
		for (String s : subFiles) {
			if (s.endsWith(".class")) {
				findClasses.add(s);
			}
		}
		for (String s : findClasses) {
			String critName = s.substring(0, s.indexOf(".class"));
			try {
				Class<?> check_class = Class.forName(myPackage + '.' + critName);
				int mod = check_class.getModifiers();
				if (Modifier.isAbstract(mod) || Modifier.isPrivate(mod)) {
					continue;
				}
				Class<?> critter_class = Critter.class;
				if (critter_class.isAssignableFrom(check_class)) {
					findCritters.add(critName);
				}
			} catch (ClassNotFoundException e) {
				continue;
			}
		}
		return FXCollections.observableArrayList(findCritters);
	}
}
