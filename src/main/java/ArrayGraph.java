import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ArrayGraph implements Graph{
    int n;
    int m;
    ArrayList<ArrayList<Integer>> vertices;

    public ArrayGraph(int n) {
        m = 0;
        vertices = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            vertices.add(new ArrayList<>());
        }
        this.n = n;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public List<Integer> getNeighbours(int v) {
        return vertices.get(v);
    }

    @Override
    public void addEdge(int v, int w) {
        vertices.get(v).add(w);
        vertices.get(w).add(v);
        m += 1;
    }

    public static ArrayGraph fromString(Scanner s) throws Exception {
        int edge = 0;
        try {
            int nIn = s.nextInt();
            int mIn = s.nextInt();
            ArrayGraph G = new ArrayGraph(nIn);
            for (; edge < mIn; edge++) {
                int v1 = s.nextInt();
                int v2 = s.nextInt();
                G.addEdge(v1,v2);
            }
            return G;
        } catch (Exception e) {
            System.out.println("Error at edge: " + edge);
            throw e;
        }

    }

    public String toString() {
        String o = "[\n";
        int name = 0;
        for (ArrayList<Integer> neighbours : vertices) {
            o += name + ": " + neighbours.toString() + "\n";
            name++;
        }
        o += "]";
        return o;
    }
}
