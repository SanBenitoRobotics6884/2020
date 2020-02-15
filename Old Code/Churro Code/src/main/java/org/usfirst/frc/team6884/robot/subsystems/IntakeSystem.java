package org.usfirst.frc.team6884.robot.subsystems;

import org.usfirst.frc.team6884.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSystem extends Subsystem {

	private Spark leftIntake;
	private Spark rightIntake;

	
	public IntakeSystem() {
		//sets motor variables to pins on roboRio
		System.out.println("ITS GOOD");
		leftIntake = new Spark(RobotMap.L_INTAKE_MOTOR);
		rightIntake = new Spark(RobotMap.R_INTAKE_MOTOR);
	
	}
	
	/**
	 * Slides cubes into robot
	 */
	public void intake() {
		System.out.print("TRUEEE");
		leftIntake.set(.6); //left wheels spin clockwise
		rightIntake.set(-.6); //right wheels spin counter-clockwise
	}
	
	
	/**
	 * Slides cubes out of the robot
	 */
	public void reverseIntake() {
		leftIntake.set(-.7); //left wheels spin counter-clockwise
		rightIntake.set(.7); //right wheels spin clockwise
	
	}
	
	/**
	 * Stops the wheels from spinning
	 */
	public void stopIntake() {
		leftIntake.set(0); //setting speed to zero on both makes them stop
		rightIntake.set(0);
	
	}

	//for some reaon you need this
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	} 
}
