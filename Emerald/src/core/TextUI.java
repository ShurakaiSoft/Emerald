package core;

import java.util.ArrayList;
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
	private Results results = new Results(new Solution("", new ArrayList<String>(0)));
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
	 * Sets the number of rows displayed to the integer value of the given
	 * String. If there are any errors from the conversion, a message will be
	 * displayed to standard out and no fields will be changed.
	 * 
	 * @param number number as a string.
	 */
	private void setRowNumber(String stringNumber) {
		if (stringNumber.equals("all") == true) {
			results.setMaxResults(Results.ALL_WORDS);
			return;
		}
		try {
			int number = Integer.valueOf(stringNumber);
			if (number > 0) {
				System.out.format("Now displaying %d results%n", number);
				results.setMaxResults(number);
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
	private void getWords(String input) {
		System.out.print("Searching.");
		input = Dictionary.sort(input.toLowerCase());
		if (Dictionary.hasVowels(input) == false) {
			System.out.println("No Vowel! Valid words have at least one vowel.");
			System.out.println("Add a vowel and try again.");				
		} else {
			Solution solution = null;
			while ((solution = controller.getAnswer(input)) == null) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// wake up and continue
				}
				System.out.print(".");
			}
			results.setSolution(solution);
			System.out.println();
			displayWords();
		}
	}
	
	
	/**
	 * sort words based on length
	 */
	private void sortBy(String input) {
		switch (input.charAt(0)) {
		case 'a':
		case 'A':
			results.sortByDefault();
			System.out.println("Sorting by default method.");
			displayWords();
			break;
		case 'l':
			results.sortByWordLengthAsc();
			System.out.println("Sorting by ascending length.");
			displayWords();			
			break;
		case 'L':
			results.sortByWordLengthDesc();
			System.out.println("Sorting by descending length.");
			displayWords();
			break;
		default:
			System.out.format("Invalid sort option: %s", input);
			help();
		break;
		}
	}
	
	
	/**
	 * This method processes an input command and calls the relevant method
	 * that handles that type of command.
	 * 
	 * @param input input string to be processed.
	 */
	private void processInput(String input) {
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
			case 'n':
			case 'N':
				setRowNumber(input.substring(2));
				break;
			case 'q':
			case 'Q':
				quit = true;
				break;
			case 's':
			case 'S':
				sortBy(input.substring(2));
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
	private void help() {
		System.out.println("Command  Details");
		System.out.println(" -h      Display this message.");
		System.out.println(" -l      Display last results.");
		System.out.println(" -nxxx   Set xx number of words to be displayd.");
		System.out.println(" -nall   Set all words to be displayed.");
		System.out.println(" -q      Quit.");
		System.out.println(" -sl     Sort by word length, shortest frist.");
		System.out.println(" -sL     Sort by word length, longest first.");
		System.out.println(" -sa     Sort by alphabetical order.");
		System.out.println(" -sz     Sort by reverse alphabetical order.");		
		// System.out.println(""); 
	}
	
	
	/**
	 * Display the results from a query.
	 * 
	 * @param input original letter set.
	 * @param words set of valid words.
	 */
	private void displayWords() {
		List<String> words = results.getSortedResults();
		if (words.size() == 0) {
			System.out.println("No results to display.");
		} else {
			for (String word : results.getSortedResults()) {
				System.out.println(word);
			}
		}
	}

}
