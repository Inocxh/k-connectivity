public class Schmidt {

    public static ConnectedResult is2Connected(Graph G) {
        /*boolean postOrder = false;
        DFSTree T = new DFSTree(G, 0, postOrder);
        //make chain decomposition
        for (int vertex : T.dfsOrder()) {
            int max = T.orderOf(vertex);
            for (int neighbour : G.getNeighbours(vertex) Alle tree edges) {
                //Is child in tree (Tarjan lemma 2)
                if (T.orderOf(neighbour) > T.orderOf(vertex)) {
                    max = Math.max(max, H[neighbour]);
                    //Isn't child
                } else if (T.getParent(vertex) != neighbour){
                    max = Math.max(max, T.orderOf(neighbour));
                }
            }
            H[vertex] = max;
            if (max == T.orderOf(vertex) && T.getParent(vertex) != -1) {
                System.out.println("Found bridge! at " + vertex);
            } else {
                System.out.println("Didn't find bridge at " + vertex);
            }
        }*/
        return ConnectedResult.None;
    }
}

