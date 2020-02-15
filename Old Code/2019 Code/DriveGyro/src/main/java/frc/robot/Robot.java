/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public Joystick m_joystick = new Joystick(0);
  public Gyro gyro = new AnalogGyro(0);
  double kP = 0.3;
  double angle;

  public Spark m_lFrontMotor = new Spark(1);
  public Spark m_lRearMotor = new Spark(2);
  public Spark m_rFrontMotor = new Spark(3);
  public Spark m_rRearMotor = new Spark(4);

  public SpeedControllerGroup m_lMotors = new SpeedControllerGroup(m_lFrontMotor, m_lRearMotor);
  public SpeedControllerGroup m_rMotors = new SpeedControllerGroup(m_rFrontMotor, m_rRearMotor);

  public DifferentialDrive m_robotDrive = new DifferentialDrive(m_lMotors, m_rMotors);

  @Override
  public void robotInit() {
    gyro.reset();
  }

  @Override
  public void robotPeriodic() {
    angle = gyro.getAngle();
    m_robotDrive.curvatureDrive(m_joystick.getRawAxis(0), m_joystick.getRawAxis(1) - angle*kP, true);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }
}
