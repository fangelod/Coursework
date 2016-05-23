
public class NodeImpl implements Node {
	private String name;
	private int id;
	
	
	public NodeImpl(String n, int i) {
		this.name = n;
		this.id = i;
	}
	
	
	public String getName() {
		return name;
	}


	public int getId() {
		return id;
	}

}
