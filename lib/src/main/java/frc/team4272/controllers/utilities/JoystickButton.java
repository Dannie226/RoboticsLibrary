package frc.team4272.controllers.utilities;

import edu.wpi.first.wpilibj.GenericHID;

public class JoystickButton {
    public final GenericHID controller;
    public final int port;

    public JoystickButton(GenericHID controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    public boolean get() {
        return controller.getRawButton(port);
    }
}
