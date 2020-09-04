// For interface Collection, see: http://docs.oracle.com/javase/8/docs/api/java/util/Collection.html
// Note that all interface methods for Collection should be implemented here in addition to those
// outlined below.
import java.util.Collection;
import java.util.Iterator;



//This is imported for you, and you're welcome to use an ArrayList for storage,
//or expand/shrink your own array storage, whichever you find more fun.
import java.util.ArrayList;


/**
 * this class implements a k-tree using a array representation with an arraylist.
 * @param <E> a accepts any type
 */
public class SimpleKTree<E> implements Collection<E> {
	//you code here
	//private or protected fields only
	
	//Notes: for this assignment, you may assume that the tree is always nearly complete
	//this will significantly simplify both your size() and height() methods
	/**
	 * an arraylist used to represent the k-tree and store tree values.
	 */
	protected ArrayList<E> storage;
	/**
	 * the k this type of tree is.
	 */
	protected int theK;
	/**
	 * the current size of the tree.
	 */
	protected int size;

	
	

	/**
	 * a contructor that initializes a empty tree with a certain k.
	 * @param k the number of children each parent node can have
	 */
	public SimpleKTree(int k) {
		//creates an empty k-ary tree
		//throws InvalidKException (you need to create this) if the k value is invalid (< 2)
		if(theK < 2){
			throw new InvalidKException();
		}
		storage = new ArrayList<E>();
		theK = k;
		size = 0;
		
	}
	
	/**
	 * takes an array of values and use the values to build a k-ary tree.
	 * @param arrayTree an array of values to use to build the heap
	 * @param k the k of the current tree
	 */
	public SimpleKTree(E[] arrayTree, int k) {
		//creates a k-ary tree from an array (which is already in k-ary tree array representation)
		//throws InvalidKException (you need to create this) if the k value is invalid (< 2)
		
		//NOTE: You may assume null values are not allowed in the tree, so any elements
		//in the array are real elements and the array will not contain more space than
		//is needed for the nearly complete tree (so no nulls appearing anywhere).
		if(theK < 2){
			throw new InvalidKException();
		}
		storage = new ArrayList<E>();
		for(int i = 0; i < arrayTree.length;i++){
			storage.add(arrayTree[i]);
			size++;
		}
		theK = k;
	
	}
	
	/**
	 * calculates the height of the tree using logarithm.
	 * @return the height of the current tree
	 */
	public int height() {
		//returns the height of the k-ary tree
		//can be done in O(1) time... use math!
		//worst case for this assignment: O(log-base-k(n))
		double num = Math.log((theK - 1) * (size) + 1);
		double base = Math.log(theK);
		double lol = (num / base) - 1;
		double returnThis = Math.ceil(lol);

		return (int)returnThis;
	}
	
	/**
	 * removes all the elements in the k-ary tree.
	 */
	public void clear() {
		//removes all the elements from the k-ary tree
		storage.clear();
		size = 0;
	}
	
	/**
	 * return the current size of the tree.
	 * @return the current size of the tree
	 */
	public int size(){
		return size;
	}

	/**
	 * checks whether the current tree is empty or not.
	 * @return true if empty, else returns false
	 */
	public boolean isEmpty(){
		if(size == 0){
			return true;
		}
		return false;
	}

	/**
	 * a toString method to represent the current tree in one line in  level order.
	 * @return string representation of the tree
	 */
	public String toString() {
		//creates a string representation of the current tree where each element
		//is separated by spaces
		
		//EXAMPLES:
		
		//The following is a k-ary tree of where k=2, height=2
		//(2 and 3 are children of 1; 4 and 5 are children of 2; 6 and 7 are children of 3):
		//  "1 2 3 4 5 6 7 "
		//Note the space at the end is allowed, but not required, this is also ok:
		//  "1 2 3 4 5 6 7"
		
		//The following is a k-ary tree of where k=3, height=2 
		//(2, 3, and 4 are children of 1; 5, 6, and 7 are children of 2; 8, 9, and 10 are children of 3):
		//  "1 2 3 4 5 6 7 8 9 10 "
		//Note the space at the end is allowed, but not required, this is also ok:
		//  "1 2 3 4 5 6 7 8 9 10"
		
		//NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)
		
		//HINT: Heaps are already stored in level order, you just need to return
		//the values space separated in a string!
		String returnThis = "";
		for(int i = 0; i < storage.size(); i++){
			returnThis += storage.get(i) + " ";
		}
		return returnThis;
	}
	
	/**
	 * a helper method to add each val in pre order traversal using recursion.
	 * @param index index of the head
	 * @param traversal the string to add vals in
	 * @return a string containing all vals in preorder order
	 */
	private String preOrder(int index, String traversal){
		if(index >= storage.size()){
			return traversal;
		}
		else{
			traversal += storage.get(index) + " ";
			for(int i = 1; i <= theK; i++){
				traversal = preOrder((theK*index) + i, traversal);
			}
		}
		return traversal;
	}

	/**
	 *  returns a string of the heaps value in a preorder traversal order.
	 * @return a string of values in preorder traversal order
	 */
	public String toStringPreOrder() {
		//prints out a pre-order walk of the tree
		
		//Examples for the k=2 and k=3 trees from toString():
		//    k=2:  "1 2 4 5 3 6 7 "
		//    k=3:  "1 2 5 6 7 3 8 9 10 4 "
		//Note the space at the end is allowed, but not required,
		//so for k=2 this is also ok: "1 2 4 5 3 6 7"
		
		//NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)
		
		//HINT: Think recursive helper methods and look back at Project 2!
		String returnThis = "";
		return preOrder(0, returnThis);
	}
	
	/**
	 * a helper method to traverse the heap and add values to a string
	 * in post order traversal.
	 * @param index the root of the heap
	 * @param traversal the string representation 
	 * @return a string contating values of the healp in post order order
	 */
	private String postOrder(int index, String traversal){
		if(index >= storage.size()){
			return traversal;
		}
		for(int i = 1; i <= theK;i++){
			traversal = postOrder((theK*index) + i, traversal);
		}
		traversal += storage.get(index) + " ";

		return traversal;

	}

	/**
	 * returns a string of the current heap in post order order.
	 * @return a string representation of the heap in post order traversal
	 */
	public String toStringPostOrder() {
		//prints out a post-order walk of the tree
		
		//Examples for the k=2 and k=3 trees from toString():
		//  "1 2 3 4 5 6 7 "
		//    k=2:  "4 5 2 6 7 3 1 "
		//    k=3:  "5 6 7 2 8 9 10 3 4 1 "
		//Note the space at the end is allowed, but not required,
		//so for k=2 this is also ok: "4 5 2 6 7 3 1"
		
		//NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)
		
		//HINT: Think recursive helper methods and look back at Project 2!
		String returnThis = "";
		return postOrder(0,returnThis);
	}
	
	//********************************************************************************
	//   THIS SECTION IS EXTRA CREDIT. DON'T WASTE TIME ON THESE AT THE EXPENSE OF
	//   GETTING NORMAL CREDIT FOR THE ASSIGNMENT! If you can do these in a few
	//   minutes, great, but otherwise come back later, there are many much easier
	//   points in the assignment. DO NOT DELETE METHODS YOU DON'T COMPLETE.
	//********************************************************************************
	
	/**
	 * returns a string representation of the heap in level order order.
	 * @return a string reprersentation of the healp in level order
	 */
	public String toStringWithLevels() {
		//creates a string representation of the current tree with line breaks
		//after each level of the tree
		
		//Examples for the k=2 and k=3 trees from toString():
		
		//k=2:
		//  1 
		//  2 3 
		//  4 5 6 7 
		//In string form, that is: "1 \n2 3 \n4 5 6 7 "
		//(a space after each element, a \n for each new level), the space at the end is optional
		
		//k=3:
		//  1 
		//  2 3 4 
		//  5 6 7 8 9 10 
		//In string form, that is: "1 \n2 3 4 \n5 6 7 8 9 10 "
		//(a space after each element, a \n for each new level), the space at the end is optional
		
		//NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)
		
		//HINT 1: Again, heaps are already in level order, so for this you just need to determine
		//when you're at a new level and add a line break.
		
		//Hint 2: If you know how to get the height of a nearly complete tree of
		//a given size... you can find when items are on the next "level"
		//in the same way in O(1) time.
		int level = 1;
		int count = 0;
		String returnThis = "";
		for(int i = 0; i < storage.size();i++){
			returnThis += storage.get(i) + " ";
			count++;
			if(count == level){
				level *= theK;
				count = 0;
				returnThis += "\n";
			}


		}
		return returnThis;
	}
	
	//********************************************************************************
	// Testing code... edit this as much as you want!
	//********************************************************************************
	
	/**
	 * the main method to ensure that the program compiles properly.
	 * @param arg an empty string array
	 */
	public static void main(String[] arg) {
		//maybe add some yays?
		
		
		//When you think you're ready for testing, uncomment this block. It's a
		//secondary check to make sure your code compiles with the test cases used
		//for grading.
		
		/**
		 * a random class for testing purposes.
		 */
		class Banana { }
		
		int x;
		String s;
		boolean b;
		SimpleKTree<Integer> tree1;
		SimpleKTree<Banana> tree2;
		//    k=3:  "5 6 7 2 8 9 10 3 4 1 "
		tree1 = new SimpleKTree<>(new Integer[] {1,2,3,4,5,6,7,8,9,10}, 3);
		tree2 = new SimpleKTree<>(100);
		System.out.println(tree1.toStringWithLevels());
		System.out.println(tree1.height());
		System.out.println(tree1.size());
		/*x = tree1.height();
		tree1.clear();
		s = tree1.toString();
		s = tree1.toStringPreOrder();
		s = tree1.toStringPostOrder();
		s = tree1.toStringWithLevels();
		x = tree1.size();
		b = tree1.isEmpty();
		*/
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	//********************************************************************************
	/**
	 * an exception class.
	 */
	public static class InvalidKException extends RuntimeException {};
	
	/**
	 * an add method not used for this class.
	 * @param e a value
	 * @return a boolean
	 */
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * an add all method that will not be used in this class.
	 * @param c collection of values
	 * @return  a boolean
	 */
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * a contains method that will not be used in this class.
	 * @param o a value to be searched for
	 * @return a boolean
	 */
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * a conatins all method that will not be used for this class.
	 * @param c a value to be searched for
	 * @return boolean
	 */
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * an equals methiod that will not be used in this class.
	 * @param o the value to be compared
	 * @return a boolean
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * a hashcode method that will not be used for this class.
	 * @return an integer
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * an iterator method thay will not be used for this class.
	 * @return an iterator object
	 */
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}
	/**
	 * a remove method that will not be used for this classed.
	 * @param o the object to be removed
	 * @return a boolean
	 */
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}
	/**
	 * a remove all method that will not be used for this class.
	 * @param c a collection of values to remove
	 * @return a boolean
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * a retailAll method that will not be used in this class.
	 * @param c a collection to retain 
	 * @return a boolean 
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * a toArraymethod that will not be used for this class.
	 * @return an array of objects
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * a toarray method that will not be used for this class.
	 * @param a a toArray method that will not be used in this class
	 * @param <T> values are generic type
	 * @return an array of generic objects
	 */
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
}