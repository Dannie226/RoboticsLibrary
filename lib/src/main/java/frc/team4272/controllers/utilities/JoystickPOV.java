package frc.team4272.controllers.utilities;

import edu.wpi.first.wpilibj.GenericHID;

public class JoystickPOV {
    private final GenericHID controller;
    private final int port;

    public JoystickPOV(GenericHID controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    public int getValue() {
        return controller.getPOV(port);
    }
}
