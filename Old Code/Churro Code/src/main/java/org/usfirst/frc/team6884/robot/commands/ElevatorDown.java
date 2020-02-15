/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6884.robot.commands;


import edu.wpi.first.wpilibj.command.Command;


import org.usfirst.frc.team6884.robot.Robot;
import org.usfirst.frc.team6884.robot.RobotMap;
import org.usfirst.frc.team6884.robot.subsystems.ElevatorSystem;


/**
 * An example command.  You can replace me with your own command.
 */
public class ElevatorDown extends Command {
	//commands are all private
	public ElevatorSystem elevator;
	//public static OI oi;
	public ElevatorDown() {
		
		//elevator = Robot.getElevator();
	
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//oi = new OI();
		//Robot.elevator.down();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		if(RobotMap.DOWN_ELEVATOR_BUTTON.get())
			Robot.elevator.down();
		else if(RobotMap.UP_ELEVATOR__BUTTON.get())
			Robot.elevator.up();
		else
			Robot.elevator.stop();
			
			
			
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.elevator.stop();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}