package frc.team4272.swerve.utils;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.team4272.globals.MathUtils;

public abstract class SwerveModuleBase {
    public abstract SwerveModulePosition getPosition();
    public abstract void goToState(SwerveModuleState state);

    /**
     * An optimize function that automatically handles all continuity logic.
     * The modules should never have to rotate more than 90 degrees to get to any
     * rotation. This optimize function automatically handles the continuity logic
     * so you can use the built in PID controller on whatever motor you happen to be
     * using.
     * @param desiredState
     * @param currentHeading
     * @return
     */
    public static SwerveModuleState optimize(SwerveModuleState desiredState, Rotation2d currentHeading) {
        double inverted = 1.0;

        double desiredDegrees = MathUtils.euclideanModulo(desiredState.angle.getDegrees(), 360.0);
        
        double currentDegrees = currentHeading.getDegrees();
        double currentMod = MathUtils.euclideanModulo(currentDegrees, 360.0);

        double deltaAngle = Math.abs(desiredDegrees - currentMod);

        if(deltaAngle > 90.0 && deltaAngle <= 270.0) {
            inverted = -1.0;
            desiredDegrees -= 180.0;
        }

        deltaAngle = MathUtils.euclideanModulo(desiredDegrees - currentMod, 360.0);

        double counterClock = deltaAngle;
        double clock = deltaAngle - 360.0;

        if(Math.abs(counterClock) < Math.abs(clock)) {
            desiredDegrees = counterClock;
        } else {
            desiredDegrees = clock;
        }

        desiredDegrees += currentDegrees;

        return new SwerveModuleState(desiredState.speedMetersPerSecond * inverted, Rotation2d.fromDegrees(desiredDegrees));
    }

    public static class PositionedSwerveModule<M extends SwerveModuleBase> {
        private M module;
        private Translation2d position;

        public PositionedSwerveModule(M module, Translation2d position) {
            this.module = module;
            this.position = position;
        }

        public PositionedSwerveModule(M module, double x, double y) {
            this(module, new Translation2d(x, y));
        }

        public M getModule() {
            return module;
        }

        public Translation2d getPosition() {
            return position;
        }
    }
}