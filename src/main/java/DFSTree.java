import util.Stack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

public class DFSTree {
    private final int[] ordering2vertex;
    private final int[] vertex2ordering;
    private Vertex[] vertices;
    private final int size;

    public DFSTree(Graph G, int root) {
       int n = G.getN();

       //Initialize maps
       ordering2vertex = new int[n];
       vertex2ordering = new int[n];

       vertices = new Vertex[n];
       Arrays.fill(vertices, new Vertex());

       int i = 0;
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
                   vertices[currentNode].children.add(neighbour);
                   vertices[neighbour].parent = currentNode;
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

class Vertex{
    public ArrayList<Integer> children;
    public int parent;
    public Vertex() {
        children = null;
        parent = -1;
    }

}