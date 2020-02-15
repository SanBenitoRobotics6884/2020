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
import org.usfirst.frc.team6884.robot.RobotMap;
import org.usfirst.frc.team6884.robot.subsystems.IntakeSystem;

/**
 * An example command.  You can replace me with your own command.
 */
public class Intake extends Command {
	private IntakeSystem intake;
	//public static OI oi;
	public Intake() {
		//super("Intake");
		intake = Robot.getIntake();
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//oi = new OI();
	//	System.out.print("INTAKE WORKS IIIIIIIIIIIIIIIIIIIIII");
		Robot.intake.intake();
	}
/*
	//// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(RobotMap.INTAKE_BUTTON.get())
		{
			
		System.out.print("INTAKE WORKS IIIIIIIIIIIIIIIIIIIIII");
		Robot.intake.intake();
		}
		else if(RobotMap.REVERSE_INTAKE_BUTTON.get())
			Robot.intake.reverseIntake();
		else
			Robot.intake.stopIntake();
	
	}

	 Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//Robot.intake.stopIntake();
	}

	/** Called when another command which requires one or more of the same
	 subsystems is scheduled to run
	  */
	protected boolean isFinished() {
		return false;
	}
	@Override
	protected void interrupted() {
		end();
	}
}
