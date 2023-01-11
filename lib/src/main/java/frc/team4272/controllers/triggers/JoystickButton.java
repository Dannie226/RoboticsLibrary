package frc.team4272.controllers.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class JoystickButton extends Trigger {
    private GenericHID controller;
    private int port;

    public JoystickButton(GenericHID controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    @Override
    public boolean get() {
        return controller.getRawButton(port);
    }
}
