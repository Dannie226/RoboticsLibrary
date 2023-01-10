package frc.team4272.globals;

import edu.wpi.first.math.geometry.Rotation2d;

public interface Gyroscope {
    /**
     * Rotation should read counterclockwise positive
    */
    public Rotation2d getRotation();

    public void setRotation(Rotation2d rotation);
}
