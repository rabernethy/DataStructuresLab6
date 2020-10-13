/* IndexNode.java, written by Russell Aberenthy */

import java.util.*;

public class IndexNode  {

	// The word for this entry
	String word;
	// The number of occurrences for this word
	int occurences;
	// A list of line numbers for this word.
	List<Integer> list; 
	// Children of the node.
	IndexNode left = null;
	IndexNode right = null;
	
	// Constructor:
	public IndexNode(String word, int lineNumber) {
		this.word = word;
		this.occurences = 1;

		list = new LinkedList<>();
		list.add(lineNumber);
	}

// addOccurance Method. Increments occurance and adds the past line number to the list.
	public void addOccurance(int lineNumber) {
		this.occurences++;
		list.add(lineNumber);
	}
	
// toString Method.	
	public String toString() {
		return "[Word --> " + word + ", Occurances --> " + occurences + ", Line(s) found at --> " + list + "]\n";
	}
}
