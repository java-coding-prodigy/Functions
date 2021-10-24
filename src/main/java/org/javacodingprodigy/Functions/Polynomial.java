package org.javacodingprodigy.Functions;


import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@SuppressWarnings("unused") public class Polynomial extends MathFunction {
    private static final Pattern POLYNOMIAL_SYNTAX =
            Pattern.compile("([+\\-]?\\d*(\\.\\d*)?)?(x(\\^(-?\\d+))?)?");
    private final double[] coefficients;
    private int degree;

    public Polynomial(Map<Integer, Double> map) {
        map.forEach((x, y) -> {
            if (y != 0) {
                degree = Math.max(degree, x);
            }
        });
        coefficients = new double[degree + 1];
        map.forEach((x, y) -> coefficients[x] = y);
    }

    public Polynomial(double... coefficients) {
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] == 0) {
                coefficients = Arrays.copyOf(coefficients, i);
            } else {
                break;
            }
        }
        this.coefficients = coefficients;
        degree = this.coefficients.length - 1;
    }

    public static Polynomial parseLine(String line) {
        line = line.replaceAll("\\+ ", "+")
                .replaceAll("- ", "-")
                .replaceAll("⁰", "^0")
                .replaceAll("¹", "^1")
                .replaceAll("²", "^2")
                .replaceAll("³", "^3")
                .replaceAll("⁴", "^4")
                .replaceAll("⁵", "^5")
                .replaceAll("⁶", "^6")
                .replaceAll("⁷", "^7")
                .replaceAll("⁸", "^8")
                .replaceAll("⁹", "^9");
        StringTokenizer splitting = new StringTokenizer(line);
        Map<Integer, Double> stuff = new HashMap<>();
        while (splitting.hasMoreTokens()) {
            String token = splitting.nextToken();
            Matcher matcher = POLYNOMIAL_SYNTAX.matcher(token);
            if (!matcher.matches()) {
                throw new InputMismatchException();
            }
            try {
                stuff.put(Integer.parseInt(matcher.group(5)), Double.parseDouble(matcher.group(1)));
            } catch (Exception e) {
                if (matcher.group(1) == null) {
                    if (matcher.group(4) == null) {
                        stuff.put(1, 1D);
                    } else if (matcher.group(3).equals("x" + matcher.group(4))) {
                        stuff.put(Integer.parseInt(matcher.group(5)), 1D);
                    } else {
                        throw e;
                    }
                } else if (matcher.group(3) == null) {
                    stuff.put(0, Double.parseDouble(matcher.group(1)));
                } else if (matcher.group(4) == null) {
                    stuff.put(1, Double.parseDouble(matcher.group(1)));
                }
            }
        }
        return new Polynomial(stuff);
    }

    public static void main(String[] args) {
        System.out.println(new Polynomial(-1, -1, 1).getRoots());
    }


    public Map<Integer, Double> coefficientsAsMap() {
        Map<Integer, Double> map = new HashMap<>(degree);
        map.put(0, 0.0);
        for (int i = 0; i <= degree; i++) {
            if (coefficients[i] != 0) {
                map.put(i, coefficients[i]);
            }
        }
        return map;
    }

    public Polynomial derivative() {
        double[] returnedArr = new double[degree];
        for (int i = 1; i <= degree; i++) {
            returnedArr[i - 1] = coefficients[i] * i;
        }
        return new Polynomial(returnedArr);
    }

    public LinearFunction getTangentLine(double x) {
        return new LinearFunction(derivative().apply(x), x, apply(x));
    }

    public Polynomial add(Polynomial polynomial) {
        int returnedDegreePlusOne = Math.max(degree, polynomial.degree) + 1;
        double[] temp1 = Arrays.copyOf(coefficients, returnedDegreePlusOne);
        double[] temp2 = Arrays.copyOf(polynomial.coefficients, returnedDegreePlusOne);
        double[] returnedArr = new double[returnedDegreePlusOne];
        IntStream.range(0, returnedDegreePlusOne)
                .forEach(i -> returnedArr[i] = temp1[i] + temp2[i]);
        return new Polynomial(returnedArr);
    }

    public Polynomial subtract(Polynomial polynomial) {
        int returnedDegreePlusOne = Math.max(degree, polynomial.degree) + 1;
        double[] temp1 = Arrays.copyOf(coefficients, returnedDegreePlusOne);
        double[] temp2 = Arrays.copyOf(polynomial.coefficients, returnedDegreePlusOne);
        double[] returnedArr = new double[returnedDegreePlusOne];
        IntStream.range(0, returnedDegreePlusOne)
                .forEach(i -> returnedArr[i] = temp1[i] - temp2[i]);
        return new Polynomial(returnedArr);
    }

    private Polynomial multiply(double coefficient, int power) {
        double[] returnedArray = new double[degree + power + 1];
        for (int i = 0; i <= degree; i++) {
            returnedArray[i + power] = coefficient * coefficients[i];
        }
        return new Polynomial(returnedArray);
    }

    public Polynomial multiply(Polynomial polynomial) {
        Polynomial returnedPoly = new Polynomial(0);
        for (int i = 0; i <= polynomial.degree; i++) {
            returnedPoly = returnedPoly.add(multiply(polynomial.coefficients[i], i));
        }
        return (returnedPoly);
    }

    public int getReducible() {
        int count = 0;
        for (double coefficient : coefficients) {
            if (coefficient != 0)
                return count;
            count++;
        }
        return count;
    }

    private @NotNull List<Double> startingPoints() {
        return Collections.emptyList();
    }

    public List<Double> getRoots() {
        List<Double> roots = new ArrayList<>(startingPoints());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < roots.size(); j++) {
                double x = roots.get(j);
                roots.remove(x);
                double den = roots.stream().map(d -> x - d).reduce(1.0, (a, b) -> a * b);
                roots.add(x - apply(x) / den);
            }
        }
        return roots;
    }

    public List<Double> intersectionPoints(Polynomial other) {
        return subtract(other).getRoots();
    }

    @Override public Double apply(final Double x) {
        double sum = 0;
        for (int i = 0; i < degree; i++) {
            sum += coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    @SuppressWarnings("DuplicateExpressions") @Override public String toString() {
        String str1;
        String str2;
        StringBuilder outputString = new StringBuilder();
        for (int i = degree; i >= 0; i--) {
            if (i == degree && coefficients[i] != 0) {
                str1 = String.valueOf(Math.floor(coefficients[i]) == coefficients[i] ?
                        String.format("%.0f", coefficients[i]) :
                        coefficients[i]);
                str2 = switch (i) {
                    case 0 -> "";
                    case 1 -> "x";
                    default -> ("x" + i).replaceAll("0", "⁰")
                            .replaceAll("1", "¹")
                            .replaceAll("2", "²")
                            .replaceAll("3", "³")
                            .replaceAll("4", "⁴")
                            .replaceAll("5", "⁵")
                            .replaceAll("6", "⁶")
                            .replaceAll("7", "⁷")
                            .replaceAll("8", "⁸")
                            .replaceAll("9", "⁹");
                };
            } else if (coefficients[i] != 0) {
                str1 = (coefficients[i] > 0 ? " + " : " - ") + (
                        Math.floor(coefficients[i]) == coefficients[i] ?
                                String.format("%.0f", Math.abs(coefficients[i])) :
                                Math.abs(coefficients[i]));
                switch (i) {
                    case 0 -> str2 = "";
                    case 1 -> str2 = "x";
                    default -> str2 = ("x" + i).replaceAll("0", "⁰")
                            .replaceAll("1", "¹")
                            .replaceAll("2", "²")
                            .replaceAll("3", "³")
                            .replaceAll("4", "⁴")
                            .replaceAll("5", "⁵")
                            .replaceAll("6", "⁶")
                            .replaceAll("7", "⁷")
                            .replaceAll("8", "⁸")
                            .replaceAll("9", "⁹");
                }
            } else {
                str1 = "";
                str2 = "";
            }

            outputString.append(str1).append(str2);
        }
        return outputString.toString();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Polynomial that = (Polynomial) o;
        return degree == that.degree && Arrays.equals(coefficients, that.coefficients);
    }

    @Override public int hashCode() {
        int result = Objects.hash(degree);
        result = 31 * result + Arrays.hashCode(coefficients);
        return result;
    }
}
