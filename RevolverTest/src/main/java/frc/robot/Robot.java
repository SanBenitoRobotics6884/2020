/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private static final double kLauncherSpeed = 0.5;
  private static final int kRevolverMotor = 1;
  private static final int kPotChannel = 0;
  private static final double kP = 0.05;
  private static final double kI = 0.0;
  private static final double kD = 0.0;
  private static final double[] kIntakeSetPoints = {1, 1, 1, 1, 1}; //FIND SETPOINTS BEFORE USE
  private static final double[] kLauncherSetPoints = {1, 1, 1, 1, 1}; //FIND SETPOINTS BEFORE USE
  private boolean[] m_chamberStatus = new boolean[4];

  private PIDController m_pidController;
  private AnalogInput m_potentiometer = new AnalogInput(kPotChannel);
  private WPI_TalonSRX m_revolverMotor = new WPI_TalonSRX(kRevolverMotor);

  private double pidOut;
  private double m_currentAngle;
  private double fixedPot;
  private boolean intakeRunning = false;

  private double findTargetChamberAngle (boolean targetLauncher, double currentAngle) {

    m_currentAngle = currentAngle;
    int returnChamber = 0;
    double returnAngle = 0;

    for (int i = 0; i <= m_chamberStatus.length; i++) {
      if (targetLauncher) {
        if ( (kLauncherSetPoints[returnChamber] - currentAngle) < (kLauncherSetPoints[i] - currentAngle)
        && m_chamberStatus[i]) {
          returnChamber = i;
        }
      } else {
        if ( (kIntakeSetPoints[returnChamber] - currentAngle) < (kIntakeSetPoints[i] - currentAngle)
        && !m_chamberStatus[i]) {
          returnChamber = i;
        }
      }

      if (targetLauncher) {
        returnAngle = kLauncherSetPoints[returnChamber];
      } else {
        returnAngle = kIntakeSetPoints[returnChamber];
      }
    }
  
      return returnAngle;

    }
  
  @Override
  public void robotInit() {

    SmartDashboard.delete("Chamber 1");
    SmartDashboard.delete("Chamber 2");
    SmartDashboard.delete("Chamber 3");
    SmartDashboard.delete("Chamber 4");
    SmartDashboard.delete("Chamber 5");

    SmartDashboard.putBoolean("Chamber 1", false);
    SmartDashboard.putBoolean("Chamber 2", false);
    SmartDashboard.putBoolean("Chamber 3", false);
    SmartDashboard.putBoolean("Chamber 4", false);
    SmartDashboard.putBoolean("Chamber 5", false);

    m_pidController = new PIDController(kP, kI, kD);

    m_revolverMotor.configFactoryDefault();
    m_revolverMotor.setInverted(false);

    m_revolverMotor.setSensorPhase(true);
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

    m_chamberStatus[0] = SmartDashboard.getBoolean("Chamber 1", false);
    m_chamberStatus[1] = SmartDashboard.getBoolean("Chamber 2", false);
    m_chamberStatus[2] = SmartDashboard.getBoolean("Chamber 3", false);
    m_chamberStatus[3] = SmartDashboard.getBoolean("Chamber 4", false);
    m_chamberStatus[4] = SmartDashboard.getBoolean("Chamber 5", false);

    fixedPot = ((60 - Math.exp(m_potentiometer.getAverageVoltage())) * (Math.PI / 55) - Math.PI) / 2;

    pidOut = m_pidController.calculate(m_potentiometer.getAverageVoltage()); //Use fixedPot for Radians (Tune First)
    m_revolverMotor.set(pidOut);

    if (intakeRunning) {
      m_pidController.setSetpoint(findTargetChamberAngle(false, m_currentAngle));
    } else {
      m_pidController.setSetpoint(findTargetChamberAngle(true, m_currentAngle));
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
