package core;

/**
 * A simple class to store a set of letters and the corresponding set of valid
 * words that can be created from that set.<p>
 * 
 * This is an immutable class<p>
 * 
 * 
 * @author Steve
 *
 */
public final class Solution {
	private final String letterSet;	
	private final String[] wordSet; 
	
	
	/**
	 * Construct a Solution object from the given letterSet and wordSet.
	 * 
	 * @param letterSet the letterSet.
	 * @param wordSet the wordSet.
	 */
	public Solution(String letterSet, String[] wordSet) {
		this.letterSet = letterSet;
		if (wordSet == null) {
			wordSet = new String[0];
		}
		this.wordSet = wordSet;
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
	public String[] getWordSet() {
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
		return wordSet.length;
	}

}
