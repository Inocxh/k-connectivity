import util.Stack;
import java.util.HashSet;
import java.util.*;

public class DFSTree {
    private int[] ordering2vertex;
    private int[] vertex2ordering;
    private ArrayList<ArrayList<Integer>> children;
    private int[] parents;
    private int size;

    public DFSTree(Graph G, int root) {
        int n = G.getN();

        //Initialize maps
        ordering2vertex = new int[n];
        vertex2ordering = new int[n];
        children = ArrayList<ArrayList<>>(n); //Too cumbersome to mix array and ArrayList
        parents = new int[n];


       int i = 0 ;
       HashSet visited = new HashSet();
       util.Stack<Integer> stack = new Stack();

       stack.push(root);
       while (!stack.isEmpty()) {
           int currentNode = stack.pop();

           //Assign ordering to currentNode
           int v_number = n-i-1;
           ordering2vertex[v_number] = currentNode;
           vertex2ordering[currentNode] = v_number;

           visited.add(currentNode);

           for(int neighbour: G.getNeighbours(currentNode)) {
               if (visited.contains(neighbour)) {
                   continue;
               }
               stack.push(neighbour);
               children[currentNode].add(neighbour);
               parents[neighbour] = currentNode;
           }
       }
       //If size != n then the graph is not connected.
       size = visited.size();


    }

    /// Returns the children in the DFS tree of vertex v
    public ArrayList<Integer> getChildren(int v) {
        return children[v];
    }
    public int getParent(int v) {
        return parents[v];
    }

    /// Returns a list of all vertices in their DFS order
    public int[] dfsOrder() {
        return ordering2vertex;
    }
    public int size() {
        return size;
    }

}
