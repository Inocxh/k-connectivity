package Benchmarks.Runners.Nadara;

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
public class K5BiTreeTestNadara {
    @Param({"2","3","4","5","6","7","8","9","10","11"})
    public int x;

    public ArrayGraph g;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/test/graphs/generated/Nadara/k5BiTree/" + x + ".gr";
        File f = new File(path);
        g = ArrayGraph.fromString(new Scanner(f));
    }

    @Benchmark
    public void NadaraBiTree() {
        Tarjan.is1EdgeConnected(g);
    }

    public static void main(String[] args) throws RunnerException {
        Options o = BenchmarkRunner
                .getOptions()
                .include(K5BiTreeTestNadara.class.getSimpleName())
                .result("./src/test/java/Benchmarks/Results/Nadara/NadaraK5BiTree.csv")
                .build();
        new Runner(o).run();
    }
}
