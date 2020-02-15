/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private WPI_VictorSPX m_intakeMotor = new WPI_VictorSPX(20);
  //private Spark m_motor2 = new Spark(1);
  private Joystick m_controller = new Joystick(0);
  double k_speedCoefficient = 0.3;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    System.out.println("test");
    m_intakeMotor.configFactoryDefault();
    m_intakeMotor.setInverted(false);
    m_intakeMotor.setSensorPhase(true);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void robotPeriodic() {
   // if (psController.getRawAxis(4) > 0) m_motor2.set(-psController.getRawAxis(4));
    if (m_controller.getRawAxis(3) > 0) {
      m_intakeMotor.set(m_controller.getRawAxis(3) * k_speedCoefficient);
    } else {
      m_intakeMotor.set(0);
    }

    SmartDashboard.putNumber("Controller Axis 3", m_controller.getRawAxis(3));

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
