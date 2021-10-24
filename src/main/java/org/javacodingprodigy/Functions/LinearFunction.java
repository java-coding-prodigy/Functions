package org.javacodingprodigy.Functions;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@SuppressWarnings({"", "unused"}) public class LinearFunction extends Polynomial
        implements Function<Double, Double> {
    public static final LinearFunction IDENTITY_FUNCTION = new LinearFunction(1, 0);
    public static final LinearFunction ADDITIVE_INVERSE_FUNCTION = new LinearFunction(-1, 0);
    private static final Function<MathFunction, LinearFunction> getFromFunction = f -> {
        if (f instanceof LinearFunction o) {
            return o;
        } else {
            List<Double> inputVals = Arrays.asList(new Double[16]);
            IntStream.range(0, 15).forEach(d -> inputVals.set(d, f.apply((double) d)));
            LinearFunction first = new LinearFunction(0, 0);
            LinearFunction second;
            for (int i = 0; i < inputVals.size(); ) {

                first = new LinearFunction(new Point2D.Double(i, inputVals.get(i)),
                        new Point2D.Double(++i, inputVals.get(i)));
                if (i >= 14) {
                    break;
                }
                second = new LinearFunction(new Point2D.Double(i, inputVals.get(i)),
                        new Point2D.Double(i + 1, inputVals.get(i + 1)));
                if (!first.equals(second)) {
                    throw new ArithmeticException("Not in the form mx + b");
                }
            }
            return new LinearFunction(first);
        }
    };
    private final Map<Double, Double> map = new HashMap<>();
    private double slope;
    private double yIntercept;

    {
        IntStream.range(map.size(), map.size() + 10)
                .forEach(x -> map.put((double) x, apply((double) x)));
    }

    public LinearFunction(double m, double b) {
        super(b, m);
        slope = m;
        yIntercept = b;
    }

    public LinearFunction(LinearFunction f) {
        this(f.slope, f.yIntercept);
    }

    public LinearFunction(MathFunction f) {
        this(getFromFunction.apply(f));
    }

    public LinearFunction(Point2D.Double a, Point2D.Double b) {
        this((a.y - b.y) / (a.x - b.x), a.y - (a.y - b.y) / (a.x - b.x) * a.x);
    }

    public LinearFunction(double slope, double x0, double y0) {
        this(slope, y0 - slope * x0);
    }

    public static void main(String[] args) {
    }

    public static LinearFunction parseLine(String line) {
        line = line.replaceAll(" ", "");
        Pattern validRegex = Pattern.compile(
                "([fgh]\\(x\\)\\s*=)?\\s*(([+-]?\\d*(\\.\\d*)?)?x)?\\s?([+-]?\\s?\\d*(\\.\\d*)?)?");
        Matcher matcher = validRegex.matcher(line);
        if (!matcher.matches()) {
            throw new InputMismatchException("Invalid syntax");
        }
        double slope = matcher.group(3).isEmpty() ?
                (matcher.group(2) == null ? 0 : 1) :
                matcher.group(3).equals("-") ? -1 : Double.parseDouble(matcher.group(3));
        double yIntercept = matcher.group(5).isEmpty() ? 0 : Double.parseDouble(matcher.group(5));
        return new LinearFunction(slope, yIntercept);
    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(final double slope) {
        this.slope = slope;
    }

    public double getYIntercept() {
        return yIntercept;
    }

    public void setYIntercept(final double yIntercept) {
        this.yIntercept = yIntercept;
    }

    public double getXIntercept() {
        return -yIntercept / slope;
    }

    public Map<Double, Double> getMap() {
        return map;
    }

    public void setXIntercept(final double xIntercept, final boolean changeSlope) {
        if (changeSlope) {
            setSlope(-yIntercept / xIntercept);
        } else {
            setYIntercept(-slope * xIntercept);
        }
    }

    @Override public Double apply(final Double x) {
        return slope * x + yIntercept;
    }

    public double inverse(final double y) {
        return (y - yIntercept) / slope;
    }

    public LinearFunction getInverse() {
        return new LinearFunction(1 / slope, -yIntercept / slope);
    }

    public Point2D.Double meets(LinearFunction g) {
        double x = (g.yIntercept - yIntercept) / (slope - g.slope);
        double y = apply(x);
        return new Point2D.Double(x, y);
    }

    public MathFunction piecewise(LinearFunction g) {
        double dcp = (g.yIntercept - yIntercept) / (slope - g.slope);
        return MathFunction.of(x -> {
            if (x >= dcp) {
                return fasterThan(g) ? apply(x) : g.apply(x);
            } else {
                return fasterThan(g) ? g.apply(x) : apply(x);
            }
        });
    }

    public boolean fasterThan(LinearFunction g) {
        return slope > g.slope;
    }

    public LinearFunction fasterIncreasingFunction(LinearFunction g) {
        return fasterThan(g) ? this : g;
    }

    @Override public List<Double> getRoots() {
        return List.of(getXIntercept());
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final LinearFunction that)) {
            return false;
        }
        return Double.compare(that.slope, slope) == 0
                && Double.compare(that.yIntercept, yIntercept) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(slope, yIntercept);
    }

}
