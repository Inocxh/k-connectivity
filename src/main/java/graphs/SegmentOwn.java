package graphs;

import java.util.ArrayList;

public class SegmentOwn {
    private ArrayList<Integer> chainsInSegment;
    private Chain minimalChain;

    public SegmentOwn(ArrayList<Integer> chains, Chain minimalChain) {
        this.minimalChain = minimalChain;
        chainsInSegment = chains;
    }
    public Chain getMinimalChain() {
        return minimalChain;
    }
    public ArrayList<Integer> getChains() {
        return chainsInSegment;
    }
}
