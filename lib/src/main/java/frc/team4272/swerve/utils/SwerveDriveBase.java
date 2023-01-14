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

    public void drive(double xSpeed, double ySpeed, double thetaSpeed) {
        ChassisSpeeds speeds = new ChassisSpeeds(xSpeed, ySpeed, thetaSpeed);
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

        setStates(states);
    }

    public void driveFieldOriented(double xSpeed, double ySpeed, double thetaSpeed) {
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, thetaSpeed, gyroscope.getRotation());
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

        setStates(states);
    }

    public Gyroscope getGyroscope() {
        return gyroscope;
    }

    public void setStates(SwerveModuleState... states) {
        if(states.length != modules.length) throw new IllegalArgumentException("Number of states provided doesnt match number of modules");

        for(int i = 0; i < states.length; i++) {
            modules[i].goToState(states[i]);
        }
    }

    public SwerveModulePosition[] getPositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];

        for(int i = 0; i < modules.length; i++) {
            positions[i] = modules[i].getPosition();
        }

        return positions;
    }
}
