import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class TestTest {

    @Test
    public void test() {
        assert(1+1 == 2);
    }

    @Test
    public void graphTest1() throws Exception {
        File f = new File("src/test/graphs/k4.gr");
        //Create the graph and print it:
        ArrayGraph G = ArrayGraph.fromString(new Scanner(f));
        System.out.println(G);

        //Create a DFS tree rooted at 0
        DFSTree T = new DFSTree(G,0);
        System.out.println(T.orderOf(0));
        System.out.println(T.getParent(1) + "," + T.orderOf(1));
        System.out.println(T.getParent(2) + "," + T.orderOf(2));
        System.out.println(T.getParent(3) + "," + T.orderOf(3));
    }
}