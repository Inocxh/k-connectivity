package graphs.Nadara;

import graphs.DFSTree;

import java.util.ArrayList;

public class MinMaxOracle {
    WeightedPaths lows;
    WeightedPaths maxUps;
    WeightedPaths maxDns;
    WeightedPaths minDns;
    WeightedPaths ddc; //DeepestDownCut
    WeightedPaths ddcNoMin; //DeepestDownCut
    WeightedPaths ddcNoMax; //DeepestDownCut

    LCA lca;
    DFSTree T;

    ArrayList<ArrayList<Integer>> lowPaths;
    ArrayList<ArrayList<Integer>> maxUpPaths;
    ArrayList<ArrayList<Integer>> maxDnPaths;
    ArrayList<ArrayList<Integer>> minDnPaths;
    ArrayList<ArrayList<Integer>> ddcPaths;
    ArrayList<ArrayList<Integer>> ddcNoMinPaths;
    ArrayList<ArrayList<Integer>> ddcNoMaxPaths;

    public MinMaxOracle(DFSTree T, DepthOracle depths) {
        lca = new LCA(T);
        this.T = T;

        computeLows(T);
        computeMaxUps(T);
        computeMinDns(T);
        computeMaxDns(T);

        computeDDCPaths(T,depths);
    }
    void computeLows(DFSTree T) {
        //Compute low1,low2,low3 using thm3
        lowPaths = new ArrayList<>();
        ArrayList<Integer> lowPathsWeights = new ArrayList<>();
        for (int v : T.dfsPreOrder()) {
            for (int u : T.getUpEdges(v)) {
                ArrayList<Integer> path = new ArrayList<>();
                path.add(v);
                path.add(u);
                lowPathsWeights.add(T.getPre(v));
                lowPaths.add(path);
            }
        }
        lows = new WeightedPaths(T,lowPaths,lowPathsWeights,3,T.size());
    }
    void computeMaxUps(DFSTree T) {
        //Compute MaxUp,MaxUp2;
        maxUpPaths = new ArrayList<>();
        ArrayList<Integer> maxPathsWeights = new ArrayList<>();
        for (int v : T.dfsPreOrder()) {
            for (int u : T.getUpEdges(v)) {
                ArrayList<Integer> path = new ArrayList<>();
                path.add(v);
                path.add(u);
                maxPathsWeights.add(T.size() - T.getPre(u));
                maxUpPaths.add(path);
            }
        }
        maxUps = new WeightedPaths(T,maxUpPaths,maxPathsWeights,2,T.size());
    }
    void computeMaxDns(DFSTree T) {

        // -//-   MaxDn1,MaxDn2. All using Theorem 3
        maxDnPaths = new ArrayList<>();
        ArrayList<Integer> maxDnWeights = new ArrayList<>();
        for (int v : T.dfsPreOrder()) {
            for (int u : T.getUpEdges(v)) {
                ArrayList<Integer> path = new ArrayList<>();
                path.add(v);
                path.add(u);
                maxDnWeights.add(T.size() - T.getPre(v));
                maxDnPaths.add(path);
            }
        }
        maxDns = new WeightedPaths(T,maxDnPaths,maxDnWeights,2,T.size());
    }
    void computeMinDns(DFSTree T) {

        // -//-   MinDn1,MinDn2;
        minDnPaths = new ArrayList<>();
        ArrayList<Integer> minDnWeights = new ArrayList<>();
        for (int v : T.dfsPreOrder()) {
            for (int u : T.getUpEdges(v)) {
                ArrayList<Integer> path = new ArrayList<>();
                path.add(v);
                path.add(u);
                minDnWeights.add(T.getPre(v));
                minDnPaths.add(path);
            }
        }
        minDns = new WeightedPaths(T,minDnPaths,minDnWeights,2,T.size());
    }
    void computeDDCPaths(DFSTree T, DepthOracle O) {
        // Two tree edges, lower case TODO Lowest common ancestor in lg(n) time
        //Construct paths
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> weights = new ArrayList<>();
        ddcPaths = paths;
        for (int v : T.dfsPreOrder()) {
            if (v >= T.size() || v == 0) {
                continue;
            }
            //Find deepest down cut
            //We add up-edges, such that the path consists of [tail,head]
            Integer maxDnTail = maxDn1(v).get(0);
            Integer minDnTail = minDn1(v).get(0);

            int ddc = lca.lca(maxDnTail,minDnTail);
            ddc = T.getPre(ddc) < T.getPre(v) ? v : ddc;
            //Weigh path with depth of DDC
            ArrayList<Integer> path = new ArrayList<>();
            path.add(ddc);
            path.add(v);

            paths.add(path);
            //We need max-first-order, so we minus the largest possible weight
            weights.add(T.size()-O.depth(v));
        }
        ddc = new WeightedPaths(T,ddcPaths,weights,1,T.size());
    }

    public boolean has3Lows(int v) {
        return lows.maxKPaths(v).size() == 3;
    }
    public ArrayList<Integer> low1(int v) {
        if (lows.maxKPaths(v) == null) {return null;}
        return lowPaths.get(lows.maxKthPathIndex(v,0));
    }
    public ArrayList<Integer> low2(int v) {
        if (lows.maxKPaths(v) == null) {return null;}
        return lowPaths.get(lows.maxKthPathIndex(v,1));
    }

    public ArrayList<Integer> maxUp1(int v) {
        if (maxUps.maxKPaths(v) == null) {return null;}
        return maxUpPaths.get(maxUps.maxKthPathIndex(v,0));
    }
    public ArrayList<Integer> maxUp2(int v) {
        if (maxUps.maxKPaths(v) == null) {return null;}
        return maxUpPaths.get(maxUps.maxKthPathIndex(v,1));
    }

    public ArrayList<Integer> maxDn1(int v) {
        if (maxDns.maxKPaths(v) == null) {return null;}
        return maxDnPaths.get(maxDns.maxKthPathIndex(v,0));
    }
    public ArrayList<Integer> maxDn2(int v) {
        if (maxDns.maxKPaths(v) == null) {return null;}
        return maxDnPaths.get(maxDns.maxKthPathIndex(v,1));
    }
    public ArrayList<Integer> minDn1(int v) {
        if (minDns.maxKPaths(v) == null) {return null;}
        return minDnPaths.get(minDns.maxKthPathIndex(v,0));
    }
    public ArrayList<Integer> minDn2(int v) {
        if (minDns.maxKPaths(v) == null) {return null;}
        return minDnPaths.get(minDns.maxKthPathIndex(v,1));
    }

    public ArrayList<Integer> maxDdcPath(int v) {
        if (ddc.maxKPaths(v) == null) {return null;}
        return ddcPaths.get(ddc.maxKthPathIndex(v,0));
    }
    public int DdcNoMin(int v) {
        Integer maxDnTail = maxDn1(v).get(0);
        Integer minDnTail = minDn2(v).get(0);

        int ddc = lca.lca(maxDnTail,minDnTail);
        ddc = T.getPre(ddc) < T.getPre(v) ? v : ddc;
        return ddc;
    }
    public int DdcNoMax(int v) {
        Integer maxDnTail = maxDn2(v).get(0);
        Integer minDnTail = minDn1(v).get(0);

        int ddc = lca.lca(maxDnTail,minDnTail);
        ddc = T.getPre(ddc) < T.getPre(v) ? v : ddc;
        return ddc;
    }

    enum DdcCase {
        Standard,
        NoMin,
        NoMax
    }
}
