package graphs;

import java.util.ArrayList;

public class Chain {
    private int parent;
    public ArrayList<Integer> vertices;
    public Chain(ArrayList<Integer> chain) {
        vertices = chain;
        parent = -1;
    }

    public void setParent(int i) {
        parent = i;
    }
    public int getParent() {
        return parent;
    }
    public int getTerminal() {
        return vertices.get(vertices.size()-1);
    }
}
// TODO: get terminal by function

