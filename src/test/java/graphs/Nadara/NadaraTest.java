package graphs.Nadara;

import graphs.ArrayGraph;
import graphs.Nadara.DFSTreeExtended;
import graphs.Nadara.Nadara;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NadaraTest {

    @Test
    public static void minFunctions() throws Exception {
        File f = new File("src/test/graphs/correctness/tarjan/k5.gr");
        ArrayGraph g = ArrayGraph.fromString(new Scanner(f));
        DFSTreeExtended t = new DFSTreeExtended(g,0);

        Nadara.min3inList()
    }
}
