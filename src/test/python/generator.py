from TestGenerator import TestGenerator

TG = TestGenerator()

sizes_kvadratic = [2**i for i in range(2,12)]
sizes_linear = [4+(i*40) for i in range(0,10)]

# make binary K5 tree
for s in range(2,12):
    TG.toGr(TG.makeK5BinaryTree(s, 1),  "k5BiTree/" + str(s) +".gr", 1)
    TG.toGr(TG.makeK5BinaryTree(s, 2),  "k5BiTree/" + str(s) +".gr", 2)
    TG.toGr(TG.makeK5BinaryTree(s, 3),  "k5BiTree/" + str(s) +".gr", 3)

#Kx-Kx
for s in sizes_linear:
    # Kvadratic
    TG.toGr(TG.kPath(2, s, 1), "kx-kx/" + str(s) + ".gr", 1)
    TG.toGr(TG.kPath(2, s, 2), "kx-kx/" + str(s) + ".gr", 2)
    TG.toGr(TG.kPath(2, s, 3), "kx-kx/" + str(s) + ".gr", 3)
    # Linear

#Grid graph
for s in sizes_linear:
    TG.toGr(TG.gridGraph(s, 1), "grid/" + str(s) + ".gr", 1)
    TG.toGr(TG.gridGraph(s, 2), "grid/" + str(s) + ".gr", 2)
    TG.toGr(TG.gridGraph(s, 3), "grid/" + str(s) + ".gr", 3)
