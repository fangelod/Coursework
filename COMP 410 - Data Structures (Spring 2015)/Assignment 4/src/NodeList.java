
public class NodeList {
	private int currentSize;
	private NodeElement<Object> front;
	private NodeElement<Object> rear;
	
	public NodeList() {
		setFront(null);
		setRear(null);
		currentSize = 0;
	}
	
	public NodeElement<Object> getFront() {
		return front;
	}
	
	public void setFront(NodeElement<Object> front) {
		this.front = front;
	}
	
	public NodeElement<Object> getRear() {
		return rear;
	}
	
	public void setRear(NodeElement<Object> rear) {
		this.rear = rear;
	}
	
	public boolean isEmpty() {
		return getFront() == null;
	}
	
	public Node peek() { //PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception
		}
		return getFront().data;
	}
	
	public Node dequeue() { //PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception
		}
		Node element = getFront().data;
		setFront(getFront().next);
		
		currentSize--;
		
		if (isEmpty()) {
			setRear(null);
		}
		
		return element;
	}
	
	public void enqueue(Node element) {
		NodeElement<Object> oldlast = getRear();
		setRear(new NodeElement<Object>(element, null));
		
		if (isEmpty()) {
			setFront(getRear());
		} else {
			oldlast.next = getRear();
		}
		
		currentSize++;
	}
}
