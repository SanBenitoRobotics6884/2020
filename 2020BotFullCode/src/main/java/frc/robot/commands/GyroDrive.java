/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSystem;

public class GyroDrive extends CommandBase {
  
  private XboxController m_controller = new XboxController(Constants.kDriveControllerChannel);

  private double m_joystickForward;
  private double m_joystickReverse;
  private double m_speed;
  private double m_turnAngle;
  private boolean m_fieldCentric;
  private boolean isFinished = false;

  public GyroDrive(boolean fieldCentric) {

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.driveSystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (m_fieldCentric) {
      m_turnAngle = DriveSystem.calculateJoystickAngle(m_controller.getRawAxis(Constants.kDriveTurnAxis),
        m_controller.getRawAxis(Constants.kDriveTurnVerticalAxis));
    } else {
      m_turnAngle = (m_controller.getRawAxis(Constants.kDriveTurnAxis) * Constants.kGyroDriveTurnGain) % 360;
    }

    m_joystickForward = m_controller.getRawAxis(Constants.kDriveForwardAxis);
    m_joystickReverse = m_controller.getRawAxis(Constants.kDriveReverseAxis);

    m_speed = m_joystickForward - m_joystickReverse;

    RobotContainer.driveSystem.ArcadeDrive(m_speed, m_turnAngle);
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
