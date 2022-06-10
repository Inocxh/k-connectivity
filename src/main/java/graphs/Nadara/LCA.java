package graphs.Nadara;

import graphs.DFSTree;

import java.util.HashSet;

//Naive implementation of LCA problem for a tree T
/*
DEAD CODE!

Has been replaced by computeDDC method in MinMaxOracle.
The class was previously a placeholder slow placeholder for a linear time LCA algorithm
 */
public class LCA {
    private final DFSTree T;
    public LCA(DFSTree T) {
        this.T = T;
    }
    //Returns the lowest common ancestor of x and y
    // O( |height(x) - height(y)| )
    public int lca(int x, int y) {
        //Set of visited
        HashSet<Integer> visited = new HashSet<>();
        int cur1 = x;
        int cur2 = y;

        //This will always finish as the tree is connected and we will at some point end up at the root
        while (true) {
            // If we haven't gone past the root and the current hasn't been visited before, continue the search
            if (cur1 != -1 && !visited.contains(cur1)) {
                visited.add(cur1);
                cur1 = T.getParent(cur1);
            // If the current has been visited before and we aren't above the root, return the cur1
            } else if (cur1 != -1){
                return cur1;
            }
            //Same as before but for the other
            if (cur2 != -1 && !visited.contains(cur2)) {
                visited.add(cur2);
                cur2 = T.getParent(cur2);
            } else if (cur2 != -1) {
                return cur2;
            }
        }
    }
}
