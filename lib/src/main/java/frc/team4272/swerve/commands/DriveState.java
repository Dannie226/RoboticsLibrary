package frc.team4272.swerve.commands;

import java.util.function.DoubleSupplier;

import frc.team4272.globals.Gyroscope;
import frc.team4272.globals.State;
import frc.team4272.swerve.utils.SwerveDriveBase;
import frc.team4272.swerve.utils.SwerveModuleBase;

public class DriveState<G extends Gyroscope, M extends SwerveModuleBase> extends State<SwerveDriveBase<G,M>> {
    private DoubleSupplier xSpeed;
    private DoubleSupplier ySpeed;
    private DoubleSupplier thetaSpeed;
    private boolean fieldOriented;

    public DriveState(SwerveDriveBase<G,M> swerveDrive, DoubleSupplier xSpeed, DoubleSupplier ySpeed, DoubleSupplier thetaSpeed, boolean fieldOriented) {
        super(swerveDrive);

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.thetaSpeed = thetaSpeed;
        this.fieldOriented = fieldOriented;

    }

    @Override
    public void execute() {
        if(fieldOriented) {
            requiredSubsystem.driveFieldOriented(xSpeed.getAsDouble(), ySpeed.getAsDouble(), thetaSpeed.getAsDouble());
        } else {
            requiredSubsystem.drive(xSpeed.getAsDouble(), ySpeed.getAsDouble(), thetaSpeed.getAsDouble());
        } 
    }
}
