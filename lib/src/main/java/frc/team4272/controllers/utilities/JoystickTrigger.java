package frc.team4272.controllers.utilities;

import edu.wpi.first.wpilibj.GenericHID;

import static frc.team4272.controllers.utilities.JoystickUtilities.deadbandAndPowerScale;

public class JoystickTrigger {
    private final GenericHID controller;
    private final int port;

    private double deadzone;
    private double power;

    public JoystickTrigger(GenericHID controller, int port) {
        this.controller = controller;
        this.port = port;

        this.deadzone = 0.1;
        this.power = 1;
    }

    public JoystickTrigger setDeadzone(double deadzone) {
        this.deadzone = 0.1;
        return this;
    }

    public JoystickTrigger setPowerScaling(double power) {
        this.power = power;
        return this;
    }

    public double getRawValue() {
        return controller.getRawAxis(port);
    }

    public double getValue() {
        return deadbandAndPowerScale(getRawValue(), deadzone, power);
    }
}
