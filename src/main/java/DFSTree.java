import util.Stack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class DFSTree {
    private final int[] ordering2vertex;
    private final int[] vertex2ordering;
    private final Vertex[] vertices;
    private final int size;

    public DFSTree(Graph G, int root, boolean postOrder) {
       int n = G.getN();

       //Initialize maps
       ordering2vertex = new int[n];
       vertex2ordering = new int[n]; // TODO: init til 1

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
           System.out.println("Popped " + currentNode.vertex);

           List<Integer> neighbours = G.getNeighbours(currentNode.vertex);

           //Check neighbours that have not been inspected yet.
           boolean repushed = false;
           for(int j = (currentNode.lastInspected); j < neighbours.size(); j++) {
               int neighbour = neighbours.get(j);
               currentNode.lastInspected = j + 1;
               System.out.println("Looking at " + neighbour);
               if (!visited.contains(neighbour)) {
                   System.out.println("Pushed " + neighbour);
                   stack.push(currentNode);
                   stack.push(new StackElement(neighbour));
                   repushed = true;


                   System.out.println("Added " + neighbour + " as child of " + currentNode.vertex);
                   vertices[currentNode.vertex].children.add(neighbour);
                   vertices[neighbour].parent = currentNode.vertex;

                   visited.add(neighbour);
                   break;
               } else { // Contains neighbour. Create backedge.

                   if (vertices[currentNode.vertex].parent != neighbour || vertices[currentNode.vertex].marked.contains(neighbour) ){
                       vertices[currentNode.vertex].eEdges.add(neighbour);
                       System.out.println("(" + currentNode.vertex + "," + neighbour + ") eEdge" );
                   } else if (vertices[currentNode.vertex].parent == neighbour){
                       vertices[currentNode.vertex].marked.add(neighbour);
                   }
               }
           }
           //Assign ordering to currentNode TODO: MAKE BETTER
           if(!repushed) {
               if (postOrder) {
                   ordering2vertex[i] = currentNode.vertex;
                   vertex2ordering[currentNode.vertex] = i;
               } else { // Preorder
                   ordering2vertex[n - i - 1] = currentNode.vertex;
                   vertex2ordering[currentNode.vertex] = n - i - 1;
               }
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
    public ArrayList<Integer> getEEdges(int v) {
        return vertices[v].eEdges;
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
    public ArrayList<Integer> eEdges;
    public HashSet<Integer> marked;
    public int parent;


    /// Initialize all children and parents to illegal values
    /// At the end of new DFSTree, all values are legal.
    public Vertex() {
        children = new ArrayList<>();
        eEdges = new ArrayList<>();
        marked = new HashSet<>();
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