package frc.team4272.controllers.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class JoystickPOV extends Trigger {
    private GenericHID controller;
    private int port;

    public JoystickPOV(GenericHID controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    public int getValue() {
        return controller.getPOV(port);
    }

    @Override
    public boolean get() {
        return getValue() != 0;
    }
}
