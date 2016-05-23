/**
 * Factory used for creating nodes
 */
public final class NodeFactory {
	private static int idCounter = 0;

    public static Node create(String name) {
    	Node made = new NodeImpl(name, idCounter);
    	idCounter++;
    	
        return made;
    }

}
