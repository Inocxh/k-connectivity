import java.util.ArrayList;

public class ChainTree {

    public ChainTree(ChainDFSTree chainDFSTree) {
        for (int i=0; i<chainDFSTree.numberOfChains; i++) {
            if(i==0) {
                chainDFSTree.chains[i].parent = -1;
            }
            else {
               int terminal = chainDFSTree.chains[i].terminal;
               int parent = chainDFSTree.vertices[terminal].sBelongs;
               chainDFSTree.chains[i].parent = parent;
               chainDFSTree.chains[parent].children.add(i);
            }
        }
    }
}


