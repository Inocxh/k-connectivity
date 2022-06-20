package CorrectnessTest;

import graphs.ArrayGraph;
import graphs.ConnectedResult;
import graphs.ThreeEdge.ThreeEdge;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class MehlhornCorrectnessTest {
    @Test
    public void mehlhornCircleCorrectness() throws Exception {
        File f = new File("src/test/graphs/correctness/mehlhorn/circle.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        assert(ThreeEdge.is3EdgeConnected(G) == ConnectedResult.ThreeEdgeConnected);
    }

}
