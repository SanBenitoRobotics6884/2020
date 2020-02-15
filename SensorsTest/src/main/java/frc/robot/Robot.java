/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.revrobotics.ColorSensorV3;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private AnalogInput ultrasonic = new AnalogInput(0);
  private DigitalInput limitSwitch = new DigitalInput(0);
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private static final String kYawDefault = "Z-Axis";
  private static final String kYawXAxis = "X-Axis";
  private static final String kYawYAxis = "Y-Axis";
  private double xAccumulation = 0;
  private double yAccumulation = 0;
  private double zAccumulation = 0;
  private String m_yawSelected;
  private ADIS16448_IMU.IMUAxis m_yawActiveAxis;
  private final SendableChooser<String> m_yawChooser = new SendableChooser<>();

  private boolean m_runCal = false;
  private boolean m_configCal = false;
  private boolean m_reset = false;
  private boolean m_setYawAxis = false;

  private final ADIS16448_IMU m_imu = new ADIS16448_IMU();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_imu.calibrate();
    m_yawChooser.setDefaultOption("Z-Axis", kYawDefault);
    m_yawChooser.addOption("X-Axis", kYawXAxis);
    m_yawChooser.addOption("Y-Axis", kYawYAxis);
    SmartDashboard.putData("IMUYawAxis", m_yawChooser);
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
    SmartDashboard.putNumber("YawAngle", m_imu.getAngle());
    SmartDashboard.putNumber("XCompAngle", m_imu.getXComplementaryAngle());
    SmartDashboard.putNumber("YCompAngle", m_imu.getYComplementaryAngle());

    SmartDashboard.putNumber("X Acceleration", m_imu.getAccelInstantX());
    SmartDashboard.putNumber("Y Acceleration", m_imu.getAccelInstantY());
    SmartDashboard.putNumber("Z Acceleration", m_imu.getAccelInstantZ());

    xAccumulation += m_imu.getAccelInstantX();
    yAccumulation += m_imu.getAccelInstantY();
    zAccumulation += m_imu.getAccelInstantZ();
    SmartDashboard.putNumber("X Accumulation", xAccumulation);
    SmartDashboard.putNumber("Y Accumulation", yAccumulation);
    SmartDashboard.putNumber("Z Accumulation", zAccumulation);

    m_runCal = SmartDashboard.getBoolean("RunCal", false);
    m_configCal = SmartDashboard.getBoolean("ConfigCal", false);
    m_reset = SmartDashboard.getBoolean("Reset", false);
    m_setYawAxis = SmartDashboard.getBoolean("SetYawAxis", false);
    m_yawSelected = m_yawChooser.getSelected();

    Color detectedColor = m_colorSensor.getColor();
    double IR = m_colorSensor.getIR();

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);

    int proximity = m_colorSensor.getProximity();
    SmartDashboard.putNumber("Proximity", proximity);
    SmartDashboard.putBoolean("Limit Switch", limitSwitch.get());

    SmartDashboard.putNumber("Ultrasonic", ultrasonic.getVoltage() * 5.55);

    // Set IMU settings
    if (m_configCal) {
      m_imu.configCalTime(8);
      m_configCal = SmartDashboard.putBoolean("ConfigCal", false);
    }
    if (m_reset) {
      m_imu.reset();
      m_reset = SmartDashboard.putBoolean("Reset", false);
    }
    if (m_runCal) {
      m_imu.calibrate();
      m_runCal = SmartDashboard.putBoolean("RunCal", false);
    }
    
    // Read the desired yaw axis from the dashboard
    if (m_yawSelected == "X-Axis") {
      m_yawActiveAxis = ADIS16448_IMU.IMUAxis.kX;
    }
    else if (m_yawSelected == "Y-Axis") {
      m_yawActiveAxis = ADIS16448_IMU.IMUAxis.kY;
    }
    else {
      m_yawActiveAxis = ADIS16448_IMU.IMUAxis.kZ;
    }
    // Set the desired yaw axis from the dashboard
    if (m_setYawAxis) {
      m_imu.setYawAxis(m_yawActiveAxis);
      m_setYawAxis = SmartDashboard.putBoolean("SetYawAxis", false);
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
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}