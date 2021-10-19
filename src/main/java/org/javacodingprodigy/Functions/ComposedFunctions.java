package org.javacodingprodigy.Functions;


import java.util.Objects;

public class ComposedFunctions extends MathFunction {

    private final MathFunction f;
    private final MathFunction g;

    public ComposedFunctions(MathFunction f, MathFunction g) {
        this.f = f;
        this.g = g;
    }

    public ComposedFunctions(ComposedFunctions fg) {
        this.f = fg.f;
        this.g = fg.g;
    }

    private MathFunction getF() {
        return f;
    }

    private MathFunction getG() {
        return g;
    }

    @Override public Double apply(final Double t) {
        return this.f.apply(this.g.apply(t));
    }


    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final ComposedFunctions that)) {
            return false;
        }
        return f.equals(that.f) && g.equals(that.g);
    }

    @Override public int hashCode() {
        return Objects.hash(f, g);
    }
}
