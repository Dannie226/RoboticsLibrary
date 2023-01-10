package frc.team4272.globals;

public class MathUtils {
    public static double euclideanModulo(double m, double n) {
        return ((m % n) + n) % n;
    }

    public static double inputModulo(double input, double min, double max) {
        return euclideanModulo(input - min, (max - min)) + min;
    }

    public static double deadband(double input, double deadzone) {
        return Math.abs(input) < deadzone ? 0.0 : (input - Math.signum(input) * deadzone) / (1.0 - deadzone);
    }
}
