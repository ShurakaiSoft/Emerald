package test;

import static org.junit.Assert.*;

import org.junit.Test;

import core.Mutators;

public class MutationTests {
	
	/**
	 * Test for all the permutations of the string "abc".
	 */
	@Test
	public void testMutation() {
		String[] result = Mutators.mutate("abc");
		assertTrue(result.length == 6);
		assertTrue(result[0].equals("abc"));
		assertTrue(result[1].equals("acb"));
		assertTrue(result[2].equals("bac"));
		assertTrue(result[3].equals("bca"));
		assertTrue(result[4].equals("cab"));
		assertTrue(result[5].equals("cba"));
	}

	
	/**
	 * Test for all permutations of the string "ab" and all it's substrings.
	 */
	@Test
	public void testAllmutationsAB() {
		String[] result = Mutators.mutateAll("ab");
		assertTrue(result.length == 4);
		assertTrue(result[0].equals("a"));
		assertTrue(result[1].equals("ab"));
		assertTrue(result[2].equals("b"));
		assertTrue(result[3].equals("ba"));
	}	
	
	
	/**
	 * Test for all permutations of the string "abc" and all it's substrings.
	 */
	@Test
	public void testAllMutationsABC() {
		String[] result = Mutators.mutateAll("abc");
		assertTrue(result.length == 15);
		assertTrue(result[0].equals("a"));
		assertTrue(result[4].equals("acb"));
		assertTrue(result[5].equals("b"));
		assertTrue(result[9].equals("bca"));
		assertTrue(result[10].equals("c"));
		assertTrue(result[14].equals("cba"));		
	}
	
	
	/**
	 * Test for all the permutations of the substring "abcd" and all it's
	 * substrings.
	 */
	@Test
	public void testAllMutationsABCD() {
		String[] result = Mutators.mutateAll("abcd");
		assertTrue(result[0].equals("a"));
		assertTrue(result[15].equals("adcb"));
		assertTrue(result[16].equals("b"));
		assertTrue(result[result.length - 1].equals("dcba"));
	}
	
}
