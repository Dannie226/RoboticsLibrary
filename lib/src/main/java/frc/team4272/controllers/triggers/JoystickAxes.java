package frc.team4272.controllers.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.team4272.globals.MathUtils.deadband;

public class JoystickAxes extends Trigger {
    public enum DeadzoneMode {
        kMagnitude,
        kXAxis,
        kYAxis
    };

    private GenericHID controller;
    private int xPort;
    private int yPort;
    private double deadzone;
    private double power;
    private DeadzoneMode mode;

    public JoystickAxes(GenericHID controller, int xPort, int yPort, double deadzone) {
        this.controller = controller;
        this.xPort = xPort;
        this.yPort = yPort;
        this.deadzone = deadzone;
        this.mode = DeadzoneMode.kMagnitude;
        this.power = 1;
    }

    private double getAngle() {
        return Math.atan2(getRawY(), getRawX());
    }

    private double getMagnitude() {
        return Math.hypot(getRawX(), getRawY());
    }

    private double powerScale(double value) {
        double s = Math.signum(value);
        return s * Math.pow(value * s, power);
    }

    public DeadzoneMode getDeadzoneMode() {
        return mode;
    }

    public void setDeadzoneMode(DeadzoneMode mode) {
        this.mode = mode;
    }

    public double getPowerScaling() {
        return power;
    }

    public void setPowerScaling(double power) {
        this.power = power;
    }

    public double getRawX() {
        return controller.getRawAxis(xPort);
    }

    public double getRawY() {
        return controller.getRawAxis(yPort);
    }

    public double getDeadzonedX() {
        double v = 0.0;

        switch(mode) {
            case kXAxis:
                v = deadband(getRawX(), deadzone);
                break;
            
            case kMagnitude:
                double mag = deadband(getMagnitude(), deadzone);
                v = Math.cos(getAngle()) * mag;
                break;

            default:
                break;
        }

        return powerScale(v);
    }

    public double getDeadzonedY() {
        double v = 0.0;

        switch(mode) {
            case kYAxis:
                v = deadband(getRawY(), deadzone);
                break;
            
            case kMagnitude:
                double mag = deadband(getMagnitude(), deadzone);
                v = Math.sin(getAngle()) * mag;
                break;

            default:
                break;
        }

        return powerScale(v);
    }

    @Override
    public boolean get() {
        switch(mode) {
            case kMagnitude:
                return getMagnitude() > deadzone;

            case kXAxis:
                return getRawX() > deadzone;

            case kYAxis:
                return getRawY() > deadzone;

            default:
                return false;
        }
    }
}
