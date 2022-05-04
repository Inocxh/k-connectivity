package graphs;

import java.util.ArrayList;

public class Chain {
    private int parent;
    private int terminal;
    public ArrayList<Integer> children;
    public ArrayList<Integer> vertices;
    public Chain(ArrayList<Integer> chain) {
        int length = chain.size(); // this is equal to the number of vertices on the path, in a cycle vertices might repeat
        terminal = chain.get(length-1);
        vertices = chain;
        children = new ArrayList<>();
        parent = -1;
    }

    public void setParent(int i) {
        parent = i;
    }
    public int getParent() {
        return parent;
    }
    public int getTerminal() {
        return terminal;
    }
}
// TODO: get terminal by function

