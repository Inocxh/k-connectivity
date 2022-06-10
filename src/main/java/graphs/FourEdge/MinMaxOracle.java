package graphs.FourEdge;

import graphs.TwoEdgeHighpoint.DFSTree;

import java.util.ArrayList;

/*
    This is where the magic happens.
    Here every static value is computed at the same time in order to achieve linear run time.
    maxUps, maxDns, minDns and lows are all computed using the WeightedPaths datastructure.
    The different deepestDnCuts are computed using off-line LCA.

 */

public class MinMaxOracle {
    WeightedPaths lows;
    WeightedPaths maxUps;
    WeightedPaths maxDns;
    WeightedPaths minDns;
    WeightedPaths ddc; //DeepestDownCut

    LCA lca;
    int[] DDCs;
    int[] DDCNoMins;
    int[] DDCNoMaxs;


    DFSTree T;

    ArrayList<ArrayList<Integer>> lowPaths;
    ArrayList<ArrayList<Integer>> maxUpPaths;
    ArrayList<ArrayList<Integer>> maxDnPaths;
    ArrayList<ArrayList<Integer>> minDnPaths;
    ArrayList<ArrayList<Integer>> ddcPaths;

    public MinMaxOracle(DFSTree T, DepthOracle depths) {
        lca = new LCA(T);
        this.T = T;

        computeLows(T);
        computeMaxUps(T);
        computeMinDns(T);
        computeMaxDns(T);

        DDCs = new int[T.size()];
        DDCNoMaxs = new int[T.size()];
        DDCNoMins = new int[T.size()];
        computeDDCs(T);

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
    void computeDDCs(DFSTree T) {
        //First generate all queries
        ArrayList<ArrayList<Query>> queries = new ArrayList<>(T.size());
        boolean[] colors = new boolean[T.size()];
        for (int i = 0; i < T.size(); i++) {
            queries.add(new ArrayList<Query>());
        }
        //Start at 1 as root doesn't have a parent
        for (int v = 1; v < T.size(); v++) {
            Query DDC = new Query(v,minDn1(v).get(0),maxDn1(v).get(0),DdcCase.Standard);
            Query DDCNoMax = new Query(v,minDn1(v).get(0),maxDn2(v).get(0),DdcCase.NoMax);
            Query DDCNoMin = new Query(v,minDn2(v).get(0),maxDn1(v).get(0),DdcCase.NoMin);
            addQuery(queries,DDC);
            addQuery(queries,DDCNoMax);
            addQuery(queries,DDCNoMin);
        }
        SetUnion F = new SetUnion(T.size());
        //Now use LCA algorithm to answer the queries
        for (int i = T.dfsPreOrder().length-1; i > 0; i--) {
            int v = T.dfsPreOrder()[i];
            int order = T.getPre(v);
            for (int child : T.getChildren(v)) {
                F.union(order,T.getPre(child));
            }
            colors[v] = true;
            for (Query q : queries.get(v)) {
                int other = q.max != v ? q.max : q.min;
                if (colors[other] == true) {
                    int lca = T.pre2vertex(F.lowest(T.getPre(other)));
                    switch (q.c) {
                        case Standard -> {
                            DDCs[q.vertex] = lca;
                        }
                        case NoMin -> {
                            DDCNoMins[q.vertex] = lca;
                        }
                        case NoMax -> {
                            DDCNoMaxs[q.vertex] = lca;
                        }
                    }
                }
            }
            F.union(order,T.getPre(T.getParent(v)));
        }
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
            //Integer maxDnTail = maxDn1(v).get(0);
            //Integer minDnTail = minDn1(v).get(0);

            int ddc = DDCs[v];//lca.lca(maxDnTail,minDnTail);
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

    void addQuery(ArrayList<ArrayList<Query>> queries, Query q) {
        queries.get(q.min).add(q);
        queries.get(q.max).add(q);
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
        //Integer maxDnTail = maxDn1(v).get(0);
        //Integer minDnTail = minDn2(v).get(0);

        int ddc = DDCNoMins[v]; //lca.lca(maxDnTail,minDnTail);
        ddc = T.getPre(ddc) < T.getPre(v) ? v : ddc;
        return ddc;
    }
    public int DdcNoMax(int v) {
        //Integer maxDnTail = maxDn2(v).get(0);
        // Integer minDnTail = minDn1(v).get(0);

        int ddc = DDCNoMaxs[v]; //lca.lca(maxDnTail,minDnTail);
        ddc = T.getPre(ddc) < T.getPre(v) ? v : ddc;
        return ddc;
    }

    class Query {
        int vertex;
        public int min;
        public int max;
        public DdcCase c;
        public Query(int vertex, int min, int max, DdcCase c) {
            this.vertex = vertex;
            this.min = min;
            this.max = max;
            this.c = c;
        }

        public String toString() {
            return "[" + vertex+","+min+","+max+","+c+"]";
        }
    }
    enum DdcCase {
        Standard,
        NoMin,
        NoMax
    }
}
