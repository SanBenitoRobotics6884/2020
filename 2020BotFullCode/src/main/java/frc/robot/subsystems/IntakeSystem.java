/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSystem extends SubsystemBase {
  
  private WPI_VictorSPX m_intakeMotor = new WPI_VictorSPX(Constants.kIntakeMotor);

  private boolean m_toggled = false;
  
  public IntakeSystem() {

    m_intakeMotor.configFactoryDefault();
    m_intakeMotor.setInverted(false);
    m_intakeMotor.setSensorPhase(true);

  }

  public void intakeOn() {
    m_toggled = true;
  }

  public void intakeOff() {
    m_toggled = false;
  }

  public boolean getStatus() {
    return m_toggled;
  }

  @Override
  public void periodic() {

    if (m_toggled) {
      m_intakeMotor.set(Constants.kIntakeSpeed);
    } else {
      m_intakeMotor.set(0);
    }

  }
}
