import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mehlhorn {
    public static ConnectedResult is3EdgeConnected(Graph G) {
        DFSTree T = new DFSTree(G, 0, false);
        ChainDecomposition chainDecomposition = new ChainDecomposition(T, true);
        chainDecomposition.computeParentChains();
        ArrayList<Chain> currentGraph = new ArrayList<>(Arrays.asList(chainDecomposition.chains.get(0), chainDecomposition.chains.get(1)));


        //this is for seeing the output
        for(int i=0; i<chainDecomposition.getNumberOfChains(); i++) {
            System.out.println("this is chain: " + i);
            System.out.println(chainDecomposition.chains.get(i).vertices);
            System.out.println("this is parent for chain: " + i);
            System.out.println(chainDecomposition.chains.get(i).parent);
        }
        // this is just for seeing the output.
        System.out.println("this is where the source of every chain is located: ");
        for (int i=0; i<T.size(); i++) {
            if(chainDecomposition.getVerticesToSC(i) != null) {
                System.out.println(chainDecomposition.getVerticesToSC(i).toString());
            }
        }

        segments.segMentcomputation

        return ConnectedResult.ThreeEdgeConnected;
    }
}
