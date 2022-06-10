package graphs.FourEdge;

/*
This file contains an implementation of the the UnionFind datastructure with weighted unions and path compression as decribed in Algorithms and Datastructures 1
We augment the datastructure with a Set.lowest call that in O(1) time returns the minimum element of find(v)
 */
public class SetUnion {
    int[] p;
    int[] sz;
    int[] min;

    // Init
    public SetUnion(int size){ // TODO: Make linear if needed. Doesnt seem like a requirement in practice.
        p = new int[size];
        sz = new int[size];
        min = new int[size];
        for (int i = 0; i < size; i++){
            p[i] = i;
            sz[i] = 1;
            min[i] = i;
        }
    }

    // Find with path compression
    public int find(int x){
        int rootx = x;
        while (rootx != p[rootx]){
            rootx = p[rootx];
        }
        while (x != rootx){
            int px = p[x];
            p[x] = rootx;
            x = px;
        }
        return rootx;
    }

    // Weighted union. All unions save min element in set.
    public void union(int x, int y){
        int rx = find(x);
        int ry = find(y);
        if (rx != ry){
            if (sz[rx] < sz[ry]){
                p[rx] = ry;
                sz[ry] += sz[rx];
                min[ry] = Math.min(min[rx], min[ry]);
            } else {
                p[ry] = rx;
                sz[rx] += sz[ry];
                min[rx] = Math.min(min[rx], min[ry]);
            }
        }
    }

    public int lowest(int x){
        int rx = find(x);
        return min[rx];
    }
}
