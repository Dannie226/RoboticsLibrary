package frc.team4272.swerve.utils;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team4272.globals.Gyroscope;
import frc.team4272.swerve.utils.SwerveModuleBase.PositionedSwerveModule;

public class SwerveDriveBase extends SubsystemBase {
    protected final Gyroscope gyroscope;
    protected final SwerveDriveKinematics kinematics;
    protected final SwerveModuleBase[] modules;

    /**
     * 
     * @param gyroscope Gyroscope for field relative driving
     * @param modules All the modules on your robot and their positions for kinematics
     */
    public SwerveDriveBase(Gyroscope gyroscope, PositionedSwerveModule... modules) {
        this.gyroscope = gyroscope;

        Translation2d[] positions = new Translation2d[modules.length];
        SwerveModuleBase[] swerveModules = new SwerveModuleBase[modules.length];

        for(int i = 0; i < modules.length; i++) {
            PositionedSwerveModule module = modules[i];
            positions[i] = module.getPosition();
            swerveModules[i] = module.getModule();
        }

        this.kinematics = new SwerveDriveKinematics(positions);
        this.modules = swerveModules;
    }

    /**
     * Drive in robot oriented
     * @param xSpeed
     * @param ySpeed
     * @param thetaSpeed
     */
    public void drive(double xSpeed, double ySpeed, double thetaSpeed) {
        ChassisSpeeds speeds = new ChassisSpeeds(xSpeed, ySpeed, thetaSpeed);
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

        setStates(states);
    }

    /**
     * Drive in field oriented
     * @param xSpeed
     * @param ySpeed
     * @param thetaSpeed
     */
    public void driveFieldOriented(double xSpeed, double ySpeed, double thetaSpeed) {
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, thetaSpeed, gyroscope.getRotation());
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

        setStates(states);
    }

    public Gyroscope getGyroscope() {
        return gyroscope;
    }

    /**
     * Set the states of all the modules
     * @implNote This should be overwritten with a more optimized version when subclassed, but technically, its not necessary
     * @param states
     */
    public void setStates(SwerveModuleState... states) {
        if(states.length != modules.length) throw new IllegalArgumentException("Number of states provided doesnt match number of modules");

        for(int i = 0; i < states.length; i++) {
            modules[i].goToState(states[i]);
        }
    }

    /**
     * Get the positions of all the modules. This is never used internally, but if desired, it can be used for odometry
     * @implNote If this is going to be used, it should be replaced with a more optimized version when subclassed
     * @return
     */
    public SwerveModulePosition[] getPositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];

        for(int i = 0; i < modules.length; i++) {
            positions[i] = modules[i].getPosition();
        }

        return positions;
    }
}
