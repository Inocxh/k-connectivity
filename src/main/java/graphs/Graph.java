package graphs;

import java.util.List;

public interface Graph {
    /// Returns the number of vertices in the graph
    int getN();
    /// Returns the number of edges in the graph
    int getM();
    /// Returns the list of vertices neighbouring v
    List<Integer> getNeighbours(int v);
}
