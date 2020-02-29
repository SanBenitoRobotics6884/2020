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
import frc.robot.commands.DirectControlPanel;

public class ControlPanelSystem extends SubsystemBase {
  
  private WPI_VictorSPX m_motor = new WPI_VictorSPX(Constants.kControlPanelMotor);

  public void directControl(double speed) {
    m_motor.set(speed * Constants.kControlPanelSpeed);
  }

  public ControlPanelSystem() {

    setDefaultCommand(new DirectControlPanel());

    m_motor.configFactoryDefault();
    m_motor.setInverted(false);
    m_motor.setSensorPhase(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
