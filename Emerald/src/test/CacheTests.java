package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import core.Cache;
import core.Solution;

public class CacheTests {
	Cache<String, Solution> emptyCache = new Cache<String, Solution>(20);
	Cache<String, Solution> nonEmptyCache = new Cache<String, Solution>(20);
	String[] answer = {"ab", "cab" };
	Solution solution = new Solution("abc", answer);
	
	@Before
	public void setUpNonEmptyCache() {
		nonEmptyCache.store(solution.getLetterSet(), solution);
	}
	
	@Test
	public void testGetAnswer() {
		assertTrue(emptyCache.retreive("abc") == null);
		assertTrue(nonEmptyCache.retreive("def") == null);
		String[] answer = nonEmptyCache.retreive("abc").getWordSet();
		assertTrue(answer != null);
		assertTrue(answer[0].equals("ab") == true);
		assertTrue(answer[1].equals("cab") == true);
	}

}
