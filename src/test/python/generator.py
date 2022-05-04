from TestGenerator import TestGenerator

TG = TestGenerator()

#make binary K5 tree
TG.toGraphViz(TG.makeK5BinaryTree(4), "binaryK5Tree_depth_4.gr")

# Kx-Kx
# for s in sizes:
#     TG.toGr(TG.kPath(2, s), "kx-kx/" + str(s) + ".gr")

# # Grid graph
# for s in sizes:
#     TG.toGr(TG.gridGraph(s), "grid/" + str(s) + ".gr")
