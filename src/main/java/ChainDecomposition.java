import com.sun.net.httpserver.Filter;

import java.util.ArrayList;

public class ChainDecomposition {
    public ArrayList<ArrayList<Integer>> chains = new ArrayList<>();;
    public int edgeCounter;
    public int cycleCounter;

    public ChainDecomposition(DFSTree T){
        this(T, true);
    }
    public ChainDecomposition(DFSTree T, boolean store){
        // For all vertices in increasing order
        for (int vertex : T.dfsOrder()) {
            // For all external edges
            for (int eN : T.vertices[vertex].downEdges){
                // Create new chain
                ArrayList<Integer> chain = new ArrayList<>();
                // Add start edge, mark visited and inc edge counter
                chain.add(vertex);
                chain.add(eN);
                T.vertices[vertex].visited = true;
                edgeCounter++;

                // Traverse towards root. Add edges underway and mark visited
                int currentVertex = eN;
                while (!T.vertices[currentVertex].visited) {
                    T.vertices[currentVertex].visited = true;
                    currentVertex = T.vertices[currentVertex].parent;
                    chain.add(currentVertex);
                    edgeCounter++;
                }
                // When traversing stops add chain, and check for chain cycle
                if (chain.get(0) == chain.get(chain.size() - 1)){
                    cycleCounter++;
                }

                if (store) {
                    chains.add(chain);
                }

            }
        }
    }
    public int getCEdges(){
        return edgeCounter;
    }
    public int getCCycles(){
        return cycleCounter;
    }



}
