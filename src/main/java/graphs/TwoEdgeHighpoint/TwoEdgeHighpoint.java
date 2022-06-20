package graphs.TwoEdgeHighpoint;

import graphs.ConnectedResult;
import graphs.Graph;

/*
    This file contains the outwards facing 2-edge-connected highpoint algorithm
    All files in this folder were written by Anders Gad and Niels
    Correctness tests were written by Anders Reher
 */

public class TwoEdgeHighpoint {

    public static ConnectedResult is1EdgeConnected(Graph G) {
        // graphs.TwoEdgeHighpoint.Tarjan utilizes postorder numbering
        boolean postOrder = true;
        // Compute DFS tree
        DFSTree T = new DFSTree(G, 0, postOrder);

        // TEST ASSERTIONS
        isDFSTree(T, G);
        upholdsOrderingInvariant(T);


        int[] H = new int[G.getN()];

        if (T.size() != G.getN()) {
            return ConnectedResult.NotConnected;
        }

        /// Compute H(v) for all vertices in DFS order
        for (int vertex : T.dfsOrder()) {

            int max = T.orderOf(vertex);
            // Check all non tree edges.

            for (int eN : T.getUpEdges(vertex)) {
                max = Math.max(max, T.orderOf(eN));
            }

            // Check all tree edges, ex. parent
            for (int chN : T.getChildren(vertex)) {
                max = Math.max(max, H[chN]);
            }
            H[vertex] = max;
            // Check theorem for all vertices which is not root.
            if (max == T.orderOf(vertex) && T.getParent(vertex) != -1) {
                //Found a bridge!
                System.out.println("Found bridge: (" + vertex + ","+T.getParent(vertex)+")");
                return ConnectedResult.HasBridge;
            }
        }
        return ConnectedResult.TwoEdgeConnected;
    }

    //Returns whether the numbering invariant holds for all vertices
    static boolean upholdsOrderingInvariant(DFSTree T) {
        boolean result;
        //Check that the parent of the root is undefined
        if(T.getParent(0) == -1){
            result = true;
        } else {
            return false;
        }

        //p(v) > v for all v in G
        for (int i = 1; i < T.size(); i++) {
            if (T.orderOf(T.getParent(i)) > T.orderOf(i)){
                result = true;
            } else {
                result = false;
                break;
            }
        }
        return result;
    }
    // Verifies Lemma 2.1.2, that is all back-edges are between ancestors and descendants
    static boolean isDFSTree(DFSTree T, Graph G) {
        //For all vertices
        boolean result = true;
        for (int i = 0; i < G.getN(); i++) {
        // Take all up-edges
            for (int ancestor : T.getUpEdges(i)) {
                int current = i;
        // While we haven't reached the target yet and we aren't on the root
                while (T.getParent(current) != ancestor) {
                    if (T.getParent(current) == -1) {
                        result = false;
                    }
        // Go through the parent
                    current = T.getParent(current);
                }
            }
        }
        // If the outer loop successfully runs we have found parent-paths to all up edges, thus the property holds.
        return result;
    }
}
