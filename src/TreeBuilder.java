
public class TreeBuilder<T> {

	private LinkedBinaryTree<T> tree;
	
	public TreeBuilder(T[] array) { // constructor
	
		LinkedQueue<T> dataQueue = new LinkedQueue<T>(); // queue to hold array elements				
		LinkedQueue<BinaryTreeNode<T>> parentQueue = new LinkedQueue<BinaryTreeNode<T>> (); // initialize empty queue

		for (int x = 0; x < array.length; x++) {	// loop through elements in array and transfer to dataQueue	
			
			dataQueue.enqueue(array[x]);
			
			}

		BinaryTreeNode<T> root = new BinaryTreeNode<T>(dataQueue.dequeue());
		parentQueue.enqueue(root);
		
		while (!dataQueue.isEmpty()) {			
			BinaryTreeNode<T> a = new BinaryTreeNode<T>(dataQueue.dequeue()); // child node
			BinaryTreeNode<T> b = new BinaryTreeNode<T>(dataQueue.dequeue());	// child node
			BinaryTreeNode<T> parent = parentQueue.dequeue();
			
			if (a.getData() != null) {
				parent.setLeft(a); 	// set left node of parent and enqueue into parentQueue
				parentQueue.enqueue(a);
				
				}	// end if
			
			if (b.getData() != null) {
				parent.setRight(b);		// set right node of parent and enqueue into parentQueue
				parentQueue.enqueue(b);						
				
				} // end if		
			
			tree = new LinkedBinaryTree<T>(root);	// intialize tree 
			
			} // end while loop
		
		
		} // end Constructor
		
		public LinkedBinaryTree<T> getTree(){
			
			return tree;		
			
		}	// end method getTree
	
}
