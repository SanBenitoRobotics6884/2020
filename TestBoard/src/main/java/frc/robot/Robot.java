/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             7*/
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
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

  private static int kMotorID = 7;
  private static double kSpeed = 0.15;
  private static boolean kInvert = false;

  private Joystick m_controller = new Joystick(0);
  private WPI_VictorSPX m_pusher = new WPI_VictorSPX(kMotorID);
  private DigitalInput ls = new DigitalInput (1);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_pusher.configFactoryDefault();
    m_pusher.setInverted(kInvert);
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

    SmartDashboard.putBoolean("LS", !ls.get());
    
    if (m_controller.getRawButton(1)) {
      m_pusher.set(kSpeed);
    }

    if (m_controller.getRawButton(2)) {
      m_pusher.set(0);
    }

    if (!ls.get()) {
      m_pusher.set(0);
    }

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
