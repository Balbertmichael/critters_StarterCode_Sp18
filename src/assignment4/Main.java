package assignment4;
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
		
		try {
			for(int i = 0; i < 100; ++i) {
				Critter.makeCritter("Algae");
			}
			
			for(int i = 0; i < 25; ++i) {
				Critter.makeCritter("Craig");
			}
			
		} catch (InvalidCritterException e) {
			System.out.println("Can't find critter named: " + e);
		}
		
		String in;
		do {
			System.out.println("Input next command: ");
			in = kb.nextLine();
			in = in.toLowerCase();
			String[] inArr = in.split(" ");
			switch (inArr[0]) {

			case ("show"):
				if (inArr.length > 1) {
					System.out.println("Error processing " + in);
					break;
				}
				Critter.displayWorld();
				break;

			case ("step"):
				int stepCount;
				if(inArr.length == 1) {
					stepCount = 1;
				}
				else {
					try {
						stepCount = Integer.parseInt(inArr[1]);
					} catch (NumberFormatException e) {
						System.out.println("Error processing " + in);
						break;
					}
				}
				for (int i = 0; i < stepCount; ++i) {
					Critter.worldTimeStep();
				}
				break;

			case ("seed"):
				if(inArr.length != 2) {
					System.out.println("Error processing " + in);
					break;
				}
				else {
					try {
					Critter.setSeed(Long.parseLong(inArr[1]));
					}catch(NumberFormatException e) {
						System.out.println("Error processing " + in);
						break;
					}
				}
				break;
			case ("make"):
				break;
			case ("stats"):
				break;
			default:
				System.out.println("Invalid command: " + in);
			}
		} while (!in.equals("quit"));

		//System.out.println("GLHF");

		/* Write your code above */
		System.out.flush();

	}
}
