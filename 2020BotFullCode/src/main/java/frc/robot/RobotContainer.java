/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.ElevatorSystem;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.LauncherSystem;
import frc.robot.subsystems.RevolverSystem;
import frc.robot.Constants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutoLaunch;
import frc.robot.commands.ExtendLift;
import frc.robot.commands.SetDriveSpeedCoef;
import frc.robot.commands.SetIntake;
import frc.robot.commands.SetLauncher;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


public class RobotContainer {
  
  //Controllers
  private XboxController m_controller = new XboxController(Constants.kControllerChannel);
  private Joystick m_joystick = new Joystick(Constants.kJoystickChannel);

  // Subsystems
  public static DriveSystem driveSystem = new DriveSystem();
  public static IntakeSystem intakeSystem = new IntakeSystem();
  public static RevolverSystem revolverSystem = new RevolverSystem();
  public static LauncherSystem launcherSystem = new LauncherSystem();
  public static ElevatorSystem elevatorSystem = new ElevatorSystem();

  //Commands
  public static SetDriveSpeedCoef setDriveSlow = new SetDriveSpeedCoef(Constants.kDriveSlow);
  public static SetDriveSpeedCoef setDriveFast = new SetDriveSpeedCoef(Constants.kDriveFast);
  public static SetIntake startIntake = new SetIntake(true);
  public static SetIntake stopIntake = new SetIntake(false);
  public static SetLauncher startLauncher = new SetLauncher(true);
  public static SetLauncher stopLauncher = new SetLauncher(false);
  public static AutoLaunch startAutoEject = new AutoLaunch(true);
  public static AutoLaunch stopAutoEject = new AutoLaunch(false);
  public static ExtendLift extendLift = new ExtendLift();


  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  // Configure the button bindings
  private void configureButtonBindings() {

    // Define all necessary buttons
    JoystickButton leftBumper = new JoystickButton(m_controller, Constants.kLeftBumper);
    JoystickButton rightBumper = new JoystickButton(m_controller, Constants.kRightBumper);
    JoystickButton buttonA = new JoystickButton(m_controller, Constants.kButtonA);
    JoystickButton buttonB = new JoystickButton(m_controller, Constants.kButtonB);
    JoystickButton trigger = new JoystickButton(m_joystick, Constants.kJoystickTrigger);
    JoystickButton joystick11 = new JoystickButton(m_joystick, Constants.kJoystickButton11);

    // Bind buttons to commands
    leftBumper.whenPressed(setDriveSlow);
    rightBumper.whenPressed(setDriveFast);
    buttonA.whenPressed(startIntake);
    buttonA.whenReleased(stopIntake);
    buttonB.whenPressed(startLauncher);
    buttonB.whenReleased(stopLauncher);
    trigger.whenPressed(startAutoEject);
    trigger.whenReleased(stopAutoEject);
    joystick11.whenPressed(extendLift);

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  /*
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
  */
}
