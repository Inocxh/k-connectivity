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
public class KxTestNadara {
    @Param({"4","44","84","124","164","204", "244", "284","324","364"})
    public int x;

    public ArrayGraph g;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/test/graphs/generated/Nadara/kx-kx/" + x + ".gr";
        File f = new File(path);
        g = ArrayGraph.fromString(new Scanner(f));
    }

    @Benchmark
    public void tarjanKx() {
        Tarjan.is1EdgeConnected(g);
    }

    public static void main(String[] args) throws RunnerException {
        Options o = BenchmarkRunner
                .getOptions()
                .include(KxTestNadara.class.getSimpleName())
                .result("./src/test/java/Benchmarks/Results/Nadara/NadaraKx.csv")
                .build();
        new Runner(o).run();
    }
}
