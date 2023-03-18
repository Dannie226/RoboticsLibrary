package frc.team4272.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import frc.team4272.controllers.utilities.JoystickAxes;
import frc.team4272.controllers.utilities.JoystickButton;
import frc.team4272.controllers.utilities.JoystickPOV;
import frc.team4272.controllers.utilities.JoystickTrigger;

import java.util.Map;
import java.util.HashMap;

public class Controller extends GenericHID {
    private final Map<String, JoystickButton> buttonMap = new HashMap<>();
    private final Map<String, JoystickAxes> axesMap = new HashMap<>();
    private final Map<String, JoystickTrigger> triggerMap = new HashMap<>();
    private final Map<String, JoystickPOV> povMap = new HashMap<>();

    protected Controller(int port) {
        super(port);
    }

    protected void addAxes(String name, int xPort, int yPort) {
        if(!axesMap.containsKey(name)){
            axesMap.put(name, new JoystickAxes(this, xPort, yPort));
        } else {
            throw new IllegalArgumentException("The name %s is already taken by another axes object".formatted(name));
        }
    }

    public JoystickAxes getAxes(String name) {
        if(axesMap.containsKey(name)) {
            return axesMap.get(name);
        } else {
            throw new IllegalArgumentException("No axes assigned to the name %s".formatted(name));
        }
    }

    protected void addTrigger(String name, int port) {
        if(!triggerMap.containsKey(name)){
            triggerMap.put(name, new JoystickTrigger(this, port));
        } else {
            throw new IllegalArgumentException("The name %s is already taken by another trigger object".formatted(name));
        }
    }

    public JoystickTrigger getTrigger(String name) {
        if(triggerMap.containsKey(name)) {
            return triggerMap.get(name);
        } else {
            throw new IllegalArgumentException("No trigger assigned to the name %s".formatted(name));
        }
    }

    protected void addButton(String name, int port) {
        if(!buttonMap.containsKey(name)){
            buttonMap.put(name, new JoystickButton(this, port));
        } else {
            throw new IllegalArgumentException("The name %s is already taken by another button object".formatted(name));
        }
    }

    public JoystickButton getButton(String name) {
        if(buttonMap.containsKey(name)) {
            return buttonMap.get(name);
        } else {
            throw new IllegalArgumentException("No button assigned to the name %s".formatted(name));
        }
    }

    protected void addPOV(String name, int port) {
        if(!povMap.containsKey(name)){
            povMap.put(name, new JoystickPOV(this, port));
        } else {
            throw new IllegalArgumentException("The name %s is already taken by another POV object".formatted(name));
        }
    }

    public JoystickPOV getPOV(String name) {
        if(povMap.containsKey(name)) {
            return povMap.get(name);
        } else {
            throw new IllegalArgumentException("No POV assigned to the name %s".formatted(name));
        }
    }
}
