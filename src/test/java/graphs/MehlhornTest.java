package graphs;

import graphs.Mehlhorn;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;
import graphs.*;

public class MehlhornTest {
    @Test
    public void blackBordTest() throws Exception {
        File f = new File("src/test/graphs/blackboard/3edgeConnected/3edgeConnected.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Mehlhorn.is3EdgeConnected(G) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void blackBordTest2() throws Exception {
        File f = new File("src/test/graphs/blackboard/3edgeConnected/3edgeConnected2.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Mehlhorn.is3EdgeConnected(G) == ConnectedResult.NotThreeEdgeConnected);
    }

    @Test
    public void blackBordTest3() throws  Exception {
        File f = new File("src/test/graphs/blackboard/3edgeConnected/3edgeConnected3.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Mehlhorn.is3EdgeConnected(G) == ConnectedResult.ThreeEdgeConnected);
    }

    @Test
    public void blackBordTest4() throws Exception {
        File f = new File("src/test/graphs/blackboard/3edgeConnected/2edgeCut.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Mehlhorn.is3EdgeConnected(G) == ConnectedResult.NotThreeEdgeConnected);
    }

    @Test
    public void blackBordTest5() throws Exception {
        File f = new File("src/test/graphs/blackboard/3edgeConnected/3failCase.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Mehlhorn.is3EdgeConnected(G) == ConnectedResult.ThreeEdgeConnected);
    }
//    @Test
//    public void generatedBigTest() throws Exception {
//        File f = new File("src/test/graphs/generated/Mehlhorn/grid/364.gr");
//        //Create the graph and print it:
//        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
//        assert(Mehlhorn.is3EdgeConnected(G) == ConnectedResult.ThreeEdgeConnected);
//    }
}
