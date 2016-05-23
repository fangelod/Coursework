
public class GraphImpl extends Graph {
	private NodeList[] tree;
	private int currentIndex;
	private HashTable<String, Node> search;
	private int[] indegree;
	private int num;
	private int[] topo;
	
	
	public GraphImpl(int nodeCount) {
		this.num = nodeCount;
		tree = new NodeList[nodeCount];
		search = HashTableFactory.create();
		indegree = new int[nodeCount];
		topo = new int[nodeCount];
	}
	

	@Override
	public void addNode(Node node) {
		NodeImpl n = new NodeImpl(node.getName(), node.getId());
		
		if (tree[currentIndex] == null) {
			NodeList temp = new NodeList();
			temp.enqueue(node);
			tree[currentIndex] = temp;
		}
		
		currentIndex++;
		
		search.put(node.getName(), node);
	}


	@Override
	public void addEdge(Node node1, Node node2) {
		int index = -1;
		
		NodeImpl n = new NodeImpl(node2.getName(), node2.getId());
		NodeImpl f = new NodeImpl(node1.getName(), node1.getId());
		for (int i = 0; i < currentIndex; i++) {
			if (tree[i].getFront().getData() == node1) {
				index = i;
				break;
			}
		}
		
		if (index != -1) {
			tree[index].enqueue(n);
		}
	}


	@Override
	public Node lookupNode(int id) {
		if(tree[id].getFront().getData().getId() == id) {
			return tree[id].getFront().getData();
		}
		
		return null;
	}


	@Override
	public Node lookupNode(String name) {
		return search.get(name);
	}

	/**
	 * Determines if this graph has no cycles.
	 */
	@Override
	public boolean isAcyclic() {
		for (int k = 0; k < num; k++) {
			indegree[k] = 0;
		}
		
		for (int k = 0; k < num; k++) {
			if (!tree[k].isEmpty()) {
				NodeElement curr = tree[k].getFront();
				while (curr.next != null) {
					curr = curr.next;
					for (int j = 0; j < num; j++) {
						if (tree[j].getFront().getData().getId() == curr.getData().getId()) {
							indegree[j] += 1;
							break;
						}
					}
				}
			}
		}
		
		Queue<Integer> Q = new Queue<Integer>();
		for (int k = 0; k < num; k++) {
			if (indegree[k] == 0) {
				Q.enqueue(k);
			}
		}
		
		int i = -1;
		for (int k = 0; k < num; k++) {
			if (Q.isEmpty()) {
				return false;
			} else {
				i = Q.dequeue();
				
			}
			if (!tree[k].isEmpty()) {
				NodeElement curr = tree[i].getFront();
				while (curr.next != null) {
					curr = curr.next;
					for (int l = 0; l < num; l++) {
						if (tree[l].getFront().getData().getId() == curr.getData().getId()) {
							indegree[l] -= 1;
							if (indegree[l] == 0) {
								Q.enqueue(l);
							}
							break;
						}
					}
				}
			}
		}
		
		return true;
	}

	/**
	 * Performs a topological sort of this graph.
     * PRE: There are no cycles in the graph.
     *
     * @return An array of node IDs, in sorted order
	 */
	@Override
	public int[] sort() {
		for (int k = 0; k < num; k++) {
			indegree[k] = 0;
		}
		
		for (int k = 0; k < num; k++) {
			if (!tree[k].isEmpty()) {
				NodeElement curr = tree[k].getFront();
				while (curr.next != null) {
					curr = curr.next;
					for (int j = 0; j < num; j++) {
						if (tree[j].getFront().getData().getId() == curr.getData().getId()) {
							indegree[j] += 1;
							break;
						}
					}
				}
			}
		}
		
		Queue<Integer> Q = new Queue<Integer>();
		for (int k = 0; k < num; k++) {
			if (indegree[k] == 0) {
				Q.enqueue(k);
			}
		}
		
		int i = -1;
		for (int k = 0; k < num; k++) {
			if (Q.isEmpty()) {
				
			} else {
				i = Q.dequeue();
				topo[k] = i;
			}
			if (!tree[k].isEmpty()) {
				NodeElement curr = tree[i].getFront();
				while (curr.next != null) {
					curr = curr.next;
					for (int l = 0; l < num; l++) {
						if (tree[l].getFront().getData().getId() == curr.getData().getId()) {
							indegree[l] -= 1;
							if (indegree[l] == 0) {
								Q.enqueue(l);
							}
							//break;
						}
					}
				}
			}
		}
		
		return topo;
	}

}