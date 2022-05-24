    package graphs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

    public class ChainDecomposition {
    //TEST VARIABLES
    public static ArrayList<Chain> chains;

    //REAL VARIABLES
    public int edgeCounter = 0;
    public int cycleCounter = 0;
    public int[] verticesSBelong;
    private ArrayList<ArrayList<Integer>> verticesToSC;

    public ChainDecomposition(DFSTree T, boolean mehlhorn){

        if(mehlhorn) {
            verticesSBelong = new int[T.size()];
            verticesToSC = new ArrayList<>(T.size());
            for (int i=0; i<T.size(); i++) {
                verticesToSC.add(null);
            }
            verticesSBelong[0] = 0;
            chains = new ArrayList<>();
        }

        HashSet<Integer> visited = new HashSet<>();

        initiateVars();
        // For all vertices in increasing order
        for (int vertex : T.dfsOrder()) {
            visited.add(vertex);
            // For all external edges
            for (int eN : T.getBackEdges(vertex)){
                // Create new chain
                ArrayList<Integer> chain = new ArrayList<>();
                // Add start edge, mark visited and inc edge counter
                chain.add(vertex);
                chain.add(eN);
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
                    if(verticesToSC.get(chain.get(0)) == null) {
                        verticesToSC.set(chain.get(0), new ArrayList<>());
                    }
                    verticesToSC.get(chain.get(0)).add(chains.size()-1);
                }
                addChain(chain);
                checkBackedgeStart(T, chain);
            }
        }
        checkVisitedVertices(T, visited);
    }

    @BeforeAll
    static void initiateVars(){
        chains = new ArrayList<>();
    }

    @Test
    static void checkVisitedVertices (DFSTree T, HashSet<Integer> visited) {
        int n  = T.getVertices().length;
        assertEquals(visited.size(), n);
    }

    @Test
    static void checkBackedgeStart (DFSTree T, ArrayList<Integer> chain){
        Integer v0 = chain.get(0);
        assertTrue(T.getBackEdges(v0).contains(chain.get(1)));
    }

    @Test
    static void addChain(ArrayList<Integer> chain){
        chains.add(new Chain(chain));
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
    public ArrayList<Integer> getVerticesToSC(int i) { return verticesToSC.get(i);}
    public void computeParentChains() {
        for (int i=1; i< this.getNumberOfChains(); i++) {
                int terminal = chains.get(i).terminal;
                chains.get(i).setParent(verticesSBelong[terminal]);
        }
    }
    public ArrayList<Chain> getChains(){return chains;}
}

