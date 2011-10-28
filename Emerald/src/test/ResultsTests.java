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
	Results results;
	
	
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
		results = new Results(adfsSolution);
	}

	
	/**
	 * Test the default result set size shows all results.
	 * Ideally this test should be with a much bigger solution set. However I
	 * haven't bothered to create one yet.
	 */
	@Test
	public void testDefaultAllResults() {
		List<String> words = results.getSortedResults();
		assertTrue(words.size() == adfsSolution.getWordSetSize());
	}
	
	
	/**
	 * Test the result list can be shortened to a requested size.
	 */
	@Test
	public void testAFewResults() {
		results.setMaxResults(3);
		List<String> words = results.getSortedResults();
		assertTrue(words.size() == 3);
	}
	
	
	/**
	 * Test zero results returns an empty list and doesn't crash.
	 */
	@Test
	public void testZeroResults() {
		results.setMaxResults(0);
		List<String> words = results.getSortedResults();
		assertTrue(words.size() == 0);	
	}
	
	
	/**
	 * Test results is larger than word set doesn't crash but return the
	 * full set of words.
	 */
	@Test
	public void testRequestBiggerThanActual() {
		int maxWords = adfsSolution.getWordSetSize();
		results.setMaxResults(maxWords + 1);
		List<String> words = results.getSortedResults();
		assertTrue(words.size() == maxWords);	
	}
	
	
	/**
	 * Test sorting words by ascending length
	 */
	@Test
	public void testAscendingLength() {
		results.sortByWordLengthAsc();
		List<String> words = results.getSortedResults();
		assertTrue(words.get(0).equals("ad"));
		assertTrue(words.get(7).equals("fads"));
	}

	
	/**
	 * Test sorting words by descending length
	 */
	@Test
	public void testDescendingLength() {
		results.sortByWordLengthDesc();
		List<String> words = results.getSortedResults();
		assertTrue(words.get(0).equals("fads"));
		assertTrue(words.get(7).equals("fa"));
	}
	
	
	/**
	 * Test reverting to default sorting after using something else.
	 */
	@Test
	public void testRevertingToDefaultSorting() {
		results.sortByWordLengthAsc();
		results.sortByWordLengthDesc();
		results.sortByDefault();
		List<String> words = results.getSortedResults();
		assertTrue(words.get(0).equals("ad"));
		assertTrue(words.get(7).equals("sad"));	
	}
	
}
