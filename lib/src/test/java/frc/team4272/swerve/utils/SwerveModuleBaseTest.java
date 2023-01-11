package frc.team4272.swerve.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import static edu.wpi.first.math.geometry.Rotation2d.fromDegrees;
import static frc.team4272.globals.MathUtils.euclideanModulo;

public class SwerveModuleBaseTest {
    private class SwerveStateParams {
        private final SwerveModuleState desiredState;
        private final Rotation2d currentAngle;
        private final SwerveModuleState testState;

        public SwerveStateParams(double currentDegrees, double desiredDegrees, double testDegrees, boolean inverted) {
            desiredState = new SwerveModuleState(1, fromDegrees(desiredDegrees));
            currentAngle = fromDegrees(currentDegrees);
            testState = new SwerveModuleState(inverted ? -1 : 1, fromDegrees(testDegrees));
        }

        public void runTest() {
            SwerveModuleState computedState = SwerveModuleBase.optimize(desiredState, currentAngle);

            assertEquals(computedState.speedMetersPerSecond, testState.speedMetersPerSecond, 1e-10);
            assertEquals(computedState.angle.getRadians(), testState.angle.getRadians(), 1e-8);
        }
    }

    private SwerveStateParams[] tests;

    @Before public void init() {
        tests = new SwerveStateParams[] {
            //Go straight to angle
            //No wrapping
            new SwerveStateParams(10, 50, 50, false),
            new SwerveStateParams(45, 130, 130, false),
            new SwerveStateParams(50, 10, 10, false),
            new SwerveStateParams(130, 45, 45, false),
            new SwerveStateParams(-10, 10, 10, false),
            new SwerveStateParams(10, -10, -10, false),

            //With wrapping
            new SwerveStateParams(340, 40, 400, false),
            new SwerveStateParams(40, 720, 0, false),
            new SwerveStateParams(720, 45, 765, false),
            new SwerveStateParams(47, 415, 55, false),

            //Ludicrously High Numbers (Obviosly has wrapping)
            new SwerveStateParams(1738257, 1108230, 1738230, false),
            new SwerveStateParams(10192224, 5712807, 10192287, false),

            //Screwing around with zeros
            new SwerveStateParams(720, 0, 720, false),
            new SwerveStateParams(409, 720, 360, false),
            new SwerveStateParams(-3600, 5760, -3600, false),
            new SwerveStateParams(-0, 720, -0, false),
            new SwerveStateParams(-370, 360, -360, false),

            //Go to opposing angle, and invert direction
            new SwerveStateParams(10, 170, -10, true),
            new SwerveStateParams(0, 91, -89, true)
        };
    }

    @Test public void testOptimizePreMade() {
        for (SwerveStateParams swerveStateParams : tests) {
            swerveStateParams.runTest();
        }
    }

    @Test public void testOptimizeRandom() {
        Random random = new Random();

        for(int i = 0; i < 1000000; i++) {
            double currentDegrees = random.nextDouble() * 7200 - 3600;
            double setDegrees = random.nextDouble() * 7200 - 3600;

            SwerveModuleState optimized = SwerveModuleBase.optimize(new SwerveModuleState(1, fromDegrees(setDegrees)), fromDegrees(currentDegrees));

            double optimizedDegrees = optimized.angle.getDegrees();
            

            // Make sure the module didn't move more than 90 degrees
            assertTrue(Math.abs(optimizedDegrees - currentDegrees) < 90);
            // Make sure that the optimized is the same relative position as set point
            assertEquals(euclideanModulo(setDegrees, 360), euclideanModulo(optimizedDegrees + 90 * (optimized.speedMetersPerSecond - 1), 360), 1e-8);
        }
    }
}
