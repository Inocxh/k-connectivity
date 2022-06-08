import random

# Test Types
# There are 3 test cases: 
# 1: is the test cases for Tarjan and Schmidt
# 2: is the test cases for Mehlhorn
# 3: is the cases for Nadara
# Each of the above 3 test cases generate slightly differet variation of 3 graph types, which are described in the report

class TestGenerator:
    # Gens path of clusters of K_x
    def kPath(self, clusters, kSize, test_case):
        graph = [[] for _ in range(clusters * kSize)]

        for i in range(clusters):
            self.connectAllInRange(graph, i * kSize, (i + 1) * kSize)
            if i != clusters - 1:
                if (test_case == 1):
                    graph[(i + 1) * kSize - 1].append((i + 1) * kSize)
                elif (test_case == 2):
                    graph[(i + 1) * kSize - 1].append((i + 1) * kSize)
                    graph[(i + 1) * kSize].append((i + 1) * kSize - 1)
                elif (test_case == 3):
                    graph[(i + 1) * kSize - 1].append((i + 1) * kSize)
                    graph[(i + 1) * kSize - 1].append((i + 1) * kSize)
                    graph[(i + 1) * kSize].append((i + 1) * kSize - 1)
        return graph

    def makeK5BinaryTree(self, depth, test_case):  # just a root is considered depth 0, having 3 K_5 is considere depth 1.
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
                if (test_case >= 2):
                    graph[low_in_tree+1].append(high_in_tree)
                if (test_case >= 3):
                    graph[high_in_tree].append(low_in_tree)
                low_in_tree = low_in_tree + 5
                # make 2 way connections for second child
                graph[low_in_tree+1].append(high_in_tree)
                graph[high_in_tree].append(low_in_tree)
                if (test_case >= 2):
                    graph[low_in_tree+1].append(high_in_tree)
                if (test_case >= 3):
                    graph[high_in_tree].append(low_in_tree)
                low_in_tree = low_in_tree + 5
                high_in_tree = high_in_tree + 5
        return graph

    # Grid graph
    def gridGraph(self, size, test_case):
        graph = [[] for _ in range(size * size)]
        for i in range(size):
            for j in range(size):
                if i < size - 1:
                    graph[i * size + j].append((i + 1) * size + j)
                    if test_case == 2 and j != size-1:
                        graph[i * size + j].append((i + 1) * size + j + 1)
                    if test_case == 3:
                        graph[i * size + j].append((i + 1) * size + j)
                if j < size - 1:
                    graph[i * size + j].append(i * size + j + 1)
                    if test_case == 2 and i != 0:
                        graph[i * size + j].append((i - 1) * size + j + 1)
                    if test_case == 3:
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
    def toGr(self, graph, name, test_case):
        if (test_case == 1):
            file = open('./src/test/graphs/generated/TarjanAndSchmidt/' + name, 'w')
        elif (test_case == 2):
            file = open('./src/test/graphs/generated/Mehlhorn/' + name, 'w')
        elif (test_case == 3):
            file = open('./src/test/graphs/generated/Nadara/' + name, 'w')
        edges = 0
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                edges += 1
        output = str(len(graph)) + " " + str(edges) + "\n"
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                output += str(i) + " " + str(graph[i][j]) + "\n"
        file.write(output)





