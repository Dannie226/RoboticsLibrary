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
