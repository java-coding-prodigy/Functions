package org.javacodingprodigy.Functions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public abstract class MathFunction implements Function<Double, Double> {
	private final Map<Double, Double> map = new HashMap<>();

	public static MathFunction of(Function<Double, Double> f) {
		return new MathFunction() {
			@Override
			public Double apply(Double x) {
				return f.apply(x);
			}
		};
	}
	public void fillMap(double startVal, double endVal, double updater) {
		DoubleStream.iterate(startVal, x -> x <= endVal, x -> x + updater)
				.forEach(x -> map.put(x, apply(x)));
	}

	public void printKnown() {
		for (Map.Entry<Double, Double> ent : map.entrySet()) {
			System.out.println("f(" + ent.getKey() + ") = " + ent.getValue());
		}
	}

}
