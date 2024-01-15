package frc.team4272.controllers;

public class PS4Controller extends Controller {

    public PS4Controller(int port) {
        super(port);

        addButton("square", 1);
        addButton("cross", 2);
        addButton("circle", 3);
        addButton("triangle", 4);
        addButton("L1", 5);
        addButton("R1", 6);
        addButton("L2", 7);
        addButton("R2", 8);
        addButton("share", 9);
        addButton("options", 10);
        addButton("L3", 11);
        addButton("R3", 12);
        addButton("playstation", 13);
        addButton("touchpad", 14);

        addAxes("left", 0, 1);
        addAxes("right", 2, 5);

        addTrigger("left", 3);
        addTrigger("right", 4);

        addPOV("d-pad", 0);
    }
}
