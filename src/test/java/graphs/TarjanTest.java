package graphs;

import graphs.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class TarjanTest {

    //Tests K5 for bridges
    @Test
    public void tarjanTest1() throws Exception {
        File f = new File("src/test/graphs/correctness/tarjan/k5.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert( Tarjan.is1EdgeConnected(G) == ConnectedResult.TwoEdgeConnected);
    }

    //Non connected grpah
    @Test
    public void tarjanTest2() throws Exception {
        File f = new File("src/test/graphs/correctness/tarjan/not-connected.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert (Tarjan.is1EdgeConnected(G) == ConnectedResult.NotConnected);
    }

    //Test that a bridge is actually found
    @Test
    public void tarjanTest3() throws Exception {
        File f = new File("src/test/graphs/correctness/tarjan/k5-k5.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert Tarjan.is1EdgeConnected(G) == ConnectedResult.HasBridge;
    }

    //When a parallel edge is added to k5-k5, the bridge is not a bridge anymore
    @Test
    public void tarjanTest4() throws Exception {
        File f = new File("src/test/graphs/correctness/tarjan/k5--k5.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert Tarjan.is1EdgeConnected(G) == ConnectedResult.TwoEdgeConnected;
    }
}