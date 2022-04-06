import graphs.DFSTree;
import graphs.Schmidt;
import graphs.Tarjan;
import graphs.ArrayGraph;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class TestTest {
    @Test
    public void graphTest1() throws Exception {
        File f = new File("src/test/graphs/k4.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        DFSTree T = new DFSTree(G,0, true);

        Tarjan.is1EdgeConnected(G);
    }

    @Test
    public void graphTest2() throws Exception {
        File f = new File("src/test/graphs/path5.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        DFSTree T = new DFSTree(G,0, true);

        Tarjan.is1EdgeConnected(G);
    }

    @Test
    public void graphTest3() throws Exception {
        File f = new File("src/test/graphs/tree.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        DFSTree T = new DFSTree(G,0, true);

        Tarjan.is1EdgeConnected(G);
    }

    @Test
    public void graphTest4() throws Exception {
        File f = new File("src/test/graphs/blackboard/bridge2.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        //graphs.DFSTree T = new graphs.DFSTree(G,0, true);

        Tarjan.is1EdgeConnected(G);
    }

    @Test
    public void graphTest5() throws Exception {
        File f = new File("src/test/graphs/doubleEdge.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        //graphs.DFSTree T = new graphs.DFSTree(G,0, true);

        Tarjan.is1EdgeConnected(G);
    }


    // SCHMIDT TESTS BELOW
    @Test
    public void graphTest6() throws Exception {
        // Expect nothing
        File f = new File("src/test/graphs/blackboard/bridge2.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        Schmidt.is2Connected(G);
    }
    @Test
    public void graphTest7() throws Exception {
        // Expect 2-connected
        File f = new File("src/test/graphs/2-connected.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        Schmidt.is2Connected(G);
    }
    @Test
    public void graphTest8() throws Exception {
        // Expect 2-edge-connected, not 2-connected
        File f = new File("src/test/graphs/2edgeNot2v.gr");
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        Schmidt.is2Connected(G);
    }
}