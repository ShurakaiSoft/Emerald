package core;

import java.util.Set;
import java.util.TreeSet;

/**
 * A Runnable class that can solve a single problem.
 *  
 * @author Steve
 *
 */
public final class Problem {
	private static Dictionary dictionary = null;
	
	private final String letterSet;			// sorted character array
	private Set<String> substringSet;		// sorted set
	private Set<String> validWordSet;		// sorted set

	
	/**
	 * Constructs a new unsolved puzzle.
	 * 
	 * @param letterSet unique identifier for puzzle
	 */
	public Problem(String letterSet) {
		this.letterSet = Dictionary.sort(letterSet);
		this.substringSet = new TreeSet<String>();
		this.validWordSet = new TreeSet<String>();
	}

	
	/**
	 * Set the dictionary that all instances of Unsolved will use to verify
	 * words against
	 * 
	 * @param dictionary new dictionary object to use.
	 */
	public static void setDictionary(Dictionary dictionary) {
		Problem.dictionary = dictionary;
	}
	
	
	/**
	 * Find a solution to this problem.
	 */
	public Solution solve() {
		addSubstrWord(letterSet);
		getValidWords();
		return new Solution(letterSet, validWordSet);
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
		for (String string : substringSet) {
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
		substringSet.add(string);
		int strlen = string.length();
		if (strlen > 1) {
			for (int i = 1; i <= string.length(); i++) {
				String substr = deleteCharAt(string, i);
				if ((Dictionary.hasVowels(substr) == true) && (substringSet.contains(substr) == false)) {
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
