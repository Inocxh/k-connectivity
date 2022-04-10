package Benchmarks.Tarjan;

import Benchmarks.BenchmarkRunner;
import graphs.Tarjan;
import org.openjdk.jmh.annotations.*;
import graphs.ArrayGraph;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;

import java.io.File;
import java.util.Scanner;

@State(Scope.Benchmark)
public class PathTest {
    @Param({"1","2","4","8","16","32","64","128","256","512","1024","2048"})
    public int x;

    public ArrayGraph g;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/test/graphs/generated/k5-path/k5-path-" + x + ".gr";
        File f = new File(path);
        g = ArrayGraph.fromString(new Scanner(f));
    }

    @Benchmark
    public void tarjan() {
        Tarjan.is1EdgeConnected(g);
    }

    public static void main(String[] args) throws RunnerException {
        Options o = BenchmarkRunner
                .getOptions()
                .include(PathTest.class.getSimpleName())
                .result("./src/test/java/Benchmarks/Results/TarjanPath.csv")
                .build();
        new Runner(o).run();
    }
}
