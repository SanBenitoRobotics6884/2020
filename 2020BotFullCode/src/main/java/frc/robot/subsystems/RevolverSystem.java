/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.RevolverOperator;

public class RevolverSystem extends SubsystemBase {

  private PIDController m_pidController;
  private AnalogInput m_potentiometer = new AnalogInput(Constants.kPotChannel);
  private WPI_TalonSRX m_revolverMotor = new WPI_TalonSRX(Constants.kRevolverMotor);

  public boolean[] chamberStatus = new boolean[4];
  private double kP = Constants.RevolverPID.P;
  private double kI = Constants.RevolverPID.I;
  private double kD = Constants.RevolverPID.D;
  private double pidOut;
  private double fixedPot;

  public RevolverSystem() {

    m_pidController = new PIDController(kP, kI, kD);

    m_revolverMotor.configFactoryDefault();
    m_revolverMotor.setInverted(false);

    m_revolverMotor.setSensorPhase(true);
    
  }

  public void setTarget(double target) {
    m_pidController.setSetpoint(target);
  }

  public double getPotAngle() {
    return fixedPot;
  }

  @Override
  public void periodic() {
    
    fixedPot = ((60 - Math.exp(m_potentiometer.getAverageVoltage())) * (Math.PI / 55) - Math.PI) / 2;

    pidOut = m_pidController.calculate(fixedPot);
    m_revolverMotor.set(pidOut);

  }

  public void initDefaultCommand() {
    setDefaultCommand(new RevolverOperator());
  }
}
