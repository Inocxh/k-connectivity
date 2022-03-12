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
        DFSTree T = new DFSTree(G,0);

        Tarjan.is1EdgeConnected(G);
    }

    @Test
    public void graphTest2() throws Exception {
        File f = new File("src/test/graphs/path5.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        DFSTree T = new DFSTree(G,0);

        Tarjan.is1EdgeConnected(G);
    }

    @Test
    public void graphTest3() throws Exception {
        File f = new File("src/test/graphs/tree.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        DFSTree T = new DFSTree(G,0);

        Tarjan.is1EdgeConnected(G);
    }

    @Test
    public void graphTest4() throws Exception {
        File f = new File("src/test/graphs/blackboard/bridge2.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        //Create a DFS tree rooted at 0
        DFSTree T = new DFSTree(G,0);

        Tarjan.is1EdgeConnected(G);
    }
}