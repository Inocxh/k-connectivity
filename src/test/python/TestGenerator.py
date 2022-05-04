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

    def makeK5BinaryTree(self, depth):  # just a root is considered depth 0, having 3 K_5 is considere depth 1.
        # the depth needs to be at least 0
        assert(depth >= 0)
        # we determine the number of groups in the binary tree
        number_of_groups = 0
        for i in range(depth):
            number_of_groups = number_of_groups + 2**i
        #There are 5 vertices in each group
        number_of_vertices = number_of_groups * 5
        graph = [[] for _ in range(number_of_vertices)]
        i = 0
        #Connect groups internally
        while (i < number_of_vertices):
            self.connectAllInRange(graph, i, i+5)
            i = i + 5
        #Connect groups in tree structure
        low_in_tree = 5
        high_in_tree = 0
        for j in range(1, depth):
            widness_of_level = 2**(j-1)
            # connect everything within the depth-level to the vertex above it in the binary tree
            for z in range(widness_of_level):
                # make 2 way connections for first child
                graph[low_in_tree+1].append(high_in_tree)
                graph[high_in_tree].append(low_in_tree)
                low_in_tree = low_in_tree + 5
                # make 2 way connections for second child
                graph[low_in_tree+1].append(high_in_tree)
                graph[high_in_tree].append(low_in_tree)
                low_in_tree = low_in_tree + 5
                high_in_tree = high_in_tree + 5
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





