package org.javacodingprodigy.Functions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {
public static final Polynomial GOLDEN_POLYNOMIAL = new Polynomial(-1,-1,1);
    @org.junit.jupiter.api.Test void parseLine() {
    }

    @org.junit.jupiter.api.Test void getReducible() {
    }

    @org.junit.jupiter.api.Test void getRoots() {
        assertEquals(GOLDEN_POLYNOMIAL.getRoots(), List.of(0.5 + Math.sqrt(5)/2, 0.5 - Math.sqrt(5)/2));
    }
}
