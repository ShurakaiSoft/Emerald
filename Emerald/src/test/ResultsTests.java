package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Results;
import core.Solution;

public class ResultsTests {
	static Solution adfsSolution;
	Results adfsResults;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<String> words = new ArrayList<String>(8);
		words.add("ad");
		words.add("ads");
		words.add("as");
		words.add("fa");
		words.add("fad");
		words.add("fads");
		words.add("fas");
		words.add("sad");
		adfsSolution = new Solution("adfs", words);
	}

	
	@Before
	public void setUp() throws Exception {
		adfsResults = new Results(adfsSolution);
	}

	
	/**
	 * Test the default result set size shows all results.
	 * Ideally this test should be with a much bigger solution set. However I
	 * haven't bothered to create one yet.
	 */
	@Test
	public void testDefaultAllResults() {
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == adfsSolution.getWordSetSize());
	}
	
	
	/**
	 * Test the result list can be shortened to a requested size.
	 */
	@Test
	public void testAFewResults() {
		adfsResults.setMaxResults(3);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 3);
	}
	
	
	/**
	 * Test zero results returns an empty list and doesn't crash.
	 */
	@Test
	public void testZeroResults() {
		adfsResults.setMaxResults(0);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 0);	
	}
	
	
	/**
	 * Test results is larger than word set doesn't crash but return the
	 * full set of words.
	 */
	@Test
	public void testRequestBiggerThanActual() {
		int maxWords = adfsSolution.getWordSetSize();
		adfsResults.setMaxResults(maxWords + 1);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == maxWords);	
	}
	
	
	/**
	 * Test sorting words by ascending length
	 */
	@Test
	public void testAscendingLength() {
		adfsResults.sortByWordLengthAsc();
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.get(0).equals("ad"));
		assertTrue(words.get(7).equals("fads"));
	}

	
	/**
	 * Test sorting words by descending length
	 */
	@Test
	public void testDescendingLength() {
		adfsResults.sortByWordLengthDesc();
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.get(0).equals("fads"));
		assertTrue(words.get(7).equals("fa"));
	}
	
	
	/**
	 * Test reverting to default sorting after using something else.
	 */
	@Test
	public void testRevertingToDefaultSorting() {
		adfsResults.sortByWordLengthAsc();
		adfsResults.sortByWordLengthDesc();
		adfsResults.sortByDefault();
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.get(0).equals("ad"));
		assertTrue(words.get(7).equals("sad"));	
	}
	
	
	/**
	 * Test filtering will return the correct subset of words.
	 */
	@Test
	public void testFilter() {
		adfsResults.addFilter(1, 'a', 1);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 6);
		assertTrue(words.contains("fads") == false);
		assertTrue(words.contains("ads") == false);
	}
	
	
	/**
	 * Test filtering with a different filter returns a different subset of
	 * words.
	 */
	@Test
	public void testFilterLong() {
		adfsResults.addFilter(2, 'd', 1);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 5);
		assertTrue(words.contains("ad") == true);
		assertTrue(words.contains("ads") == true);
		assertTrue(words.contains("fad") == true);
		assertTrue(words.contains("fads") == true);
		assertTrue(words.contains("sad") == true);	
	}
	
	
	/**
	 * Test filtering with no random leading characters.
	 */
	@Test
	public void testFilterStartsWithZero() {
		adfsResults.addFilter(0, 's', 3);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 1);
		assertTrue(words.contains("sad") == true);
	}
	
	
	/**
	 * Test filtering with no random trailing characters.
	 */
	@Test
	public void testFilterEndsWithZero() {
		adfsResults.addFilter(2, 's', 0);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 3);
		assertTrue(words.contains("ads") == true);
		assertTrue(words.contains("as") == true);		
		assertTrue(words.contains("fas") == true);
	}
	
	
	/**
	 * Test removing a filter returns the full set of words.
	 */
	@Test
	public void testFilterRemoved() {
		adfsResults.addFilter(1, 'a', 1);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 6);
		adfsResults.removeFilter();
		words = adfsResults.getSortedResults();
		assertTrue(words.size() == 8);
	}
	
	
	@Test
	public void testLengthSubset() {
		adfsResults.sortByWordLengthDesc();
		adfsResults.setMaxResults(5);
		List<String> words = adfsResults.getSortedResults();
		assertTrue(words.size() == 5);
		assertTrue(words.contains("fads"));
		assertTrue(words.contains("ads"));
		assertTrue(words.contains("fad"));
		assertTrue(words.contains("fas"));
		assertTrue(words.contains("sad"));
	}
}
