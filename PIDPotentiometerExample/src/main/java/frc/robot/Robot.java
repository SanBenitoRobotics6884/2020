/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a sample program to demonstrate how to use a soft potentiometer and a
 * PID controller to reach and maintain position setpoints on an elevator
 * mechanism.
 */
public class Robot extends TimedRobot {
  private static final int kPotChannel = 1;
  private static final int kMotorChannel = 0;
  private static final int kJoystickChannel = 0;

  // bottom, middle, and top elevator setpoints
  private static final double[] kSetPoints = {-0.7, 0.0};

  // proportional, integral, and derivative speed constants; motor inverted
  // DANGER: when tuning PID constants, high/inappropriate values for kP, kI,
  // and kD may cause dangerous, uncontrollable, or undesired behavior!
  // these may need to be positive for a non-inverted motor
  private static final double kP = 0.4;
  private static final double kI = 0.0;
  private static final double kD = 0.0;
  private static final double kF = 0.2;
  private double fixedPot;

  private PIDController m_pidController;
  private AnalogInput m_potentiometer;
  private Spark m_elevatorMotor;
  private Joystick m_joystick;
  Faults faults = new Faults(); 

  @Override
  public void robotInit() {
    m_potentiometer = new AnalogInput(kPotChannel);
    m_elevatorMotor = new Spark(kMotorChannel);
    m_joystick = new Joystick(kJoystickChannel);

    m_pidController = new PIDController(kP, kI, kD);
    
  }

  @Override
  public void teleopInit() {
    /* factory default values */
    //m_elevatorMotor.configFactoryDefault();

    //m_elevatorMotor.setInverted(false); // !< Update this

    /*
     * [4] adjust sensor phase so sensor moves positive when Talon LEDs are green
     */
    //m_elevatorMotor.setSensorPhase(true);
  }

  @Override
  public void robotPeriodic() {

    fixedPot = ((60 - Math.exp(m_potentiometer.getAverageVoltage())) * (Math.PI / 55) - Math.PI) / 2;

    SmartDashboard.putNumber("Pot", fixedPot );

    if (m_pidController.atSetpoint()) {
      m_pidController.reset();
    }

    // Run the PID Controller
    double pidOut
        = m_pidController.calculate(fixedPot);
    m_elevatorMotor.set(pidOut + kF * Math.cos(fixedPot));
    SmartDashboard.putNumber("Arm Height", fixedPot);

    //double leftVelUnitsPer100ms = m_elevatorMotor.getSelectedSensorVelocity(0);
    //m_elevatorMotor.getFaults(faults);

    // when the button is pressed once, the selected elevator setpoint
    // is incremented

    if (  m_joystick.getRawButton(1) == true) {

      m_pidController.setSetpoint(kSetPoints[0]);

    } else if (  m_joystick.getRawButton(2) == true) {

      m_pidController.setSetpoint(kSetPoints[1]);

    }

  }
}
