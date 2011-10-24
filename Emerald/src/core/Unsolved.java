package core;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * A Runnable class that can solve a single problem.
 *  
 * @author Steve
 *
 */
public class Unsolved implements Runnable {
	private static Dictionary dictionary = null;
	
	private final String identifier;		// sorted character array
	private Set<String> mutationSet;		// sorted set
	private Set<String> validWordSet;		// sorted set
	private boolean doneSolving;			// flag 

	
	/**
	 * Constructs a new unsolved puzzle.
	 * 
	 * @param letterSet unique identifier for puzzle
	 */
	public Unsolved(String letterSet) {
		this.identifier = sort(letterSet);
		this.mutationSet = new TreeSet<String>();
		this.validWordSet = new TreeSet<String>();
		this.doneSolving = false;
	}

	
	/**
	 * Set the dictionary that all instances of Unsolved will use to verify
	 * words against
	 * 
	 * @param dictionary new dictionary object to use.
	 */
	public static void setDictionary(Dictionary dictionary) {
		Unsolved.dictionary = dictionary;
	}
	
	
	/**
	 * Returns a string of letters in ascending order.
	 * 
	 * @param letterSet original String
	 * @return sorted String
	 */
	public static String sort(String letterSet) {
		char[] data = letterSet.toCharArray();
		Arrays.sort(data);
		return new String(data);
	}

	
	/**
	 * Get a string that uniquely identifies this unsolved problem.
	 *  
	 * @return unique identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	
	/**
	 * When solved returns true, it is safe to read the solution by calling
	 * newSolution.
	 * 
	 * @return true when safe to call newSolution.
	 */
	public boolean solved() {
		return doneSolving;
	}
	
	
	/**
	 * This method will return an solution to the problem identified by 
	 * getIdentifier as an instance of Solution, if the solution has been 
	 * solved. If the solution has not yet been solved, it will return null.
	 *  
	 * @return the answer in an instance of Solution or null if not yet ready.
	 */
	public Solution newSolution() {
		if (doneSolving == false) {
			return null;
		}
		return new Solution(identifier, validWordSet.toArray(new String[validWordSet.size()]));
	}
	
	
	@Override
	public void run() {
		addSubset(identifier);
		getValidWords();
		doneSolving = true;
	}
	
	
	/**
	 * For all mutations in the mutation set, it will validate them against
	 * the dictionary. Mutations that are in the dictionary are added to the
	 * valid words set.<p>
	 */
	private void getValidWords() {
		if (dictionary == null) {
			// XXX might be worth logging this unusual situation
			return;
		}
		for (String string : mutationSet) {
			if (dictionary.isWord(string) == true) {
				validWordSet.add(string);
			}
		}		
	}
	
	
	/**
	 * Recursively adds all mutations of a given string and all it's substrings
	 * to the mutationSet if the string has at least one vowel.
	 * 
	 * @param string a set of letters
	 */
	private void addSubset(String string) {
		if (Dictionary.hasVowels(string) == true) {
			Mutators.mutate(mutationSet, string);
			int strlen = string.length();
			if (strlen > 1) {
				for (int i = 1; i <= string.length(); i++) {
					addSubset(Mutators.substrDelete(string, i));						
				}
			}
		}
	}
	
}
