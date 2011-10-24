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
	
	private final String letterSet;		// sorted character array
	private Set<String> substrWordSet;		// sorted set
	private Set<String> validWordSet;		// sorted set
	private boolean doneSolving;			// flag 

	
	/**
	 * Constructs a new unsolved puzzle.
	 * 
	 * @param letterSet unique identifier for puzzle
	 */
	public Unsolved(String letterSet) {
		this.letterSet = sort(letterSet);
		this.substrWordSet = new TreeSet<String>();
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
		return letterSet;
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
		return new Solution(letterSet, validWordSet.toArray(new String[validWordSet.size()]));
	}
	
	
	@Override
	public void run() {
		addSubstrWord(letterSet);
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
		for (String string : substrWordSet) {
			addValidWord("", string);
		}		
	}
	
	
	/**
	 * Recursively adds all substrings given string to the subsetWordSet if the
	 * string has at least one vowel and isn't already in the subsetWordSet.
	 * 
	 * @param string a set of letters
	 */
	private void addSubstrWord(String string) {
		substrWordSet.add(string);
		int strlen = string.length();
		if (strlen > 1) {
			for (int i = 1; i <= string.length(); i++) {
				String substr = deleteCharAt(string, i);
				if ((Dictionary.hasVowels(substr) == true) && (substrWordSet.contains(substr) == false)) {
					addSubstrWord(substr);
				}
			}
		}
	}
	
	
	/**
	 * Recursively finds all permutations of given input and adds them to the
	 * validWordSet if they are verified in the dictionary.<p>
	 * 
	 * Initially the whole word will change, so the root will typically be a
	 * null string and the variable will be the full set of letters.
	 * 
	 * @param root constant part of word or null string
	 * @param variable changeable part of word
	 */
	private void addValidWord(String root, String variable) {
		if (variable.length() <= 1) {
			String word = root + variable;
			if (dictionary.isWord(word) == true) {
				validWordSet.add(word);
			}
		} else {
			for (int i = 0; i < variable.length(); i++) {
				String newString = variable.substring(0, i) + variable.substring(i + 1);
				addValidWord(root + variable.charAt(i), newString);
			}
		}
	}
	
	
	/**
	 * Returns a string with the character at index removed.
	 * 
	 * @param string original string
	 * @param index the character for deletion index. First character in the
	 * original string is at 1.
	 * @return modified string
	 */
	private static String deleteCharAt(String string, int index) {
		if (index == 1) {
			return string.substring(1);
		}
		int length = string.length(); 
		if (index >= length) {
			return string.substring(0, length - 1);
		}
		return string.substring(0, index - 1) + string.substring(index, length);
	}

}
