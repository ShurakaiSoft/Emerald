package core;

import java.io.FileNotFoundException;

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
	
	private Cache cache;
	private Solver solver;
	private String dictionaryName;
	private Thread solverThread;
	
	
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
		try {
			this.dictionaryName = dictionaryName;
			cache = new Cache();
			solver = new Solver(this.cache, this.dictionaryName);
			solverThread = new Thread(solver);
			solverThread.start();
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
	 * @return set of valid words. If the given set of letters forms no words,
	 * then an empty set is returned. A null is returned if the return value is
	 * not yet ready.
	 */
	public String[] getAnswer(String letterSet) {		
		// short circuit fail
		if (Dictionary.hasVowels(letterSet) == false) {
			return new String[0];
		}
		
		// Check cache. If it already exists, return answer.
		String[] answer = cache.getAnswer(letterSet); 
		if (answer != null) {
			return answer;
		}
				
		// XXX Clear the solution queue in the solver to prevent deadlock. 
		// Need to find a more elegant solution to this problem 
		solver.getSolution();
		
		// ask the solver to add to the cache.
		while ((solver.requestWordSet(letterSet)) == false) {
			// keep trying to add the request.
		}
		return null;
	}

}
