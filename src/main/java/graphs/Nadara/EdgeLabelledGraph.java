package graphs.Nadara;

import graphs.ArrayGraph;
import graphs.DFSTree;
import graphs.Graph;
import java.util.*;

/*
    If we reach the case, where we are trying to find a 3 edge cut with only edges from the DFS-tree,
    we need to be able to contract the non-tree edges of the graph, this is done in this class. Further a
    labeling is stored to be able to determine the edges in the original graph, if a cut is found after the
    contraction of the original graph.
 */

public class EdgeLabelledGraph implements Graph{
    Graph G;
    ArrayList<ArrayList<Pair>> names;
    HashMap<Pair,Integer> takenNames;

    public EdgeLabelledGraph(Graph g) {
        /*
            The creation of the graph is just giving each edge a name from a vertex to its neighbour.
            These names are consistent through all the contraction, so if new contracted graphs are generated, no new names are generated,
            the new edges just inherit the names of the edges they represent in the original graph.
            For the above reason this constructor is only run a single time, no mater the number of recursive contractions.

         */
        G = g;
        names = new ArrayList<>();
        for (int v = 0; v < G.getN(); v++) {
            names.add(new ArrayList<>());
            for (int u : G.getNeighbours(v)) {
                names.get(v).add(new Pair(u,v));
            }
        }
    }
    public EdgeLabelledGraph() {
        G = null;
        names = null;
    }

    public EdgeLabelledGraph contract(DFSTree T) {
        /*
            This method contracts all non tree edges.
            And saves the components for which each vertex belongs to original graph without tree edges.
         */

        int[] vertex2component = new int[G.getN()];
        // Find connected components
        int components = connectedComponents(T, vertex2component);

        // create new graph with vertices corresponding to the found connected components in the original
        ArrayGraph contracted = new ArrayGraph(components);
        // The next 20 lines, transfer edges from components to new graph, edges are added to the new graph,
        // only if they connect two distinct components, all other edges will disappear with the contraction.
        ArrayList<ArrayList<Pair>> contractedEdgeNames= new ArrayList<>();
        for (int i = 0; i < components; i++) {
            contractedEdgeNames.add(new ArrayList<>());
        }

        for (int v = 0 ; v < G.getN(); v++) {
            //Index is needed to get the name
            for (int i = 0 ; i < G.getNeighbours(v).size(); i++) {
                int u = G.getNeighbours(v).get(i);
                Pair name = names.get(v).get(i);
                //If this is a tree edge and both ends are in distinct components
                if (T.getParent(u) == v && vertex2component[v] != vertex2component[u]) {
                    int component1 = vertex2component[v];
                    int component2 = vertex2component[u];

                    contracted.addEdge(component1,component2);
                    contractedEdgeNames.get(component1).add(name);
                    contractedEdgeNames.get(component2).add(name);
                }
            }
        }
        // Create a new graph, that inherit edges and names.
        EdgeLabelledGraph out = new EdgeLabelledGraph();
        out.G = contracted;
        out.names = contractedEdgeNames;
        return out;
    }

    int connectedComponents(DFSTree T, int[] vertex2component) {
        // this functions maps from vertices to each connected component in the original graph excluding the edges in the DFS-tree
        int component = 0;
        boolean[] visited = new boolean[G.getN()];

        // using a que to make a BFS search from the vertex i
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < G.getN(); i++) {
            if (visited[i]) {
                continue;
            }
            q.add(i);
            while (!q.isEmpty()) {
                int current = q.remove();
                vertex2component[current] = component;
                for (int neighbour : T.getBackEdges(current)) {
                    if (!visited[neighbour]) {
                        q.add(neighbour);
                        visited[neighbour] = true;
                    }
                }
                for (int neighbour : T.getUpEdges(current)) {
                    if (!visited[neighbour]) {
                        q.add(neighbour);
                        visited[neighbour] = true;
                    }
                }
            }
            component++;
        }
        return component;
    }

    //This is a bad solution to a stupid problem
    //To deal with parallel edges we have a hashset, counting each time an edge-name has been extracted,
    //then when we search for the name, we simply skip hits until an amount of hit equal to the number of previous extracted names has been extracted
    //This is not(!) linear time, the easiest fix would probably be to replace names with a hashmap
    public String findName(int v, int u) {
        if (takenNames == null) {
            takenNames = new HashMap<>();
        }
        int n = takenNames.getOrDefault(new Pair(u,v),0);
        takenNames.put(new Pair(u,v),n+1);
        for (int i = 0; i < G.getNeighbours(v).size(); i++) {
            if (G.getNeighbours(v).get(i) == u) {
                if (n != 0) {
                    n--;
                    continue;
                }
                return names.get(v).get(i).toString();
            }
        }
        return "[-1,-1]";
    }

    public Pair findOriginalHead(int v, int u){
        if (takenNames == null) {
            takenNames = new HashMap<>();
        }
        int n = takenNames.getOrDefault(new Pair(u,v),0);
        takenNames.put(new Pair(u,v),n+1);
        for (int i = 0; i < G.getNeighbours(v).size(); i++) {
            if (G.getNeighbours(v).get(i) == u) {
                if (n != 0) {
                    n--;
                    continue;
                }
                return names.get(v).get(i);
            }
        }
        return null;
    }

    public void resetTakenNames() {
        takenNames = new HashMap<>();
    }

    @Override
    public int getN() {
        return G.getN();
    }

    @Override
    public int getM() {
        return G.getM();
    }

    @Override
    public List<Integer> getNeighbours(int v) {
        return G.getNeighbours(v);
    }
}
