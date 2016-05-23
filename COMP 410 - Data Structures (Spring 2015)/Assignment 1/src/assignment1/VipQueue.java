package assignment1;

public class VipQueue<T> {
	private int currentSize;
	private Queue<T> vipQ;
	
	public VipQueue() {
		vipQ = new Queue<T>();
		currentSize = 0;
	}
	
	public boolean isEmpty() {
		return vipQ.getFront() == null;
	}
	
	public T peek() { // PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception 
		}
		return vipQ.getFront().data;
	}
	
	public T dequeue() { // PRE: Queue is not empty
		if (isEmpty()) {
			// Assume that it will not run if queue is empty
			// Run exception
		}
		T element = vipQ.getFront().data;
		vipQ.setFront(vipQ.getFront().next);
		
		currentSize--;
		
		if (isEmpty()) {
			vipQ.setRear(null);
		}
		
		return element;
	}
	
	public void enqueue(T element) {
		Node<T> oldlast = vipQ.getRear();
		vipQ.setRear(new Node<T>(element, null));
		
		if (isEmpty()) {
			vipQ.setFront(vipQ.getRear());
		} else {
			oldlast.next = vipQ.getRear();
		}
		
		currentSize++;
	}
	
	public void vipEnqueue(T element) {
		Queue<T> temp = vipQ;
		Queue<T> frontQ = new Queue<T>();
		
		frontQ.enqueue(element);
		
		while (!temp.isEmpty()) {
			frontQ.enqueue(temp.dequeue());
		}
		
		vipQ = frontQ;
		
		currentSize++;
	}
}

