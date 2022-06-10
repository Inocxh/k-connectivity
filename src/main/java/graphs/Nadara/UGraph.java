package graphs.Nadara;

import graphs.DFSTree;

import java.util.ArrayList;
/*
    The U-grpah as decribed in report
    The vertices are the tree edges of T, and e is a parent of f if maxUp1(f).head = e.tail
 */


public class UGraph {
    DFSTreeExtended T;
    ArrayList<ArrayList<Integer>> children;

    public UGraph(DFSTree T, DepthOracle BFS, MinMaxOracle minMax) {
        int root = 0;
        children = new ArrayList<>();
        //Initialize children
        for (int i = 0; i < T.size(); i++) {
            children.add(new ArrayList<>());
        }
        // In BFS order construct the U-graph
        for (int current : BFS.depthOrder()) {
            // v - p(v) is not defined for root
            if (current == root) {continue;}
            int head = minMax.maxUp1(current).get(1);
            children.get(head).add(current);
        }
    }

    public ArrayList<Integer> getChildren(int v) {
        return children.get(v);
    }
}
