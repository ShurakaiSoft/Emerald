package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple text user interface. It can be run from the command line or
 * interactively. When run from the command line it accepts and processes input
 * from command line options. If no options are given it will start an
 * interactive session. This session can be terminated with the "-q" flag.
 * 
 * @author Steve
 * 
 */
public class TextUI {
	private final Controller controller;
	private final Results results;
	private boolean quit;

	
	/**
	 * Private constructor
	 */
	private TextUI() {
		controller = new Controller();
		results = new Results(new Solution("", new ArrayList<String>(0)));
		quit = false;
	}
	
	
	/**
	 * Main application entry point. Command line arguments are optional. If no
	 * command line options are given, it will start an interactive session.<p>
	 * 
	 * Using "-q" will terminate the interactive session.<p>
	 * 
	 * @param args from the command line. 
	 */
	public static void main(String[] args) {
		TextUI app = new TextUI();
		
		if (args.length > 0) {
			app.processCommandSet(args);
		} else {
			app.interactive();
		}
	}
	

	/**
	 * Repeatedly prompts for a set of commands from the user. It will process
	 * these commands and display the results. It also checks for a termination
	 * condition.
	 */
	private void interactive() {
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.print("Enter Command: ");
			try {
				processCommandSet(tokenizeInput(keyboard.readLine()));
				if (quit == true) {
					System.out.println("Terminated at users request.");
					System.exit(0);
				}
			} catch (IOException e) {
				System.err.println("An IO Error Occured. " + e.getMessage());
				System.exit(1);
			}
		}		
	}
	
	
	/**
	 * Tokenize the input string using white space as the separator.
	 * 
	 * @param input the String to be tokenized.
	 * @return a set of tokens.
	 */
	private static String[] tokenizeInput(String input) {
		ArrayList<String> tokens = new ArrayList<String>(8);
		
		Scanner inputScanner = new Scanner(input);
		while (inputScanner.hasNext()) {
			tokens.add(inputScanner.next());
		}
		return tokens.toArray(new String[tokens.size()]);
	}
	
	
	/**
	 * Given a set of String arguments representing commands, it will process
	 * each command and display the results, if any.
	 * 
	 * Flags and parameters always come before a set of letters. Any flags or
	 * options after a set of letters will be ignored.
	 * 
	 * @param commands the set of commands to process.
	 * 
	 */
	private void processCommandSet(String[] commands) {
		if (commands.length == 0) {
			printHelp();
			return;
		}
		for (int i = 0; i < commands.length; i++) {
			if (commands[i].equals("-f") == true) {
				if (++i < commands.length) {
					processFilterFlag(commands[i]);
				} else {
					System.out.println("Missing argument to -f flag");
					printHelp();
				}
			} else if (commands[i].equals("-h") == true) {
				printHelp();
			} else if (commands[i].equals("-n") == true) {
				if (++i < commands.length) {
					processDisplayFlag(commands[i]);
				} else {
					System.out.println("Missing argument to -n flag");
					printHelp();
					return;
				}
			} else if (commands[i].equals("-q") == true) {
				quit = false;
				return;
			} else if (commands[i].equals("-r") == true) {
				displayWords();
			} else if (commands[i].equals("-s") == true) {
				if (++i < commands.length) {
					processSortFlag(commands[i]);
				} else {
					System.out.println("Missing argument to -s flag");
					printHelp();
				}
			} else if (commands[i].charAt(0) != '-') {
				String input = Dictionary.sort(commands[i]);
				if (Dictionary.hasVowels(input) == false) {
					System.out.println("No Vowel! Valid words must hava at least one vowel.");
					System.out.println("Add a vowel and try again.");
					return;
				}
				getWords(input);
				return;
			} else {
				System.out.format("Invalid command or option: %s%n", commands[i]);
				printHelp();
				return;
			}
		}
	}
	
	
	/**
	 * Given a set of letters as a String, it will display to standard out a
	 * set of words that conform with the requested filter, sort method and
	 * word count size.<p>
	 * 
	 * This method prints a string of "progress dots" to standard out while it
	 * is processing the request.
	 * 
	 * @param input the set of letters.
	 */
	private void getWords(String input) {
		System.out.print("Searching.");
		Solution solution = null;
		while ((solution = controller.getAnswer(input)) == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// XXX don't know what to do here...
			}
			System.out.print(".");	// progress dots
		}
		System.out.println();		// new line for progress dots
		results.setSolution(solution);
		displayWords();
	}
	
	
	/**
	 * Display to standard out the most recently returned set of results, 
	 * formatted with the current filter, sort method and word count size.
	 */
	private void displayWords() {
		List<String> words = results.getSortedResults();
		if (words.size() == 0) {
			System.out.println("NO results to display.");
		} else {
			for (String word : words) {
				System.out.println(word);
			}
		}
	}
	
	
	/**
	 * Adds a filter to the results. Only words that match the filter are
	 * displayed.<p>
	 * 
	 * <b>Filter Syntax</b><p>
	 * The filter format is zero or more '*'s followed by one 'char' followed
	 * by zero or more '*'s. The '*' represents an optional letter and the
	 * 'char' represents the location of the fixed board character. 
	 * 
	 * @param filter the board filter.
	 */
	private void processFilterFlag(String filter) {
		if (filter.equals("none") == true) {
			System.out.println("Removing board filter.");
			results.removeFilter();
			return;
		}
		char ch;
		int i = 0;
		while (i < filter.length()) {
			ch = Character.toLowerCase(filter.charAt(i));
			if (ch == '*') {
				i++;
			} else if(ch >= 'a' && ch <= 'z') {
				int trailing = (filter.length() - 1) - i;
				System.out.format("Added filter: %d%c%d%n", i, ch, trailing);
				results.addFilter(i,  ch, trailing);
				return;				
			} else {
				break;
			}
		}
		System.out.println("Board filter syntax error!");
		System.out.println("0 or more '*'s followed by 1 lower case character, followed by 0 or more stars.");
	}
	
	
	/**
	 * Sets the number of words to display. If the string "all" is given it 
	 * will display all results. Otherwise it will convert the given input into
	 * a number and use it to determine the number of words to display.<p>
	 * 
	 * If there are any errors in the conversion process, an error message is
	 * printed to standard out.
	 * 
	 * @param size number of words to display.
	 */
	private void processDisplayFlag(String size) {
		if (size.equals("all") == true) {
			System.out.println("Now displaying all words.");
			results.setMaxResults(Results.ALL_WORDS);
			return;
		} 
		try {
			int number = Integer.valueOf(size);
			if (number > 0) {
				System.out.format("Now displaying %d results%n", number);
				results.setMaxResults(number);
			} else {
				System.out.format("Invalid number: %d. Can't display zero or less words.%n", number);
				printHelp();
			}
		} catch (NumberFormatException e) {
			System.out.format("Invalid number: %s%n", size);
			printHelp();
		}
	}
	
	
	/**
	 * Sets the sorting method for use when displaying the set of words.<p>
	 * 
	 * <b>Usage</b><p>
	 * {@code -s <sort_type> }<p>
	 * 
	 * <b>sort_type</b><p>
	 * <i>len</i> - sorted by length in descending order.<br>
	 * <i>alpha</i> - sorted alphabetically in ascending order.<br>
	 * 
	 * @param method command to sorting flag
	 */
	private void processSortFlag(String method) {
		if (method.equals("len") == true) {
			System.out.println("Sorting results by length in descending order");
			results.sortByWordLengthDesc();
		} else if (method.equals("alpha") == true) {	
			System.out.println("Sorting results alphapetically");
			results.sortByDefault();
		} else {
			System.out.format("Invalid sorting option: %s%n", method);
			printHelp();
		}
	}
	
	
	/**
	 * Print out a help message detailing the usage of command line options.
	 */
	private void printHelp() {
		System.out.println("Flag  Param    Details");
		System.out.println(" -f   **x**    Add a filter. 'x' is the board letter.");
		System.out.println("               Can have 0 or more stars before and after the board letter.");
		System.out.println(" -h            Print this message.");
		System.out.println(" -q            Quit interactive session.");
		System.out.println(" -n   xx       Display xx number of words.");
		System.out.println(" -n   all      Display all words.");
		System.out.println(" -r            Repeat last search.");
		System.out.println(" -s   len      Words are sorted by length.");
		System.out.println(" -s   alpha    Words are sorted alphapetically.");
		// System.out.println("");
	}

}
