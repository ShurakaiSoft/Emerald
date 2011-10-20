package core;

import java.util.Set;
import java.util.TreeSet;

/**
 * A class of static functions to recursively mutate strings.
 * 
 * @author Steve
 *
 */
public class Mutators {
	
	
	/**
	 * For a given String, will return a unique set of all permutations. Does 
	 * not return any substring permutations. 
	 * 
	 * @param seed an initial string.
	 * @return the set of permutations.
	 * @see mutateAll
	 */
	public static String[] mutate(String seed) {
		Set<String> result = new TreeSet<String>();
		addMutation(result, "", seed);
		return result.toArray(new String[result.size()]);
	}
	
	
	/**
	 * For a given String, will return a unique set of all permutations, 
	 * including all substrings.
	 * 
	 * @param seed an initial String.
	 * @return the set of permutations.
	 * @see mutate
	 */
	public static String[] mutateAll(String seed) {
		Set<String> result = new TreeSet<String>();

		addSubset(result, seed);
				
		return result.toArray(new String[result.size()]);
	}
	
	
	/**
	 * Recursively add all mutations of a given string to a given set of 
	 * results.
	 * 
	 * @param result for collecting the mutated strings.
	 * @param root unchanging portion of string.
	 * @param variable changing portion of string.
	 */
	private static void addMutation(Set<String> result, String root, String variable) {
		if (variable.length() <= 1) {
			result.add(root + variable);
		} else {
			for (int i = 0; i < variable.length(); i++) {
				try {
					String newString = variable.substring(0, i) + variable.substring(i + 1);
					addMutation(result, root + variable.charAt(i), newString);
				} catch (Exception e) {
					System.out.println("Possible Stach overflow; " + e.getMessage());
				}
			}
		}
	}
	
	
	/**
	 * Recursively add all mutations of a given string and all it's substrings
	 * to a given set of results.
	 * 
	 * @param result for collection all the mutated strings.
	 * @param string the string to mutate.
	 */
	private static void addSubset(Set<String> result, String string) {
		addMutation(result, "", string);
		int strlen = string.length();
		if (strlen > 1) {
			for (int i = 1; i <= string.length(); i++) {
				addSubset(result, substrDelete(string, i));
			}
		}
	}
	

	/**
	 * Return a string with the character at I removed
	 * @return
	 */
	private static String substrDelete(String string, int index) {
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
