from matplotlib import pyplot as plt
import pandas as pd

class Plotter:
    
    columns = ["Samples","Score","Score Error (99.9%)","Unit","Param: x"]
    
    def getParams(self, df):
        return df["Param: x"], df["Score"], df["Score Error (99.9%)"]

    def setOptions(self, plt):
        #plt.rc('text', usetex=True)
        #plt.rc('font', family='serif')
        plt.xscale('log')
        plt.yscale('log')
        #plt.xlabel(r'Input size ($n + m$)', fontsize=11)
        #plt.ylabel(r'Execution time (ms)', fontsize=11)
        
    def tarjan(self, show, export):
        # Kx Test
        df1 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanKx.csv", usecols=self.columns)
        xSize1, y1, err1 = self.getParams(df1)
        m = ((xSize1 * xSize1) - xSize1) + 1
        n = xSize1 * 2
        x1 = n + m

        f = plt.figure()
        self.setOptions(plt)

        plt.errorbar(x1, y1, yerr=err1, fmt="o", markersize=3, label="Kx")

        plt.legend(loc='lower right')
        #plt.title(r'\textbf{Empirical analysis of Tarjan}', fontsize=11)
        if show: plt.show()
        if export: f.savefig("./src/test/java/Benchmarks/Results/TarjanKxPlot.pdf", bbox_inches='tight')

        # K5-tree Test
        df2 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanK5Tree.csv", usecols=self.columns)
        xSize2, y2, err2 = self.getParams(df2)
        m = 10 * xSize2 + xSize2 - 1
        n = xSize2 * 5
        x2 = n + m

        f = plt.figure()
        self.setOptions(plt)

        plt.errorbar(x2, y2, yerr=err2, fmt="o", markersize=3, label="Kx")

        plt.legend(loc='lower right')
        #plt.title(r'\textbf{Empirical analysis of Tarjan}', fontsize=11)
        if show: plt.show()
        if export: f.savefig("./src/test/java/Benchmarks/Results/TarjanK5TreePlot.pdf", bbox_inches='tight')

        # K5-tree-backedges test
        df3 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanK5TreeBackedge.csv", usecols=self.columns)
        xSize3, y3, err3 = self.getParams(df3)
        m = 10 * xSize3 + xSize3 - 1 + xSize3 / 2
        n = xSize3 * 5
        x3 = n + m

        f = plt.figure()
        self.setOptions(plt)

        plt.errorbar(x3, y3, yerr=err3, fmt="o", markersize=3, label="Kx")

        plt.legend(loc='lower right')
        #plt.title(r'\textbf{Empirical analysis of Tarjan}', fontsize=11)
        if show: plt.show()
        if export: f.savefig("./src/test/java/Benchmarks/Results/TarjanK5TreeBackedgePlot.pdf", bbox_inches='tight')

        # Grid test
        df4 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanGrid.csv", usecols=self.columns)
        xSize4, y4, err4 = self.getParams(df4)
        m = (xSize4 - 1) * (xSize4 - 1) * 2 + 2 * (xSize4 - 1)
        n = xSize4 * xSize4
        x4 = n + m

        f = plt.figure()
        self.setOptions(plt)

        plt.errorbar(x4, y4, yerr=err4, fmt="o", markersize=3, label="Kx")

        plt.legend(loc='lower right')
        #plt.title(r'\textbf{Empirical analysis of Tarjan}', fontsize=11)
        if show: plt.show()
        if export: f.savefig("./src/test/java/Benchmarks/Results/TarjanGridPlot.pdf", bbox_inches='tight')




    
