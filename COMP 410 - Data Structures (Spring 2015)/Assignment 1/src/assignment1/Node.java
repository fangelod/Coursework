package assignment1;

public class Node<C> {
	C data;
	Node<C> next;
	
	public Node(C d, Node<C> n) {
		data = d;
		next = n;
	}
}
