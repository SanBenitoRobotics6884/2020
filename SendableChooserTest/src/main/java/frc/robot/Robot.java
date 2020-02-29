/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
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

  private WPI_VictorSPX m_motor = new WPI_VictorSPX(10);

  private Joystick m_joystick = new Joystick(0);
  
  private static final String kSpeedA = "A";
  private static final String kSpeedB = "B";
  private static final String kSpeedC = "C";
  private static final String kSpeedD = "D";
  private String m_speedSelected;
  private final SendableChooser<String> m_speedChooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    m_motor.configFactoryDefault();
    m_motor.setInverted(false);

    m_speedChooser.setDefaultOption("10%", kSpeedA);
    m_speedChooser.addOption("30%", kSpeedB);
    m_speedChooser.addOption("50%", kSpeedC);
    m_speedChooser.addOption("100%", kSpeedD);
    SmartDashboard.putData("Speed Choices", m_speedChooser);

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
  public void teleopInit() {
    m_speedSelected = m_speedChooser.getSelected();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void teleopPeriodic() {
    m_speedSelected = m_speedChooser.getSelected();

    switch (m_speedSelected) {
      case kSpeedA:
        if (m_joystick.getRawButton(1)) m_motor.set(0.1);
        break;
      case kSpeedB:
        if (m_joystick.getRawButton(1)) m_motor.set(0.3);
        break;
      case kSpeedC:
        if (m_joystick.getRawButton(1)) m_motor.set(0.5);
        break;
      case kSpeedD:
        if (m_joystick.getRawButton(1)) m_motor.set(1.0);
        break;
    }

    if (!m_joystick.getRawButton(1)) m_motor.set(0);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
