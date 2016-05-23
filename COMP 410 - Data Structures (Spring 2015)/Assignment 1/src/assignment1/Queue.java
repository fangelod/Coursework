package assignment1;
import assignment1.Node;

public class Queue<T> {
	private int currentSize;
	private Node<T> front;
	private Node<T> rear;
	
	public Queue() {
		setFront(null);
		setRear(null);
		currentSize = 0;
	}
	
	public Node<T> getFront() {
		return front;
	}
	
	public void setFront(Node<T> front) {
		this.front = front;
	}
	
	public Node<T> getRear() {
		return rear;
	}
	
	public void setRear(Node<T> rear) {
		this.rear = rear;
	}
	
	public boolean isEmpty() {
		return getFront() == null;
	}
	
	public T peek() { //PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception
		}
		return getFront().data;
	}
	
	public T dequeue() { //PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception
		}
		T element = getFront().data;
		setFront(getFront().next);
		
		currentSize--;
		
		if (isEmpty()) {
			setRear(null);
		}
		
		return element;
	}
	
	public void enqueue(T element) {
		Node<T> oldlast = getRear();
		setRear(new Node<T>(element, null));
		
		if (isEmpty()) {
			setFront(getRear());
		} else {
			oldlast.next = getRear();
		}
		
		currentSize++;
	}
}
