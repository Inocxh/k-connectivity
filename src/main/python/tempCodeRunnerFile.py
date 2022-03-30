import random

class TestGenerator:
    def kPath(self, clusters, kSize):
        graph = [[] for _ in range(clusters * kSize)]

        for i in range(clusters):
            self.connectAllInRange(graph, i * kSize, (i + 1) * kSize)
            if i != clusters - 1:
                graph[(i + 1) * kSize - 1].append((i + 1) * kSize)         
        return graph


    def kTree(self, clusters, kSize):
        graph = [[] for _ in range(clusters * kSize)]

        for i in range(clusters):
            self.connectAllInRange(graph, i * kSize, (i + 1) * kSize)
            if i != 0:
                v = random.randint(0, i * kSize)
                graph[i * kSize].append(v)   
        return graph
        

    def connectAllInRange(self, graph, a, b): # j not included
        for i in range(a, b):
            for j in range(i, b):
                if i != j:
                    graph[i].append(j)

    def toGraphViz(self, graph):
        file = open('./src/main/python/output.txt', 'w')
        output = "digraph G {\n"
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                output += str(i) + " -> " + str(graph[i][j]) + "\n"
        output += "}"
        file.write(output)



TG = TestGenerator()

TG.kPath(3, 4)
graph = TG.kPath(500, 2)
TG.toGraphViz(graph)