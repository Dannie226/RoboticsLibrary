package frc.team4272.controllers.utilities;

import frc.team4272.globals.MathUtils;

class JoystickUtilities {
    private JoystickUtilities() {}

    public static double powerScale(double input, double power) {
        return Math.signum(input) * Math.pow(Math.abs(input), power);
    }

    public static double deadbandAndPowerScale(double input, double deadzone, double power) {
        return powerScale(MathUtils.deadband(input, deadzone), power);
    }
}
