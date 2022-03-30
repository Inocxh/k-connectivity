package Benchmarks;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ParamTest {

    @Param({"1","10","100","1000","10000","100000"})
    public int lol;
}