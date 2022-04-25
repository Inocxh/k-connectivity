package graphs;

import util.Stack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DFSTreeExtended {
    private final int[] preOrder;
    private final int[] postOrder;
    private final int[] pre2vertex;
    private final int[] post2vertex;
    private final Vertex2[] vertices;
    private final int size;

    public DFSTreeExtended(Graph G, int root) {
        int n = G.getN();

        //Initialize maps
        preOrder = new int[n];
        postOrder = new int[n];
        pre2vertex = new int[2 * n]; // Todo fix.
        post2vertex = new int[2 * n];


        vertices = new Vertex2[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex2();
            preOrder[i] = -1;
        }

        int i = 0;
        // Todo change to array
        HashSet<Integer> visited = new HashSet<>();

        //This is our custom implementation of Stack.
        //The java standard library's stack is thread-safe
        util.Stack<StackElement> stack = new Stack<>();

        stack.push(new StackElement(root));
        visited.add(root);

        // While stack is not empty
        while (!stack.isEmpty()) {
            StackElement currentNode = stack.pop();
            if (preOrder[currentNode.vertex] == -1){
                preOrder[currentNode.vertex] = i;
                pre2vertex[i] = currentNode.vertex;
                i++;
            }

            List<Integer> neighbours = G.getNeighbours(currentNode.vertex);

            //Check neighbours that have not been inspected yet.
            boolean repushed = false;

            // Inspect next neighbour
            for (int j = (currentNode.lastInspected); j < neighbours.size(); j++) {
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
                    if (preOrder[neighbour] != -1) {
                        vertices[currentNode.vertex].downEdges.add(neighbour);
                        vertices[neighbour].upEdges.add(currentNode.vertex);
                    }
                }
            }
            //Assign ordering to current node depending on postorder or not.
            if (!repushed) {
                postOrder[currentNode.vertex] = i;
                post2vertex[i] = currentNode.vertex;
                i++;
            }
        }
        // If size != n then the graph is not connected.
        size = visited.size();
    }



    public boolean isAncestor(int u, int v){
        return getPre(u) <= getPre(v) && getPost(u) >= getPre(v);
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
    public int[] dfsPreOrder() {
        return pre2vertex;
    } // Todo fix
    public int[] dfsPostOrder() {
        return post2vertex;
    }

    public int[] vertexToPreOrder() {
        return preOrder;
    }
    public int[] vertexToPostOrder() {
        return postOrder;
    }

    public int size() {
        return size;
    }


    public int getPre(int u) {
        return preOrder[u];
    }

    public int getPost(int u) {
        return postOrder[u];
    }
}



class Vertex2{
    public ArrayList<Integer> children;
    public ArrayList<Integer> upEdges;
    public ArrayList<Integer> downEdges;

    public int parent;

    /// Initialize all children and parents to illegal values
    /// At the end of new graphs.DFSTree, all values are legal.
    public Vertex2() {
        children = new ArrayList<>();
        upEdges = new ArrayList<>();
        downEdges = new ArrayList<>();
        parent = -1;

    }

}

