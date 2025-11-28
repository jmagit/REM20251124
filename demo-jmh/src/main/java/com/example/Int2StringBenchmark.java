package com.example;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@Warmup(timeUnit = TimeUnit.SECONDS, time = 1, iterations = 2 )
@Measurement(timeUnit = TimeUnit.SECONDS, time = 1, iterations = 5 )
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Int2StringBenchmark {

	@Param({ "10", "1234567890" })
	int caso = 10;
	
    @Benchmark
    public void baseline() {
    }
    
    @Benchmark
    public String IntegerToString() {
    	return Integer.toString(caso);
    }
    
    @Benchmark
    public String StringValueOf() {
    	return String.valueOf(caso);
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Int2StringBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
