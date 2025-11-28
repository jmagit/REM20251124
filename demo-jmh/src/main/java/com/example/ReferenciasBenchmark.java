package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@Warmup(timeUnit = TimeUnit.SECONDS, time = 1, iterations = 2)
@Measurement(timeUnit = TimeUnit.SECONDS, time = 1, iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class ReferenciasBenchmark {
	static final int ELEMENTOS = 100;
	
	@State(Scope.Thread)
	public static class ArrayState {
		int[] elementos;

		@Setup
		public void prepare() {
			elementos = new int[ELEMENTOS];
			for (int i = 0; i < elementos.length; i++)
				elementos[i] = i * 2;
		}
	}
	
	@State(Scope.Thread)
	public static class ListState {
		List<Integer> elementos;

		@Setup
		public void prepare() {
			elementos = new ArrayList<>();
			for (int i = 0; i < ELEMENTOS; i++)
				elementos.add(i * 2);
		}
	}

	@Benchmark
	public void baseline() {
	}

	@Benchmark
	public int withArray(ArrayState estado) {
		var result = 0;
		for(int i : estado.elementos)
			result += i;
		return result;
	}

	@Benchmark
	public int withList(ListState estado) {
		var result = 0;
		for(int i : estado.elementos)
			result += i;
		return result;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(ReferenciasBenchmark.class.getSimpleName()).forks(1).build();

		new Runner(opt).run();
	}

}
