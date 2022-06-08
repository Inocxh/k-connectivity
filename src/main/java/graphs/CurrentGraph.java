package graphs;
import java.util.ArrayList;

public class CurrentGraph {
    private boolean[] inGraphChains;
    private int[] degreeOfVertices;
    private ArrayList<Chain> chains;
    private SegmentOwn[] chainsToSegments;

    public CurrentGraph(int n, ChainDecomposition chainDecomposition) {
        chains = chainDecomposition.chains;
        degreeOfVertices = new int[n];
        inGraphChains = new boolean[chains.size()];
        chainsToSegments = new SegmentOwn[chains.size()];
        addChain(0);
        addChain(1);
    }

    public void addChain(int chain) {
        inGraphChains[chain] = true;
        degreeOfVertices[chains.get(chain).vertices.get(0)] +=  1;
        int endOfList = chains.get(chain).vertices.size() -1;
        for (int i=1; i < endOfList; i++) {
            degreeOfVertices[chains.get(chain).vertices.get(i)] += 2;
        }
        degreeOfVertices[chains.get(chain).vertices.get(endOfList)] += 1;
    }

    public int[] getDegreeOfVertices() {
        return degreeOfVertices;
    }

    public boolean getChainInGraph(int i) {
        return inGraphChains[i];
    }

    public void setSegmentForMultipleC(ArrayList<Integer> chains, SegmentOwn segment) {
        for (int i: chains) {
           chainsToSegments[i] = segment;
        }
    }

    public SegmentOwn getSegmentFromC(int chain) {
        return chainsToSegments[chain];
    }
}