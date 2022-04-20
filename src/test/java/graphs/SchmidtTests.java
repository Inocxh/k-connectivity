package graphs;

import graphs.ConnectedResult;
import graphs.Schmidt;
import graphs.ArrayGraph;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class SchmidtTests {
    @Test
    public void bridgeTest() throws Exception {
        File f = new File("src/test/graphs/schmidt-tests/bridge.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Schmidt.is2Connected(G) == ConnectedResult.HasBridge);
    }
    @Test
    public void twoEdgeTest() throws Exception {
        File f = new File("src/test/graphs/schmidt-tests/2edge.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Schmidt.is2Connected(G) == ConnectedResult.TwoEdgeConnected);
    }
    @Test
    public void twoConnectedTest() throws Exception {
        File f = new File("src/test/graphs/schmidt-tests/2connected.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Schmidt.is2Connected(G) == ConnectedResult.TwoConnected);
    }
    @Test
    public void singleEdgeTest() throws Exception {
        File f = new File("src/test/graphs/schmidt-tests/singleEdge.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(Schmidt.is2Connected(G) == ConnectedResult.HasBridge);
    }
    @Test
    public void doubleEdgeTest() throws Exception {
        File f = new File("src/test/graphs/schmidt-tests/doubleEdge.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert (Schmidt.is2Connected(G) == ConnectedResult.TwoConnected);
    }
}