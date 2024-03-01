// Binary Search Tree class From your textbook (Weiss)

import java.util.LinkedList; //only for the return of values(), do not use it anywhere else

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @param <AnyTypeT> node
 */
public class WeissBST<AnyTypeT extends Comparable<? super AnyTypeT>> {


	// Basic node stored in unbalanced binary search trees
	// Note that this class is not accessible outside
	// of this class.

	/**
	 * BinaryNode class to represent nodes.
	 * 
	 * @author basel Barham
	 *
	 * @param <AnyTypeT> data in the node
	 */
	private class BinaryNode<AnyTypeT> {
		// Constructor
		/**
		 * Constructor to initialize the node.
		 * 
		 * @param theElement data in the node.
		 */
		BinaryNode(AnyTypeT theElement) {
			element = theElement;
			left = right = null;
		}

		/**
		 * The data in the node.
		 */
		AnyTypeT element; // The data in the node

		/**
		 * Left child of the node.
		 */
		BinaryNode<AnyTypeT> left; // Left child

		/**
		 * Right child of the node.
		 */
		BinaryNode<AnyTypeT> right; // Right child
	}

	// NOTE: you may not have any other instance variables, only this one below.
	// if you make more instance variables your binary search tree class
	// will receive a 0, no matter how well it works

	/** The tree root. */
	private BinaryNode<AnyTypeT> root;

	/**
	 * Construct the tree.
	 */
	public WeissBST() {
		root = null;
	}

	/**
	 * Insert into the tree.
	 * 
	 * @param x the item to insert.
	 * @throws Exception if x is already present.
	 */
	public void insert(AnyTypeT x) {
		root = insert(x, root);
	}

	/**
	 * Remove minimum item from the tree.
	 * 
	 * @throws Exception if tree is empty.
	 */
	public void removeMin() {
		root = removeMin(root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 */
	public AnyTypeT findMin() {
		return elementAt(findMin(root));
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x the item to search for.
	 * @return the matching item or null if not found.
	 */
	public AnyTypeT find(AnyTypeT x) {
		return elementAt(find(x, root));
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Internal method to get element field.
	 * 
	 * @param t the node.
	 * @return the element field or null if t is null.
	 */
	private AnyTypeT elementAt(BinaryNode<AnyTypeT> t) {
		return t == null ? null : t.element;
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x the item to insert.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if x is already present.
	 */
	private BinaryNode<AnyTypeT> insert(AnyTypeT x, BinaryNode<AnyTypeT> t) {
		if (t == null)
			t = new BinaryNode<AnyTypeT>(x);
		else if (x.compareTo(t.element) < 0)
			t.left = insert(x, t.left);
		else if (x.compareTo(t.element) > 0)
			t.right = insert(x, t.right);
		else
			throw new IllegalArgumentException("Duplicate Item: " + x.toString()); // Duplicate
		return t;
	}

	/**
	 * Internal method to remove minimum item from a subtree.
	 * 
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if t is empty.
	 */
	private BinaryNode<AnyTypeT> removeMin(BinaryNode<AnyTypeT> t) {
		if (t == null)
			throw new IllegalArgumentException("Min Item Not Found");
		else if (t.left != null) {
			t.left = removeMin(t.left);
			return t;
		} else
			return t.right;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyTypeT> findMin(BinaryNode<AnyTypeT> t) {
		if (t != null)
			while (t.left != null)
				t = t.left;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return node containing the matched item.
	 */
	private BinaryNode<AnyTypeT> find(AnyTypeT x, BinaryNode<AnyTypeT> t) {
		while (t != null) {
			if (x.compareTo(t.element) < 0)
				t = t.left;
			else if (x.compareTo(t.element) > 0)
				t = t.right;
			else
				return t; // Match
		}

		return null; // Not found
	}


	/**
	 * Remove from the tree..
	 * 
	 * @param x the item to remove.
	 * @throws Exception if x is not found.
	 */
	public void remove(AnyTypeT x) {
		root = helpRemove(x, root);
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x the item to remove.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if x is not found.
	 */
	private BinaryNode<AnyTypeT> helpRemove(AnyTypeT x, BinaryNode<AnyTypeT> t) {
		if (t == null) {
			throw new IllegalArgumentException("Item Not Found: " + x.toString());

		}

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = helpRemove(x, t.left);
		else if (compareResult > 0)
			t.right = helpRemove(x, t.right);
		else if (t.left != null && t.right != null) { // Two children
			t.element = findMax(t.left).element;
			t.left = helpRemove(t.element, t.left);
		} else {
			t = (t.left != null) ? t.left : t.right;
		}
		return t;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyTypeT> findMax(BinaryNode<AnyTypeT> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x the item to remove.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if x is not found.
	 */
	private BinaryNode<AnyTypeT> remove(AnyTypeT x, BinaryNode<AnyTypeT> t) {
		if (t == null)
			throw new IllegalArgumentException("Item Not Found: " + x.toString());
		if (x.compareTo(t.element) < 0)
			t.left = remove(x, t.left);
		else if (x.compareTo(t.element) > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = removeMin(t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	

	// report the number of nodes in tree
	// return 0 for null trees
	// O(N): N is the tree size
	/**
	 * Method to return the size.
	 * 
	 * @return size, 0 if it is null
	 */
	public int size() {
		return helpSize(root);
	}

	/**
	 * Helper method to return the size.
	 * 
	 * @param t binarynode
	 * @return size, 0 if it is null
	 */
	private int helpSize(BinaryNode<AnyTypeT> t) {
		if (t == null) {
			return 0;
		} else {
			return 1 + helpSize(t.left) + helpSize(t.right);
		}
	}

	// Return a string representation of the tree
	// follow IN-ORDER traversal to include all nodes.
	// Include one space after each node.
	// O(N): N is the tree size
	//
	// Return empty string "" for null trees.
	// Check main method below for more examples.
	//
	// Example 1: a single-node tree with the root data as "A"
	// toString() should return "A "
	//
	// Example 2: a tree with three nodes: B
	// / \
	// A C
	// toString() should return "A B C "

	/**
	 * Method to return a string representation of the tree in order.
	 * 
	 * @return in order string representation.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		helpToString(root, sb);
		return sb.toString();
	}

	/**
	 * Helper method to return a string representation of the tree in order.
	 * 
	 * @param t  binary node
	 * @param sb a string
	 */
	private void helpToString(BinaryNode<AnyTypeT> t, StringBuilder sb) {
		if (t != null) {
			helpToString(t.left, sb);
			sb.append(t.element).append(" ");
			helpToString(t.right, sb);
		}
	}

	// Return an array representation of all values
	// following PRE-ORDER traversal.
	// O(N): N is the tree size
	//
	// Return an empty LinkedList for null trees.
	// Example : a tree with three nodes: B
	// / \
	// A C
	// values should return a linked list with B-->A-->C
	// Check main() below for more examples.

	/**
	 * Method to return an array representation of all values in pre order.
	 * 
	 * @return list in pre order
	 */
	public LinkedList<AnyTypeT> values() {
		LinkedList<AnyTypeT> list = new LinkedList<>();
		buildValues(root, list);
		return list;
	}

	/**
	 * Helper method to return an array representation of all values in pre order.
	 * 
	 * @param t    binary node
	 * @param list linkedList
	 */
	private void buildValues(BinaryNode<AnyTypeT> t, LinkedList<AnyTypeT> list) {
		if (t != null) {
			list.add(t.element);
			buildValues(t.left, list);
			buildValues(t.right, list);
		}
	}


	

	/**
	 * Main method for testing.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		WeissBST<Integer> t = new WeissBST<Integer>();

		if (t.size() == 0 && t.isEmpty() && t.find(310) == null) {
			System.out.println("Yay 1");
		}

		t.insert(310);
		t.insert(112);
		t.insert(440);
		t.insert(330);
		t.insert(471);
		LinkedList<Integer> values = t.values();

		// Current tree:
		// 310
		// / \
		// 112 440
		// / \
		// 330 471

		if (t.size() == 5 && t.toString().equals("112 310 330 440 471 ") && !t.isEmpty()) {
			System.out.println("Yay 2");
		}

		if (values.size() == 5 && values.get(0) == 310 && values.get(1) == 112 && values.get(2) == 440
				&& values.get(3) == 330 && values.get(4) == 471) {
			System.out.println("Yay 3");
		}

		// remove
		t.remove(440);

		// check removal with predecessor replacement
		// tree expected:
		//
		// 310
		// / \
		// 112 330
		// \
		// 471
		values = t.values();
		// System.out.println(values.size());
		// System.out.println(values.get(2));
		// System.out.println(values.get(3));

		if (values.size() == 4 && values.get(2) == 330 && values.get(3) == 471) {
			System.out.println("Yay 4");
		}
		// System.out.print(t);

	}

}
