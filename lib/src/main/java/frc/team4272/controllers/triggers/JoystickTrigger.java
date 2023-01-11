package frc.team4272.controllers.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static frc.team4272.globals.MathUtils.deadband;

public class JoystickTrigger extends Trigger {
    private GenericHID controller;
    private int port;
    private double deadzone;
    private double power;

    public JoystickTrigger(GenericHID controller, int port, double deadzone) {
        this.controller = controller;
        this.port = port;
        this.deadzone = deadzone;
        this.power =  1;
    }

    private double powerScale(double value) {
        double s = Math.signum(value);
        return s * Math.pow(value * s, power);
    }

    public double getRaw() {
        return controller.getRawAxis(port);
    }

    public double getDeadzoned() {
        return powerScale(deadband(getRaw(), deadzone));
    }

    @Override
    public boolean get() {
        return getRaw() > deadzone;
    }
}