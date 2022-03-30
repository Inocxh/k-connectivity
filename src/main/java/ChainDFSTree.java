import java.util.ArrayList;

public class ChainDFSTree extends DFSTree {
    //ArrayList<ArrayList<Integer>> chains = new ArrayList<>();;
    int edgeCounter;
    int cycleCounter;
    int chainCounter;
    int numberOfChains;
    Chain[] chains;

    public ChainDFSTree(Graph G, int root) {
        super(G, root, false);
        edgeCounter = 0;
        cycleCounter = 0;
        chainCounter = 0; // chains are 0-indexed, which is different from the article, where they are 1-indexed.
        numberOfChains = G.getM() - G.getN() + 1;
        vertices[root].sBelongs = chainCounter;
        chains = new Chain[numberOfChains];
        findChains();
    }

    public void findChains(){

        // For all vertices in increasing order
        for (int vertex : dfsOrder()) {
            // For all external edges
            for (int eN : vertices[vertex].downEdges){
                // Check we have a backedge
                if (orderOf(eN) < orderOf(vertex)){
                    assert(false);
                    continue;
                }
                // Create new chain
                ArrayList<Integer> chain = new ArrayList<>();
                // Add start edge, mark visited and inc edge counter
                chain.add(vertex);
                chain.add(eN);
                vertices[vertex].visited = true;
                edgeCounter++;

                // Traverse towards root. Add edges underway and mark visited
                int currentVertex = eN;
                while (!vertices[currentVertex].visited) {
                    vertices[currentVertex].visited = true;
                    vertices[currentVertex].sBelongs = chainCounter;
                    currentVertex = vertices[currentVertex].parent;
                    chain.add(currentVertex);
                    edgeCounter++;
                }
                // When traversing stops add chain, and check for chain cycle
                if (chain.get(0) == chain.get(chain.size() - 1)){
                    cycleCounter++;
                }
                chains[chainCounter] = new Chain(chain);
                chainCounter ++;
            }
        }
    }
    public int getCEdges(){
        return edgeCounter;
    }
    public int getCCycles(){
        return cycleCounter;
    }
    public void printResults(){
        String output = "\n\nCHAINS ARE:\n";
        for (int i = 0; i < chains.length; i++){
            ArrayList<Integer> chain = chains[i].vertices;
            output += "[" + chain.get(0);
            for (int j = 1; j < chain.size(); j++){
                output += ", " + chain.get(j);
            }
            output += "]\n";
        }
        // Print results
        System.out.println(output);
        System.out.println("\n#Edges: " + edgeCounter);
        System.out.println("\n#Cycles: " + cycleCounter);
    }
}

class Chain {
    public int parent;
    private int source;
    public int terminal;
    public ArrayList<Integer> children;
    public ArrayList<Integer> vertices;
    public Chain(ArrayList<Integer> chain) {
        int length = chain.size();
        source = chain.get(0);
        terminal = chain.get(length-1);
        vertices = chain;
    }
}