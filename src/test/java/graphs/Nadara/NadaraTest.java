package graphs.Nadara;

import graphs.ArrayGraph;
import graphs.ConnectedResult;
import graphs.DFSTree;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;


public class NadaraTest {

    @Test
    public void lowsMissing () throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/3para.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(Nadara.Nadara(g) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void lowerCase1() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/lower.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(Nadara.Nadara(g) == ConnectedResult.ThreeEdgeConnected);
    }
    @Test
    public void lowerCase2() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/lower2.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(Nadara.Nadara(g) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void upperCase1() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/upper.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(Nadara.Nadara(g) == ConnectedResult.ThreeEdgeConnected);
    }
    @Test
    public void upperCase2() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/upperNoMax.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(Nadara.Nadara(g) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void manyCuts1() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/minfuncs.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(Nadara.Nadara(g) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void contraction() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/minfuncs.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        EdgeLabelledGraph g2 = new EdgeLabelledGraph(g);
        DFSTree T = new DFSTree(g2,0,false);

        EdgeLabelledGraph g3 = g2.contract(T);

        System.out.println("Contracted n,m: " + g3.getN()+ ","+g3.getM());
        for (int v = 0 ; v < g3.getN(); v++) {
            for (int i = 0; i < g3.getNeighbours(v).size(); i++) {
                int u = g3.getNeighbours(v).get(i);
                Pair name = g3.names.get(v).get(i);
                System.out.println(v+","+u+";"+name);
            }
        }
    }

}
