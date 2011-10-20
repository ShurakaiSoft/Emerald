package core;

import java.util.Scanner;

/**
 * A simple interactive text user interface.
 * 
 * @author Steve
 *
 */
public class TextUI {
	Controller controller = new Controller();
	
	
	/**
	 * The main loop. To exit this loop, use the string "-p".
	 */
	public void run() {
		Scanner keyboard = new Scanner(System.in);
		String input;
		String[] answer;
		
		while (true) {
			System.out.print("Enter string: ");
			input = keyboard.next();
			if (input.equals("-q")) {
				return;
			}
			input = Unsolved.sort(input.toLowerCase());
			answer = controller.getAnswer(input);
			if (answer == null) {
				System.out.println("No Vowel! Valid words have at least one vowel.");
				System.out.println("Add a vowel and try again.");
			} else {
				displayResults(input, answer);
			}
		}		
	}

	
	/**
	 * Used for quick testing/debugging of output
	 */
	public void quickTest() {
		
	}

	
	/**
	 * Start an interactive application
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		TextUI textui = new TextUI();
		
		textui.run();
		System.out.println("Terminated at users request.");		
	}

	
	/**
	 * Display the results from a query.
	 * 
	 * @param input original letter set.
	 * @param words set of valid words.
	 */
	public static void displayResults(String input, String[] words) {
		System.out.format("There are %d results for %s%n", words.length, input);
		for (String word : words) {
			System.out.println(word);
		}
	}

}
