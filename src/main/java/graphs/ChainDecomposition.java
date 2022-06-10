    package graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

    public class ChainDecomposition {
    //TEST VARIABLES
    public static ArrayList<Chain> testChains;

    //REAL VARIABLES
    public static ArrayList<Chain> chains;
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

        assert initiateVars();
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
                    chains.add(new Chain(chain));
                }
                assert checkBackedgeStart(T, chain);
            }
        }
        assert checkVisitedVertices(T, visited);
    }

    public int getCEdges(){
        return edgeCounter;
    }
    public int getCCycles(){
        return cycleCounter;
    }
    public int getNumberOfChains() { return chains.size(); }

    //Helper function for correctness test
    public static boolean initiateVars(){
        boolean result = true;
        testChains = new ArrayList<>();
        return result;
        }

    @Test
    //Makes sure that every vertex is visited
    public static boolean checkVisitedVertices (DFSTree T, HashSet<Integer> visited) {
        int n  = T.getVertices().length;
        return visited.size() == n;
    }

    @Test
    //Makes sure every chain starts with a backedge
    public static boolean checkBackedgeStart (DFSTree T, ArrayList<Integer> chain){
        addTestChain(chain);
        Integer v0 = chain.get(0);
        return T.getBackEdges(v0).contains(chain.get(1));
    }
    //Helper function for correctness test
    public static void addTestChain(ArrayList<Integer> chain){
        testChains.add(new Chain(chain));
    }

    //Helper function for correctness test
    public ArrayList<Chain> getTestChains(){
        return testChains;
    }

    @Test
    //Checks the 6 parts of lemma 2 found in the article certifying 3-edge-connectivity by Kurt Mehlhorn
    public static boolean chainCheckLemma2 (ChainDecomposition chainDecomposition, DFSTree dfsTree){
        ArrayList<Chain> chains = chainDecomposition.chains;
        boolean result1 = false, result2 = false, result3 = false, result4 = false;
        for(Chain chain : chains){
            int sC = dfsTree.orderOf(chain.vertices.get(0));
            int tC = dfsTree.orderOf(chain.getTerminal());
            //Checks 1 of lemma 2
            result1 = sC <= tC;
            //Checks 2 and 3 of lemma 2
            if (chains.indexOf(chain) >= 1) {
                Chain parentChain = chains.get(chain.getParent());
                int sPC = dfsTree.orderOf(parentChain.vertices.get(0));
                int tPC = dfsTree.orderOf(parentChain.getTerminal());
                if (sPC <= sC && tC != dfsTree.dfsToVertex(0) && tPC < tC){
                    result2 = true;
                } else result2 = sPC <= sC && tC == dfsTree.dfsToVertex(0) && tPC == tC;
            }
            //Checks 4, 5 for lemma 2
            for (int v = 0; v < dfsTree.size() - 1; v++){
                for (int u = 0; u < dfsTree.size() - 1; u++){
                    for (Chain dChain : chains){
                        if (u < v && chains.get(chainDecomposition.verticesSBelong[u]).equals(chain) && chains.get(chainDecomposition.verticesSBelong[v]).equals(dChain)){
                            result3 = chains.indexOf(chain) <= chains.indexOf(dChain);
                        }
                        if (u < dChain.getTerminal() && chains.get(chainDecomposition.verticesSBelong[u]).equals(chain)){
                            result3 = chains.indexOf(chain) <= chains.indexOf(dChain);
                        }

                    }
                }
            }
            //Checks 6 for lemma 2
            if (chains.indexOf(chain) >= 1){
                Chain chain2 = chains.get(chainDecomposition.verticesSBelong[sC]);
                result4 = chains.indexOf(chain) > chains.indexOf(chain2);
            }
        }
        return (result1 && result2 && result3 && result4);

    }

}

