package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import core.Solution;
import core.Solver;
import java.io.FileNotFoundException;

public class SolverTests {
	Solver solver;
	
	/**
	 * Initialize a new solver for each test.
	 * 
	 * @throws FileNotFoundException
	 */
	@Before
	public void setUp() throws FileNotFoundException {
		solver = new Solver("dictionary.txt");
	}
	
	
	/**
	 * Verify an invalid filename throws an exception.
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(expected=FileNotFoundException.class)
	public void testConstructor() throws FileNotFoundException {
		solver = new Solver("");
	}
	
	
	/**
	 * Test get solution can actually get a solution
	 */
	@Test
	public void testGetSolution() {
		Solution solution = solver.getSolution("asdf");
		assertTrue(solution.getWordSetSize() == 8);	
	}
	
}
