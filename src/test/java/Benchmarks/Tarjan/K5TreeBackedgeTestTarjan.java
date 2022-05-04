package Benchmarks.Tarjan;

import Benchmarks.BenchmarkRunner;
import graphs.ArrayGraph;
import graphs.Tarjan;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;

import java.io.File;
import java.util.Scanner;

@State(Scope.Benchmark)
public class K5TreeBackedgeTestTarjan {
    @Param({"1","2","4","8","16","32","64","128","256","512"})
    public int x;

    public ArrayGraph g;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/test/graphs/generated/k5-tree-backedges/" + x + ".gr";
        File f = new File(path);
        g = ArrayGraph.fromString(new Scanner(f));
    }

    @Benchmark
    public void tarjanK5TreeBackedge() {
        Tarjan.is1EdgeConnected(g);
    }

    public static void main(String[] args) throws RunnerException {
        Options o = BenchmarkRunner
                .getOptions()
                .include(K5TreeBackedgeTestTarjan.class.getSimpleName())
                .result("./src/test/java/Benchmarks/Results/TarjanK5TreeBackedge.csv")
                .build();
        new Runner(o).run();
    }
}
