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

import org.usfirst.frc.team6884.robot.subsystems.ElevatorSystem;


/**
 * An example command.  You can replace me with your own command.
 */
public class ElevatorUp extends Command {
	//commands are all private
	public static ElevatorSystem elevator;
	public static OI oi;
	
	public ElevatorUp() {
		
		elevator = Robot.getElevator();
		
		// Use requires() here to declare subsystem dependencies
		requires(elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		oi = new OI();
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		System.out.print("Elevator works EEEEEEEEEEEEEEEEUUUUUUUUUUUUUUUUUUPPPPPPPPP");
		elevator.up();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		elevator.stop();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	
	}
}