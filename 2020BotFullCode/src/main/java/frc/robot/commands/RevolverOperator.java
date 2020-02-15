/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class RevolverOperator extends CommandBase {
  
  private boolean isFinished = false;
  private boolean[] m_chamberStatus;
  private double[] kIntakeSetPoints = Constants.RevolverPID.kIntakeSetPoints; 
  private double[] kLauncherSetPoints = Constants.RevolverPID.kLauncherSetPoints;
  private double m_currentAngle;

  private double findTargetChamberAngle (boolean targetLauncher, double currentAngle) {

    m_currentAngle = RobotContainer.revolverSystem.getPotAngle();
    m_chamberStatus = RobotContainer.revolverSystem.chamberStatus;
    int returnChamber = 0;
    double returnAngle;

    for (int i = 0; i <= m_chamberStatus.length; i++) {
      if (targetLauncher) {
        if ( (kLauncherSetPoints[returnChamber] - currentAngle) < (kLauncherSetPoints[i] - currentAngle)
        && m_chamberStatus[i]) {
          returnChamber = i;
        }
      } else {
        if ( (kIntakeSetPoints[returnChamber] - currentAngle) < (kIntakeSetPoints[i] - currentAngle)
        && !m_chamberStatus[i]) {
          returnChamber = i;
        }
      }
    }

    if (targetLauncher) {
      returnAngle = kLauncherSetPoints[returnChamber];
    } else {
      returnAngle = kIntakeSetPoints[returnChamber];
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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    if (RobotContainer.intakeSystem.getStatus()) {
      RobotContainer.revolverSystem.setTarget(findTargetChamberAngle(false, m_currentAngle));
    } else {
      RobotContainer.revolverSystem.setTarget(findTargetChamberAngle(true, m_currentAngle));
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
