from Plotter import Plotter
# Plots
# Tarjan
p = Plotter()
for i in range(1,4): #1: kx-kx graph, 2: Grid graph, 3: k5BiTree
    for j in range(3,4): # Nadara skiped
        p.plot(show=False, export=True, type_graph=i, test_case=j)

