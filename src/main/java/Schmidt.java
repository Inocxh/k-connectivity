public class Schmidt {

    public static ConnectedResult is2Connected(Graph G) {
        ChainDFSTree T = new ChainDFSTree(G, 0);
        T.printResults();

        if (T.getCEdges() < G.getM()){
            System.out.println("Not 2-edge-connected");
            return ConnectedResult.HasBridge;
        } else if (T.getCCycles() > 1){
            System.out.println("2-edge-connected, not 2-connected");
            return ConnectedResult.TwoEdgeConnected;
        } else if (T.getCCycles() == 1){
            return ConnectedResult.TwoConnected;
        } else { // Should never be reached
            return ConnectedResult.None;
        }
    }
}

