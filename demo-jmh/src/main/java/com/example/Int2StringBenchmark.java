package com.example;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.Throughput)
@Warmup(timeUnit = TimeUnit.SECONDS, time = 1, iterations = 2 )
@Measurement(timeUnit = TimeUnit.SECONDS, time = 1, iterations = 5 )
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
public class Int2StringBenchmark {

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
