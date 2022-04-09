package graphs;

import java.util.Arrays;

public class Tarjan {

    public static ConnectedResult is1EdgeConnected(Graph G) {
        // graphs.Tarjan utilizes postorder numbering
        boolean postOrder = true;
        // Compute DFS tree
        DFSTree T = new DFSTree(G, 0, postOrder);

        int[] H = new int[G.getN()];

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
                //System.out.println("Found bridge! at " + vertex);
            } else {
                //System.out.println("Didn't find bridge at " + vertex);
            }
        }
        return ConnectedResult.None;
    }
}
