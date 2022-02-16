import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

public class DFSTree {
    public DFSTree(Graph G, int root) {
       int i = 0 ;
       HashSet visited = new HashSet();
       Stack<Integer> stack = new Stack<Integer>();
       stack.push(root);
       while (!stack.isEmpty()) {
           int currentNode = stack.pop();
           for(int neighbour: G.getNeighbours(currentNode) {
              stack.push(neighbour);


           }

       }
       for currentNode.children

    }
    public int[] getChildren(int node) {
        return children;
    }
    public int getParent(int node) {
        return parent;
    }
    public int[] dfsOrder() {

    }

}
