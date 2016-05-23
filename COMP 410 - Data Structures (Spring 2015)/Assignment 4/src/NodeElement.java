
public class NodeElement<Object> {
	Node data;
	NodeElement<Object> next;
	
	public NodeElement(Node d, NodeElement<Object> n) {
		data = d;
		next = n;
	}
	
	public Node getData() {
		return data;
	}
}
