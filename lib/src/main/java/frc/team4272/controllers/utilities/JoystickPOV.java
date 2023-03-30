package frc.team4272.controllers.utilities;

import edu.wpi.first.wpilibj.GenericHID;

public class JoystickPOV {
    private final GenericHID controller;
    private final int port;

    public static enum Direction {
        UP(0),
        UP_RIGHT(45),
        RIGHT(90),
        DOWN_RIGHT(135),
        DOWN(180),
        DOWN_LEFT(225),
        LEFT(270),
        UP_LEFT(315),
        NONE(-1);

        private int value;
        private Direction(int value) {
            this.value = value;
        }

        public boolean matches(int value) {
            return this.value == value;
        }

        public static Direction getFromValue(int value) {
            switch(value) {
                case 0:
                    return UP;

                case 45:
                    return UP_RIGHT;

                case 90:
                    return RIGHT;

                case 135:
                    return DOWN_RIGHT;

                case 180:
                    return DOWN;

                case 225:
                    return DOWN_LEFT;

                case 270:
                    return LEFT;

                case 315:
                    return UP_LEFT;

                case -1:
                    return NONE;

                default:
                    throw new IllegalArgumentException("%d is not a valid POV Direction".formatted(value));
            }
        }
    }

    public JoystickPOV(GenericHID controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    public int getValue() {
        return controller.getPOV(port);
    }

    public Direction getDirection() {
        return Direction.getFromValue(getValue());
    }
}
