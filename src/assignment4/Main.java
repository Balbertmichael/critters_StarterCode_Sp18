package assignment4;

import java.util.List;
/* CRITTERS Main.java
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
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

	static Scanner kb; // scanner connected to keyboard input, or input file
	private static String inputFile; // input file, used instead of keyboard input if specified
	static ByteArrayOutputStream testOutputString; // if test specified, holds all console output
	private static String myPackage; // package of Critter file. Critter cannot be in default pkg.
	private static boolean DEBUG = false; // Use it or not, as you wish!
	static PrintStream old = System.out; // if you want to restore output to console

	// Gets the package name. The usage assumes that Critter and its subclasses are
	// all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            args can be empty. If not empty, provide two parameters -- the
	 *            first is a file name, and the second is test (for test output,
	 *            where all output to be directed to a String), or nothing.
	 */
	public static void main(String[] args) {
		if (args.length != 0) {
			try {
				inputFile = args[0];
				kb = new Scanner(new File(inputFile));
			} catch (FileNotFoundException e) {
				System.out.println("USAGE: java Main OR java Main <input file> <test output>");
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
			}
			if (args.length >= 2) {
				if (args[1].equals("test")) { // if the word "test" is the second argument to java
					// Create a stream to hold the output
					testOutputString = new ByteArrayOutputStream();
					PrintStream ps = new PrintStream(testOutputString);
					// Save the old System.out.
					old = System.out;
					// Tell Java to use the special stream; all console output will be redirected
					// here from now
					System.setOut(ps);
				}
			}
		} else { // if no arguments to main
			kb = new Scanner(System.in); // use keyboard and console
		}

		/* Do not alter the code above for your submission. */
		/* Write your code below. */
		
		// DEBUG: Prints results to an output file
		PrintStream outFile;
		try {
			kb = new Scanner(new File("AllStep60.txt"));
			outFile = new PrintStream(new File("output_all.txt"));
			System.setOut(outFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		String in;
		do {
//			System.out.println("Input next command: ");
			// Parse keyboard input
			in = kb.nextLine();
			String trimIn = in.trim();
			String[] inArr = trimIn.split("\\s+");
			switch (inArr[0].toLowerCase()) {

			// display critter world to console
			case ("show"):
				if (inArr.length > 1) {
					System.out.println("error processing: " + in);
					break;
				}
				Critter.displayWorld();
				break;
			
			// advance one time step in world
			case ("step"):
				int stepCount;
				if (inArr.length == 1) {
					stepCount = 1;
				} else {
					try {
						stepCount = Integer.parseInt(inArr[1]);
					} catch (NumberFormatException e) {
						System.out.println("error processing: " + in);
						break;
					}
				}
				for (int i = 0; i < stepCount; ++i) {
					Critter.worldTimeStep();
				}
				break;

			// set seed for random number generator
			case ("seed"):
				if (inArr.length != 2) {
					System.out.println("error processing: " + in);
					break;
				} else {
					try {
						Critter.setSeed(Long.parseLong(inArr[1]));
					} catch (NumberFormatException e) {
						System.out.println("error processing: " + in);
						break;
					}
				}
				break;
				
			// create a Critter and add it to population
			case ("make"):
				if (inArr.length != 3 && inArr.length != 2) {
					System.out.println("error processing: " + in);
					break;
				} else {
					try {
						int critNum = 1;
						if (inArr.length == 3) {
							critNum = Integer.parseInt(inArr[2]);
						}
						for (int i = 0; i < critNum; ++i) {
							Critter.makeCritter(inArr[1]);
						}
					} catch (InvalidCritterException | NumberFormatException e) {
						System.out.println("error processing: " + in);
					}
				}
				break;
				
			// run Critter statistics
			case ("stats"):
				// if(inArr.length != 1 && inArr.length != 2) {
				// System.out.println("error processing: " + in);
				// break;
				// }
				if (inArr.length != 2) {
					System.out.println("error processing: " + in);
					break;
				} else {
					try {
						List<Critter> critters = Critter.getInstances(inArr[1]);
						Class<?> critter_class = Class.forName(myPackage + '.' + inArr[1]);
						
						// Calls static runStats method on the abstract critter class
						// Critter c = (Critter) critter_class.newInstance();
						// c.runStats(critters);

						// Calls static runStats method on specific critter class
						critter_class.getMethod("runStats", List.class).invoke(null, critters);
					}
					// catch (InvalidCritterException | IllegalAccessException |
					// InstantiationException | ClassNotFoundException | ClassCastException |
					// IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					// | SecurityException e) {
					// System.out.println("error processing: " + in);
					// break;
					// }
					catch (InvalidCritterException | IllegalAccessException | ClassNotFoundException
							| ClassCastException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						System.out.println("error processing: " + in);
						break;
					}
				}
				break;
				
			// terminate program
			case ("quit"):
				break;
			
			// invalid command
			default:
				System.out.println("Invalid command: " + in);
			}
		} while (!in.equals("quit"));

		// System.out.println("GLHF");

		/* Write your code above */
		System.out.flush();

	}
}
