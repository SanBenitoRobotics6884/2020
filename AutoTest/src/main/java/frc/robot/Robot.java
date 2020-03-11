/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
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

  private WPI_TalonSRX m_leftLead = new WPI_TalonSRX(2);
  private WPI_VictorSPX m_leftFollow = new WPI_VictorSPX(20);
  private WPI_TalonSRX m_rightLead = new WPI_TalonSRX(1);
  private WPI_VictorSPX m_rightFollow = new WPI_VictorSPX(10);

  private DifferentialDrive m_Drive = new DifferentialDrive(m_leftLead, m_rightLead);

  private Joystick m_joystick = new Joystick(0);

  private Timer m_timer = new Timer();
  private static final double kEndTime = 5;
  
  private int m_selected;
  private final SendableChooser<Integer> m_autoChooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    /* factory default values */
    m_rightLead.configFactoryDefault();
    m_rightFollow.configFactoryDefault();
    m_leftLead.configFactoryDefault();
    m_leftFollow.configFactoryDefault();

    /* set up followers */
    m_rightFollow.follow(m_rightLead);
    m_leftFollow.follow(m_leftLead);

    /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
    m_rightLead.setInverted(true); // !< Update this
    m_leftLead.setInverted(false); // !< Update this

    /*
     * set the invert of the followers to match their respective master controllers
     */
    m_rightFollow.setInverted(InvertType.FollowMaster);
    m_leftFollow.setInverted(InvertType.FollowMaster);

    m_autoChooser.setDefaultOption("Time", 1); //Time
    m_autoChooser.addOption("Auto Routine 2", 2); //Encoders
    m_autoChooser.addOption("Auto Routine 3", 3); //Vision
    m_autoChooser.addOption("Auto Routine 4", 4);
    SmartDashboard.putData("Speed Choices", m_autoChooser);

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

  public void AutoLoopOne() {
    if (m_timer.getMatchTime() < kEndTime) {
      m_Drive.arcadeDrive(0.2, 0);
    } else {
      m_Drive.arcadeDrive(0, 0);
    }
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
    m_selected = m_autoChooser.getSelected();
    switch (m_selected) {
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

    m_selected = m_autoChooser.getSelected();

    switch (m_selected) {
      case 1:
        AutoLoopOne();
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
    }

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
