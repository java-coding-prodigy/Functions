package org.javacodingprodigy.Functions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
@SuppressWarnings("unused")
public class QuadraticFunction extends Polynomial {
    private double a;
    private double b;
    private double c;

    public QuadraticFunction(double a, double b, double c) {
        super(c, b, a);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public QuadraticFunction(QuadraticFunction f) {
        this(f.a, f.b, f.c);
    }

    public QuadraticFunction(LinearFunction f, LinearFunction g) {
        this(f.getSlope() * g.getSlope(),
                f.getSlope() * g.getYIntercept() + g.getSlope() * f.getYIntercept(),
                f.getYIntercept() + g.getYIntercept());
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getVertex() {
        return -b / (2 * a);
    }

    @Override public Double apply(Double x) {
        return a * x * x + b * x + x;
    }

    @Override public List<Double> getRoots() {
        double desc = b * b - 4 * a * c;
        int sgn = (int) Math.signum(desc);
        if (desc == 0)
            return List.of(-b / (2 * a));
        if (desc > 0) {
            double sqrt = Math.sqrt(desc);
            return List.of((-b - sqrt) / (2 * a), (-b + sqrt) / (2 * a));
        }
        return Collections.emptyList();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof QuadraticFunction that))
            return false;
        if (!super.equals(o))
            return false;
        return Double.compare(that.a, a) == 0 && Double.compare(that.b, b) == 0
                && Double.compare(that.c, c) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(super.hashCode(), a, b, c);
    }
}
