/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LauncherSystem extends SubsystemBase {
  
  private WPI_TalonSRX m_launcherMotor = new WPI_TalonSRX(Constants.kLauncherMotor);

  private boolean m_enabled = false;

  public void setState(boolean enabled) {
    m_enabled = enabled;
  }
  
  public LauncherSystem() {
    m_launcherMotor.configFactoryDefault();
    m_launcherMotor.setInverted(false);
    m_launcherMotor.setSensorPhase(true);
  }

  @Override
  public void periodic() {
    if (m_enabled) {
      m_launcherMotor.set(Constants.kLauncherSpeed);
    } else {
      m_launcherMotor.set(0);
    }
  }
}
