/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6884.robot.subsystems;

import org.usfirst.frc.team6884.robot.RobotMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveSystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Spark l1;
	public Spark r1;
	
	
	public Spark l2;
	public Spark r2;
	
	public SpeedControllerGroup m_right;
	public SpeedControllerGroup m_left;
	
	public DifferentialDrive m_drive;   
	ADXRS450_Gyro gyro;  
	boolean goalReached; 
	double kP = .03; //rate of how fast the gyro will adjust angle
	
	public DriveSystem() {
		l1 = new Spark(RobotMap.L1_DRIVETRAIN);
		l2 = new Spark(RobotMap.L2_DRIVETRAIN);
		m_left = new SpeedControllerGroup(l1,l2);
				
		r1 = new Spark(RobotMap.R1_DRIVETRAIN);
		r2 = new Spark(RobotMap.R2_DRIVETRAIN);
	
		m_right = new SpeedControllerGroup(r1,r2);
		
		m_drive = new DifferentialDrive(m_left, m_right);
		
		gyro = new ADXRS450_Gyro();
		
	}
	public void drive(double speed, double rotation) {
		System.out.println(speed);
		//Squaring these values makes them lower --> lower velocity so it wont spin as fast
		m_drive.curvatureDrive(speed * .7, rotation *.7, true);
		
		
	}
	public void reduceSpeed() {
		m_drive.curvatureDrive(.4,0, true);
	}

		// GYRO SYSTEM 			
		
	/*
	public double getAngle() {
		return gyro.getAngle();
	}
	public boolean turn90Clockwise() {
		double angle = gyro.getAngle();
		boolean goalReached = false; 
		if(angle >= 90) {
			goalReached = true;
			return true;
		}
		else if(!goalReached) {
			drive(.2,angle * kP);
		}
		return false;
	}
	public boolean turn90CounterClockwise() { 
		double angle = gyro.getAngle();
		boolean goalReached = false;
		if(angle >= 90) {
			goalReached = true;
			return true;
		}
		else if(!goalReached) {
			drive(.2, -angle * kP );
		} 
		return false;
	}
	
	public boolean driveStraight() {
		boolean driveStraight = false;
		double angle = Math.round(gyro.getAngle());
		if(angle == 0) {
			driveStraight = true;
			return false;
		}
		else if(!driveStraight)
			drive(.1 ,angle * kP);
		return false;
	}
	*/
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
