package graphs.Nadara;

import graphs.DFSTree;

import java.util.ArrayList;

/*
    Implementation of the MPF algorithm as described in Nadara et. al. and the report.
    A weighted paths object returns in O(1) time the maximum kth path going through v - p(v)
 */
public class WeightedPaths {
    private final ArrayList<Integer> pathOrder;
    //index v denotes edge v p(v)
    private final ArrayList<ArrayList<Integer>> edgeSets;
    private final int k;
    private final DFSTree T;
    private final SetUnion su;

    public WeightedPaths(DFSTree T, ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> weights, int k, int C){
        this.T = T;
        this.k = k;
        pathOrder = computeSortedPathOrder(paths, weights, C);
        //System.out.println("Pathorder: " + pathOrder);
        su = new SetUnion(T.size());
        edgeSets = new ArrayList<>(T.size());
        for (int i = 0; i < T.size(); i++) {
            edgeSets.add(null);
        }
        // Process paths in order:
        for (int i = 0; i < paths.size(); i++){
            ArrayList<Integer> path = paths.get(pathOrder.get(i));
            go(path.get(0), path.get(path.size() - 1), i);
            go(path.get(path.size() - 1), path.get(0), i);
        }
        //printEdgeSets();
    }

    //Returns the k-max-paths going through the edge p(v) - v
    public ArrayList<Integer> maxKPaths(int vertex) {
        return edgeSets.get(vertex);
    }
    public int order2Index(int i) {
        return pathOrder.get(i);
    }
    public int maxKthPathIndex(int v, int k) {
        return order2Index(maxKPaths(v).get(k));
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
        u = T.pre2vertex(su.lowest(T.getPre(u)));
        while (!T.isAncestor(u, v)){
            int edge = u;
            // If no arraylist exist, create it. TODO: remove this, it doesn't impact performance.
            if (edgeSets.get(edge) == null){
                edgeSets.set(edge, new ArrayList<>());
            }
            edgeSets.get(edge).add(i);
            if (edgeSets.get(edge).size() == k){
                su.union(T.getPre(u), T.getPre(T.getParent(u)));
            }
            u = T.pre2vertex(su.lowest(T.getPre(T.getParent(u))));
        }
    }
    public String toString() {
        return edgeSets.toString();
    }

    public void printOrder() {
        System.out.println(pathOrder);
    }

    public void printEdgeSets(){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < edgeSets.size(); i++){
            ArrayList<Integer> path = edgeSets.get(i);
            output.append("\nEdge (").append(i).append(",").append(T.getParent(i)).append("): ");
            if (path != null){
                for (int j = 0; j < path.size(); j++){
                    output.append(" ");
                    output.append(path.get(j));
                }
            }

        }
        System.out.println(output);
    }
}

