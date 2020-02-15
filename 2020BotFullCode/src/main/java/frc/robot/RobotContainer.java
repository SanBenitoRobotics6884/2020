/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.LauncherSystem;
import frc.robot.subsystems.RevolverSystem;
import frc.robot.Constants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.SetDriveSpeedCoef;
import frc.robot.commands.SetIntake;
import frc.robot.commands.SetLauncher;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  private XboxController m_controller0 = new XboxController(0);

  // The robot's subsystems and commands are defined here...
  public static DriveSystem driveSystem = new DriveSystem();
  public static IntakeSystem intakeSystem = new IntakeSystem();
  public static RevolverSystem revolverSystem = new RevolverSystem();
  public static LauncherSystem launcherSystem = new LauncherSystem();

  public static ArcadeDrive arcadeDrive = new ArcadeDrive();
  public static SetDriveSpeedCoef setDriveSlow = new SetDriveSpeedCoef(Constants.kDriveSlow);
  public static SetDriveSpeedCoef setDriveFast = new SetDriveSpeedCoef(Constants.kDriveFast);
  public static SetIntake startIntake = new SetIntake(true);
  public static SetIntake stopIntake = new SetIntake(false);
  public static SetLauncher startLauncher = new SetLauncher(true);
  public static SetLauncher stopLauncher = new SetLauncher(false);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    JoystickButton leftBumper = new JoystickButton(m_controller0, Constants.kLeftBumper);
    JoystickButton rightBumper = new JoystickButton(m_controller0, Constants.kRightBumper);
    JoystickButton buttonA = new JoystickButton(m_controller0, Constants.kButtonA);
    JoystickButton buttonB = new JoystickButton(m_controller0, Constants.kButtonB);

    leftBumper.whenPressed(setDriveSlow);
    rightBumper.whenPressed(setDriveFast);
    buttonA.whenPressed(startIntake);
    buttonA.whenReleased(stopIntake);
    buttonB.whenPressed(startLauncher);
    buttonB.whenReleased(stopLauncher);

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
