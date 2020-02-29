/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class RevolverOperator extends CommandBase {
  
  private boolean isFinished = false;
  private boolean[] m_chamberStatus;
  private double[] kIntakeSetPoints = Constants.RevolverPID.kIntakeSetPoints; 
  private double[] kLauncherSetPoints = Constants.RevolverPID.kLauncherSetPoints;
  private double m_fixedPot;
  private int m_chamberTarget = 0;
  private double updateStatusTime = Constants.kLSUpdateDelay;
  private boolean lsPressed = false;
  private Timer m_timer = new Timer();

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

  public RevolverOperator() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.revolverSystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    m_fixedPot = RobotContainer.revolverSystem.getPotAngle();
    
    if (RobotContainer.intakeSystem.getStatus() && RobotContainer.launcherSystem.isHome()) {
      RobotContainer.revolverSystem.setTarget(findTargetChamberAngle(false, m_fixedPot));
    } else {
      RobotContainer.revolverSystem.setTarget(findTargetChamberAngle(true, m_fixedPot));
    }

    if(RobotContainer.revolverSystem.getLS()) {
      lsPressed = true;
      updateStatusTime = m_timer.get() + 1;
    }

    if (lsPressed && m_timer.get() > updateStatusTime) {
      lsPressed = false;
      m_chamberStatus[m_chamberTarget] = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
