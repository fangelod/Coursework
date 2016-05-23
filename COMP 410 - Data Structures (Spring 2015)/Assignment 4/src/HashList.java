
public class HashList<Object> {
	private int currentSize;
	private HashNode<Object> front;
	private HashNode<Object> rear;
	
	public HashList() {
		setFront(null);
		setRear(null);
		currentSize = 0;
	}
	
	public HashNode<Object> getFront() {
		return front;
	}
	
	public void setFront(HashNode<Object> front) {
		this.front = front;
	}
	
	public HashNode<Object> getRear() {
		return rear;
	}
	
	public void setRear(HashNode<Object> rear) {
		this.rear = rear;
	}
	
	public boolean isEmpty() {
		return getFront() == null;
	}
	
	public HashEntry peek() { //PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception
		}
		return getFront().data;
	}
	
	public HashEntry dequeue() { //PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception
		}
		HashEntry element = getFront().data;
		setFront(getFront().next);
		
		currentSize--;
		
		if (isEmpty()) {
			setRear(null);
		}
		
		return element;
	}
	
	public void enqueue(HashEntry element) {
		HashNode<Object> oldlast = getRear();
		setRear(new HashNode<Object>(element, null));
		
		if (isEmpty()) {
			setFront(getRear());
		} else {
			oldlast.next = getRear();
		}
		
		currentSize++;
	}
}
	
