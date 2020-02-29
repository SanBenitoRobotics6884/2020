/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  WPI_TalonSRX _rghtFront = new WPI_TalonSRX(2);
  WPI_TalonSRX _rghtFollower = new WPI_TalonSRX(20);
  WPI_TalonSRX _leftFront = new WPI_TalonSRX(1);
  WPI_TalonSRX _leftFollower = new WPI_TalonSRX(10);

  DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);

  Joystick _joystick = new Joystick(0);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    _rghtFront.configFactoryDefault();
    _rghtFollower.configFactoryDefault();
    _leftFront.configFactoryDefault();
    _leftFollower.configFactoryDefault();

    _rghtFollower.follow(_rghtFront);
    _leftFollower.follow(_leftFront);

    _rghtFront.setInverted(true);
    _leftFront.setInverted(false);

    _rghtFollower.setInverted(InvertType.FollowMaster);
    _leftFollower.setInverted(InvertType.FollowMaster);

    _rghtFront.setSensorPhase(true);
    _leftFront.setSensorPhase(true);

    _diffDrive.setRightSideInverted(false);


  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double forw = -1 * _joystick.getRawAxis(1);
    double turn = +1 * _joystick.getRawAxis(2);
    boolean btn1 = _joystick.getRawButton(1);

    if (Math.abs(forw) < 0.10) {
      forw = 0;
  }
  if (Math.abs(turn) < 0.10) {
      turn = 0;
  }
  _diffDrive.arcadeDrive(forw, turn);

  }
/* print to console if btn1 is held down */

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
