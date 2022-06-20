package graphs.FourEdge;

import graphs.ArrayGraph;
import graphs.ConnectedResult;
import graphs.TwoEdgeHighpoint.DFSTree;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;


public class FourEdgeTest {

    @Test
    public void lowsMissing () throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/3para.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));

        assert(FourEdge.is4EdgeConnected(g) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void lowerCase1() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/lower.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(FourEdge.is4EdgeConnected(g) == ConnectedResult.ThreeEdgeConnected);
    }
    @Test
    public void lowerCase2() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/lower2.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(FourEdge.is4EdgeConnected(g) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void upperCase1() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/upper.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(FourEdge.is4EdgeConnected(g) == ConnectedResult.ThreeEdgeConnected);
    }
    @Test
    public void upperCase2() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/upperNoMax.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(FourEdge.is4EdgeConnected(g) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void manyCuts1() throws Exception {
        File f = new File("src/test/graphs/correctness/nadara/minfuncs.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        assert(FourEdge.is4EdgeConnected(g) == ConnectedResult.ThreeEdgeConnected);
    }

}
