package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Value Class to store a set of letters and the corresponding set of valid
 * words that can be created from that set.<p>
 * 
 * This is an immutable class<p>
 * 
 * 
 * @author Steve
 *
 */
public final class Solution implements Comparable<Solution> {
	private final String letterSet;
	private final List<String> wordSet; 
	
	
	/**
	 * Construct a Solution object from the given letterSet and wordSet.
	 * 
	 * @param letterSet the letterSet.
	 * @param wordSet the wordSet.
	 */
	public Solution(String letterSet, Collection<String> wordSet) {
		this.letterSet = letterSet;
		this.wordSet = new ArrayList<String>(wordSet.size());
		this.wordSet.addAll(wordSet);
	}
	
	
	/**
	 * Get the set of letters as a String.
	 * 
	 * @return letterSet.
	 */
	public String getLetterSet() {
		return letterSet;
	}
	
	
	/**
	 * Get the set of words as an array of Strings.
	 * 
	 * @return the wordSet
	 */
	public List<String> getWordSet() {
		return wordSet;
	}
	
	
	/**
	 * Get the number of words created from associated letterSet. This also
	 * corresponds to the size of the array returned by getWordSet.
	 * 
	 * @return number of words.
	 * @see getWordSet
	 */
	public int getWordSetSize() {
		return wordSet.size();
	}

	
	@Override
	public boolean equals(Object o) {
		return letterSet.equals(((Solution) o).letterSet);
	}
	
	@Override
	public int hashCode() {
		return letterSet.hashCode();
	}

	@Override
	public int compareTo(Solution solution) {
		// Since there exists one and only one valid wordSet for every 
		// letterSet we can use letterSet's compareTo method.
		return letterSet.compareTo(solution.letterSet);
	}

}
