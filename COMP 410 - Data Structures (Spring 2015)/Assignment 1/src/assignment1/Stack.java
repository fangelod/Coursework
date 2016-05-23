package assignment1;
import assignment1.Node;

public class Stack<T> {
	private Node<T> front;
	private int currentSize;
	
	public Stack() {
		front = null;
		currentSize = 0;
	}
	
	public boolean isEmpty() {
		return (front == null);
	}
	
	public T peek() { //PRE: Stack is not empty
		if (isEmpty()) {
			// Assume that it will not run if stack is empty
			// Run exception
		}
		return front.data;
	}
	
	public T pop() { //PRE: Stack is not empty
		if (isEmpty()) {
			// Assume that it will not run if stack is empty
			// Run exception
		}
		T element = front.data;
		front = front.next;
		
		currentSize--;
		
		return element;
	}
	
	public void push(T element) {
		Node<T> lastFront = front;
		front = new Node<T>(element, lastFront);
		
		currentSize++;
	}
}
