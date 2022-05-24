from TestGenerator import TestGenerator

TG = TestGenerator()

#make binary K5 tree
sizes = [2**i for i in range(2,12)]

for s in range(2,12):
    TG.toGr(TG.makeK5BinaryTree(s),  "k5BiTree/" + str(s) +".gr")

#Kx-Kx
for s in sizes:
    TG.toGr(TG.kPath(2, s), "kx-kx/" + str(s) + ".gr")

#Grid graph
for s in sizes:
    TG.toGr(TG.gridGraph(s), "grid/" + str(s) + ".gr")
