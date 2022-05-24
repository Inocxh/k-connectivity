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
        
    def tarjanKx(self, show, export):
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
        if export: 
            #f.savefig("./images/TarjanKxPlot.pdf", bbox_inches='tight')
            f.savefig("./images/TarjanKxPlot.pdf")

    
    def tarjanGrid(self, show, export):
            # Kx Test
            df1 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanGrid.csv", usecols=self.columns)
            xSize1, y1, err1 = self.getParams(df1)
            m = 2 * (xSize1-1) * xSize1
            n = xSize1 * xSize1 
            x1 = n + m

            f = plt.figure()
            self.setOptions(plt)

            plt.errorbar(x1, y1, yerr=err1, fmt="o", markersize=3, label="Grid")

            plt.legend(loc='lower right')
            #plt.title(r'\textbf{Empirical analysis of Tarjan}', fontsize=11)
            if show: plt.show()
            if export: 
                #f.savefig("./images/TarjanKxPlot.pdf", bbox_inches='tight')
                f.savefig("./images/TarjanGridPlot.pdf")

    def tarjanK5BiTree(self, show, export):
        # Kx Test
        df1 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanK5BiTree.csv", usecols=self.columns)
        xSize1, y1, err1 = self.getParams(df1)
        number_of_groups =  2**(xSize1+1) -1
        m =  number_of_groups * 10 + (2**(xSize1+1))*2 - 4
        n =  number_of_groups * 5
        x1 = m +n

        f = plt.figure()
        self.setOptions(plt)

        plt.errorbar(x1, y1, yerr=err1, fmt="o", markersize=3, label="k5BiTree")

        plt.legend(loc='lower right')
        #plt.title(r'\textbf{Empirical analysis of Tarjan}', fontsize=11)
        if show: plt.show()
        if export: 
            #f.savefig("./images/TarjanKxPlot.pdf", bbox_inches='tight')
            f.savefig("./images/TarjanK5BiTree.pdf")

    
