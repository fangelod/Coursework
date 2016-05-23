/**
 * DO NOT MODIFY THIS FILE!
 * Defines how a node should be used.
 * TODO: Create a class which implements this.
 */
public interface Node {

    /**
     * @return The node's unique name
     */
    public String getName();

    /**
     * This returns the node's ID. The ID is not the hash, but rather a unique integer from 0 to |V|, where |V| is the
     * number of nodes in the graph.
     */
    public int getId();

}
