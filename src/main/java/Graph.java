public interface Graph {

    /// Returns the number of vertices in the graph
    int getN();
    /// Returns the number of edges in the graph
    int getM();
    /// Returns the list of vertices neighbouring v
    int[] getNeighbours(int v);
    // Add an edge
    void addEdge(int v, int w);
}
