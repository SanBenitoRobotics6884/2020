/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6884.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	/**
	For example to map the left and right motors, you could define the
	 following variables to use with your drivetrain subsystem.
	 public static int leftMotor = 1;
	 public static int rightMotor = 2;
   
	 If you are using multiple modules, make sure to define both the port
	 number and the module. For example you with a rangefinder:
	 public static int rangefinderPort = 1;
	 public static int rangefinderModule = 1;
	 */
	
	/* Drive Train motors */
	public static final int L2_DRIVETRAIN = 3; //C
	public static final int L1_DRIVETRAIN = 2; //C
	public static final int R1_DRIVETRAIN = 0; //C
	public static final int R2_DRIVETRAIN = 1; //C 
	
	/* DIO */
	public static final int L_Encoder_CHANNEL_A = 6; //C--BLUE
	public static final int L_Encoder_CHANNEL_B = 7; //C--YELLOW
	public static final int R_Encoder_CHANNEL_A = 1; //C--BLUE
	public static final int R_Encoder_CHANNEL_B = 2 ; //C--YELLOW 
	public static final int SWITCH_LEFT = 5;
	public static final int SWITCH_RIGHT = 0;
	
	
	/* Elevator Motors */
	public static final int L_ELEVATOR_MOTOR = 6; //C
	public static final int R_ELEVATOR_MOTOR = 7; //C
	
	/* Cube Intake Motors */
	public static final int R_INTAKE_MOTOR = 5; //C
	public static final int L_INTAKE_MOTOR = 4; //????
	
	/* Joy-sticks */
	public static final int USB_PORT_JOYSTICK = 0;
	public static final Joystick joystick = new Joystick(USB_PORT_JOYSTICK);
	
	/* Button Numbers*/
	public static final int L_BUMPER_BUTTON = 5; 
	public static final int R_BUMPER_BUTTON = 6;
	public static final int X_BUTTON = 1;
	public static final int Y_BUTTON = 2;
	
	// Solenoids
	public static final int PCM_ID_DRIVER = 0;
	public static final int PCM_ID_GEAR = 1;
	public static final int PCM_ID = 2;
	
	
	/* Buttons */
	public static final JoystickButton INTAKE_BUTTON = new JoystickButton(joystick, L_BUMPER_BUTTON );
	public static final JoystickButton REVERSE_INTAKE_BUTTON = new JoystickButton(joystick, R_BUMPER_BUTTON );
	public static final JoystickButton UP_ELEVATOR__BUTTON = new JoystickButton(joystick, X_BUTTON );
	public static final JoystickButton DOWN_ELEVATOR_BUTTON = new JoystickButton(joystick, Y_BUTTON );
	
}
