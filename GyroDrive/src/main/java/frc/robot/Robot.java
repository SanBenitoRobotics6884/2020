/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private ADIS16448_IMU.IMUAxis m_yawActiveAxis;

  private boolean m_runCal = false;
  private boolean m_configCal = false;
  private boolean m_reset = false;

  private static final double kP = -0.005;
  private static final double kI = 0.0;
  private static final double kD = 0.0;

  PIDController m_pidController;

  private final ADIS16448_IMU m_imu = new ADIS16448_IMU();

  WPI_TalonSRX _rghtFront = new WPI_TalonSRX(1);
  WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(10);
  WPI_TalonSRX _leftFront = new WPI_TalonSRX(2);
  WPI_VictorSPX _leftFollower = new WPI_VictorSPX(20);


  private DifferentialDrive m_robotDrive = new DifferentialDrive(_rghtFront, _leftFront);

  double m_driveSpeed;
  double m_rotation;
  double k_coefficient;

  private Joystick m_controller = new Joystick(0);

  private double calculateJoystickAngle( double controllerX, double controllerY) {
    double x = controllerX;
    double y = -controllerY;

    if (Math.pow(x, 2) + Math.pow(y, 2) < Math.pow(0.2, 2)) {
      return 0;
    }

    if (x > 0 && y > 0) {
      return Math.atan( y / x) * (180/Math.PI);
    } else if (x < 0 && y > 0) {
      return 180 + Math.atan( y / x) * (180/Math.PI);
    } if (x < 0 && y < 0) {
      return 180 + Math.atan( y / x) * (180/Math.PI);
    } else if (x > 0 && y < 0) {
      return 360 + Math.atan( y / x) * (180/Math.PI);
    }

    return 0;

  }

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    /* factory default values */
    _rghtFront.configFactoryDefault();
    _rghtFollower.configFactoryDefault();
    _leftFront.configFactoryDefault();
    _leftFollower.configFactoryDefault();

    /* set up followers */
    _rghtFollower.follow(_rghtFront);
    _leftFollower.follow(_leftFront);

    /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
    _rghtFront.setInverted(true); // !< Update this
    _leftFront.setInverted(false); // !< Update this

    /*
     * set the invert of the followers to match their respective master controllers
     */
    _rghtFollower.setInverted(InvertType.FollowMaster);
    _leftFollower.setInverted(InvertType.FollowMaster);

    /*
     * [4] adjust sensor phase so sensor moves positive when Talon LEDs are green
     */
    _rghtFront.setSensorPhase(true);
    _leftFront.setSensorPhase(true);

    /*
     * WPI drivetrain classes defaultly assume left and right are opposite. call
     * this so we can apply + to both sides when moving forward. DO NOT CHANGE
     */
    m_robotDrive.setRightSideInverted(false);

// Set IMU settings
    if (m_configCal) {
      m_imu.configCalTime(8);
    }
    if (m_reset) {
      m_imu.reset();
    }
    if (m_runCal) {
      m_imu.calibrate();
    }

    m_pidController = new PIDController(kP, kI, kD);
    m_pidController.enableContinuousInput(0, 360); //CHECK OUTPUT RANGE
    m_yawActiveAxis = ADIS16448_IMU.IMUAxis.kZ;
    k_coefficient = 0.3;
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

    m_pidController.setSetpoint(calculateJoystickAngle(m_controller.getRawAxis(0), m_controller.getRawAxis(1)));

    if (m_controller.getRawButton(5)) {
      k_coefficient = 0.4;
    } else if (m_controller.getRawButton(6)) {
      k_coefficient = 0.7;
    }

    m_driveSpeed = m_controller.getRawAxis(3) - m_controller.getRawAxis(2);
    m_rotation = m_controller.getRawAxis(0);

    double pidOut
        = m_pidController.calculate(m_imu.getAngle());

    m_robotDrive.curvatureDrive(m_driveSpeed * k_coefficient, pidOut, true);
    SmartDashboard.putNumber("PID Out", pidOut);
    SmartDashboard.putNumber("YawAngle", m_imu.getAngle());

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
   * This function is called periodically during autonomous. pee pee poopoo
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    System.out.println(calculateJoystickAngle(m_controller.getRawAxis(0), m_controller.getRawAxis(1)));
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}