from matplotlib import pyplot as plt
import pandas as pd
import statsmodels.api as sm
import seaborn as sns
#from sklearn.linear_model import LinearRegression
import numpy as np

def compute_m_plus_n(type_graph, test_case, argument_size):
    #type_graph and test_case is described in the method plot 
    if type_graph == 1:
        m = (argument_size * (argument_size - 1)) + 1
        n = argument_size * 2
        if test_case >= 3:
            m = m + 1
        if test_case >= 4:
            m = m + 1
    if type_graph == 2:
        n = argument_size * argument_size
        m = 2 * (argument_size-1) * argument_size
        if test_case == 3:
            m = m + ((argument_size - 1)**2) * 2
        elif test_case == 4:
            m = 2 * m
    if type_graph == 3:
        number_of_groups =  2**argument_size -1
        m =  number_of_groups * 10 + (2**(argument_size) - 2)*2
        n =  number_of_groups * 5
        if test_case >= 3:
            m = m + (2**(argument_size) - 2)
        if test_case >= 4:
            m = m + (2**(argument_size) - 2)
    return m + n


class Plotter:

    columns = ["Samples","Score","Score Error (99.9%)","Unit","Param: x"]

    def getParams(self, df):
        return df["Param: x"], df["Score"], df["Score Error (99.9%)"]


    def setOptions(self, plt, type_graph, test_case):
        name = ""
        if (test_case==1): name = "2-edge-connectivity: "
        elif (test_case==2): name = "2-edge-and-vertex-connectivity: "
        elif (test_case==3): name = "3-edge-connectivity: "
        elif (test_case==4): name = "4-edge-connectivity: "
        if (type_graph==1): name = name + "Dense graph"
        elif (type_graph==2): name = name + "Grid graph"
        elif (type_graph==3): name = name + "Binary tree of K5\'s"
        plt.title(name, weight='bold').set_fontsize('12')
        plt.xlabel('Input size (n + m)', fontsize=16)
        plt.ylabel('Execution time (ms)', fontsize=16)

    def read_csv(self, type_graph, test_case):
        #type_graph and test_case is described in the method plot 
        start_path = "./src/test/java/Benchmarks/Results/"
        if type_graph == 1:
            if test_case == 1:
                df1 = pd.read_csv(start_path + "Tarjan/TarjanKx.csv", usecols=self.columns)
            elif test_case == 2:
                df1 = pd.read_csv(start_path + "Schmidt/SchmidtKx.csv", usecols=self.columns)
            elif test_case == 3:
                df1 = pd.read_csv(start_path + "Mehlhorn/MehlhornKx.csv", usecols=self.columns)
            elif test_case == 4:
                df1 = pd.read_csv(start_path + "Nadara/NadaraKx.csv", usecols=self.columns)
        elif type_graph == 2:
            if test_case == 1:
                df1 = pd.read_csv(start_path + "Tarjan/TarjanGrid.csv", usecols=self.columns)
            elif test_case == 2:
                df1 = pd.read_csv(start_path + "Schmidt/SchmidtGrid.csv", usecols=self.columns)
            elif test_case == 3:
                df1 = pd.read_csv(start_path + "Mehlhorn/MehlhornGrid.csv", usecols=self.columns)
            elif test_case == 4:
                df1 = pd.read_csv(start_path + "Nadara/NadaraGrid.csv", usecols=self.columns)
        elif type_graph == 3:
            if test_case == 1:
                df1 = pd.read_csv(start_path + "Tarjan/TarjanK5BiTree.csv", usecols=self.columns)
            elif test_case == 2:
                df1 = pd.read_csv(start_path + "Schmidt/SchmidtK5BiTree.csv", usecols=self.columns)
            elif test_case == 3:
                df1 = pd.read_csv(start_path + "Mehlhorn/MehlhornK5BiTree.csv", usecols=self.columns)
            elif test_case == 4:
                df1 = pd.read_csv(start_path + "Nadara/NadaraK5BiTree.csv", usecols=self.columns)
        return df1

    def save_file(self, type_graph, test_case, f):
        #f.savefig("./images/TarjanKxPlot.pdf", bbox_inches='tight') could be used.
        if type_graph == 1:
            if test_case == 1:
                f.savefig("./images/TarjanPlot/TarjanKxPlot.pdf")
            elif test_case == 2:
                f.savefig("./images/SchmidtPlot/SchmidtKxPlot.pdf")
            elif test_case == 3:
                f.savefig("./images/MehlhornPlot/MehlhornKxPlot.pdf")
            elif test_case == 4:
                f.savefig("./images/NadaraPlot/NadaraKxPlot.pdf")
        elif type_graph == 2:
            if test_case == 1:
                f.savefig("./images/TarjanPlot/TarjanGridPlot.pdf")
            elif test_case == 2:
                f.savefig("./images/SchmidtPlot/SchmidtGridPlot.pdf")
            elif test_case == 3:
                f.savefig("./images/MehlhornPlot/MehlhornGridPlot.pdf")
            elif test_case == 4:
                f.savefig("./images/NadaraPlot/NadaraGridPlot.pdf")
        elif type_graph == 3:
            if test_case == 1:
                f.savefig("./images/TarjanPlot/TarjanK5BiTreePlot.pdf")
            elif test_case == 2:
                f.savefig("./images/SchmidtPlot/SchmidtK5BiTreePlot.pdf")
            elif test_case == 3:
                f.savefig("./images/MehlhornPlot/MehlhornK5BiTreePlot.pdf")
            elif test_case == 4:
                f.savefig("./images/NadaraPlot/NadaraK5BiTreePlot.pdf")



    def plot(self, show, export, type_graph, test_case):
        plt.figure()
        # type_graph is 1: Kx-Kx, 2: Grid, 3: K5BiTree
        # test_case is 1: Tarjan, 2: Schmidt, 3: Mehlhorn, 4: Nadara
        df1 = self.read_csv(type_graph, test_case)
        xSize1, time, err1 = self.getParams(df1)
        mn = compute_m_plus_n(type_graph, test_case, xSize1)
        # in our linear_regression model t is the dependt variable and mn is the independt variable
        lm = sm.OLS(time,mn).fit()
        rsquaredv = lm.rsquared
        a = lm.params[0]
        y_pred = a * mn
        # sns.scatterplot(x=mn, y=time)
        sns.lineplot(x=mn,y=y_pred, color='red', label="rsquared value: " + str(round(rsquaredv,4)))

        self.setOptions(plt, type_graph, test_case)

        plt.errorbar(mn, time, yerr=err1, fmt="o", markersize=6, label="Kx")

        # plt.legend(loc='lower right')
        if show: plt.show()
        if export: self.save_file(type_graph, test_case, plt)
