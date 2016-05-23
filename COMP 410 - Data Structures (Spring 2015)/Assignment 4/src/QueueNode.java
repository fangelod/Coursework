
public class QueueNode<C> {
	C data;
	QueueNode<C> next;
	
	public QueueNode(C d, QueueNode<C> n) {
		data = d;
		next = n;
	}
}
