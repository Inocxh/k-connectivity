package Benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class BenchmarkTest {

    @Fork(warmups = 1,value = 1)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 1,time = 100,timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 10,time = 100,timeUnit = TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void init(ParamTest l) {
        int c = 0;
        for(int i = 0; i < l.lol; i++) {
            for(int j = 0; j < l.lol; j++) {
                c+=i+j*2;
            }
        }
        assert c != 0;
    }
}
