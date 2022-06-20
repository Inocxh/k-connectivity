package graphs.Demo;

import graphs.ArrayGraph;
import graphs.ConnectedResult;
import graphs.FourEdge.FourEdge;
import graphs.ThreeEdge.ThreeEdge;
import graphs.TwoEdgeChains.TwoEdgeChains;
import graphs.TwoEdgeHighpoint.TwoEdgeHighpoint;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;


public class Demo {

    @Test
    public void Demo2EdgeConnected() throws Exception {
        File f = new File("src/test/graphs/demo/demo_1kantsnit.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));

        TwoEdgeHighpoint.is1EdgeConnected(g);
    }

    @Test
    public void Demo2EdgeConnected2() throws Exception {
        File f = new File("src/test/graphs/demo/demo_1kantsnit.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        System.out.println(TwoEdgeChains.is2Connected(g));
    }
    @Test
    public void Demo3EdgeConnected() throws Exception {
        File f = new File("src/test/graphs/demo/demo_2kantsnit.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));

        ThreeEdge.is3EdgeConnected(g);
    }
    @Test
    public void Demo4EdgeConnected() throws Exception {
        File f = new File("src/test/graphs/demo/demo_3kantsnit.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));

        FourEdge.is4EdgeConnected(g);
    }
}