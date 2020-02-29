# 2020

All code for FRC 6884 Deep-Space. This includes the full competition code as well as individual test projects. For each project, the java files are located in '2020/<ProjectName>/src/main/java/frc/robot/'. All code is written in Java.

## Setup Instructions

### With Gradle
**Getting Started**
1. Clone this repository
2. Navigate to the directory of the target project (i.e. 2020/2020BotFullCode/ )
3. Run `gradlew`

**Gradle Commands**
- `gradlew deploy`
- `gradlew build`
- `gradlew test`

### With Visual Studio (Official IDE)
1. Install VSCode for FRC from the following [instructions](https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/wpilib-setup.html)
2. Open VSCode and navigate to the target project
3. Use the ... menu in the upper right of the window to build, deploy, or test code

## Variable Naming Conventions
**Casing:** All variables must use Camel Case (i.e. `exampleVariableName`)
**Prefixes:**
- **k** - (i.e. `kMotorID`): Used for Constants
- **m_** - (i.e. `m_leftMotor`): Used for private variables

## Resources
- [WPILib Documentation](https://docs.wpilib.org/en/latest/): In-Depth Documentation for WPI Libraries
- [Chief Delphi](https://www.chiefdelphi.com/): Helpful FRC Forum
- [Limelight Documentation](https://docs.limelightvision.io/en/latest/): Documentation for Limelight Vision Camera
- [FRC ReadTheDocs](https://frc-pdr.readthedocs.io/en/latest/): Extremely In-Depth FRC Documentation
- [CTRE Example Java Code](https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java%20General): Example code for CTRE-Phonenix hardware such as the TalonSRX
