package Benchmarks;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    public static ChainedOptionsBuilder getOptions() {
        return new OptionsBuilder()
                .timeUnit(TimeUnit.MILLISECONDS)
                .forks(1)
                .warmupBatchSize(1)
                .warmupIterations(10)
                .warmupTime(TimeValue.milliseconds(200))
                .measurementIterations(20)
                .measurementTime(TimeValue.milliseconds(200))
                .mode(Mode.AverageTime)
                .resultFormat(ResultFormatType.CSV);
    }
}

