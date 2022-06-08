package graphs;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Schmidt {

    public static ConnectedResult is2Connected(Graph G) {
        DFSTree T = new DFSTree(G, 0, false);
        ChainDecomposition CD = new ChainDecomposition(T, false);

        //TEST ASSERTION
        checkCorrectPreOrder(T);
        checkAmountOfChains(G, CD);

        if (CD.getCEdges() < G.getM()){
            //System.out.println("Not 2-edge-connected");
            return ConnectedResult.HasBridge;
        } else if (CD.getCCycles() > 1){
            //System.out.println("2-edge-connected, not 2-connected");
            correctAmountCycle(CD);
            return ConnectedResult.TwoEdgeConnected;
        } else if (CD.getCCycles() == 1){
            return ConnectedResult.TwoConnected;
        } else { // Should never be reached
            return ConnectedResult.None;
        }


    }

    @Test
    static void checkCorrectPreOrder (DFSTree T){
        assertEquals(T.getParent(0), -1);
        for (int i = 1; i < T.size(); i++) {
            assertTrue(T.orderOf(T.getParent(i)) < T.orderOf(i));
        }
    }

    @Test
    static void checkAmountOfChains(Graph G, ChainDecomposition CD){
        int n = G.getN();
        int m = G.getM();
        assertEquals((m - n + 1), CD.getTestChains().size());
    }

    @Test
    static void correctAmountCycle(ChainDecomposition CD){
        int amountCycle = 0;
        for (Chain C : CD.getTestChains()){
            if (C.vertices.get(0).equals(C.getTerminal())) {
                amountCycle ++;
            }
        }
        assertTrue(amountCycle > 1);
    }
}

