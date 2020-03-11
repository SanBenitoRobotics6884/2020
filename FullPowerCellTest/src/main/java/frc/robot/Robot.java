/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
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

  private static final int kRevolverMotor = 8;
  private static final int kIntakeMotor = 6;
  private static final int kLauncherMotor = 4;
  private static final int kEjectorMotor = 7;
  private static final int kPotChannel = 0;
  private static final double kEjectorSpeed = 0.15;
  private static final double kEjectDelay = 1.5;
  private static final double kRetractDelay = 0.5;
  private static final double kP = 0.05;
  private static final double kI = 0.0;
  private static final double kD = 0.005;
  private static final double[] kIntakeSetPoints = {189, 198, 207.7, 217, 227};
  private static final double[] kLauncherSetPoints = {171.5, 181, 189.3, 199, 208.8}; //FIND SETPOINTS BEFORE USE
  private boolean[] m_chamberStatus = {false, false, false, false, false};

  private PIDController m_pidController;
  private AnalogInput m_potentiometer = new AnalogInput(kPotChannel);
  private WPI_VictorSPX m_revolverMotor = new WPI_VictorSPX(kRevolverMotor);
  private WPI_VictorSPX m_intakeMotor = new WPI_VictorSPX(kIntakeMotor);
  private WPI_VictorSPX m_launcherMotor = new WPI_VictorSPX(kLauncherMotor);
  private WPI_VictorSPX m_pusherMotor = new WPI_VictorSPX(kEjectorMotor);
  private DigitalInput ejectorLimit = new DigitalInput (1);
  private DigitalInput intakeLimit = new DigitalInput (0);
  private Joystick m_joystick = new Joystick(0);
  private Timer timer = new Timer();
  private double ejectDelayTarget = 0;
  private double retractDelayTarget = 0;
  private boolean retracting;

  private double pidOut;
  private double fixedPot;
  private boolean intakeRunning = false;
  private int targetChamber;
  private double targetAngle;

  private void findTargetChamberAngle (boolean targetLauncher, double currentAngle) {

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

      targetChamber = returnChamber;
      targetAngle = returnAngle;

    }

    }

  
  @Override
  public void robotInit() {

    m_pidController = new PIDController(kP, kI, kD);
    timer.start();

    m_revolverMotor.configFactoryDefault();
    m_intakeMotor.configFactoryDefault();
    m_launcherMotor.configFactoryDefault();
    m_pusherMotor.configFactoryDefault();
    m_pusherMotor.setInverted(false);
    m_revolverMotor.setInverted(true);

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

    SmartDashboard.putBoolean("Chamber 1", m_chamberStatus[0]);
    SmartDashboard.putBoolean("Chamber 2", m_chamberStatus[1]);
    SmartDashboard.putBoolean("Chamber 3", m_chamberStatus[2]);
    SmartDashboard.putBoolean("Chamber 4", m_chamberStatus[3]);
    SmartDashboard.putBoolean("Chamber 5", m_chamberStatus[4]);
    intakeRunning = m_joystick.getRawButton(2);

    fixedPot = m_potentiometer.getAverageVoltage() * 100;

    pidOut = m_pidController.calculate(fixedPot);
    m_revolverMotor.set(pidOut * Math.abs(m_joystick.getY())); /*CHECK IF SENSOR IN PHASE BEFORE UNCOMMENTING*/

    if (!intakeLimit.get() && Math.abs(targetAngle - fixedPot) < 2) {
      findTargetChamberAngle(false, fixedPot);
      m_chamberStatus[targetChamber] = true;
    }

    if (intakeRunning) {
      findTargetChamberAngle(false, fixedPot);
      m_pidController.setSetpoint(targetAngle);
      SmartDashboard.putNumber("Target Chamber", targetChamber);
      if (m_joystick.getRawAxis(3) < 0) m_intakeMotor.set(0.5);
    } else {
      findTargetChamberAngle(true, fixedPot);
      m_pidController.setSetpoint(targetAngle);
      SmartDashboard.putNumber("Target Chamber", targetChamber);
      m_intakeMotor.set(0);
    }

    if (m_joystick.getTrigger()) {
      m_launcherMotor.set(0.75);
      //AutoLaunch
      if (timer.get() > ejectDelayTarget) {
        ejectDelayTarget = timer.get() + kEjectDelay;
        m_pusherMotor.set(kEjectorSpeed);
      }
    } else {
      m_launcherMotor.set(0);
      ejectDelayTarget = timer.get() + 1;
    }

    if (!ejectorLimit.get()) {
      m_pusherMotor.set(0);
      retracting = true;
      retractDelayTarget = timer.get() + kRetractDelay;

    }

    if (timer.get() > retractDelayTarget && retracting) {
      findTargetChamberAngle(true, fixedPot);
      m_chamberStatus[targetChamber] = false;
      retracting = false;
    }

    SmartDashboard.putNumber("Pot", fixedPot);
    SmartDashboard.putNumber("PID Out", pidOut);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
