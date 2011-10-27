package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.List;

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
		List<String> answer = solution.getWordSet();
		assertTrue(answer.size() == 11);
		assertTrue(answer.contains("b") == false);
		assertTrue(answer.contains("c") == false);
		assertTrue(answer.contains("bc") == false);
		assertTrue(answer.contains("cb") == false);
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
		List<String> abcAnswer = abcSolution.getWordSet();
		List<String> cbaAnswer = cbaSolution.getWordSet();
		assertTrue(abcAnswer.size() == cbaAnswer.size());
		assertTrue(abcAnswer.containsAll(cbaAnswer) == true);
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
