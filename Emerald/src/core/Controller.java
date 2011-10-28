package core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class ties all other classes together<p>
 * 
 * It receives sets of letters and provides sets of words. <p>
 *
 * It now handles requests asynchronously through the getAnswer() method.
 *
 * @author Steve
 *
 */
public class Controller {
	final static String DICTIONARY_FILENAME = "dictionary.txt";
	
	private Cache<String, Solution> cache;
	private Solver solver;
	private String dictionaryName;
	private String currentProblem;
	
	
	/**
	 * Constructs a controller with a default dictionary.
	 */
	public Controller() {
		this(DICTIONARY_FILENAME);
		currentProblem = null;
	}


	/**
	 * Constructs a controller with a dictionary created from the given
	 * dictionaryName.
	 * 
	 * @param dictionaryName name of the dictionary file.
	 */
	public Controller(String dictionaryName) {
		try {
			this.dictionaryName = dictionaryName;
			cache = new Cache<String, Solution>(4096);
			solver = new Solver(this.cache, this.dictionaryName);
		} catch (FileNotFoundException e) {
			System.out.println("Missing dictionary file: " + this.dictionaryName);
			System.exit(-1);
		}
	}
	
	
	/**
	 * Given a set of letters, it will return a set of valid words that can be
	 * created from those letters or any subset of those letters. Verification
	 * is performed with the dictionary provided to the constructor of this 
	 * class. This method is asynchronous. If the return value is not
	 * immediately available, it will return null while a background thread
	 * generates the return result. Future calls may succeed.<p> 
	 * 
	 * NOTE: Currently there is no way to find out how long it will take to 
	 * generate the return value, or even be notified when the return value is 
	 * ready.<p> 
	 * 
	 * @param letterSet set of letters
	 * @return An instance of Solution representing the answer or null if the
	 * answer is not yet available.
	 */
	public Solution getAnswer(String letterSet) {
		Dictionary.sort(letterSet);  // verify sorted.
		
		// short circuit fail
		if (Dictionary.hasVowels(letterSet) == false) {
			return new Solution(letterSet, new ArrayList<String>(0));
		}
		
		// Check cache. If it already exists, return answer.
		Solution solution = cache.retreive(letterSet);
		if (solution != null) {
			currentProblem = null;
			return solution;
		}
				
		if (currentProblem == null) {
			currentProblem = letterSet;
			if (solver.requestSolution(letterSet) == false) {
				throw new IllegalStateException();
			}
		}
		return null;
	}

}
