/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6884.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6884.robot.commands.Drive;
import org.usfirst.frc.team6884.robot.commands.ElevatorDown;
import org.usfirst.frc.team6884.robot.commands.ElevatorUp;
import org.usfirst.frc.team6884.robot.commands.Intake;
import org.usfirst.frc.team6884.robot.commands.ReverseIntake;
import org.usfirst.frc.team6884.robot.subsystems.ACompressor;
import org.usfirst.frc.team6884.robot.subsystems.DriveSystem;

import org.usfirst.frc.team6884.robot.subsystems.ElevatorSystem;
import org.usfirst.frc.team6884.robot.subsystems.EncoderSystem;

import org.usfirst.frc.team6884.robot.subsystems.IntakeSystem;
import org.usfirst.frc.team6884.robot.subsystems.SolenoidIn;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static DriveSystem drive;
	public static OI m_oi;
	public static RobotMap map;
	public static EncoderSystem encoder;
	public  static ElevatorSystem elevator;
	public static  IntakeSystem intake;
	public static Intake cintake;
	public static ElevatorDown cElevatorDown; 
	public static SolenoidIn solenoidopen; 
	public static ACompressor compress; 
	
	public int count = 0;
	DigitalInput switchLeft;
	DigitalInput switchRight;
	/*public Spark leftIntake;
	public Spark rightIntake;
	public Spark leftElevator;
	public Spark rightElevator;
	*/
	public int state = 1;
	//public static GyroSystem gyro;
	
	Command cDrive;
	ElevatorUp cElevatorUp;
	
	Intake cIntake;
	ReverseIntake cReverseIntake;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		drive = new DriveSystem();
		cDrive = new Drive();
		map = new RobotMap();
		encoder = new EncoderSystem();
		//gyro = new GyroSystem();
		elevator = new ElevatorSystem();
		intake = new IntakeSystem();
		cintake = new Intake();
		cElevatorDown = new ElevatorDown();
		switchLeft = new DigitalInput(RobotMap.SWITCH_LEFT);
		switchRight = new DigitalInput(RobotMap.SWITCH_RIGHT);
		solenoidopen = new SolenoidIn();
		compress = new ACompressor(); 
		/*
		leftIntake  = new Spark(RobotMap.L_INTAKE_MOTOR);
		rightIntake  = new Spark(RobotMap.R_INTAKE_MOTOR);
		leftElevator = new Spark(RobotMap.L_ELEVATOR_MOTOR);
		rightElevator = new Spark(RobotMap.R_ELEVATOR_MOTOR);
		*/
		
		//cElevatorDown = new ElevatorDown();
		//cElevatorUp = new ElevatorUp();
		//cIntake = new Intake();
		//cReverseIntake = new ReverseIntake();
		//intake = new IntakeSystem();
		
		//m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		/*if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}*/
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		count++; 
		// Scheduler.getInstance().run();
		SmartDashboard.putNumber("Encoder Distance1", encoder.distance1());
		SmartDashboard.putNumber("Encoder Distance2", encoder.distance2());
//		if(switchLeft.get() && !switchRight.get())
//			System.out.print("LEFT SWITCH");
//		else if(!switchLeft.get() && switchRight.get())
//			System.out.print("RIGHT SWITCH");
//		else if(!switchLeft.get() && !switchRight.get())
//			System.out.print("CENTER SWITCH");
//		else
//			System.out.print("error"); 
		//L or R
		// if((switchLeft.get() && !switchRight.get()) || (!switchLeft.get() && switchRight.get())) {
			if(count <= 150) {
			 drive.drive(-.6,0);
			}
			
		// }
		//center
//		else if(!switchLeft.get() && !switchRight.get()){
//			if(count <=  20) {
//				drive.drive(-.4,0);
//			}
//			else if(count <= 40) {
//				drive.drive(-.4,.5);
//			}
//			else if(count <= 60)
//				drive.drive(-.4,0);
//			else if(count <= 80)
//				drive.drive(-.4,-.5);
//			else if(count <= 100)
//				drive.drive(-.4,0);
//		}
			
//		if(switchLeft.get() && !switchRight.get())
//			System.out.print("LEFT SWITCH");
//		else if(!switchLeft.get() && switchRight.get())
//			System.out.print("RIGHT SWITCH");
//		else if(!switchLeft.get() && !switchRight.get())
//			System.out.print("CENTER SWITCH");
//		else
//			System.out.print("error"); 
//		
//		if(count <=  20) {
//			drive.drive(.5,0);
//		}
//		else if(count <= 40) {
//			
//		}
//		*/	
		//if(count <= 100)
		//drive.drive(-.6,0);	
		/*
	//-1.7 was for 10 ft 
	if(state == 1) {
		if() {
			drive.drive(.4,0);
		//	drive.driveStraight();
		}
		
		else {
			state = 2;
			encoder.reset();
		}
	}
	//the system updates periodically (state variable still keeps value since it is in autoINIT)
	//assuming left1 is ours and we start on left 
	else if(state == 2) {
		if(!drive.turn90Clockwise()){
			state = 3;
			encoder.reset();
		}
	}
	else if(state == 3) 
		if(encoder.distance() <= -.3) {
			drive.drive(.2,0);
		//	drive.driveStraight();
		}
		else {
			state = 4;
			encoder.reset();
		}
	else if(state == 4) {
		intake.reverseIntake();
		//how can i wait until this is done?
		state = 5;
		encoder.reset();
	}
	//going backwards that short distance
	else if(state == 5){
		if(encoder.distance() <= -.2) {
			drive.drive(-.2,0); // go backwards
		//	drive.driveStraight();
		}
		else {
			state = 6;
			encoder.reset();
		}
	}
	//turning counter clockwise from being backwards
	else if(state == 6) {
		if(!drive.turn90CounterClockwise()){
			state = 7; 
		encoder.reset();
		}
	}
	else if(state == 7){ 
		if(encoder.distance() <= -1.8) //not completely sure about this d
			drive.reduceSpeed();
		else {
			state = 8;
			encoder.reset();
		}
	}
		*/
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		//cElevatorUp.start();
		//cElevatorDown.start();
		//cIntake.start();
		//cReverseIntake.start();
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		cDrive.start();
		//if(RobotMap.INTAKE_BUTTON.get())
	//		new Intake();
		//cintake.start();
		cElevatorDown.start();
		//cElevatorUp.start();
		//cElevatorDown.start();
		//cIntake.start();
		//cReverseIntake.start();
	
		SmartDashboard.putNumber("Encoder Distance1", encoder.distance1());

		SmartDashboard.putNumber("Encoder Distance2", encoder.distance2());
		/**
		if(RobotMap.joystick.getRawButton(5))
		{
			
			leftElevator.set(.3);		
			rightElevator.set(.3);
			
		}
		**/
		elevator.getML();
		elevator.getMR();
		
		//System.out.print(gyro.getAngle() + "***************************");
		
		
		
		/*
			cDrive.start();
		SmartDashboard.putNumber("Encoder Distance", encoder.getD1());
		SmartDashboard.putNumber("Encoder Rate", encoder.getD2());
		
		System.out.print(encoder.testA());
		System.out.print(encoder.
		testB());
		*/
		
		
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	/*These are getter methods used to obtain the object's reference variable  
	 * The reference variable can then be used in another class, in this case the Command Class
	 * In short, The command class gets the "key" to its subsystems
	 */
	public static DriveSystem getDrive() {
		return drive;
	}
	public static ElevatorSystem getElevator() {
		return elevator;
	}
	public static IntakeSystem getIntake() {
		return intake;
	}
}
