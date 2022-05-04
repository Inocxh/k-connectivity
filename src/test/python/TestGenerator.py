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
    
    # Gens trees of clusters of K_x, with backedges
    def kTreeWithBackedges(self, clusters, kSize, backedges):
        graph = self.kTree(clusters, kSize)
        for _ in range(backedges):
            s = random.randint(0, len(graph) - 1)
            t = random.randint(0, len(graph) - 1)
            while t // kSize == s // kSize:
                t = random.randint(0, len(graph) - 1)
            graph[s].append(t)
        return graph




    # Grid graph
    def gridGraph(self, size):
        graph = [[] for _ in range(size * size)]
        for i in range(size):
            for j in range(size):
                if i < size - 1:
                    graph[i * size + j].append((i + 1) * size + j)
                if j < size - 1:
                    graph[i * size + j].append(i * size + j + 1)
        return graph



    # Connects all vertices in range (a, b), b not included.
    def connectAllInRange(self, graph, a, b):
        for i in range(a, b):
            for j in range(i, b):
                if i != j:
                    graph[i].append(j)





    # Convert to graphviz format
    def toGraphViz(self, graph, name):
        file = open('./src/test/python/' + name, 'w')
        output = "graph G {\n"
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                output += str(i) + " -- " + str(graph[i][j]) + "\n"
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





