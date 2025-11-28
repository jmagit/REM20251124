package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
public class ExcepcionesBenchmark {
	static final int ELEMENTOS = 100;
	
	@State(Scope.Thread)
	public static class ArrayState {
		Optional<Integer>[] elementos;

		@Setup
		public void prepare() {
			elementos = new Optional[ELEMENTOS];
			for (int i = 0; i < elementos.length; i++)
				elementos[i] = Optional.ofNullable(i % 7 == 0 ? null : i * 2);
		}
	}
	
	@State(Scope.Thread)
	public static class ListState {
		List<Optional<Integer>> elementos;

		@Setup
		public void prepare() {
			elementos = new ArrayList<>();
			for (int i = 0; i < ELEMENTOS; i++)
				elementos.add(Optional.ofNullable(i % 7 == 0 ? null : i * 2));
		}
	}

	@Benchmark
	public void baseline() {
	}

	@Benchmark
	public int withArray(ArrayState estado) {
		var result = 0;
		for(var i : estado.elementos)
			if(i.isPresent())
				result += i.get();
		return result;
	}

	@Benchmark
	public int withArrayException(ArrayState estado) {
		var result = 0;
		for(var i : estado.elementos)
			try {
				result += i.get();
			} catch (NoSuchElementException e) {
			}
		return result;
	}

	@Benchmark
	public int withList(ListState estado) {
		var result = 0;
		for(var i : estado.elementos)
			if(i.isPresent())
				result += i.get();
		return result;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(ExcepcionesBenchmark.class.getSimpleName()).forks(1).build();

		new Runner(opt).run();
	}

}
