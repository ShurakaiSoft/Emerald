package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public final class Dictionary {
	private String[] words;

	
	/**
	 * A constructor to create a serachable dictionary from the given file. The
	 * given file must contain one word per line and also be sorted in
	 * ascending order. 
	 * 
	 * @param filename dictionary file.
	 * @throws FileNotFoundException if the file can't be found.
	 */
	public Dictionary(String filename) throws FileNotFoundException {
		words = readDictionary(filename);
	}

	
	/**
	 * Uses a binary searchy to verify if the given word exists in the
	 * dictionary.
	 * 
	 * @param word to search for
	 * @return true, if it exists. false otherwise.
	 */
	public boolean isWord(String word) {
		// return (Arrays.binarySearch(words, word) >= 0) ? true : false;
		return iterativeBinarySearch(word);
		
	}

	
	/**
	 * Read in a file representing a dictionary of words, one per line and
	 * sorted in ascending order. Words are added to an array of strings which
	 * is returned on completion.
	 * 
	 * @param filename the ordered file of words
	 * @return a dictionary of words as an array of strings.
	 * @throws FileNotFoundException if the file can't be found in the
	 * underlying file system.
	 */
	private String[] readDictionary(String filename) throws FileNotFoundException {
		LinkedList<String> wordList = new LinkedList<String>();
		
		Scanner fileIn = new Scanner(new BufferedReader(new FileReader(filename)));
		
		while (fileIn.hasNext() == true) {
			wordList.add(fileIn.next());
		}
		if (fileIn != null) {
			fileIn.close();
		}
		return wordList.toArray(new String[wordList.size()]);
	}
	
	/**
	 * My implementation of an iterative binary search before I realised that
	 * java.util.Arrays has a static binarySearch method.
	 * 
	 * @param word
	 * @return
	 */
	private boolean iterativeBinarySearch(String word) {
		int min = 0;
		int max = words.length - 1;
		int mid;
		int compare;
		
		while ( min <= max) {
			mid = (min + max) / 2;
			compare = word.compareTo(words[mid]);
			if (compare == 0) {
				return true;
			} else if (compare < 0) {
				max = mid - 1;
			} else {
				min = mid + 1;
			}
		}
		return false;
	}
	
}
