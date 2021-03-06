/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ManualElevatorControl extends CommandBase {
  
  private boolean isFinished = false;

  private Joystick m_joystick = new Joystick(Constants.kJoystickChannel);

  public ManualElevatorControl() {
    addRequirements(RobotContainer.elevatorSystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (RobotContainer.elevatorSystem.isManual()) {

      RobotContainer.elevatorSystem.setLSpeed(m_joystick.getY());
      RobotContainer.elevatorSystem.setRSpeed(m_joystick.getY());

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
