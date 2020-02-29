/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
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
public class Robot extends TimedRobot { //Sensor out of phase

  private static final int kRevolverMotor = 3;
  private static final int kPotChannel = 0;
  private static final double kP = 0.045;
  private static final double kI = 0.0;
  private static final double kD = 0.005;
  private static final double[] kIntakeSetPoints = {185, 195, 205, 214, 224};
  private static final double[] kLauncherSetPoints = {215, 225, 235, 244, 254}; //FIND SETPOINTS BEFORE USE
  private boolean[] m_chamberStatus = {false, false, false, false, false};

  private PIDController m_pidController;
  private AnalogInput m_potentiometer = new AnalogInput(kPotChannel);
  private WPI_VictorSPX m_revolverMotor = new WPI_VictorSPX(kRevolverMotor);
  private Joystick m_joystick = new Joystick(0);

  private double pidOut;
  private double fixedPot;
  private boolean intakeRunning = true;

  private double findTargetChamberAngle (boolean targetLauncher, double currentAngle) {

    int returnChamber = 0;
    double returnAngle = 0;

    for (int i = 0; i < m_chamberStatus.length; i++) {

      if (targetLauncher) {

        if ( !m_chamberStatus[returnChamber] && m_chamberStatus[i] ) {
          returnChamber = i;
        }

        if ( Math.abs(kLauncherSetPoints[returnChamber] - currentAngle) > Math.abs(kLauncherSetPoints[i] - currentAngle)
        && m_chamberStatus[i]) {
          returnChamber = i;
        }

      } else {

        if ( m_chamberStatus[returnChamber] && !m_chamberStatus[i] ) {
          returnChamber = i;
        }

        if ( Math.abs(kIntakeSetPoints[returnChamber] - currentAngle) > Math.abs(kIntakeSetPoints[i] - currentAngle)
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
    SmartDashboard.delete("Intake Running");

    SmartDashboard.putBoolean("Chamber 1", false);
    SmartDashboard.putBoolean("Chamber 2", false);
    SmartDashboard.putBoolean("Chamber 3", false);
    SmartDashboard.putBoolean("Chamber 4", false);
    SmartDashboard.putBoolean("Chamber 5", false);
    SmartDashboard.putBoolean("Intake Running", true);

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
    intakeRunning = SmartDashboard.getBoolean("Intake Running", true);

    fixedPot = m_potentiometer.getAverageVoltage() * 100;

    pidOut = m_pidController.calculate(fixedPot);
    m_revolverMotor.set(pidOut * m_joystick.getY()); /*CHECK IF SENSOR IN PHASE BEFORE UNCOMMENTING*/

    if (intakeRunning) {
      m_pidController.setSetpoint(findTargetChamberAngle(false, fixedPot));
    } else {
      m_pidController.setSetpoint(findTargetChamberAngle(true, fixedPot));
    }

    SmartDashboard.putNumber("Pot", fixedPot);
    SmartDashboard.putNumber("PID Out", pidOut);
    SmartDashboard.putNumber("Target Angle", findTargetChamberAngle(false, fixedPot));
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
