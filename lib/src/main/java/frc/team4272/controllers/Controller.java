package frc.team4272.controllers;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.GenericHID;
import frc.team4272.controllers.triggers.*;

public class Controller extends GenericHID {
    private Map<String, JoystickAxes> axesMap = new HashMap<>();
    private Map<String, JoystickTrigger> triggerMap = new HashMap<>();
    private Map<String, JoystickButton> buttonMap = new HashMap<>();
    private Map<String, JoystickPOV> povMap = new HashMap<>();

    private double axesDeadzone;
    private double triggerDeadzone;

    protected Controller(int port, double axesDeadzone, double triggerDeadzone) {
        super(port);

        this.axesDeadzone = axesDeadzone;
        this.triggerDeadzone = triggerDeadzone;
    }

    protected Controller(int port, double deadzone) {
        this(port, deadzone, deadzone);
    }

    protected void addAxes(String name, int xPort, int yPort) {
        axesMap.put(name, new JoystickAxes(this, xPort, yPort, axesDeadzone));
    }

    public JoystickAxes getAxes(String name) {
        return axesMap.get(name);
    }

    protected void addButton(String name, int port) {
        buttonMap.put(name, new JoystickButton(this, port));
    }

    public JoystickButton getButton(String name) {
        return buttonMap.get(name);
    }

    protected void addTrigger(String name, int port) {
        triggerMap.put(name, new JoystickTrigger(this, port, triggerDeadzone));
    }

    public JoystickTrigger getTrigger(String name) {
        return triggerMap.get(name);
    }

    protected void addPOV(String name, int port) {
        povMap.put(name, new JoystickPOV(this, port));
    }

    public JoystickPOV getPOV(String name) {
        return povMap.get(name);
    }
}