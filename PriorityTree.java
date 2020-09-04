import java.util.Queue;
import java.util.NoSuchElementException;
import java.util.Comparator;

//For interface Queue, see: http://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
//Note that all interface methods for Queue should be implemented here, those for
//the Collection interface should be in SimpleTree.

//IMPORTANT: This class _extends_ SimpleKTree, don't re-implement anything here, use inheritance!

//NOTE: You may assume null values are not allowed in the Queue.
/**
 * a priority tree that implememts a Queue to represent the heap data structure.
 * @param <E> the values in the tree are generic
 */
public class PriorityTree<E> extends SimpleKTree<E> implements Queue<E> {
	//you code here
	//private or protected fields only
	/**
	 * a comparorator object used to compare each value with different priorities.
	 */
	private Comparator<? super E> comp;


	
	


	
	/**
	 * a contructor that initializes the tree's storage and the comparorator to be used.
	 * @param comp a comparorator used to distinguish each object
	 * @param k the amount of children a parent node can have
	 */
	public PriorityTree(Comparator<? super E> comp, int k) {
		//constructor which takes a comparator to be used later when adding/swapping
		//values in the queue and the k value of the k-ary tree
		
		//if you are unfamiliar with the Comparator interface, you can find it here:
		//see: http://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
		//it's just a way of comparing objects using something other than their
		//"natural ordering"
		super(k);
		this.comp = comp;
		
	}
	/**
	 * a contnstructor that includes a array that will be used to be heapafied and use
	 * to build the tree from the bottom up.
	 * @param comp a compararator to be used to distinguish each object
	 * @param arrayTree an array that will be heapified and used to build the tree
	 * @param k the amount of children a parent node can have
	 */
	public PriorityTree(Comparator<? super E> comp, E[] arrayTree, int k) {
		//OPTIONAL METHOD (you do not get extra points for doing it, just an imaginary pat on the
		//back). Takes an array representation of a tree and performs "heapify" to create the queue
		//use the method which forms the heap from the bottom up (not the method that repeatedly
		//adds elements). This may (or may not) be covered in all sections of the class, so
		//it is not required, but encouraged for those interested.
		
		//DO NOT DELETE METHODS YOU DON'T COMPLETE.
		
		super(-1);
	}

	/**
	 * a helper method  used to shift a child upwards in a tree if the child breaks 
	 * the rules of a heap.
	 * @param nodeIndex the node to be rotated upward
	 */
	private void shiftUp(int nodeIndex){
		int parentIndex;
		int tempIndex;
		if(nodeIndex != 0){
			parentIndex = (nodeIndex - 1) / theK;
			int test = comp.compare(storage.get(parentIndex), storage.get(nodeIndex));
			if(comp.compare(storage.get(parentIndex), storage.get(nodeIndex)) > 0){
				E temp = storage.get(parentIndex);
				storage.set(parentIndex,storage.get(nodeIndex));
				storage.set(nodeIndex,temp);
				shiftUp(parentIndex);
			}
		}
	}
	
	/**
	 * a method to add a value in the current tree.
	 * @param e value to be added
	 * @return returns true
	 */
	public boolean add(E e){
		size++;
		storage.add(e);
		shiftUp(storage.size() - 1);
		return true;
	}
	/**
	 * returns the root of tree.
	 * @return the root of the tree, null if empty.
	 */
	public E peek(){
		if(storage.size() == 0){
			return null;
		}

		return storage.get(0);
	}

	/**
	 * returns the root of the tree.
	 * @return the root of the tree, throws an exception if empty
	 */
	public E element(){
		if(storage.size() == 0){
			throw new NoSuchElementException();
		}

		return storage.get(0);
	}

	/**
	 * removes the root of the tree.
	 * @return the root of the tree, null if tree was empty
	 */
	public E poll(){
		if(storage.size() == 0){
			return null;
		}
		
		return remove();
	}
	/**
	 * a helper method to shift a node down a tree if the node breaks any 
	 * rules of the heap.
	 * @param nodeIndex the node to be rotated
	 */
	private void shiftDown(int nodeIndex){
		int childIndex = (theK * nodeIndex) + 1;
		E value = storage.get(nodeIndex);

		while(childIndex < storage.size()){
			E maxPrior = value;
			int maxIndex = -1;

			for(int i = 0; i < theK && i + childIndex < storage.size(); i++){
				if(comp.compare(storage.get(i+childIndex), maxPrior) < 0){
					maxPrior = storage.get(i + childIndex);
					maxIndex = i + childIndex;
				}
			}

			if(maxPrior.equals(value)){
				return;
			}

			else{
				E temp = storage.get(nodeIndex);
				storage.set(nodeIndex,maxPrior);
				storage.set(maxIndex, temp);
				nodeIndex = maxIndex;
				childIndex = (theK * nodeIndex) + 1;

			}

				
			
		}
	}
	/**
	 * remove the head of the tree.
	 * @return the root of the tree, throws exception if empty
	 */
	public E remove(){
		if(storage.size() == 0){
			throw new NoSuchElementException();
		}
		E returnThis = storage.get(0);
		storage.set(0,storage.get(storage.size() - 1));
		size--;
		storage.remove(storage.size() - 1);
		if(storage.size() > 0){
			shiftDown(0);
		}
		return returnThis;
	}

	/**
	 * adds a value the the tree.
	 * @param e the value to be added
	 * @return true when value is added
	 */
	public boolean offer(E e){
		add(e);

		return true;
	}
	
	//you code here
	//private or protected methods only (except those required by the Queue interface)
	
	
	
	//********************************************************************************
	// Testing code... edit this as much as you want!
	//********************************************************************************
	
	/**
	 * the main method used to ensure this program compiles correctly.
	 * @param arg a empty string array
	 */
	public static void main(String[] arg) {
		//maybe add some yays?
		
		
		//When you think you're ready for testing, uncomment this block. It's a
		//secondary check to make sure your code compiles with the test cases used
		//for grading.
		
		/**
		 * a random class to creates objects to be added in the heap.
		 */
		class Banana { int size; }
		
		int x;
		String s;
		boolean b;
		Banana a;
		PriorityTree<Banana> tree1;
		PriorityTree<String> tree2;
		
		
		//comparator for bananas in size order
		Comparator<Banana> comp = new Comparator<Banana>() {
			public int compare(Banana o1, Banana o2) { return ((Integer)(o1.size)).compareTo(o2.size); }
		};
		
		//comparator for strings in reverse alphabetical order
		Comparator<String> revComp = new Comparator<String>() {
			public int compare(String o1, String o2) { return o2.compareTo(o1); }
		};
		
		
		//tree1 = new PriorityTree<>(comp, 2);
		//tree2 = new PriorityTree<>(revComp, new String[] {"a", "b", "c"}, 7);
		
		tree2 = new PriorityTree<>(revComp,3);
		tree2.add("a");
		tree2.add("b");
		tree2.add("c");
		tree2.add("d");
		tree2.add("e");
		tree2.add("f");
		tree2.remove();
		//tree2.add("a");
		System.out.println(tree2.height());
		System.out.println(tree2);
		System.out.println(tree2.toStringPreOrder());
		System.out.println(tree2.toStringPostOrder());
		System.out.println(tree2.toStringWithLevels());

		/*b = tree1.add(new Banana());
		System.out.println(b);
		b = tree1.offer(new Banana());
		System.out.println(b);
		a = tree1.element();
		System.out.println(a);
		a = tree1.peek();
		System.out.println(b);
		a = tree1.poll();
		System.out.println(b);
		a = tree1.remove();
		System.out.println(b);
		
		x = tree1.height();
		System.out.println(x);
		tree1.clear();
		s = tree1.toString();
		System.out.println(s);
		s = tree1.toStringPreOrder();
		System.out.println(s);
		s = tree1.toStringPostOrder();
		System.out.println(s);
		s = tree1.toStringWithLevels();
		System.out.println(s);
		x = tree1.size();
		System.out.println(x);
		b = tree1.isEmpty();
		System.out.println(b);
		*/
	}
}