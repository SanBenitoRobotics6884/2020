/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LauncherSystem extends SubsystemBase {
  
  private WPI_TalonSRX m_launcherMotor = new WPI_TalonSRX(Constants.kLauncherMotor);
  private WPI_VictorSPX m_ejectorMotor = new WPI_VictorSPX(Constants.kEjectorMotor);
  private DigitalInput m_ejectorMaxSwitch = new DigitalInput(Constants.kLimitSwitchEjectorMax);
  private DigitalInput m_ejectorMinSwitch = new DigitalInput(Constants.kLimitSwitchEjectorMin);
  private Timer m_timer = new Timer();

  private boolean m_enabled = false;
  private boolean m_extending = false;
  private boolean m_home = true;
  private boolean m_autoLaunching = false;
  private double m_targetTime;

  public void setState(boolean enabled) {
    m_enabled = enabled;
  }

  public boolean isEnabled() {
    return m_enabled;
  }

  private void eject() {
    m_extending = true;
  }

  public boolean isHome() {
    return m_home;
  }

  public void setAutoLaunch(boolean autoEjectStatus) {
    m_autoLaunching = autoEjectStatus;
  }
  
  public LauncherSystem() {
    m_launcherMotor.configFactoryDefault();
    m_launcherMotor.setInverted(false);
    m_launcherMotor.setSensorPhase(true);

    m_ejectorMotor.configFactoryDefault();
    m_ejectorMotor.setInverted(false);
    m_ejectorMotor.setSensorPhase(true);
  }

  @Override
  public void periodic() {

    if (m_enabled) {
      m_launcherMotor.set(Constants.kLauncherSpeed);
    } else {
      m_launcherMotor.set(0);
    }

    if (m_extending) {

      m_ejectorMotor.set(Constants.kEjectorSpeed);
      if (m_ejectorMaxSwitch.get()) {
        m_extending = false;
        m_ejectorMotor.set(0);
      }

    }

    if (m_ejectorMinSwitch.get()) m_home = true;

    if (m_autoLaunching) {
      if (m_timer.get() > m_targetTime) {
        eject();
        m_targetTime = m_timer.get() + Constants.kAutoEjectDelay;
      }
    }

  }
}
