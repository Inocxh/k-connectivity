package graphs.Nadara;

import graphs.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*

    This class contains an implementation of the algorithm described in a paper by Nadara et. al.
    The algorithm was modified to test for four-edge-connectivity instead of reporting all three-edge-cuts.
    Although all three edge cuts can easily be found by removing return statements, this implementation is unable to report the original edges in the graph in linear time.

 */
public class Nadara {
    // Outwards facing function that takes any graph and returns whether it is four-edge-connected or not
    public static ConnectedResult Nadara(Graph G) {
        EdgeLabelledGraph Gprime = new EdgeLabelledGraph(G);
        //Check that the graph is 3-connected.
        ConnectedResult mehlhornResult = Mehlhorn.is3EdgeConnected(G);
        if (mehlhornResult != ConnectedResult.ThreeEdgeConnected) {
            return mehlhornResult;
        }
        return NadaraHelper(Gprime);
    }

    private static ConnectedResult NadaraHelper(EdgeLabelledGraph G) {

        //Build DFS-tree with pre- and postorder
        DFSTree T= new DFSTree(G,0,false);

        //Construct BFS-tree used in MinMaxOracle
        DepthOracle depthOracle = new DepthOracle(T,0);
        //Compute all static values: lows, maxUp,maxDn,minDn,ddc,ddcNoMin,ddcNoMax
        MinMaxOracle minMax = new MinMaxOracle(T, depthOracle);

        // One tree edge case, check if low3 is defined
        for (int v : T.dfsPreOrder()) {
            if (v == 0) {
                continue;
            }
            if (!minMax.has3Lows(v)) {
                assert checkLow3Correct(G.findOriginalHead(v, T.getParent(v)), T);
                G.resetTakenNames();
                String cut = G.findName(v,T.getParent(v))+ ";" +
                G.findName(minMax.low1(v).get(0),minMax.low1(v).get(1)) + ";" +
                G.findName(minMax.low2(v).get(0),minMax.low2(v).get(1));
                G.resetTakenNames();
                System.out.println("Not four edge connected, first case: " + cut);
                return ConnectedResult.ThreeEdgeConnected;
            }
        }

        // Two tree edge lower case, check for all tree edges if the source of the highest deepest down cut path makes a cut with maxup1 and the edge
        for (int vf : T.dfsPreOrder()) {
            //The root has no edge, the dfsPreOrder function returns list of size 2n for some reason... and if no path goes through this edge no cut lower case cut can be made for it.
            if (vf >= T.size() || vf == 0 || minMax.maxDdcPath(vf) == null ) {
                continue;
            }
            // find an e and g
            ArrayList<Integer> g = minMax.maxUp1(vf);

            Integer eHead = minMax.maxDdcPath(vf).get(1);
            Integer eTail = T.getParent(eHead);
            // If MaxUp2 of f leaps over e return cut {e,f,g}
            Integer maxUp2Head = minMax.maxUp2(vf).get(1);
            //If maxUp2Head is higher than e's tail, we have a cut
            if ((T.getPre(maxUp2Head) <= T.getPre(eTail))) {
                assert checkGLowerCase(G.findOriginalHead(eTail,eHead), G.findOriginalHead(vf,T.getParent(vf)), G.findOriginalHead(g.get(0),g.get(1)), T);
                G.resetTakenNames();
                assert checkCorrectCutLowerCase(G.findOriginalHead(eTail,eHead), G.findOriginalHead(vf,T.getParent(vf)), G.findOriginalHead(g.get(0),g.get(1)));
                G.resetTakenNames();
                String cut = G.findName(eTail,eHead)+ ";" +
                G.findName(vf,T.getParent(vf)) + ";" +
                G.findName(g.get(0),g.get(1));
                G.resetTakenNames();

                System.out.println("Found 3 edge cut, lower case: " + cut);
                return ConnectedResult.ThreeEdgeConnected;
            }
        }

        // Two tree edges, upper case
        //Construct the u-graph see paper for details
        UGraph U = new UGraph(T,depthOracle,minMax);

        //Each edge denoted by the preOrder of parent =>
        // if preOrder(x) < preOrder(y) and x and y are on the same path then
        // y is below x
        SetUnion FuNoMin = new SetUnion(T.size());
        SetUnion FuNoMax = new SetUnion(T.size());

        //The last "edge" is the root, whose parent is not well defined and thus not an edge => i > 0
        for (int i = depthOracle.depthOrder().size()-1; i > 0; i--) {
            int e = depthOracle.depthOrder().get(i);

            for (int c : U.getChildren(e)) {
                FuNoMin.union(T.getPre(e),T.getPre(c));
                FuNoMax.union(T.getPre(e),T.getPre(c));
            }

            ArrayList<Integer> g = minMax.minDn1(e);
            int f0 = minMax.DdcNoMin(e);
            int fprime = FuNoMin.lowest(T.getPre(f0));
            int fprime_actual = T.pre2vertex(fprime);

            if (fprime_actual != e) {

                assert checkGUpperCase(G.findOriginalHead(e, T.getParent(e)), G.findOriginalHead(f0, T.getParent(f0)), G.findOriginalHead(g.get(0), g.get(1)), T);
                G.resetTakenNames();
                assert checkCorrectCutUpperCase(G.findOriginalHead(e, T.getParent(e)), G.findOriginalHead(f0, T.getParent(f0)), minMax);
                G.resetTakenNames();
                String cut = G.findName(e,T.getParent(e))+ ";" +
                        G.findName(fprime_actual,T.getParent(fprime_actual)) + ";" +
                        G.findName(g.get(0),g.get(1));
                G.resetTakenNames();
                System.out.println("Found upper case 3-cut; no min: "+cut);
                return ConnectedResult.ThreeEdgeConnected;
            }

            ArrayList<Integer> g2 = minMax.maxDn1(e);
            int f02 = minMax.DdcNoMax(e);
            int fprime2 = FuNoMax.lowest(T.getPre(f02));
            int fprime_actual2 = T.pre2vertex(fprime2);

            if (fprime_actual2 != e) {
                assert checkGUpperCase(G.findOriginalHead(e, T.getParent(e)), G.findOriginalHead(f02, T.getParent(f02)), G.findOriginalHead(g2.get(0), g2.get(1)), T);
                G.resetTakenNames();
                assert checkCorrectCutUpperCase(G.findOriginalHead(e, T.getParent(e)), G.findOriginalHead(f02, T.getParent(f02)), minMax);
                G.resetTakenNames();
                String cut = G.findName(e,T.getParent(e))+ ";" +
                        G.findName(fprime_actual2,T.getParent(fprime_actual2)) + ";" +
                        G.findName(g2.get(0),g2.get(1));
                G.resetTakenNames();
                System.out.println("Found upper case 3-cut; no max: "+cut);
                return ConnectedResult.ThreeEdgeConnected;
            }

        }

        //Contract and recurse
        EdgeLabelledGraph contracted = G.contract(T);
        if (contracted.getM() == 0) {
            return ConnectedResult.FourEdgeConnected;
        } else {
            return NadaraHelper(contracted);
        }
    }

    @Test
    //Makes sure that edge f is in between the edge e and the edge g.
    public static boolean checkCorrectCutLowerCase(Pair e, Pair f, Pair g){
        return (e.sec < f.sec && f.sec <= g.sec);
    }

    @Test
    //Makes sure that g is in between the edge e and f.
    public static boolean checkCorrectCutUpperCase(Pair e, Pair f, MinMaxOracle minMax){
        ArrayList<Integer> h0 = minMax.maxUp1(f.sec);
        boolean result;
        if (leapsOver(h0, e.sec, e.fst)){
            result = true;
        } else if (e.sec < f.sec && f.sec <= h0.get(1) ){
            result = true;
        } else {
            result = false;
        }
        return (result);
    }

    public static boolean leapsOver(ArrayList<Integer> h0, int eHead, int eTail){
        return h0.get(1) <= eHead && h0.get(0) >= eTail;
    }

    @Test
    //Checks that g is the only backedge going betwen the two subtrees Te/Tf and Te
    public static boolean checkGLowerCase(Pair e, Pair f, Pair g, DFSTree dfsTree){
        ArrayList<Integer> Tf = dfsTree.subtree(Math.max(f.fst, f.sec));
        ArrayList<Integer> Te = dfsTree.subtree(Math.max(e.fst, e.sec));
        ArrayList<Integer> TeWithoutTf = new ArrayList<>();
        int counter = 0;
        for(int v : Te){
            if (Tf.contains(v)){
                continue;
            } else {
                TeWithoutTf.add(v);
            }

        }
        for (int v : Tf){
            for (int i : dfsTree.getUpEdges(v)){
                if(TeWithoutTf.contains(i)){
                    counter++;
                }
            }
        }
        return Tf.contains(g.sec) && !Tf.contains(g.fst) && TeWithoutTf.contains(g.fst) && !TeWithoutTf.contains(g.sec) && counter == 1;
    }

    @Test
    //Makes sure that g is the only backedge going between the two subtrees T/Te and Te/Tf
    public static boolean checkGUpperCase(Pair e, Pair f, Pair g, DFSTree dfsTree){
        ArrayList<Integer> T = (ArrayList<Integer>) Arrays.stream(dfsTree.dfsPreOrder()).boxed().collect(Collectors.toList());
        ArrayList<Integer> Tf = dfsTree.subtree(Math.max(f.fst, f.sec));
        ArrayList<Integer> Te = dfsTree.subtree(Math.max(e.fst, e.sec));
        ArrayList<Integer> TWithoutTe = new ArrayList<>();
        ArrayList<Integer> TeWithoutTf = new ArrayList<>();
        int counter = 0;
        for(int v : T){
            if (Te.contains(v)){
                continue;
            } else {
                TWithoutTe.add(v);
            }
        }
        for(int v : Te){
            if (Tf.contains(v)){
                continue;
            } else {
                TeWithoutTf.add(v);
            }

        }
        for (int v : TeWithoutTf){
            for (int i : dfsTree.getUpEdges(v)){
                if(TWithoutTe.contains(i)){
                    counter++;
                }
            }
        }
        return (TeWithoutTf.contains(g.sec) && !TeWithoutTf.contains(g.fst) && TWithoutTe.contains(g.fst) && !TWithoutTe.contains(g.sec) && counter == 1);

    }

    @Test
    //Makes sure there is only 2 backedges between T/Te and Te
    public static boolean checkLow3Correct (Pair e, DFSTree dfsTree){
        ArrayList<Integer> T = (ArrayList<Integer>) Arrays.stream(dfsTree.dfsPreOrder()).boxed().collect(Collectors.toList());
        ArrayList<Integer> Te = dfsTree.subtree(Math.max(e.fst, e.sec));
        ArrayList<Integer> TWithoutTe = new ArrayList<>();
        int counter = 0;
        for(int v : T){
            if (Te.contains(v)){
                continue;
            } else {
                TWithoutTe.add(v);
            }

        }
        for(int v : Te){
            for(int i : dfsTree.getUpEdges(v)){
                if (TWithoutTe.contains(i)){
                    counter++;
                }
            }
        }
        return counter == 2;
    }

    static String asString(int u, int v) {
        return "["+u+","+v+"]";
    }
}
