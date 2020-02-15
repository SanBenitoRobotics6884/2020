/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6884.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6884.robot.OI;
import org.usfirst.frc.team6884.robot.Robot;
import org.usfirst.frc.team6884.robot.subsystems.IntakeSystem;

/**
 * An example command.  You can replace me with your own command.
 */
public class ReverseIntake extends Command {
	private IntakeSystem intake;
	//public static OI oi;
	public ReverseIntake() {
		intake = Robot.getIntake();
		// Use requires() here to declare subsystem dependencies
		requires(intake);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//oi = new OI();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		intake.reverseIntake();
		System.out.print("Reverse Intake RRRRRRR  iIIIIIIIIIIIIIIIIIIII");
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		intake.stopIntake();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
