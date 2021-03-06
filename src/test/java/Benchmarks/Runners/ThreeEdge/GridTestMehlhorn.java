package Benchmarks.Runners.ThreeEdge;

import Benchmarks.BenchmarkRunner;
import graphs.ArrayGraph;
import graphs.ThreeEdge.ThreeEdge;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;

import java.io.File;
import java.util.Scanner;

@State(Scope.Benchmark)
public class GridTestMehlhorn {
    @Param({"4","44","84","124","164","204","244","284","324","364"})
    public int x;

    public ArrayGraph g;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/test/graphs/generated/Mehlhorn/grid/" + x + ".gr";
        File f = new File(path);
        g = ArrayGraph.fromString(new Scanner(f));
    }

    @Benchmark
    public void MehlhornGrid() {
        ThreeEdge.is3EdgeConnected(g);
    }

    public static void main(String[] args) throws RunnerException {
        Options o = BenchmarkRunner
                .getOptions()
                .include(GridTestMehlhorn.class.getSimpleName())
                .result("./src/test/java/Benchmarks/Results/Mehlhorn/MehlhornGrid.csv")
                .build();
        new Runner(o).run();
    }
}
