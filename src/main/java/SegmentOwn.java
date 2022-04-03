import java.util.ArrayList;

public class SegmentOwn {
    private ArrayList<Chain> chainsInSegment;
    private Chain minimalChain;

    public SegmentOwn(ArrayList<Chain> chains, Chain minimalChain) {
        this.minimalChain = minimalChain;
        chainsInSegment = chains;
    }
    public void addChains(ArrayList<Chain> chains) {
        chainsInSegment.addAll(chains);
        for (Chain chain : chains) {
            chain.setSegment(this);
        }
    }
    public Chain getMinimalChain() {
        return minimalChain;
    }
    public ArrayList<Chain> getChains() {
        return chainsInSegment;
    }
}
