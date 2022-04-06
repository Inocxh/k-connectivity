package graphs;

import java.util.Arrays;

public class Mehlhorn {
    public static ConnectedResult is3EdgeConnected(Graph G) {
        DFSTree T = new DFSTree(G, 0, false);
        ChainDecomposition chainDecomposition = new ChainDecomposition(T, true);
        chainDecomposition.computeParentChains();
        CurrentGraph currentGraph = new CurrentGraph(chainDecomposition.chains.get(0), chainDecomposition.chains.get(1), G.getN());
        for(int i=0; i<chainDecomposition.getNumberOfChains(); i++) {
            System.out.println("this is chain: " + i);
            System.out.println(chainDecomposition.chains.get(i).vertices);
            System.out.println("this is parent for chain: " + i);
            if (chainDecomposition.chains.get(i).parent != null) {
                System.out.println(chainDecomposition.chains.get(i).parent.vertices);
            }
            else {
                System.out.println("no parent its the root");
            }
        }
        // this is just for seeing the output.
        System.out.println("this is where the source of every chain is located: ");
        for (int i=0; i<T.size(); i++) {
            if(chainDecomposition.getVerticesToSC(i) != null) {
                System.out.println(chainDecomposition.getVerticesToSC(i).toString());
            }
        }

        /*for (int i=0; i < chainDecomposition.chains.size(); i++) {
            currentGraph.processChain(chainDecomposition.chains.get(i),  chainDecomposition);
        } */

        //printing info about the current graph
        System.out.println("The branch vertices on the first chain are: \n");
        for (int i : currentGraph.getBranchVertices(chainDecomposition.chains.get(0))) {
            System.out.println(i);
        }
        System.out.println("The degree of the vertices are: ");
        System.out.println(Arrays.toString(currentGraph.getDegreeOfVertices()));
        currentGraph.processChain(chainDecomposition.chains.get(0), chainDecomposition);
        System.out.println("For the first iteration the segments are: \n");
        int i = 0;
        for (SegmentOwn segment : currentGraph.getSegments()) {
            System.out.printf("This is the minimal chain of the %d segments \n", i);
            System.out.println(segment.getMinimalChain().vertices);
            System.out.println("The segment contains the chains");
            for (Chain chains : segment.getChains())
                System.out.println(chains.vertices);
            i++;
        }
        //this is for seeing the output

        return ConnectedResult.ThreeEdgeConnected;
    }
}
