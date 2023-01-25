package frc.team4272.globals;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public interface Gyroscope extends Sendable {
    /**
     * @implNote Rotation from this should read counterclockwise positive
     * @return Rotation reading from the gyroscope
     */
    public Rotation2d getRotation();

    public void setRotation(Rotation2d rotation);

    @Override
    default void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", () -> getRotation().getDegrees(), null);
    }
}
