/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6884.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6884.robot.OI;
import org.usfirst.frc.team6884.robot.Robot;
import org.usfirst.frc.team6884.robot.subsystems.DriveSystem;


/**
 * An example command.  You can replace me with your own command.
 */
public class Drive extends Command {
	//commands are all private
	public DriveSystem drive;
	public static OI oi;
	public static Robot robo;
	private Joystick j1;
	public Drive() {
		robo = new Robot();
		drive = Robot.getDrive();
		j1 = new Joystick(0);
		// Use requires() here to declare subsystem dependencies
		requires(drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
		j1 = new Joystick(0);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		drive.drive(j1.getY(), j1.getX());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {


	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}