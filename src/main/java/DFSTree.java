import util.Stack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class DFSTree {
    private final int[] ordering2vertex;
    private final int[] vertex2ordering;
    public final Vertex[] vertices;
    private final int size;

    public DFSTree(Graph G, int root, boolean postOrder) {
        int n = G.getN();

        //Initialize maps
        ordering2vertex = new int[n];
        vertex2ordering = new int[n];

        vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex();
            vertex2ordering[i] = -1;
        }

        int i = 0;
        //TODO: Change to array
        HashSet<Integer> visited = new HashSet<>();

        util.Stack<StackElement> stack = new Stack<>();

        stack.push(new StackElement(root));
        visited.add(root);

        // While stack is not empty
        while (!stack.isEmpty()) {
            StackElement currentNode = stack.pop();


            List<Integer> neighbours = G.getNeighbours(currentNode.vertex);

            //Check neighbours that have not been inspected yet.
            boolean repushed = false;

            // Inspect next neighbour
            for(int j = (currentNode.lastInspected); j < neighbours.size(); j++) {
                int neighbour = neighbours.get(j);
                currentNode.lastInspected = j + 1;

                // If neighbour not visited
                if (!visited.contains(neighbour)) {

                    // Push to stack
                    stack.push(currentNode);
                    stack.push(new StackElement(neighbour));
                    repushed = true;

                    // Set children of vertex and set parent of child

                    vertices[currentNode.vertex].children.add(neighbour);
                    vertices[neighbour].parent = currentNode.vertex;

                    // Set neighbour visited after it has been added to tree
                    visited.add(neighbour);
                    break;
                } else { // Neighbour is visited. If parent edge, we mark. If not parent edge we add.
                    if (vertex2ordering[neighbour] != -1) {
                        vertices[currentNode.vertex].downEdges.add(neighbour);
                        vertices[neighbour].upEdges.add(currentNode.vertex);
                    }
                }
            }
            //Assign ordering to current node depending on postorder or not.
            if(!repushed) {
                int num = postOrder ? i : n - i - 1;
                ordering2vertex[num] = currentNode.vertex;
                vertex2ordering[currentNode.vertex] = num;
                i++;
            }
        }
        // If size != n then the graph is not connected.
        size = visited.size();
    }

    /// Returns the children in the DFS tree of vertex v
    public ArrayList<Integer> getChildren(int v) {
        return vertices[v].children;
    }
    public ArrayList<Integer> getBackEdges(int v) {
        return vertices[v].downEdges;
    }
    public ArrayList<Integer> getUpEdges(int v) {
        return vertices[v].upEdges;
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
    public ArrayList<Integer> upEdges;
    public ArrayList<Integer> downEdges;

    public int parent;
    public boolean visited;


    /// Initialize all children and parents to illegal values
    /// At the end of new DFSTree, all values are legal.
    public Vertex() {
        children = new ArrayList<>();
        upEdges = new ArrayList<>();
        downEdges = new ArrayList<>();
        parent = -1;
        visited = false;

    }

}

class StackElement {
    public int vertex;
    public int lastInspected = 0;

    public StackElement(int v) {
        vertex = v;
    }

}