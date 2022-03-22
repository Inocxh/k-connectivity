import java.util.Arrays;

public class Tarjan {

    public static ConnectedResult is1EdgeConnected(Graph G) {
        boolean postOrder = true;
        DFSTree T = new DFSTree(G, 0, postOrder);


        int[] H = new int[G.getN()];
        /// Compute H(v)
        for (int vertex : T.dfsOrder()) {
            System.out.println("\nAt vertex: " + vertex);
            int max = T.orderOf(vertex);

            for (int eN : T.getEEdges(vertex)) { // Check all upEdges.
                System.out.println("ex edge " + eN);
                max = Math.max(max, T.orderOf(eN));
                System.out.println("Max: " + max);
            }
            for (int chN : T.getChildren(vertex)) { // Check all children in tree
                System.out.println("child " + chN);
                max = Math.max(max, H[chN]);
                System.out.println("Max: " + max);
            }
            H[vertex] = max;
            if (max == T.orderOf(vertex) && T.getParent(vertex) != -1) {
                System.out.println("Found bridge! at " + vertex);
            } else {
                System.out.println("Didn't find bridge at " + vertex);
            }
        }
        System.out.println(Arrays.toString(H));
        return ConnectedResult.None;
    }
}
