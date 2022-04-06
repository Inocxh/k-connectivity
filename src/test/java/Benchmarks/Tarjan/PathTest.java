package Benchmarks.Tarjan;

import graphs.Tarjan;
import org.openjdk.jmh.annotations.*;
import graphs.ArrayGraph;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(warmups = 1, value = 1)
@Warmup(iterations = 1,time=200,timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10,time=200,timeUnit = TimeUnit.MILLISECONDS)
public class PathTest {

    @State(Scope.Benchmark)
    public static class TestDataPaths {
        @Param({"1","2","4","8","16","32","64","128","256","512","1024","2048"})
        public int x;

        public ArrayGraph g;

        @Setup(Level.Trial)
        public void setup() throws Exception {
            String path = "src/test/graphs/generated/k5-path/k5-path-"+x+".gr";
            File f = new File(path);
            g = ArrayGraph.fromString(new Scanner(f));
        }
    }

    @Benchmark
    public void benchPath(TestDataPaths t) {
        Tarjan.is1EdgeConnected(t.g);
    }

    public static void main(String[] args) throws RunnerException {
        Options o = new OptionsBuilder()
                .include(KxTest.class.getSimpleName())
                .build();
        new Runner(o).run();
    }
}
