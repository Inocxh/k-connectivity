package graphs;

import java.util.ArrayList;

public class CurrentGraph {
    private ArrayList<Chain> inGraphChains;
    private int[] degreeOfVertices;
    private ArrayList<SegmentOwn> segments;
    public CurrentGraph(Chain chain_0, Chain chain_1, int n) {
        degreeOfVertices = new int[n];
        inGraphChains = new ArrayList<>();
        addChain(chain_0);
        addChain(chain_1);
        segments = new ArrayList<>();
    }

    public void addChain(Chain chain) {
        inGraphChains.add(chain);
        degreeOfVertices[chain.vertices.get(0)] +=  1;
        int endOfList = chain.vertices.size() -1;
        for (int i=1; i < endOfList; i++) {
            degreeOfVertices[chain.vertices.get(i)] += 2;
        }
        degreeOfVertices[chain.vertices.get(endOfList)] += 1;
    }

    public ArrayList<Integer> getBranchVertices(Chain chain) {
        ArrayList<Integer> branchVertices = new ArrayList<>();
        for (Integer i : chain.vertices) {
            if (degreeOfVertices[i] > 2) {
                branchVertices.add(i);
            }
        }
        return branchVertices;
    }

    public void processChain(Chain chain, ChainDecomposition chainDecomposition) {
        // getting the chains we are processing
        ArrayList<Integer> chainsFromSource = new ArrayList<>();
        for (int i = 0; i < chain.vertices.size(); i++) {
            if(chainDecomposition.getVerticesToSC(i) != null) {
                chainsFromSource.addAll(chainDecomposition.getVerticesToSC(i));
            }
        }
        // computing minimal parent for all chains, that have source on current chain
        for (int i : chainsFromSource) {
            if (chainDecomposition.chains.get(i).getSegment() != null) {
                continue;
            }
            if (inGraphChains.contains(chainDecomposition.chains.get(i))) {
                continue;
            }
            else {
                ArrayList<Chain> chainAsList = new ArrayList<>();
                chainAsList.add(chainDecomposition.chains.get(i));
                setSegment(chainAsList);
            }
        }
    }

    public void setSegment(ArrayList<Chain> chains) {
        if (inGraphChains.contains(chains.get(chains.size()-1).parent)) {
            SegmentOwn segment =  new SegmentOwn(chains, chains.get(chains.size()-1));
            chains.get(chains.size()-1).setSegment(segment);
            segments.add(segment);
        }
        else if (chains.get(chains.size()-1).parent.getSegment() != null) {
            SegmentOwn segment = chains.get(chains.size() -1).parent.getSegment();
            segment.addChains(chains);
        }
        else {
            chains.add(chains.get(chains.size()-1).parent);
            setSegment(chains);
        }
    }
    public ArrayList<SegmentOwn> getSegments() {
        return segments;
    }
    public int[] getDegreeOfVertices() {
        return degreeOfVertices;
    }
}