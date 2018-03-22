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

import java.util.Iterator;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

/**
 * Critter class is the base abstract class
 * It holds the basic functionality of all Critters in the Critter world
 * It also contains the entire Critter world population
 */
public abstract class Critter {
	private static String myPackage;
	private static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name. This assumes that Critter and its subclasses are all
	// in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static java.util.Random rand = new java.util.Random();

	/**
	 * Returns a random integer
	 * @param max maximum amount the integer can be
	 * @return random integer between 0 and max
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	/**
	 * Sets the seed for the random integer
	 * Will allow for more predictable testing
	 * @param new_seed value of seed
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
//		if (x_coord == 0 || y_coord == 0) {
//			System.out.println("Flag");
//		}
		//System.out.println("OX_coord: " + x_coord);
		//System.out.println("OY_coord: " + y_coord);
		switch (direction) {
		case (0):
			x_coord = negModulo(x_coord + speed, Params.world_width);
			break;
		case (1):
			x_coord = negModulo(x_coord + speed, Params.world_width);
			y_coord = negModulo(y_coord + speed, Params.world_height);
			break;
		case (2):
			y_coord = negModulo(y_coord + speed, Params.world_height);
			break;
		case (3):
			x_coord = negModulo(x_coord - speed, Params.world_width);
			y_coord = negModulo(y_coord + speed, Params.world_height);
			break;
		case (4):
			x_coord = negModulo(x_coord - speed, Params.world_width);
			break;
		case (5):
			x_coord = negModulo(x_coord - speed, Params.world_width);
			y_coord = negModulo(y_coord - speed, Params.world_height);
			break;
		case (6):
			y_coord = negModulo(y_coord - speed, Params.world_height);
			break;
		case (7):
			x_coord = negModulo(x_coord + speed, Params.world_width);
			y_coord = negModulo(y_coord - speed, Params.world_height);
			break;
		}
//		if (x_coord < 0 || y_coord < 0) {
//			System.out.println("Error");
//		}
		//System.out.println("X_coord: " + x_coord);
		//System.out.println("Y_coord: " + y_coord);
	}

	protected final void reproduce(Critter offspring, int direction) {
		// Can only reproduce if energy is higher than minimum reproduction energy
		if(energy < Params.min_reproduce_energy) {
			return;
		}
		offspring.energy = energy / 2;
		energy /= 2;
		//energy -= Params.min_reproduce_energy;
		switch (direction) {
		case (0):
			offspring.x_coord = negModulo(x_coord + 1, Params.world_width);

			offspring.x_coord = negModulo(x_coord + 1, Params.world_width);
			offspring.x_coord = negModulo(x_coord - 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord + 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord - 1, Params.world_width);

			offspring.y_coord = y_coord % Params.world_height;
			break;
		case (1):
			offspring.x_coord = negModulo(x_coord + 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord + 1, Params.world_width);
			break;
		case (2):
			offspring.x_coord = (x_coord) % Params.world_width;
			offspring.y_coord = negModulo(y_coord + 1, Params.world_width);
			break;
		case (3):
			offspring.x_coord = negModulo(x_coord - 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord + 1, Params.world_width);
			break;
		case (4):
			offspring.x_coord = negModulo(x_coord - 1, Params.world_width);
			offspring.y_coord = y_coord % Params.world_height;
			break;
		case (5):
			offspring.x_coord = negModulo(x_coord - 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord - 1, Params.world_width);
			break;
		case (6):
			offspring.x_coord = x_coord % Params.world_width;
			offspring.y_coord = negModulo(y_coord - 1, Params.world_width);
			break;
		case (7):
			offspring.x_coord = negModulo(x_coord + 1, Params.world_width);
			offspring.y_coord = negModulo(y_coord - 1, Params.world_width);
			break;
		}
		babies.add(offspring);
	}

	private int negModulo(int div, int mod) {
		if (div < 0) {
			return mod + div;
		} else {
			return div % mod;
		}
	}

	/**
	 * Contains an individual Critter's actions during one world step
	 */
	public abstract void doTimeStep();

	/**
	 * Contains how an individual Critter fights when faced with an encounter
	 * @param oponent the string representation of the Critter's fighting opponent
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
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
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
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
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
		 * @param new_energy_value
		 */
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		/**
		 * Sets Critter's x location in the Critter world
		 * @param new_x_coord
		 */
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		/**
		 * Sets Critter's y location in the Critter world
		 * @param new_y_coord
		 */
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		/**
		 * Gets Critter's x location
		 * @return Critter's x coordinate
		 */
		protected int getX_coord() {
			return super.x_coord;
		}

		/**
		 * Gets Critter's y location
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
	 * *Invoke every critter's doTimeStep()
	 * *Resolve encounters
	 * *Update energy (subtract rest energy)
	 * *Generate algae
	 * *Move any babies born during the time step to the general Critter population
	 */
	public static void worldTimeStep() {
		
		// DoTimeSteps
		for (Critter c : population) {
			c.doTimeStep();
			if (c.toString().equals("@")) {
				c.energy += Params.photosynthesis_energy_amount;
			}
		}

		// Encounters
		// Checks each member of population against every other member of population not yet checked
		// Determine if both Critters are at the same position
		for (int i = 0; i < population.size(); ++i) {
			Critter refC = population.get(i);
			for (int k = i + 1; k < population.size(); ++k) {
				Critter othC = population.get(k);
				// If Critters occupy the same location, resolve encounter
				if (refC.x_coord == othC.x_coord && refC.y_coord == othC.y_coord) {
					// Save current location in case Critters attempt to run to invalid locations
					int oldX = refC.x_coord;
					int oldY = refC.y_coord;
					
					// Give both Critters one chance to run away if desired; if destination is occupied = cannot run, must fight
					// First Critter
					boolean refCFight = refC.fight(othC.toString());
					if (refCFight == false) {
						if (locOccupied(refC.x_coord, refC.y_coord, refC)) {
							// Invalid location: return position to original position, and force it to fight
							refC.x_coord = oldX;
							refC.y_coord = oldY;
							System.out.println("Here");
						}
					}

					// Second Critter
					boolean othCFight = othC.fight(refC.toString());
					if (othCFight == false) {
						if (locOccupied(othC.x_coord, othC.y_coord, othC)) {
							// Invalid location: return position to original position, and force it to fight
							othC.x_coord = oldX;
							othC.y_coord = oldY;
							System.out.println("Here");
						}
					}

					// Remove from population if Critters had used up all energy to run
					if (refC.energy <= 0 || othC.energy <= 0) {
						if (refC.energy < 0) {
							population.remove(refC);
						}
						if (othC.energy < 0) {
							population.remove(othC);
						}
						continue;
					}

					// TODO: Review this change in implementation
					// If both Critters are still in the same location despite having the chance to run away
					if(refC.x_coord == othC.x_coord && refC.y_coord == othC.y_coord) {
						
						Critter winner;
						Critter loser;
						
						// The Critter that tried to run away loses
						if(refCFight && !othCFight) {
							winner = refC;
							loser = othC;
						}
						else if(othCFight && !refCFight) {
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

							// Non-Algae critter fight
							else if (refCRoll >= othCRoll) {
								winner = refC;
								loser = othC;
							} else {
								winner = othC;
								loser = refC;
							}
							
							winner.energy += loser.energy / 2;	// Winner gets half of loser's energy
							population.remove(loser);			// Loser dies
						}				
					}

				}
			}
		}

		// Update rest energy
		// Iterator Implementation:
		Iterator<Critter> critIter = population.iterator();
		while (critIter.hasNext()) {
			Critter c = critIter.next();
			c.energy -= Params.rest_energy_cost;
			if (c.energy <= 0) {
				critIter.remove();
			}
		}

		// for (Critter c : population) {
		// c.energy -= Params.rest_energy_cost;
		// if (c.energy <= 0) {
		// population.remove(c);
		// }
		// }
		
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

		// for (Critter b : babies) {
		// population.add(b);
		// babies.remove(b);
		// }
	}

	/**
	 * Helper function to determine if location is occupied by a critter
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return true if location is occupied; false if not
	 */
	private static boolean locOccupied(int x, int y, Critter me) {
		for (Critter c : population) {
			if(c.equals(me)) {
				continue;
			}
			if ((c.x_coord == x) && (c.y_coord == y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Prints out entire Critter world to the console
	 */
	public static void displayWorld() {
		int height = Params.world_height + 2;
		int width = Params.world_width + 2;
		char[][] worldArray = new char[height][width];

		String worldOut = "+";
		for (int i = 0; i < Params.world_width; ++i) {
			worldOut += "-";
		}
		worldOut += "+";
		worldArray[0] = worldOut.toCharArray();

		for (int i = 0; i < Params.world_height; ++i) {
			worldOut = "|";
			for (int k = 0; k < Params.world_width; ++k) {
				worldOut += " ";
			}
			worldOut += "|";
			worldArray[i + 1] = worldOut.toCharArray();
		}

		worldOut = "+";
		for (int i = 0; i < Params.world_width; ++i) {
			worldOut += "-";
		}
		worldOut += "+";
		worldArray[height - 1] = worldOut.toCharArray();

		for (Critter c : population) {
			// Assuming to String is char and also coordinates are within world bounds
			try {
				worldArray[c.y_coord + 1][c.x_coord + 1] = c.toString().charAt(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Y_coord: " + c.y_coord);
				System.out.println("X_coord: " + c.x_coord);
				throw e;
			}
		}
		for (int i = 0; i < height; ++i) {
			String row = new String(worldArray[i]);
			System.out.println(row);
		}
//		String rowConcat = "";
//		for (int i = 0; i < height; ++i) {
//			String row = new String(worldArray[i]);
//			rowConcat += row + '\n';
//		}
//		System.out.print(rowConcat);
	}
}
