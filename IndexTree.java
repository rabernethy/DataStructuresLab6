/* IndexTree.java, written by Russell Abernethy */

import IndexNode;
import java.util.*;
import java.io.*;

public class IndexTree {

	private IndexNode root;

// Constructor method:
	public IndexTree() {
		this.root = null;
	}

// Add method: Calls a recursive add method to add a node to the tree.
	public void add(String word, int lineNumber){
		this.root = add(this.root, word, lineNumber);
	}
	
// Add method: Recursivly tries to add a node to the tree.
	private IndexNode add(IndexNode root, String word, int lineNumber){
		// If there is nothing in the tree at the root, make the root node the passed node.
		if(root == null) {
			return new IndexNode(word, lineNumber);
		}
		// Compare the current node trying to be added to the current root node.
		int comparison = word.compareTo(root.word);

		// If the current and root nodes contain the same word, increment occurances of the root node and add the line found to the node.
		if(comparison == 0) { 
			root.addOccurance(lineNumber);
			return root;
		}
		// If the current node is less than the root node, add the current node to the left child of the root.
		if(comparison < 0) {
			root.left = add(root.left, word, lineNumber);
			return root;
		} 
		// If the current node is greater than the root node, add the current node to the right child of the root.
		else {
			root.right = add(root.right, word, lineNumber);
			return root;
		}
	}
		
// Contains Method. Calls a recursive method to find if a word is in a tree.
	public boolean contains(String word){
		return contains(this.root, word);
	}

// Contains method. Recursivly tries to find if a word exists in the tree.
	private boolean contains(IndexNode root, String word) {
		// If the root is null, there is no match.
		if(root == null)
			return false;
		
		// Compare the current root's word to the passed word.
		int comparison = word.compareTo(root.word);

		// Found!
		if(comparison == 0)
			return true;
		
		// If the word is less than the root's word, search down the root's left child.
		else if(comparison < 0)
			return contains(root.left, word);

		// If the word is greater than the root's word, search down the root's right child.
		else
			return contains(root.right, word);
	}
	
// Delete Method. Calls a recursive method to delete a word from the tree.
	public void delete(String word){
		this.root = this.delete(this.root, word);
	}
	
// Delete Method. Recursivly tries to delete a passed word from the tree.
	private IndexNode delete(IndexNode root, String word){
		// If the node is empty, return null.
		if(root == null)
			return null;

		// Compare the current root's word to the passed word.
		int comparison = word.compareTo(root.word);
		
		// If less than, go down the left child.
		if(comparison < 0) {
			root.left = delete(root.left, word);
			return root;
		}

		// If greater than, go down the right child.
		else if(comparison > 0) {
			root.right = delete(root.right, word);
			return root;
		}

		// If equal to, it gets complicated.
		else {
			// If the root has no children:
			if(root.left == null && root.right == null)
				return null;

			// If the root has a left but not right child:
			else if(root.left != null && root.right == null)
				return root.left;
			
			// If the root has a right but not left child:
			else if(root.left == null && root.right != null)
				return root.right;

			// If the root has both children:
			else {
				// Find the first null child
				IndexNode current = root.left;
				while(current.right != null) {
					current = current.right;
				}
				root.word = current.word;
				// Delete the node from the tree.
				delete(root.left, root.word);
				return root;
			}
			
		}

	}
	
// printIndex Method. Prints all the nodes in the index via an In Order Traversal.
	public void printIndex() {
		printIndex(this.root);
	}
	private void printIndex(IndexNode node) {
		//Inorder traversal: Left ==> Right ==> Root
		if(node == null) {return;}
		printIndex(node.left);
		printIndex(node.right);
		System.out.println(node);
	}
	
// Main.
	public static void main(String[] args){
		IndexTree index = new IndexTree();
		File file = new File("pg100.txt");

		// Add all the words to the tree
		try {
			Scanner scanner = new Scanner(file);
			int line = 0;

			while(scanner.hasNextLine()) {
				String[] words = scanner.nextLine().split(" ");
				for(int i = 0; i < words.length; i++)
					index.add(words[i].replaceAll("[^a-zA-Z]","").toLowerCase(), line);
				line++;
			}
			scanner.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("Could not open file.");
		}
		
		// Print out the index
		index.printIndex();
		
		// Test removing a word from the index
		index.delete("the");
		
	}
}
