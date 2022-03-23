import java.util.Arrays;

public class Tarjan {

    public static ConnectedResult is1EdgeConnected(Graph G) {
        // Tarjan utilizes postorder numbering
        boolean postOrder = true;
        // Compute DFS tree
        DFSTree T = new DFSTree(G, 0, postOrder);

        int[] H = new int[G.getN()];

        /// Compute H(v) for all vertices in DFS order
        for (int vertex : T.dfsOrder()) {

            int max = T.orderOf(vertex);
            // Check all non tree edges.
            System.out.println("Neighbours for "  + vertex + " " +Arrays.toString(T.getUpEdges(vertex).toArray()));
            for (int eN : T.getUpEdges(vertex)) {
                max = Math.max(max, T.orderOf(eN));
            }
            System.out.println("Children for "  + vertex + " " + Arrays.toString(T.getChildren(vertex).toArray()));
            // Check all tree edges, ex. parent
            for (int chN : T.getChildren(vertex)) {
                max = Math.max(max, H[chN]);
            }
            H[vertex] = max;
            // Check theorem for all vertices which is not root.
            if (max == T.orderOf(vertex) && T.getParent(vertex) != -1) {
                System.out.println("Found bridge! at " + vertex);
            } else {
                System.out.println("Didn't find bridge at " + vertex);
            }
        }
        System.out.println();
        System.out.println(Arrays.toString(H));
        System.out.println(Arrays.toString(T.dfsOrder()));
        return ConnectedResult.None;
    }
}
