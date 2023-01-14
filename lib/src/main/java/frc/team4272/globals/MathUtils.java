package frc.team4272.globals;

public class MathUtils {
    /**
     * Performs a euclidean modulo operation with the inputs. This wraps negative modulos to positive values
     * @param m divisor
     * @param n dividend
     * @return
     */
    public static double euclideanModulo(double m, double n) {
        return ((m % n) + n) % n;
    }

    /**
     * Wraps the input to be between the min and max
     * @param input
     * @param min
     * @param max
     * @return
     */
    public static double inputModulo(double input, double min, double max) {
        return euclideanModulo(input - min, (max - min)) + min;
    }

    /**
     * Deadbands the input to be between 0 and 1 after the deadzone on either side
     * @param input
     * @param deadzone
     * @return deadbanded input
     */
    public static double deadband(double input, double deadzone) {
        return Math.abs(input) < deadzone ? 0.0 : (input - Math.signum(input) * deadzone) / (1.0 - deadzone);
    }
}
