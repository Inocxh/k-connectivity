package graphs.TwoEdgeChains;

import graphs.ConnectedResult;
import graphs.Graph;
import graphs.TwoEdgeHighpoint.DFSTree;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
    This file contains the outwards facing implementation  of the 2-edge-connectivity with chains algoritm
    This implementation was mainly implemented by Magnus and tests were written by Anders Reher
 */

public class TwoEdgeChains {

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

    //Checks that the preorder is correct
    static void checkCorrectPreOrder (DFSTree T){
        assertEquals(T.getParent(0), -1);
        for (int i = 1; i < T.size(); i++) {
            assertTrue(T.orderOf(T.getParent(i)) < T.orderOf(i));
        }
    }

    //Makes sure that the amount of chains found is correct.
    static void checkAmountOfChains(Graph G, ChainDecomposition CD){
        int n = G.getN();
        int m = G.getM();
        assertEquals((m - n + 1), CD.getTestChains().size());
    }

    //Makes sure that when more that exists more then two cycles.
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

