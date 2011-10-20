package core;

import java.io.FileNotFoundException;

/**
 * Currently only solves a single problem at a time.<p>
 * 
 * Future versions are planned to be able to handle multiple requests in 
 * parallel.<p>
 * 
 * @author Steve
 *
 */
public class Solver {
	private Dictionary dictionary;

	
	/**
	 * Construct a Solver subsystem with a given dictionary filename.
	 * 
	 * @param dictionaryFilename name of the dictionary file.
	 * @throws FileNotFoundException if the system can't find the given file
	 */
	public Solver(String dictionaryFilename) throws FileNotFoundException {
		dictionary = new Dictionary(dictionaryFilename);
		Unsolved.setDictionary(dictionary);
	}
	
	
	/**
	 * Return a solution to a given problem.
	 * 
	 * @param letterSet the letter set that identifies the problem
	 * @return a instance of a Solution object that contains the identifier
	 * and corresponding solution.
	 */
	public Solution getSolution(String letterSet) {
		Unsolved unsolved = new Unsolved(letterSet);
		
		unsolved.run();		
		return unsolved.newSolution();
	}
	
}
