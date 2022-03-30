import random

class TestGenerator:
    # Gens path of clusters of K_x
    def kPath(self, clusters, kSize):
        graph = [[] for _ in range(clusters * kSize)]

        for i in range(clusters):
            self.connectAllInRange(graph, i * kSize, (i + 1) * kSize)
            if i != clusters - 1:
                graph[(i + 1) * kSize - 1].append((i + 1) * kSize)         
        return graph

    # Gens trees of clusters of K_x
    def kTree(self, clusters, kSize):
        graph = [[] for _ in range(clusters * kSize)]

        for i in range(clusters):
            self.connectAllInRange(graph, i * kSize, (i + 1) * kSize)
            if i != 0:
                v = random.randint(0, i * kSize)
                graph[i * kSize].append(v)   
        return graph
    
    # Gen vertices and connect to at least x other vertices
    def genRatio(self, vertices, connections):
        graph = [[] for _ in range(vertices)]
        for i in range(vertices):
            for _ in range(connections):
                v = random.randint(0, vertices)
                while v == i:
                    v = random.randint(0, vertices)
                graph[i].append(v)
        return graph
    # Connects all vertices in range (a, b), b not included.
    def connectAllInRange(self, graph, a, b):
        for i in range(a, b):
            for j in range(i, b):
                if i != j:
                    graph[i].append(j)

    # Convert to graphviz format
    def toGraphViz(self, graph, name):
        file = open('./src/main/python/' + name, 'w')
        output = "digraph G {\n"
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                output += str(i) + " -> " + str(graph[i][j]) + "\n"
        output += "}"
        file.write(output)
    
    # Convert to .gr format
    def toGr(self, graph, name):
        file = open('./src/test/graphs/generated/' + name, 'w')
        edges = 0
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                edges += 1
        
        output = str(len(graph)) + " " + str(edges) + "\n"
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                output += str(i) + " " + str(graph[i][j]) + "\n"
        file.write(output)



TG = TestGenerator()

#TG.toGr(TG.kPath(100000, 5), "k5path.gr") # 100 path of k5's
#TG.toGr(TG.kPath(2, 2000), "k2000bridge.gr") # 2 path of k100's
#TG.toGr(TG.kTree(100000, 5), "k5tree.gr") # 100 node tree of k5's
#TG.toGr(TG.kPath(1, 2000), "k2000.gr")
TG.toGraphViz(TG.genRatio(10, 2), "output.txt")
#TG.toGraphViz(graph) # 