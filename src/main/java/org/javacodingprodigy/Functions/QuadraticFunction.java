package org.javacodingprodigy.Functions;

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

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }
}
