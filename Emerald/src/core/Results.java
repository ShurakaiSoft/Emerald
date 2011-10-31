package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
	private Pattern filter;

	
	public Results(Solution solution) {
		comparator = null;
		filter = null;
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
	}


	/**
	 * Filters the returned list of words so that it matches the given pattern.
	 * Words can start with 0 to 7 leading characters and end in 0 to 7
	 * trailing characters. Only one character can be specified in the middle.
	 * 
	 * @param leading number of leading characters (0-7)
	 * @param ch board character
	 * @param trailing number of trailing characters (0-7)
	 */
	public void addFilter(int leading, char ch, int trailing) {
		StringBuilder regex = new StringBuilder();
		if (leading > 0) {
			regex.append(".{0,");
			regex.append(leading);
			regex.append("}");
		}
		regex.append(ch);
		if (trailing > 0) {
			regex.append(".{0,");
			regex.append(trailing);
			regex.append("}");
		}
		this.filter = Pattern.compile(regex.toString());
	}
	
	
	/**
	 * Removes the filter. Results are no longer filtered before sorting and
	 * selecting the top x results as returned by getSortedResults.
	 * 
	 * @see getSortedResults
	 */
	public void removeFilter() {
		filter = null;
	}
	
	
	/**
	 * Sort results by word length in ascending order.
	 */
	public void sortByWordLengthAsc() {
		comparator = new WordLengthAsc();
	}
	
	
	/**
	 * Sort results by word length in descending order.
	 * 
	 * @see sortByWordLengthAsc
	 */
	public void sortByWordLengthDesc() {
		comparator = new WordLengthDesc();
	}
	
	
	/**
	 * Sort by default method.
	 */
	public void sortByDefault() {
		comparator = null;
	}

	
	/**
	 * Set the maximum number of entries to be displayed. To display all
	 * results, set wordLimit to Results.ALL_WORDS.
	 * 
	 * @param wordLimit maximum number of words to display.
	 */
	public void setMaxResults(int wordLimit) {
		this.wordLimit = wordLimit;
	}

	
	/**
	 * Get the set of letters that this set of words corresponds to.
	 *  
	 * @return alphabetically ordered set of letters as a String.
	 */
	public String getLetters() {
		return solution.getLetterSet();
	}
	
	
	/**
	 * For a given Solution, it will return a list of words that match the 
	 * given filter if any and sorted by the given sort order or the default
	 * sort order if none has been given. The list will contain no more entries
	 * than that specified by setMaxResults.
	 * 
	 * @return a list of words as strings.
	 * @see setMaxResults
	 * @see addFilter
	 * @see sortByWordLengthDesc
	 * @see sortByWordLengthAsc
	 */
	public List<String> getSortedResults() {
		List<String> filteredResults;
		if (filter == null) {
			filteredResults = allWords;
		} else {
			filteredResults = getFilteredWords();
		}
		
		int wordCount = Math.min(filteredResults.size(), wordLimit);
		ArrayList<String> results = new ArrayList<String>(wordCount);	
		results.addAll(filteredResults.subList(0, wordCount));
		sortResults(results);
		return results;
	}
	
	
	/**
	 * Utility method to sort new results according to current settings.
	 */
	private void sortResults(List<String> results) {
		if (comparator == null) {
			Collections.sort(results);
		} else {
			Collections.sort(results, comparator);
		}
	}
	

	/**
	 * Utility method to return a filtered list of words according to the regex
	 * set in filter.
	 * 
	 * @return new filtered list of words as an ArrayList.
	 */
	private List<String> getFilteredWords() {
		ArrayList<String> filteredWords = new ArrayList<String>(16);
		
		for (String word : allWords) {
			if (matchesFilter(word) == true) {
				filteredWords.add(word);
			}
		}
		return filteredWords;
	}
	
	
	/**
	 * Test the given word against the filter field.
	 * 
	 * @return true if word matches filter.
	 */
	private boolean matchesFilter(String word) {
		Matcher matcher = filter.matcher(word);
		
		return matcher.matches();				
	}
	
}
