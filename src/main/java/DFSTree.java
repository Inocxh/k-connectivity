import util.Stack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

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
       for (int i = 0; i < n; i++) {
           vertices[i] = new Vertex();
       }

       int i = 0;
       HashSet<Integer> visited = new HashSet<>();
       util.Stack<StackElement> stack = new Stack<>();

       stack.push(new StackElement(root));
       visited.add(root);

       while (!stack.isEmpty()) {
           StackElement currentNode = stack.pop();

           List<Integer> neighbours = G.getNeighbours(currentNode.vertex);

           //Check neighbours that have not been inspected yet.

           for(int j = (currentNode.lastInspected); j < neighbours.size(); j++) {
               int neighbour = neighbours.get(j);
               currentNode.lastInspected = j+1;
               if (!visited.contains(neighbour)) {
                   stack.push(currentNode);
                   stack.push(new StackElement(neighbour));

                   vertices[currentNode.vertex].children.add(neighbour);
                   vertices[neighbour].parent = currentNode.vertex;
                   visited.add(neighbour);
                   break;
               }
           }
           //Assign ordering to currentNode TODO: MAKE BETTER
           if(currentNode.lastInspected >= neighbours.size()-1) {
               ordering2vertex[i] = currentNode.vertex;
               vertex2ordering[currentNode.vertex] = i;
               i++;
           }
       }
       //If size != n then the graph is not connected.
       size = visited.size();
    }

    /// Returns the children in the DFS tree of vertex v
    public ArrayList<Integer> getChildren(int v) {
        return vertices[v].children;
    }
    public int getParent(int v) {
        return vertices[v].parent;
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
//Holds a tree-vertex
class Vertex{
    public ArrayList<Integer> children;
    public int parent;
    /// Initialize all children and parents to illegal values
    /// At the end of new DFSTree, all values are legal.
    public Vertex() {
        children = new ArrayList<>();
        parent = -1;
    }

}

class StackElement {
    public int vertex;
    public int lastInspected = 0;

    public StackElement(int v) {
        vertex = v;
    }
}