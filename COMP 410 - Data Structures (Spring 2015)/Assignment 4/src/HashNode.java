
public class HashNode<Object> {
	HashEntry data;
	HashNode<Object> next;
	
	public HashNode(HashEntry d, HashNode<Object> n) {
		data = d;
		next = n;
	}
	
	public HashEntry getData() {
		return data;
	}
}
