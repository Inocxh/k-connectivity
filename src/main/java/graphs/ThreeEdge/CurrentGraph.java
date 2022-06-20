package graphs.ThreeEdge;
import graphs.TwoEdgeChains.Chain;
import graphs.TwoEdgeChains.ChainDecomposition;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrentGraph {
    private static boolean[] inGraphChains;
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

    //Tests that invariant always holds.
    public static boolean invarientChecker(ArrayList<ArrayList<Integer>> cToSonC, int phase){
        boolean result = true;
        for(int i = 0; i < phase; i++){
            ArrayList<Integer> chainsWithSource = cToSonC.get(i);
            for(Integer chain : chainsWithSource){
                if(inGraphChains[chain]){
                    result = true;
                } else {
                    result = false;
                }
            }
        }
        return result;
    }

    //Checks that the chain in phase i isn't a part of the current graph
    public static boolean notPartOfGCChecker(ArrayList<ArrayList<Integer>> cToSonC, int phase, ChainDecomposition chainDecomposition){
        boolean result = true;
        ArrayList<Integer> chainsWithSource = cToSonC.get(phase);
        for(Integer intChain : chainsWithSource){
            if(!inGraphChains[intChain]){
                result = true;
            } else {
                result = false;
            }
        }
        return result;

    }

}