package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public final class Dictionary {
	private LinkedList<String> words;
	
	public Dictionary(String filename) throws FileNotFoundException {
		words = readDictionary(filename);
		
		// TODO populate this list with data from a file.
	}
	
	public boolean isWord(String word) {
		return words.contains(word);
	}
	
	private LinkedList<String> readDictionary(String filename) throws FileNotFoundException {
		LinkedList<String> wordList = new LinkedList<String>();
		
		Scanner fileIn = new Scanner(new BufferedReader(new FileReader(filename)));
		
		while (fileIn.hasNext() == true) {
			wordList.add(fileIn.next());
		}
		if (fileIn != null) {
			fileIn.close();
		}
		return wordList;
	}
	
}
