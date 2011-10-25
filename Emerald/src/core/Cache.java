package core;

import java.util.TreeMap;

/**
 * Implements an in memory cache. There is no limit to the size of the cache,
 * beyond the limits of hardware.
 * 
 * @author Steve
 *
 */
public final class Cache {
	private TreeMap<String, String[]> cachedData;

	
	/**
	 * Construct an empty cache.
	 */
	public Cache() {
		cachedData = new TreeMap<String, String[]>(); 
	}
	
	
	/**
	 * Add a new solution to the cache.
	 * 
	 * @param solution 
	 */
	public void addSolution(Solution solution) {
		cachedData.put(solution.getLetterSet(), solution.getWordSet());
	}

	
	/**
	 * return the solution.
	 * 
	 * @return An array of words
	 */
	public String[] getAnswer(String letterSet) {
		return cachedData.get(letterSet);
	}
	
}
