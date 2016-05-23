/**
 * Factory used for creating graphs
 */
public final class GraphFactory {

    public static Graph create(int nodeCount) {
        return new GraphImpl(nodeCount);
    }

}
