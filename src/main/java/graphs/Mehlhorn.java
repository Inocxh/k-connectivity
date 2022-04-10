package graphs;

import java.util.Arrays;

public class Mehlhorn {
    private static boolean printV = true;

    public static ConnectedResult is3EdgeConnected(Graph G) {
        DFSTree T = new DFSTree(G, 0, false);
        ChainDecomposition chainDecomposition = new ChainDecomposition(T, true);
        chainDecomposition.computeParentChains();
        CurrentGraph currentGraph = new CurrentGraph(chainDecomposition.chains.get(0), chainDecomposition.chains.get(1), G.getN(), T);

        if (printV) {
            for (int i = 0; i < chainDecomposition.getNumberOfChains(); i++) {
                System.out.println("this is chain: " + i);
                System.out.println(chainDecomposition.chains.get(i).vertices);
                System.out.println("this is parent for chain: " + i);
                if (chainDecomposition.chains.get(i).parent != null) {
                    System.out.println(chainDecomposition.chains.get(i).parent.vertices);
                } else {
                    System.out.println("no parent its the root");
                }
            }

            System.out.println("this is where the source of every chain is located: ");
            for (int i = 0; i < T.size(); i++) {
                if (chainDecomposition.getVerticesToSC(i) != null) {
                    System.out.printf("vertex: %d is the source to the following Chains: %s \n", i, chainDecomposition.getVerticesToSC(i).toString());
                }
            }
        }

        // proccessing one chain at a time
        for (int i=0; i < chainDecomposition.chains.size(); i++) {
            if (printV) {
                System.out.println("The degree of the vertices are: ");
                System.out.println(Arrays.toString(currentGraph.getDegreeOfVertices()));
                //printing info about the current graph
                System.out.printf("The branch vertices on chain %d are: \n", i);

                for (int j : currentGraph.getBranchVertices(chainDecomposition.chains.get(i))) {
                    System.out.println(j);
                }
            }
            boolean cutFound = currentGraph.processChain(chainDecomposition.chains.get(i),  chainDecomposition);
            if (printV) {
                int j = 0;
                for (SegmentOwn segment : currentGraph.getSegments()) {
                    System.out.printf("This is the minimal chain of segment %d: \n", j);
                    System.out.println(segment.getMinimalChain().vertices);
                    System.out.println("The segment contains the chains");
                    for (Chain chains : segment.getChains())
                        System.out.println(chains.vertices);
                    j++;
                }
            }
            if (cutFound) {
                System.out.printf("\n The graph is not connected \n When trying to add the chains with source on chain: " + chainDecomposition.chains.get(i).vertices + "\n A cut was found \n");
                return ConnectedResult.NotThreeEdgeConnected;
            }

        }

        return ConnectedResult.ThreeEdgeConnected;
    }
}
