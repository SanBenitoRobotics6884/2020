/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ManualElevatorControl;

public class ElevatorSystem extends SubsystemBase {
  
  private WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(Constants.kLiftMotorLeft);
  private WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(Constants.kLiftMotorRight);

  private Servo m_leftServo = new Servo(Constants.kLiftServoLeft);
  private Servo m_rightServo = new Servo(Constants.kLiftServoLeft);

  private DigitalInput m_leftMin = new DigitalInput(Constants.kLimitSwitchLiftMinLeft);
  private DigitalInput m_rightMin = new DigitalInput(Constants.kLimitSwitchLiftMinRight);

  private boolean m_lServoExtended = false;
  private boolean m_rServoExtended = false;
  private double m_lSpeed = 0;
  private double m_rSpeed = 0;
  private boolean m_enableManual = false;

  public void setLSpeed(double speed) {

    m_lSpeed = speed * Constants.kLiftSpeed;

  }

  public void setRSpeed(double speed) {

    m_rSpeed = speed * Constants.kLiftSpeed;
    
  }

  public double getLSpeed() {

    return m_lSpeed;

  }

  public double getRSpeed() {

    return m_rSpeed;
    
  }

  public void setLServo(boolean extended) {

    if (extended) {
      m_leftServo.setSpeed(1);
    } else {
      m_leftServo.setSpeed(-1);
    }

  }

  public void setRServo(boolean extended) {

    if (extended) {
      m_rightServo.setSpeed(1);
    } else {
      m_rightServo.setSpeed(-1);
    }
    
  }

  public boolean lServoExtended() {

    return m_lServoExtended;

  }

  public boolean rServoExtended() {

    return m_rServoExtended;

  }

  public void enableManual() {
    
    m_enableManual = true;

  }

  public boolean isManual() {

    return m_enableManual;

  }



  public ElevatorSystem() {

    setDefaultCommand(new ManualElevatorControl());

    m_leftMotor.configFactoryDefault();
    m_rightMotor.configFactoryDefault();

    m_leftMotor.setInverted(false);
    m_rightMotor.setInverted(false);

    m_leftMotor.setSensorPhase(true);
    m_rightMotor.setSensorPhase(true);

    m_leftServo.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    m_rightServo.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);

  }

  @Override
  public void periodic() {
    
    if (!m_leftMin.get() && m_lSpeed < 0) {
      m_leftMotor.set(m_lSpeed);
    } else {
      m_leftMotor.set(0);
    }

    if (!m_rightMin.get() && m_rSpeed < 0) {
      m_rightMotor.set(m_lSpeed);
    } else {
      m_rightMotor.set(0);
    }

  }

}
