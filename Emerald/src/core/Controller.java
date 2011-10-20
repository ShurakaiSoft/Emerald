package core;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * This class ties all subsystems together<p>
 * 
 * Receives requests and provides answers.<p>
 * 
 * This version is more or less a stub. It only handles one request at a time.
 * This needs expanding to be able to handle multiple requests, 
 * asynchronously. <p>
 * 
 * @author Steve
 *
 */
public class Controller {
	final static char[] VOWEL_LIST = { 'a', 'e', 'i', 'o', 'u', 'y' };
	final static String DICTIONARY_FILENAME = "dictionary.txt";
	
	private Cache cache;
	private Solver solver;
	private String dictionaryName;
	
	
	/**
	 * Constructs a controller with a default dictionary.
	 */
	public Controller() {
		this(DICTIONARY_FILENAME);
	}


	/**
	 * Constructs a controller with a dictionary created from the given
	 * dictionaryName.
	 * 
	 * @param dictionaryName name of the dictionary file.
	 */
	public Controller(String dictionaryName) {
		this.dictionaryName = dictionaryName;
		cache = new Cache();
		try {
			solver = new Solver(this.dictionaryName);
		} catch (FileNotFoundException e) {
			System.out.println("Missing dictionary file: " + this.dictionaryName);
			System.exit(-1);
		}
	}
	
	
	/**
	 * Given a set of letters, it will return a set of valid words that can be
	 * created from those letters or any substring that can be created from
	 * that set of letters.
	 * 
	 * @param letterSet set of letters
	 * @return set of valid words
	 */
	public String[] getAnswer(String letterSet) {		
		// short circuit fail
		if (hasVowels(letterSet) == false) {
			return null;
		}
		
		// check cache, if it already exists return answer.
		String[] answer = cache.getAnswer(letterSet); 
		if (answer != null) {
			return answer;
		}
		
		// not in cache, so find the answer and add to cache.
		Solution solution = solver.getSolution(letterSet);
		cache.addSolution(solution);
		
		return solution.getWordSet();
	}

	
	/**
	 * Search string for vowels. If a vowel is present it will return true.
	 * NOTE: vowels include 'a', 'e', 'i', 'o', 'u' AND 'y'. and can be upper
	 * case or lower case.
	 * 
	 * @param letterSet to be searched
	 * @return true if a vowel is present.
	 */
	public static boolean hasVowels(String letterSet) {
		char[] data = letterSet.toLowerCase().toCharArray();
		Arrays.sort(data);

		for (char ch : VOWEL_LIST) {
			if (Arrays.binarySearch(data, ch) >= 0) {
				return true;
			}
		}
		return false;
	}
	
}
