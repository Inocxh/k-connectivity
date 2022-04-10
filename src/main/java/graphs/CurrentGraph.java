package graphs;
import javax.swing.text.AsyncBoxView;
import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.Stack;

public class CurrentGraph {
    private ArrayList<Chain> inGraphChains;
    private int[] degreeOfVertices;
    private ArrayList<SegmentOwn> segments;
    private int[] vertex2ordering;

    public CurrentGraph(Chain chain_0, Chain chain_1, int n, DFSTree T) {
        degreeOfVertices = new int[n];
        inGraphChains = new ArrayList<>();
        addChain(chain_0);
        addChain(chain_1);
        segments = new ArrayList<>();
        vertex2ordering = T.vertexToDFSOrder();
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

    private int countUniqueVertices(Chain chain) {
        int v = chain.vertices.size()-1;
        if(chain.vertices.get(0) == chain.vertices.get(v)) {
            return v;
        }
        return v+1;
    }

    public ArrayList<Integer> getBranchVertices(Chain chain) {
        ArrayList<Integer> branchVertices = new ArrayList<>();
        int uniqueVertices = countUniqueVertices(chain);
        for (Integer i : chain.vertices.subList(0,uniqueVertices)) {
            if (degreeOfVertices[i] > 2) {
                branchVertices.add(i);
            }
        }
        return branchVertices;
    }

    public boolean processChain(Chain chain, ChainDecomposition chainDecomposition) {
        segments = new ArrayList<>();
        // get all chains with source on the current chain
        ArrayList<Integer> chainsFromSource = CFromS(chain, chainDecomposition);

        // computing segments for these chains
        computeSegments(chainsFromSource, chainDecomposition);

        // subdividing the chains into interlacing and nested.
        ArrayList<SegmentOwn> interlacing = new ArrayList<>();
        ArrayList<SegmentOwn> nested = new ArrayList<>();
        for (SegmentOwn segment : segments) {
            if (interlacingCheck(segment)) {
                interlacing.add(segment);
            }
            else {
                nested.add(segment);
            }
        }
        // add all chains in a segment, where the minimal chain is interlacing
        for (SegmentOwn segment : interlacing) {
            for (Chain chainInSegment : segment.getChains()) {
                addChain(chainInSegment);
            }
        }
        //compute all the intervals for the spanning forest
        if (!nested.isEmpty()) {
            Intervals intervals = findAllIntervals(nested, chain);
            boolean connected = findSpanningForest(intervals);
            if (connected) {
                for (SegmentOwn segment : nested) {
                    for (Chain chainInSegment : segment.getChains()) {
                        addChain(chainInSegment);
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer> CFromS(Chain chain, ChainDecomposition chainDecomposition) {
        ArrayList<Integer> CWithS = new ArrayList<>();
        //setting number of vertices, we need to look at.
        int uniqueVertices = countUniqueVertices(chain);
        // getting the chains we are processing
        for (int i : chain.vertices) {
            if(chainDecomposition.getVerticesToSC(i) != null) {
                CWithS.addAll(chainDecomposition.getVerticesToSC(i));
            }
        }
        return CWithS;
    }

    private void computeSegments(ArrayList<Integer> chainsFromSource, ChainDecomposition chainDecomposition) {
        for (int i : chainsFromSource) {
            if (inGraphChains.contains(chainDecomposition.chains.get(i))) {
                continue;
            }
            if (chainDecomposition.chains.get(i).getSegment() != null) {
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
        Chain currentChain = chains.get(chains.size() -1);
        if (inGraphChains.contains(currentChain.parent)) {
            SegmentOwn segment =  new SegmentOwn(chains, currentChain);
            for (Chain c : chains) {
                c.setSegment(segment);
            }
            segments.add(segment);
        }
        else if (currentChain.parent.getSegment() != null) {
            SegmentOwn segment = currentChain.parent.getSegment();
            segment.addChains(chains);
        }
        else {
            chains.add(currentChain.parent);
            setSegment(chains);
        }
    }

    private boolean interlacingCheck(SegmentOwn segment) {
        Chain C = segment.getMinimalChain();
        Chain CHat = C.parent;
        int sC = vertex2ordering[C.vertices.get(0)];
        int tC = vertex2ordering[C.vertices.get(C.vertices.size()-1)];
        int sCHat = vertex2ordering[CHat.vertices.get(0)];
        int tCHat = vertex2ordering[CHat.vertices.get(CHat.vertices.size()-1)];
        //interlacing check
        if ((sCHat <= sC) && (sC <= tCHat) && (tCHat <= tC)) {
            return true;
        }
        return false;
    }

    private Intervals findAllIntervals(ArrayList<SegmentOwn> segments, Chain currentChain) {
        Intervals intervals = new Intervals();
        ArrayList<Integer> branchVertices= getBranchVertices(currentChain);

        // adding all (-1,branch) intervals
        for (int branchVertex : branchVertices) {
            intervals.addInterval(-1, vertex2ordering[branchVertex]);
        }

        // making the intervals for each segment
        for (SegmentOwn segment : segments) {
            ArrayList<Integer> attachmentPoints = new ArrayList<>();
            Chain minC = segment.getMinimalChain();
            int a_0 = vertex2ordering[minC.vertices.get(0)];
            int a_k = vertex2ordering[minC.vertices.get(minC.vertices.size()-1)];
            for (Chain chainsInSegment : segment.getChains()) {
                if (chainsInSegment == segment.getMinimalChain()) {
                    continue;
                } else {
                    attachmentPoints.add(vertex2ordering[chainsInSegment.vertices.get(0)]);
                }
            }

           // adding all (a_0,attachmentPoint) and (attachmentPoint,a_k) intervals
            for (int attachmentPoint : attachmentPoints) {
                intervals.addInterval(a_0, attachmentPoint);
                intervals.addInterval(attachmentPoint, a_k);
            }
            // adding last interval a_0 to a_k
            intervals.addInterval(a_0, a_k);
        }
        // now we have all the Intervals for all segments;
        return intervals;
    }

    private boolean findSpanningForest(Intervals intervals) {
        intervals.sort();
        intervals.reverse();
        Stack<Interval> stack = new Stack<>();
        for (Interval interval : intervals.intervals) {
           while ((!stack.empty()) && interval.b > stack.peek().b) {
               stack.pop();
           }
           if ((!stack.empty()) && interval.b >= stack.peek().a) {
               stack.peek().addConnectedTo(interval);
               interval.addConnectedTo(stack.peek());
           }
           stack.push(interval);
        }
        stack.clear();
        intervals.swap();
        intervals.sort();
        intervals.swap();
        for (Interval interval : intervals.intervals) {
            while ((!stack.empty()) && interval.a < stack.peek().a) {
                stack.pop();
            }
            if ((!stack.empty()) && interval.a <= stack.peek().b) {
                stack.peek().addConnectedTo(interval);
                interval.addConnectedTo(stack.peek());
            }
            stack.push(interval);
        }
        // got to this point
        // who should one take point 1 amd 4 of the 4 points on page 333 into consideration here.
        // make a third field for the intervals numbering them, one is shifted slightly to the right by their number, if they have the same starting and ending poin.
        // check if all intervals are in one connected component.
        return dfsConnection(intervals);
    }

    private boolean dfsConnection(Intervals intervals) {
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
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<SegmentOwn> getSegments() {
        return segments;
    }
    public int[] getDegreeOfVertices() {
        return degreeOfVertices;
    }
}