from TestGenerator import TestGenerator

TG = TestGenerator()

dataSize = 12
sizes = [2**i for i in range(dataSize)]

# k5 path length x
for s in sizes:
    TG.toGr(TG.kPath(s, 5), "k5-path/k5-path-" + str(s) + ".gr")

# kx-kx
for s in sizes:
    TG.toGr(TG.kPath(2, s), "kx-kx/k" + str(s) + ".gr")

# k3 next
for s in sizes:
    TG.toGr(TG.connectNext(3, s), "next-3/next-3-" + str(s) + ".gr")
