package graphs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public static void invarientChecker(ArrayList<ArrayList<Integer>> cToSonC, int phase){
        for(int i = 0; i < phase; i++){
            ArrayList<Integer> chainsWithSource = cToSonC.get(i);
            for(Integer chain : chainsWithSource){
                assertTrue(inGraphChains[chain]);
            }

        }
    }

    @Test
    public static void notPartOfGCCheckerLemma6(ArrayList<ArrayList<Integer>> cToSonC, int phase, ChainDecomposition chainDecomposition){
        ArrayList<Integer> chainsWithSource = cToSonC.get(phase);
        for(Integer intChain : chainsWithSource){
            assertFalse(inGraphChains[intChain]);
        }

    }

    @Test
    public static void  ancestorCheckLemma6 (Integer intChain, ChainDecomposition chainDecomposition, int phase){
        ArrayList<Chain> chains = chainDecomposition.chains;
        Chain cChain = chainDecomposition.chains.get(intChain);
        Chain ciChain = chainDecomposition.chains.get(phase);
        Chain parrentChain = chains.get(cChain.getParent());
        int parrentIntChain = cChain.getParent();
        if (inGraphChains[parrentIntChain]){
            System.out.println(chains.get(chainDecomposition.verticesSBelong[parrentChain.vertices.get(0)]).vertices.toString());
            assertEquals(chains.get(chainDecomposition.verticesSBelong[parrentChain.vertices.get(0)]), ciChain);
        } else {
            ancestorCheckLemma6(parrentIntChain, chainDecomposition, phase);
        }
    }
}