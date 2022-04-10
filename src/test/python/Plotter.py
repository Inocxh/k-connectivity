from matplotlib import pyplot as plt
import pandas as pd

class Plotter:
    
    columns = ["Samples","Score","Score Error (99.9%)","Unit","Param: x"]
    
    def getParams(self, df):
        return df["Param: x"], df["Score"], df["Score Error (99.9%)"]

    def setOptions(self, plt):
        plt.rc('text', usetex=True)
        plt.rc('font', family='serif')
        plt.xscale('log')
        plt.yscale('log')
        plt.xlabel(r'Input size', fontsize=11)
        plt.ylabel(r'Execution time (ms)', fontsize=11)
        
        






    def tarjan(self, show, export):
        df1 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanKx.csv", usecols=self.columns)
        x1, y1, err1 = self.getParams(df1)

        df2 = pd.read_csv("./src/test/java/Benchmarks/Results/TarjanPath.csv", usecols=self.columns)
        x2, y2, err2 = self.getParams(df2)
        
        f = plt.figure()
        self.setOptions(plt)

        plt.errorbar(x1, y1, yerr=err1, fmt="o", markersize=4, label="Kx")
        plt.errorbar(x2, y2, yerr=err2, fmt="D", markersize=4, label="path")

        plt.legend(loc='lower right')
        plt.title(r'\textbf{Empirical analysis of Tarjan}', fontsize=11)
        if show: plt.show()
        if export: f.savefig("./src/test/java/Benchmarks/Results/TarjanPlot.pdf", bbox_inches='tight')

    def schmidt(self, show, export):
        df1 = pd.read_csv("./src/test/java/Benchmarks/Results/SchmidtKx.csv", usecols=self.columns)
        x1, y1, err1 = self.getParams(df1)

        df2 = pd.read_csv("./src/test/java/Benchmarks/Results/SchmidtPath.csv", usecols=self.columns)
        x2, y2, err2 = self.getParams(df2)

        f = plt.figure()
        self.setOptions(plt)

        plt.errorbar(x1, y1, yerr=err1, fmt="o", markersize=4, label="Kx")
        plt.errorbar(x2, y2, yerr=err2, fmt="D", markersize=4, label="path")
        
        plt.legend(loc='lower right')
        plt.title(r'\textbf{Empirical analysis of Schmidt}', fontsize=11)
        if show: plt.show()
        if export: f.savefig("./src/test/java/Benchmarks/Results/SchmidtPlot.pdf", bbox_inches='tight')

    