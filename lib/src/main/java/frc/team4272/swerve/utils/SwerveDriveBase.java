package frc.team4272.swerve.utils;

import java.lang.reflect.Array;
import java.util.List;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team4272.globals.Gyroscope;
import frc.team4272.swerve.utils.SwerveModuleBase.PositionedSwerveModule;

public class SwerveDriveBase<G extends Gyroscope, M extends SwerveModuleBase> extends SubsystemBase {
    protected final G gyroscope;
    protected final SwerveDriveKinematics kinematics;
    protected final M[] modules;
    protected final int numModules;

    private double maxTranslational;
    private double maxRotational;
    private double maxTotal;

    /**
     * 
     * @param gyroscope Gyroscope for field relative driving
     * @param modules All the modules on your robot and their positions for kinematics
     */
    public SwerveDriveBase(G gyroscope, Class<M> moduleClass, List<PositionedSwerveModule<M>> modules) {
        this.gyroscope = gyroscope;
        this.numModules = modules.size();

        Translation2d[] positions = new Translation2d[numModules];

        @SuppressWarnings({"unchecked"}) // Suppress unchecked warning because there is type checking elsewhere
        M[] swerveModules = (M[]) Array.newInstance(moduleClass, numModules);

        for(int i = 0; i < numModules; i++) {
            PositionedSwerveModule<M> module = modules.get(i);
            positions[i] = module.getPosition();
            swerveModules[i] = module.getModule();
        }

        this.kinematics = new SwerveDriveKinematics(positions);
        this.modules = swerveModules;
    }

    public SwerveDriveBase<G,M> setMaxSpeeds(double translationalSpeed, double rotationalSpeed, double moduleSpeed) {
        maxTranslational = translationalSpeed;
        maxRotational = rotationalSpeed;
        maxTotal = moduleSpeed;
        return this;
    }

    public SwerveDriveBase<G,M> setMaxSpeeds(double moduleSpeed) {
        maxTranslational = 0.0;
        maxRotational = 0.0;
        maxTotal = moduleSpeed;
        return this;
    }

    public void drive(ChassisSpeeds speeds) {
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);
        if(maxTotal == 0) {
            throw new IllegalCallerException("Max total must have a value. Call setMaxSpeeds at some point during initialization");
        }

        if(maxTranslational == 0 && maxRotational == 0) {
            SwerveDriveKinematics.desaturateWheelSpeeds(states, maxTotal);
        } else {
            SwerveDriveKinematics.desaturateWheelSpeeds(states, speeds, maxTotal, maxTranslational, maxRotational);
        }

        setStates(states);
    }

    /**
     * Drive in robot oriented
     * @param xSpeed
     * @param ySpeed
     * @param thetaSpeed
     */
    public void drive(double xSpeed, double ySpeed, double thetaSpeed) {
        ChassisSpeeds speeds = new ChassisSpeeds(xSpeed, ySpeed, thetaSpeed);
        drive(speeds);
    }

    /**
     * Drive in field oriented
     * @param xSpeed
     * @param ySpeed
     * @param thetaSpeed
     */
    public void driveFieldOriented(double xSpeed, double ySpeed, double thetaSpeed) {
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, thetaSpeed, gyroscope.getRotation());
        drive(speeds);
    }

    public G getGyroscope() {
        return gyroscope;
    }

    /**
     * Set the states of all the modules
     * @implNote This should be overwritten with a more optimized version when subclassed, but technically, its not necessary
     * @param states
     */
    public void setStates(SwerveModuleState... states) {
        if(states.length != numModules) throw new IllegalArgumentException("Number of states provided doesnt match number of modules");

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
        SwerveModulePosition[] positions = new SwerveModulePosition[numModules];

        for(int i = 0; i < numModules; i++) {
            positions[i] = modules[i].getPosition();
        }

        return positions;
    }
}
