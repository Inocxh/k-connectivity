package graphs;

import java.util.ArrayList;

public class Chain {
    public Chain parent;
    private int source;
    public int terminal;
    public ArrayList<Integer> children;
    public ArrayList<Integer> vertices;
    private SegmentOwn segment;
    public Chain(ArrayList<Integer> chain) {
        int length = chain.size(); // this is equal to the number of vertices on the path, in a cycle vertices might repeat
        source = chain.get(0);
        terminal = chain.get(length-1);
        vertices = chain;
        children = new ArrayList<>();
    }
    public SegmentOwn getSegment() {
        return segment;
    }
    public void setSegment(SegmentOwn segment) {
        this.segment = segment;
    }
}
