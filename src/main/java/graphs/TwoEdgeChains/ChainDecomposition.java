package graphs.TwoEdgeChains;

import graphs.TwoEdgeHighpoint.DFSTree;

import java.util.ArrayList;
import java.util.HashSet;

public class ChainDecomposition {
    public ArrayList<Chain> chains;
    public int edgeCounter = 0;
    public int cycleCounter = 0;
    public int[] verticesSBelong;

    public ChainDecomposition(DFSTree T, boolean mehlhorn){

        if(mehlhorn) {
            verticesSBelong = new int[T.size()];
            verticesSBelong[0] = 0;
            chains = new ArrayList<>();
        }

        HashSet<Integer> visited = new HashSet<>();

        // For all vertices in increasing order
        for (int vertex : T.dfsOrder()) {
            // For all external edges
            for (int eN : T.getBackEdges(vertex)){
                // Create new chain
                ArrayList<Integer> chain = new ArrayList<>();
                // Add start edge, mark visited and inc edge counter
                chain.add(vertex);
                chain.add(eN);
                visited.add(vertex);
                edgeCounter++;

                // Traverse towards root. Add edges underway and mark visited
                int currentVertex = eN;
                while (!visited.contains(currentVertex)) {
                    if (mehlhorn) {
                        verticesSBelong[currentVertex] = chains.size();
                    }

                    visited.add(currentVertex);
                    currentVertex = T.getParent(currentVertex);
                    chain.add(currentVertex);
                    edgeCounter++;
                }
                // When traversing stops add chain, and check for chain cycle
                if (chain.get(0) == chain.get(chain.size() - 1)){
                    cycleCounter++;
                }
                // if needed save the chain
                if (mehlhorn) {
                    chains.add(new Chain(chain));
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
    public int getNumberOfChains(){
        return chains.size();
    }
}

