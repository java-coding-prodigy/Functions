package org.javacodingprodigy.Functions;

import java.awt.geom.Point2D;

public class ExponentialFunctions extends MathFunction {

    private double a;
    private double b;

    public ExponentialFunctions(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public ExponentialFunctions(double a) {
        this(1, a);
    }

    public ExponentialFunctions(Point2D.Double a, Point2D.Double b) {
        this.b = Math.pow(a.y / b.y, 1 / (a.x - b.x));
        this.a = a.y / Math.pow(this.b, a.x);
    }

    public static void main(String[] args) {
        System.out.println(new ExponentialFunctions(Math.E).apply(1.));
    }

    private double getA() {
        return a;
    }

    private void setA(final double a) {
        this.a = a;
    }

    private double getB() {
        return b;
    }

    private void setB(final double b) {
        this.b = b;
    }

    @Override public String toString() {
        String aStr;
        String bStr;
        if (a == 1) {
            aStr = "";
        } else {
            aStr = a + "(";
        }
        if (b == 0 || a == 0) {
            return "f(x) = 0";
        }

        bStr = "e^x" + (a == 1 ? "" : ")");
        return aStr + bStr;
    }

    @Override public Double apply(final Double x) {
        return a * Math.pow(b, x);
    }
}
