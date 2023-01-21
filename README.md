# 4272 Robotics Library
Library for holding general purpose programming helpers

## Building
1. Clone Repository
2. Run ./gradlew build in a terminal
3. JAR should be placed into the build folder

## Adding to Build System
1. Create a folder called libs in the root directory of the project
2. Add built library to the libs folder
3. Go into build.gradle and add 
```groovy
repositories {
  flatDir {
    dirs "./libs" 
  }
}
```
4. Find dependencies block and add ```implementation name:'[insert name of JAR here, omit the .jar]'``` after the ```implementation wpi.java.vendor.java()``` line

Note: As far as I know, this only works for Java programmers. If C++ users can find a way to use this in their own projects, good for them. Otherwise, sorry.

Note: With this setup, if you have any other java libraries you like to use, put the jar in the libs folder, and add another line following step 3.

## Usage

### Swerve

For running swerve with this code, you need to do some setup. First off, I have no way of knowing the configuration of your robot. So, you will have to implement some things yourself. I tried to make it as easy as possible, but, at the end of the day, swerve is complicated. This only exists to attempt to make it less so.

1. The first thing you should do is create a Gyroscope. There is a built in interface for a gyroscope that should be implemented. An example of how this might work is as follows.
```java
public class Pigeon implements Gyroscope {
  public Pigeon() {

  }

  @Override
  public Rotation2d getRotation() {
    return new Rotation2d(0.0);
    // Stub method for example purposes.
    // This needs to be implemented.
  }

  @Override
  public void setRotation(Rotation2d rotation) {
    // Stub method for example purposes.
    // This needs to be implemented
  }
}
```

2. Next, you create your swerve module, with whatever parameters you need. There is also a built in base class for this with methods that you need to implement.
    - Note: There is an optimize function included in the swerve module class. This automatically handles all continuity logic required to make the module move the least distance necessary
```java
public class SwerveModule extends SwerveModuleBase {
  public SwerveModule() {

  }

  public void goToState(SwerveModuleState desiredState) {
    // Stub method for example purposes.
    // This needs to be implemented
  }

  public SwerveModulePosition getPosition() {
    return null;
    // Stub method for example purposes.
    // This is never actually used internally, but kept in
    // incase someone wants to use the built in odometry classes.
    // It still needs to be implemented. If you aren't planning
    // on using it, return null
  }
}
```
3. Finally, you create your swerve drive. This takes in multiple parameters. The first parameter is a gyroscope. If you are never planning on using field relative driving, pass in null. The rest of the parameters are the swerve modules with a position attached to them. The library has a built in class, just call the constructor with your swerve module and its position relative to the center of the robot.

```java
public class SwerveDrive extends SwerveDriveBase {
  public SwerveDrive() {
    super(
      null, // Gyroscope
      new PositionedSwerveModule(new SwerveModule(), xPosition, yPosition),
      new PositionedSwerveModule(new SwerveModule(), new Translation2d(xPosition, yPosition))
    )

    // This constructor is only meant to be as an example
  }

  public void setStates(SwerveModuleState... states) {
    // Stub method for example purposes.
    // This method has a default signature, but a more
    // optimized version of the method should be implemented
    // by the end user.
  }

  public SwerveModulePosition[] getPositions() {
    return null;
    // Stub method for example purposes.
    // This method has a default signature. It is never used
    // internally, so overriding it isn't strictly necessary.
    // The reason this is kept in is so that if someone
    // wishes to use the odometry built into WPILib, that
    // option does exist.
  }
}
```

4. With that, you should have a functioning swerve drive, immenent debugging not withstanding

### Controllers

1. First, you subclass the Controller class included in the library.
2. Add all buttons, triggers, and joysticks on the controller
3. Use controller, and pull buttons.

```java
//Example, though actually works
public class XboxController extends Controller {
  public XboxController(int port) {
    super(port);

    addButton("a", 1);
    addButton("b", 2);
    addButton("x", 3);
    addButton("y", 4);
    addButton("leftBumper", 5);
    addButton("rightBumper", 6);
    addButton("back", 7);
    addButton("start", 8);
    addButton("leftStick", 9);
    addButton("rightStick", 10);

    addAxes("left", 0, 1);
    addAxes("right", 4, 5);

    addTrigger("left", 2);
    addTrigger("right", 3);
  }
}
```

## Full Working use case:
The [2023 Robot Code](https://github.com/maverick-boiler-robotics-team-4272/2023Bot/tree/main) uses the library, and should give a starting point for anyone interested in using the library.