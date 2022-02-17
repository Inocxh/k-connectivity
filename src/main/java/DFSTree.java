import util.Stack;
import java.util.HashSet;
import java.util.ArrayList;

public class DFSTree {
    private final int[] ordering2vertex;
    private final int[] vertex2ordering;
    private final ArrayList<ArrayList<Integer>> children;
    private final int[] parents;
    private final int size;

    public DFSTree(Graph G, int root) {
        int n = G.getN();

        //Initialize maps
        ordering2vertex = new int[n];
        vertex2ordering = new int[n];
        children = new ArrayList<>(n); //Too cumbersome to mix array and ArrayList
        //Init n new arraylists
        for (int i = 0; i < n; i++) {
            children.add(new ArrayList<>());
        }
        parents = new int[n];


       int i = 0 ;
       HashSet<Integer> visited = new HashSet<>();
       util.Stack<Integer> stack = new Stack<>();

       stack.push(root);
       while (!stack.isEmpty()) {
           int currentNode = stack.pop();

           //Assign ordering to currentNode
           int v_number = n-i-1;
           ordering2vertex[v_number] = currentNode;
           vertex2ordering[currentNode] = v_number;

           visited.add(currentNode);

           for(int neighbour: G.getNeighbours(currentNode)) {
               if (!visited.contains(neighbour)) {
                   stack.push(neighbour);
                   children.get(currentNode).add(neighbour);
                   parents[neighbour] = currentNode;
               }
           }
       }
       //If size != n then the graph is not connected.
       size = visited.size();


    }

    /// Returns the children in the DFS tree of vertex v
    public ArrayList<Integer> getChildren(int v) {
        return children.get(v);
    }
    public int getParent(int v) {
        return parents[v];
    }

    /// Returns a list of all vertices in their DFS order
    public int[] dfsOrder() {
        return ordering2vertex;
    }

    public int orderOf(int v) {
        return vertex2ordering[v];
    }
    public int size() {
        return size;
    }

}
