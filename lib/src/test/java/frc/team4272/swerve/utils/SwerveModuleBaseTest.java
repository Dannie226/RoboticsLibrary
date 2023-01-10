package frc.team4272.swerve.utils;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModuleBaseTest {

    private Comparator<SwerveModuleState> equal;

    @Before public void init() {
        equal = new Comparator<SwerveModuleState>() {
            public int compare(SwerveModuleState a, SwerveModuleState b) {
                assertEquals(a.speedMetersPerSecond, b.speedMetersPerSecond, 1e-6);
                assertEquals(a.angle.getRadians(), b.angle.getRadians(), 1e-6);
                return 0;
            }
        };
    }

    @Test public void testOptimize() {
        
    }

    private static <T> void assertSimilar(Comparator<T> comparator, T a, T b) {
        comparator.compare(a, b);
    }
}
