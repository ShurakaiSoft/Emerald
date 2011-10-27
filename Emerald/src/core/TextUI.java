package core;

import java.util.List;
import java.util.Scanner;


/**
 * A simple interactive text user interface.
 * 
 * @author Steve
 *
 */
public class TextUI {
	private Controller controller = new Controller();
	
	private String input = null;
	private List<String> words;
	private int rows = Integer.MAX_VALUE;		
	private boolean quit = false;
	
	/**
	 * The main loop.
	 */
	public void run() {
		Scanner keyboard = new Scanner(System.in);
		String input;
		
		while (quit == false) {
			System.out.print("Enter string: ");
			input = keyboard.next();
			processInput(input);
		}		
	}

	/**
	 * Sets the number of rows displayed to the integer value of the given
	 * String. If there are any errors from the conversion, a message will be
	 * displayed to standard out and no fields will be changed.
	 * 
	 * @param number number as a string.
	 */
	public void setRows(String stringNumber) {
		if (stringNumber.equals("all") == true) {
			rows = Integer.MAX_VALUE;
			return;
		}
		try {
			int number = Integer.valueOf(stringNumber);
			if (number > 0) {
				System.out.format("Now displaying %d results%n", number);
				rows = number;
			}
		} catch (NumberFormatException e) {
			System.out.format("Invalid number: %s%n", stringNumber);
		}
	}
	
	
	/**
	 * Display a set of valid words for the given input string.
	 * 
	 * @param input
	 */
	public void getWords(String input) {
		System.out.print("Searching.");
		this.input = input;
		input = Dictionary.sort(input.toLowerCase());
		if (Dictionary.hasVowels(input) == false) {
			System.out.println("No Vowel! Valid words have at least one vowel.");
			System.out.println("Add a vowel and try again.");				
		} else {
			words = null;
			while ((words = controller.getAnswer(input)) == null) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// wake up and continue
				}
				System.out.print(".");
			}
			System.out.println();
			displayWords();
		}
	}
	
	
	/**
	 * This method processes an input command and calls the relevant method
	 * that handles that type of command.
	 * 
	 * @param input input string to be processed.
	 */
	public void processInput(String input) {
		switch (input.charAt(0)) {
		case '-':
			switch(input.charAt(1)) {
			case 'h':
			case 'H':
				help();
				break;
			case 'l':
			case 'L':
				displayWords();
				break;
			case 's':
			case 'S':
				setRows(input.substring(2));
				break;
			case 'q':
			case 'Q':
				quit = true;
				break;
			default:
				System.out.format("Invalid command: %s%n", input);
				break;
			}
			break;
		default:
			getWords(input);
			break;
		}
	}

	
	/**
	 * Display the interactive command line message.
	 */
	public void help() {
		System.out.println("Command  Details");
		System.out.println(" -sxx    Set xx number of words to be displayd.");
		System.out.println(" -sall   Set all words to be displayed.");
		System.out.println(" -l      Display last results.");
		System.out.println(" -h      Display this message.");
		System.out.println(" -q      Quit."); 
		// System.out.println(""); 
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
	public void displayWords() {
		if (input == null || words == null) {
			System.out.println("No previous results to show.");
			return;
		}
		if (rows == Integer.MAX_VALUE) {
			System.out.format("There are %d results for %s.%n", words.size(), input);
		} else {
			System.out.format("There are %d results for %s. Showing top %d%n", words.size(), input, rows);
		}
		int i = 0;
		for (String word : words) {
			System.out.println(word);
			if (i++ == rows) {
				break;
			}
		}
	}

}
