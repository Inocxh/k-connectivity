package graphs;

import java.util.ArrayList;

public class WeightedPaths {
    private ArrayList<Integer> pathOrder;
    private ArrayList<ArrayList<Integer>> edgeSets;
    private int k;
    private DFSTreeExtended T;
    private SetUnion su;

    public WeightedPaths(DFSTreeExtended T, ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> weights, int k, int C){
        this.T = T;
        this.k = k;
        pathOrder = computeSortedPathOrder(paths, weights, C);
        //System.out.println("Pathorder: " + pathOrder);
        su = new SetUnion(T.size());


        edgeSets = new ArrayList<>(T.size());
        for (int i = 0; i < T.size(); i++) {
            edgeSets.add(null);
        }

        // Process paths in order
        for (int i = 0; i < paths.size(); i++){
            ArrayList<Integer> path = paths.get(pathOrder.get(i));
            go(path.get(0), path.get(path.size() - 1), i);
            go(path.get(path.size() - 1), path.get(0), i);
        }
        printEdgeSets();
    }

    private ArrayList<Integer> computeSortedPathOrder(ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> weights, int C) {
        ArrayList<Integer> pathOrder;
        // Init bucket
        ArrayList<ArrayList<Integer>> bucket = new ArrayList<>(C + 1);
        for (int i = 0; i <= C; i++) {
            bucket.add(null);
        }
        // Sort paths
        for (int i = 0; i < paths.size(); i++){
            int w = weights.get(i);
            if (bucket.get(w) == null){
                bucket.set(w, new ArrayList<>());
            }
            bucket.get(w).add(i);
        }
        pathOrder = new ArrayList<>(paths.size());
        for (int i = 0; i <= C; i++){
            if (bucket.get(i) != null){
                pathOrder.addAll(bucket.get(i));
            }
        }
        return pathOrder;
    }

    private void go(int u, int v, int i){
        u = su.lowest(u);
        while (!T.isAncestor(u, v)){
            int edge = u;
            if (edgeSets.get(edge) == null){
                edgeSets.set(edge, new ArrayList<>());
            }
            edgeSets.get(edge).add(i + 1); // Todo: add if doesnt exists.
            if (edgeSets.get(edge).size() == k){
                su.union(u, T.getParent(u));
            }
            u = su.lowest(T.getParent(u));
        }
    }
    private void printEdgeSets(){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < edgeSets.size(); i++){
            ArrayList<Integer> path = edgeSets.get(i);
            output.append("\nEdge (").append(i).append(",").append(T.getParent(i)).append("): ");
            if (path != null){
                for (int j = 0; j < path.size(); j++){
                    output.append(path.get(j));
                }
            }

        }
        System.out.println(output);
    }
}
