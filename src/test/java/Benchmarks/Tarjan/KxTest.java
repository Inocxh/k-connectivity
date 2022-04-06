package Benchmarks.Tarjan;

import graphs.Schmidt;
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
@Warmup(time = 1)
@Measurement(iterations = 10,time=1)
@State(Scope.Benchmark)
public class KxTest {

    @Param({"1","2","4","8","16","32","64","128","256","512","1024","2048"})
    public int x;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/test/graphs/generated/kx-kx/k"+x+".gr";
        File f = new File(path);
        g = ArrayGraph.fromString(new Scanner(f));
    }

    public ArrayGraph g;


    @Benchmark
    public void tarjan() {
        Tarjan.is1EdgeConnected(g);
    }

    @Benchmark
    public void schmidt() {Schmidt.is2Connected(g);}


    public static void main(String[] args) throws RunnerException {
        Options o = new OptionsBuilder()
                .include(KxTest.class.getSimpleName())
                .build();
        new Runner(o).run();
    }
}
