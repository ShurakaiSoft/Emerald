package core;

/**
 * This is a dummy cache class that only caches one solution. Used for testing.
 * 
 * @author Steve
 *
 */
public class Cache {
	Solution solution;  // temporary cache, for testing.	

	
	/**
	 * Construct an empty cache
	 */
	public Cache() {
		solution = null;
	}
	
	
	/**
	 * Add a new solution to the cache
	 * 
	 * @param solution
	 */
	public void addSolution(Solution solution) {
		this.solution = solution; 
	}

	
	/**
	 * return the solution
	 * 
	 * @return
	 */
	public String[] getAnswer(String letterSet) {
		if (solution != null && solution.getLetterSet().equals(letterSet) == true) {
			return solution.getWordSet();
		} else {
			return null;
		}
	}
	
}
