/**
 * DO NOT MODIFY THIS FILE!
 * Instead extend this class.
 */
public abstract class Graph {

    /**
     * TODO: Override this method w/ an implementation
     * Adds the provided node to the graph
     */
    public abstract void addNode(Node node);

    /**
     * Adds the provided nodes to the graph
     */
    public final void addNodes(Node... nodes) {
        for (Node node : nodes)
            addNode(node);
    }

    /**
     * TODO: Override this method w/ an implementation
     * Adds an edge between the two provided nodes to the graph.
     * PRE: The two nodes have already been added to the graph.
     */
    public abstract void addEdge(Node node1, Node node2);

    /**
     * TODO: Override this method w/ an implementation
     * Returns a node based on its unique ID.
     * This must be O(1) worst case!
     */
    public abstract Node lookupNode(int id);

    /**
     * TODO: Override this method w/ an implementation
     * Returns a node based on its unique name.
     */
    public abstract Node lookupNode(String name);

    /**
     * TODO: Override this method w/ an implementation
     * Determines if this graph has no cycles.
     */
    public abstract boolean isAcyclic();

    /**
     * TODO: Override this method w/ an implementation
     * Performs a topological sort of this graph.
     * PRE: There are no cycles in the graph.
     *
     * @return An array of node IDs, in sorted order
     */
    public abstract int[] sort();

    /**
     * This prints out an analysis of this graph.
     */
    public final void analyze() {
        if (isAcyclic()) {
            System.out.println("This graph is acyclic.");

            int[] order = sort();
            System.out.print("Sorted order: ");
            for (int id : order)
                System.out.print(lookupNode(id).getName() + " ");
            System.out.println("");
        } else
            System.out.println("This graph is cyclic.");
    }


}
