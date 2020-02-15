package org.usfirst.frc.team6884.robot.subsystems;

import org.usfirst.frc.team6884.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorSystem extends Subsystem {

	public Spark leftElevator;
	public Spark rightElevator;

	
	public ElevatorSystem() {
		leftElevator = new Spark(RobotMap.L_ELEVATOR_MOTOR);
		rightElevator = new Spark(RobotMap.R_ELEVATOR_MOTOR);
		
	}
	public void getML() {
		SmartDashboard.putNumber("R ELEV", rightElevator.get());
	}
	public  void getMR() {
		SmartDashboard.putNumber("L ELEV", leftElevator.get());
	}
	
	public void up(){
		rightElevator.set(.6);
		leftElevator.set(.6);
		
	}
	public void down(){
		rightElevator.set(-.2);
		leftElevator.set(-.2); 
		
	}
	
	public void stop(){	
		rightElevator.set(0);
		leftElevator.set(0);
	}
	
	
	

	//for some reaon you need this
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	} 
}
