package core;

import java.util.TreeMap;

/**
 * Implements a synchronized in-memory cache. There is no limit to the size of
 * the cache, beyond the limits of hardware.<p>
 * 
 * This version of synchronization is rather simple. Only one thread is allowed
 * to access the cache at a time, thus only one reader can access the cache.<p>
 * 
 * Future implementations will address this issue.<p>
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
	public synchronized void addSolution(Solution solution) {
		cachedData.put(solution.getLetterSet(), solution.getWordSet());
	}

	
	/**
	 * return the solution.
	 * 
	 * @return An array of words
	 */
	public synchronized String[] getAnswer(String letterSet) {
		return cachedData.get(letterSet);
	}
	
}
