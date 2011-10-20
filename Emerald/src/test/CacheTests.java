package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import core.Cache;
import core.Solution;

public class CacheTests {
	Cache emptyCache = new Cache();
	Cache nonEmptyCache = new Cache();
	String[] answer = {"ab", "cab" };
	Solution solution = new Solution("abc", answer);
	
	@Before
	public void setUpNonEmptyCache() {
		nonEmptyCache.addSolution(solution);
	}
	
	@Test
	public void testGetAnswer() {
		assertTrue(emptyCache.getAnswer("abc") == null);
		assertTrue(nonEmptyCache.getAnswer("def") == null);
		String[] answer = nonEmptyCache.getAnswer("abc");
		assertTrue(answer != null);
		assertTrue(answer[0].equals("ab") == true);
		assertTrue(answer[1].equals("cab") == true);
	}

}
