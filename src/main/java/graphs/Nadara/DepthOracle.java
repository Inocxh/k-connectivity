package graphs.Nadara;


import graphs.DFSTree;

import java.util.ArrayList;

//Runs a BFS from root during construction
public class    DepthOracle {
    ArrayList<Integer> queue;
    int[] depths;

    public DepthOracle(DFSTree T, int root) {
        //Construct the queue and add the root with depth 0
        queue = new ArrayList<>();
        queue.add(root);
        depths = new int[T.size()];
        depths[root] = 0;
        int currentIdx = 0;
        // While the queue isn't empty, take the children of the current and add them with depth +1 and continue with next in line
        // As we have a tree we don't need a visited set.
        while (currentIdx < queue.size()) {
            int current = queue.get(currentIdx);
            for (int child : T.getChildren(current)) {
                depths[child] = depths[current]+1;
                queue.add(child);
            }
            currentIdx += 1;
        }
    }

    public ArrayList<Integer> depthOrder() {
        return queue;
    }
    public int depth(int x) {
        return depths[x];
    }

}
