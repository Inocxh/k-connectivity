import java.util.Arrays;

public class Tarjan {

    public static ConnectedResult is1EdgeConnected(Graph G) {
        DFSTree T = new DFSTree(G, 0);
        int[] H = new int[G.getN()];
        /// Compute H(v)
        for (int vertex : T.dfsOrder()) {
            int max = T.orderOf(vertex);
            for (int neighbour : G.getNeighbours(vertex)/* Alle tree edges*/) {
                //Is child in tree (Tarjan lemma 2)
                if (T.orderOf(neighbour) < T.orderOf(vertex)) {
                    max = max < H[neighbour] ? H[neighbour] : max;
                //Isn't child
                } else if (T.getParent(vertex) != neighbour){
                    max = max < T.orderOf(neighbour) ? T.orderOf(neighbour) : max;
                }
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
