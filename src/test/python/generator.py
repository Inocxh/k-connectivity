from TestGenerator import TestGenerator

TG = TestGenerator()




sizes = [2**i for i in range(10)]
# K5-tree
for s in sizes:
    TG.toGr(TG.kTree(s, 5), "k5-tree/" + str(s) + ".gr")

# K5-tree with backedges
for s in sizes:
    TG.toGr(TG.kTreeWithBackedges(s, 5, s//2), "k5-tree-backedges/" + str(s) + ".gr")

# Kx-Kx
for s in sizes:
    TG.toGr(TG.kPath(2, s), "kx-kx/" + str(s) + ".gr")

# Grid graph
for s in sizes:
    TG.toGr(TG.gridGraph(s), "grid/" + str(s) + ".gr")