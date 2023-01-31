package frc.team4272.controllers.utilities;

import edu.wpi.first.wpilibj.GenericHID;

import static frc.team4272.controllers.utilities.JoystickUtilities.deadbandAndPowerScale;

public class JoystickAxes {
    public enum DeadzoneMode {
        kMagnitude,
        kXAxis,
        kYAxis
    }

    private final GenericHID controller;
    private final int xPort;
    private final int yPort;

    private double deadzone;
    private DeadzoneMode mode;
    private double power;

    public JoystickAxes(GenericHID controller, int xPort, int yPort) {
        this.controller = controller;
        this.xPort = xPort;
        this.yPort = yPort;
        
        this.deadzone = 0.1;
        this.mode = DeadzoneMode.kMagnitude;
        this.power = 1.0;
    }

    public JoystickAxes setPowerScale(double power) {
        this.power = power;
        return this;
    }

    public JoystickAxes setDeadzoneMode(DeadzoneMode mode) {
        this.mode = mode;
        return this;
    }

    public JoystickAxes setDeadzone(double deadzone) {
        this.deadzone = deadzone;
        return this;
    }

    public double getRawX() {
        return controller.getRawAxis(xPort);
    }

    public double getRawY() {
        return controller.getRawAxis(yPort);
    }

    public double getRawMagnitude() {
        return Math.hypot(getRawX(), getRawY());
    }

    public double getAngle() {
        return Math.atan2(getRawY(), getRawX());
    }

    public double getDeadzonedX() {
        switch(mode) {
            case kMagnitude: 
                return getDeadzonedMagnitude() * Math.cos(getAngle());

            case kXAxis:
                return deadbandAndPowerScale(getRawX(), deadzone, power);

            default:
                return 0;
        }
    }

    public double getDeadzonedY() {
        switch(mode) {
            case kMagnitude: 
                return getDeadzonedMagnitude() * Math.sin(getAngle());

            case kYAxis:
                return deadbandAndPowerScale(getRawY(), deadzone, power);

            default:
                return 0;
        }
    }

    public double getDeadzonedMagnitude() {
        return deadbandAndPowerScale(getRawMagnitude(), deadzone, power);
    }
}