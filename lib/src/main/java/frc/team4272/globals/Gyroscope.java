package frc.team4272.globals;

import edu.wpi.first.math.geometry.Rotation2d;

public interface Gyroscope {
    /**
     * @implNote Rotation from this should read counterclockwise positive
     * @return Rotation reading from the gyroscope
     */
    public Rotation2d getRotation();

    public void setRotation(Rotation2d rotation);
}
