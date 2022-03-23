
public class Schmidt {

    public static ConnectedResult is2Connected(Graph G) {
        DFSTree T = new DFSTree(G, 0, false);
        ChainDecomposition CD = new ChainDecomposition(T, false);

        if (CD.getCEdges() < G.getM()){
            System.out.println("Not 2-edge-connected");
            return ConnectedResult.HasBridge;
        } else if (CD.getCCycles() > 1){
            System.out.println("2-edge-connected, not 2-connected");
            return ConnectedResult.TwoEdgeConnected;
        } else if (CD.getCCycles() == 1){
            return ConnectedResult.TwoConnected;
        } else { // Should never be reached
            return ConnectedResult.None;
        }
    }
}

