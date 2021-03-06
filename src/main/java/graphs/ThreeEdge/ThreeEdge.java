package graphs.ThreeEdge;

import graphs.ConnectedResult;
import graphs.Graph;
import graphs.TwoEdgeChains.Chain;
import graphs.TwoEdgeChains.ChainDecomposition;

import graphs.TwoEdgeHighpoint.DFSTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
    This file contains the outwards facing 3-edge-connectivity procedure
    The primary author of this code is Anders Gad, with Niels writing the now dead and wrongly implemented RadixSort classes
    correctness tests were written by Anders Reher
 */

public class ThreeEdge {

    static private ArrayList<ArrayList<Integer>> cToSonC;

    public static ConnectedResult is3EdgeConnected(Graph G) {
        DFSTree dfsTree = new DFSTree(G, 0, false);
        ChainDecomposition chainDecomposition = new ChainDecomposition(dfsTree, true);
        cToSonC = new ArrayList<>(chainDecomposition.chains.size());
        for (int i = 0; i < chainDecomposition.chains.size(); i++) {
            cToSonC.add(new ArrayList());
        }

        computeParentChainsAndSource(chainDecomposition);
        //CORRECTNESS TEST
        assert chainDecomposition.chainCheckLemma2(chainDecomposition, dfsTree);

        CurrentGraph currentGraph = new CurrentGraph(G.getN(), chainDecomposition);

        // processing one chain at a time
        for (int i=0; i < chainDecomposition.chains.size(); i++) {
            //CORRECTNESS TEST
            if(i > 1){
                assert currentGraph.notPartOfGCChecker(cToSonC, i, chainDecomposition);
            }
           //compute segments
            ArrayList<SegmentOwn> segments = computeSegments(currentGraph, i,  chainDecomposition);

            // subdividing the segments into interlacing and nested.
            ArrayList<SegmentOwn> interlacing = new ArrayList<>();
            ArrayList<SegmentOwn> nested = new ArrayList<>();
            for (SegmentOwn segment : segments) {
                if (interlacingCheck(dfsTree, chainDecomposition, segment)) {
                    interlacing.add(segment);
                } else {
                    nested.add(segment);
                    assert nestedCheckLemma8(segment, chainDecomposition, dfsTree);
                }
            }
            // add all chains in a segment, where the minimal chain is interlacing
            addSegments(currentGraph, interlacing);

            //compute all the intervals for the spanning forest and see if the spanning forest is connected
            Optional<Cut> cutFound = tryAddingNested(currentGraph, nested, chainDecomposition.chains.get(i), dfsTree, chainDecomposition);

            if (cutFound.isPresent()) {
                System.out.println(cutFound.get().toString());  // this line can be uncommented to print existing cuts, this is commented out to avoid too much print with Benchmark tests.
                return ConnectedResult.NotThreeEdgeConnected;
            }
            //CORRECTNESS TEST
            assert CurrentGraph.invarientChecker(cToSonC, i);
        }
        return ConnectedResult.ThreeEdgeConnected;
    }


    public static ArrayList<SegmentOwn> computeSegments(CurrentGraph currentGraph, int chainNumber, ChainDecomposition chainDecomposition) {
        ArrayList<SegmentOwn> segments = new ArrayList<>();
        // get all chains with source on the current chain
        ArrayList<Integer> chainsFromSource = cToSonC.get(chainNumber);

        // computing segments for these chains
        for (int i : chainsFromSource) {
            if (currentGraph.getChainInGraph(i)) {
                continue;
            }
            if (currentGraph.getSegmentFromC(i) != null) {
                continue;
            }
            else {
                ArrayList<Integer> chainAsIntList = new ArrayList<>();
                chainAsIntList.add(i);
                SegmentOwn segment = setSegment(currentGraph, chainAsIntList, chainDecomposition);
                if (segment != null) {
                    segments.add(segment);
                }
            }
        }
        return segments;
    }

    public static SegmentOwn setSegment(CurrentGraph currentGraph, ArrayList<Integer> chainsInt, ChainDecomposition chainDecomposition) {
        while (true) {
            Chain currentChain = chainDecomposition.chains.get(chainsInt.get(chainsInt.size()-1));
            if (currentGraph.getChainInGraph(currentChain.getParent())) {
                SegmentOwn segment =  new SegmentOwn(chainsInt, currentChain);
                currentGraph.setSegmentForMultipleC(chainsInt, segment);
                return segment;
            }
            else if (currentGraph.getSegmentFromC(currentChain.getParent()) != null) {
                SegmentOwn segment = currentGraph.getSegmentFromC(currentChain.getParent());
                segment.addChains(chainsInt);
                currentGraph.setSegmentForMultipleC(chainsInt, segment);
                return null;
            }
            else {
                chainsInt.add(currentChain.getParent());
            }
        }
    }

    private static boolean interlacingCheck(DFSTree dfsTree, ChainDecomposition chainDecomposition, SegmentOwn segment) {
        Chain C = segment.getMinimalChain();
        Chain CHat = chainDecomposition.chains.get(C.getParent());
        int sC = dfsTree.orderOf(C.vertices.get(0));
        int tC = dfsTree.orderOf(C.vertices.get(C.vertices.size()-1));
        int sCHat = dfsTree.orderOf(CHat.vertices.get(0));
        int tCHat = dfsTree.orderOf(CHat.vertices.get(CHat.vertices.size()-1));
        //interlacing check
        if ((sCHat <= sC) && (sC <= tCHat) && (tCHat <= tC)) {
            return true;
        }
        return false;
    }

    private static Optional<Cut> tryAddingNested(CurrentGraph currentGraph, ArrayList<SegmentOwn> nested, Chain currentChain, DFSTree dfsTree, ChainDecomposition chainDecomposition) {
        if (!nested.isEmpty()) {
            Intervals intervals = findAllIntervals(currentGraph, nested, currentChain, dfsTree, chainDecomposition);
            Optional<Cut> connected = findSpanningForest(intervals, dfsTree, currentChain);
            if (connected.isEmpty()) {
                for (SegmentOwn segment : nested) {
                    for (int chainInSegment : segment.getChains()) {
                        currentGraph.addChain(chainInSegment);
                    }
                }
            } else {
                return connected;
            }
        }
        return Optional.empty();
    }


    private static Intervals findAllIntervals(CurrentGraph currentGraph, ArrayList<SegmentOwn> segments, Chain currentChain, DFSTree dfsTree, ChainDecomposition chainDecomposition) {
        int tiebreaker = 0;
        Intervals intervals = new Intervals();
        ArrayList<Integer> branchVertices= getBranchVertices(currentGraph, currentChain);

        // adding all (-1,branch) intervals
        for (int branchVertex : branchVertices) {
            intervals.addInterval(-1, dfsTree.orderOf(branchVertex), tiebreaker);
            tiebreaker ++;
        }

        // making the intervals for each segment
        for (SegmentOwn segment : segments) {
            ArrayList<Integer> attachmentPoints = new ArrayList<>();
            Chain minC = segment.getMinimalChain();
            int a_0 = dfsTree.orderOf(minC.vertices.get(0));
            int a_k = dfsTree.orderOf(minC.vertices.get(minC.vertices.size()-1));
            for (int chainsInSegment : segment.getChains()) {
                if (chainDecomposition.chains.get(chainsInSegment) == segment.getMinimalChain()) {
                    continue;
                } else {
                    attachmentPoints.add(dfsTree.orderOf(chainDecomposition.chains.get(chainsInSegment).vertices.get(0)));
                }
            }

            // adding all (a_0,attachmentPoint) and (attachmentPoint,a_k) intervals
            for (int attachmentPoint : attachmentPoints) {
                intervals.addInterval(a_0, attachmentPoint, tiebreaker);
                tiebreaker ++;
                intervals.addInterval(attachmentPoint, a_k, tiebreaker);
                tiebreaker ++;
            }
            // adding last interval a_0 to a_k
            intervals.addInterval(a_0, a_k, tiebreaker);
        }
        // now we have all the Intervals for all segments;
        return intervals;
    }

    private static Optional<Cut> findSpanningForest(Intervals intervals, DFSTree dfsTree, Chain currentChain) {
        intervals.sort();
        intervals.reverse();
        Stack<Interval> stack = new Stack<>();
        for (Interval interval : intervals.intervals) {
            while ((!stack.empty()) && interval.b > stack.peek().b) {
                stack.pop();
            }
            if ((!stack.empty()) && interval.b >= stack.peek().a) {
                //finding left neighbour for current interval
                interval.addConnectedTo(stack.peek());
                stack.peek().addConnectedTo(interval);
            }
            stack.push(interval);
        }
        stack.clear();
        intervals.sortReverse();
        for (Interval interval : intervals.intervals) {
            while ((!stack.empty()) && interval.a < stack.peek().a) {
                stack.pop();
            }
            if ((!stack.empty()) && interval.a <= stack.peek().b) {
                //finding right neighbour for current interval
                interval.addConnectedTo(stack.peek());
                stack.peek().addConnectedTo(interval);
            }
            stack.push(interval);
        }

        return dfsConnection(intervals, dfsTree, currentChain);
    }

    private static Optional<Cut> dfsConnection(Intervals intervals, DFSTree dfsTree, Chain currentChain) {
        Stack<Interval> stack = new Stack<>();
        stack.push(intervals.intervals.get(0));
        intervals.intervals.get(0).setVisited(true);
        int size = 1;
        while(!stack.isEmpty()) {
            Interval interval = stack.pop();
            for (Interval neighbour : interval.connectedToo) {
                if(!neighbour.getVisited()) {
                    stack.push(neighbour);
                    neighbour.setVisited(true);
                    size ++;
                }
            }
        }
        if (size == intervals.intervals.size()) {
            return Optional.empty();
        }
        else {
            return Optional.of(findCut(intervals, dfsTree, currentChain));
        }
    }

    private static Cut findCut(Intervals intervals, DFSTree dfsTree, Chain currentChain) {
        int i = 0;
        // when every is not in one connected component intervals must not be visited by the previous dfs-search
        while(intervals.getIntervals().get(i).getVisited()) {
            i ++;
        }
        Interval unconnectedInterval = intervals.getIntervals().get(i);
        int minimumPoint = unconnectedInterval.a;
        int maximumPoint = unconnectedInterval.b;
        Stack<Interval> stack = new Stack<>();
        stack.push(unconnectedInterval);
        unconnectedInterval.setVisited(true);
        while(!stack.isEmpty()) {
            Interval interval = stack.pop();
            for (Interval neighbour : interval.connectedToo) {
                if(!neighbour.getVisited()) {
                    neighbour.setVisited(true);
                    stack.push(neighbour);
                    if (neighbour.a < minimumPoint) {
                        minimumPoint = neighbour.a;
                    }
                    if (neighbour.b > maximumPoint) {
                        maximumPoint = neighbour.b;
                    }
                }
            }
        }
        // find x, P(x), y and z.
        int x = dfsTree.dfsToVertex(minimumPoint);
        int pX = dfsTree.getParent(dfsTree.dfsToVertex(minimumPoint));
        int y = dfsTree.dfsToVertex(maximumPoint);
        int z = currentChain.vertices.get(currentChain.vertices.indexOf(y)-1);
        return new Cut(x, pX, y, z);
    }

    private static int countUniqueVertices(Chain chain) {
        int v = chain.vertices.size()-1;
        if(chain.vertices.get(0) == chain.vertices.get(v)) {
            return v;
        }
        return v+1;
    }

    public static ArrayList<Integer> getBranchVertices(CurrentGraph currentGraph, Chain chain) {
        int[] degreeOfVertices = currentGraph.getDegreeOfVertices();
        ArrayList<Integer> branchVertices = new ArrayList<>();
        int uniqueVertices = countUniqueVertices(chain);
        for (Integer i : chain.vertices.subList(0,uniqueVertices)) {
            if (degreeOfVertices[i] > 2) {
                branchVertices.add(i);
            }
        }
        return branchVertices;
    }

    private static void addSegments(CurrentGraph currentGraph, ArrayList<SegmentOwn> interlacing) {
        for (SegmentOwn segment : interlacing) {
            for (int chainInSegment : segment.getChains()) {
                currentGraph.addChain(chainInSegment);
            }
        }
    }

    public static void computeParentChainsAndSource(ChainDecomposition chainDecomposition) {
        for (int i=1; i< chainDecomposition.getNumberOfChains(); i++) {
            int terminal = chainDecomposition.chains.get(i).getTerminal();
            int source = chainDecomposition.chains.get(i).vertices.get(0);
            cToSonC.get(chainDecomposition.verticesSBelong[source]).add(i);
            chainDecomposition.chains.get(i).setParent(chainDecomposition.verticesSBelong[terminal]);
        }
    }

    @Test
    //Checks lemma 8 found in the article certifying 3-edge-connectivity by Kurt Mehlhorn
    public static boolean nestedCheckLemma8(SegmentOwn segment, ChainDecomposition chainDecomposition, DFSTree dfsTree){
        ArrayList<Chain> chains = chainDecomposition.chains;
        ArrayList<Integer> segmentChain = segment.getChains();
        Chain minimalChain = segment.getMinimalChain();
        int attPoint1 = minimalChain.vertices.get(0);
        int attPoint2 = minimalChain.getTerminal();
        boolean result = false;
        //Goes through every chain.
        for(Integer Cchain : segmentChain){
            //Checks if a chain in the segment has the outer most attechment points
            if (chains.get(Cchain).vertices.get(0) == attPoint1 && chains.get(Cchain).getTerminal() == attPoint2){
                ArrayList<Integer> chainVertices = chains.get(Cchain).vertices;
                //Checks if every other chain in the segment has source on that chain
                int[] treePath;
                for(Integer Dchain : segmentChain){
                    int sourceDChain = chains.get(Dchain).vertices.get(0);
                    //Finds the tree path between the attachment points.
                    int from = dfsTree.orderOf(chainVertices.get(0));
                    int to = dfsTree.orderOf(chainVertices.get(chainVertices.size() - 1));
                    if (from > to){
                        treePath  = subArray(dfsTree.dfsOrder(), to, from);
                    } else {
                        treePath  = subArray(dfsTree.dfsOrder(), from, to);
                    }
                    //Check if source of the D chain is on the treepath.
                    if(Arrays.stream(treePath).anyMatch(x -> x == sourceDChain)){
                        result = true;
                    } else {
                        result = false;
                        break;
                    }
                }
                break;
            }
        }
        return result;
    }



    public static int[] subArray(int[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }

}

class Cut {
   private int x;
   private int pX;
   private int y;
   private int z;
   public Cut(int x, int pX, int y, int z) {
       this.x = x;
       this.pX = pX;
       this.y = y;
       this.z = z;
   }
   public String toString() {
       return "Two edge cut found: (" + x + "," + pX + "),(" + y + "," + z+")";
   }
}
