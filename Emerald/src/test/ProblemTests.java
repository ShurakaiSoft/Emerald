package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import core.Dictionary;
import core.Solution;
import core.Problem;

public class ProblemTests {
	
	
	private static Dictionary dictionary;		// normal dictionary
	private static Dictionary abcDictionary;	// special test dictionary

	
	/**
	 * Initialize two separate dictionary objects, so different tests can use
	 * different dictionary files.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			dictionary = new Dictionary("dictionary.txt");
			abcDictionary = new Dictionary("abcDictionary.txt");
		} catch (FileNotFoundException e) {
			System.exit(-1);
		}
	}

	
	/**
	 * Tests that all permutations of a string and it's substrings have at
	 * least one vowel in them.
	 * 
	 * Since there is no public access to this permutation set, this test
	 * uses a modified dictionary that has entries for all permutations of
	 * 'abc'. Therefore all permutations will be found in the dictionary and 
	 * the Solution's word set returned by getWordSet() will contain the actual
	 * permutation set.
	 * 
	 */
	@Test
	public void testPermutationVowel() {
		setDictionary(abcDictionary);
		Problem unsolved = new Problem("abc");
		unsolved.solve();
		Solution solution = unsolved.solve();
		String[] answer = solution.getWordSet();
		assertTrue(answer.length == 11);
		assertTrue(Arrays.binarySearch(answer, "b") < 0);
		assertTrue(Arrays.binarySearch(answer, "c") < 0);
		assertTrue(Arrays.binarySearch(answer, "bc") < 0);
		assertTrue(Arrays.binarySearch(answer, "cb") < 0);
	}

	
	/**
	 * Verify that two strings with the same characters but in different order,
	 * produce the same permutation list.
	 */
	@Test
	public void testMutationSetConsistancy() {
		setDictionary(dictionary);
		Problem abcUnsolved = new Problem("abc");
		Problem cbaUnsolved = new Problem("cba");
		abcUnsolved.solve();
		cbaUnsolved.solve();
		Solution abcSolution = abcUnsolved.solve();
		Solution cbaSolution = cbaUnsolved.solve();
		String[] abcAnswer = abcSolution.getWordSet();
		String[] cbaAnswer = cbaSolution.getWordSet();
		assertTrue(abcAnswer.length == cbaAnswer.length);
		for (int i = 0; i < abcAnswer.length; i++) {
			assertTrue(abcAnswer[i].equals(cbaAnswer[i]));
		}
	}
	
	
	/**
	 * convenience method to select which dictionary will be used for
	 * individual tests.
	 * 
	 * @param dictionary
	 */
	private void setDictionary(Dictionary dictionary) {
		Problem.setDictionary(dictionary);		
	}
}