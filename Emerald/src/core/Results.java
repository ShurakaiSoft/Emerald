package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class encapsulates a solution and provides various options to format 
 * and display the results.
 * 
 * @author Steve
 *
 */
public class Results {
	private static class WordLengthAsc implements Comparator<String> {
		@Override
		public int compare(String arg0, String arg1) {
			if (arg0.length() == arg1.length()) {
				return 0;
			}
			return (arg0.length() < arg1.length()) ? -1 : 1;
		}		
	}
	private static class WordLengthDesc implements Comparator<String> {
		@Override
		public int compare(String arg0, String arg1) {
			if (arg0.length() == arg1.length()) {
				return 0;
			}
			return (arg0.length() > arg1.length()) ? -1 : 1;
		}		
	}
	
	public static final int ALL_WORDS = Integer.MAX_VALUE;
	
	private Solution solution;
	private List<String> allWords;				// mutable list for sorting
	private Comparator<String> comparator;		
	private int wordLimit;
	
	public Results(Solution solution) {
		comparator = null;
		wordLimit = Results.ALL_WORDS;
		setSolution(solution);
	}
	

	/**
	 * Provide a new solution to be formatted.
	 * 
	 * @param solution
	 */
	public void setSolution(Solution solution) {
		this.solution = solution;
		allWords = new ArrayList<String>(solution.getWordSetSize());
		allWords.addAll(solution.getWordSet());
		sortResults();
	}

	/**
	 * Sort results by word length in ascending order.
	 */
	public void sortByWordLengthAsc() {
		comparator = new WordLengthAsc();
		sortResults();
	}
	
	
	/**
	 * Sort results by word length in descending order.
	 */
	public void sortByWordLengthDesc() {
		comparator = new WordLengthDesc();
		sortResults();
	}
	
	
	/**
	 * Sort by default method.
	 */
	public void sortByDefault() {
		comparator = null;
		sortResults();
	}
	
	/**
	 * Set the maximum number of entries to be displayed.
	 * 
	 * @param wordLimit
	 */
	public void setMaxResults(int wordLimit) {
		this.wordLimit = wordLimit;
	}

	
	/**
	 * 
	 */
	public String getLetters() {
		return solution.getLetterSet();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<String> getSortedResults() {
		int wordCount = Math.min(solution.getWordSetSize(), wordLimit);
		ArrayList<String> results = new ArrayList<String>(wordCount);
	
		results.addAll(allWords.subList(0, wordCount));
		return results;
	}
	
	
	/**
	 * Utility method to sort new results according to current settings.
	 */
	private void sortResults() {
		if (comparator == null) {
			Collections.sort(allWords);
		} else {
			Collections.sort(allWords, comparator);
		}
	}
	
}
