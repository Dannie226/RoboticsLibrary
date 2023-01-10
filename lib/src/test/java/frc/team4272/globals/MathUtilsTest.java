package frc.team4272.globals;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilsTest {
    @Test public void testEuclidModulo() {
        assertEquals(MathUtils.euclideanModulo(5, 2), 1, 1e-12);
        assertEquals(MathUtils.euclideanModulo(-6, 2), 0, 1e-12);
        assertEquals(MathUtils.euclideanModulo(-5, 3), 1, 1e-12);
        assertEquals(MathUtils.euclideanModulo(1.5, 1), 0.5, 1e-12);
        assertEquals(MathUtils.euclideanModulo(-1.25, 1), 0.75, 1e-12);
    }

    @Test public void testInputModulo() {
        assertEquals(MathUtils.inputModulo(1.5, 0, 5), 1.5, 1e-12);
        assertEquals(MathUtils.inputModulo(-3.4, -2, 2), 0.6, 1e-12);
        assertEquals(MathUtils.inputModulo(0.5, 1, 2), 1.5, 1e-12);
        assertEquals(MathUtils.inputModulo(7.6, 2, 5), 4.6, 1e-12);
    }

    @Test public void testDeadband() {
        assertEquals(MathUtils.deadband(0.1, 0.15), 0, 1e-12);
        assertEquals(MathUtils.deadband(1, 0.1), 1, 1e-12);
        assertEquals(MathUtils.deadband(0.15, 0.15), 0, 1e-12);
        assertEquals(MathUtils.deadband(0.55, 0.1), 0.5, 1e-12);
    }
}
