package org.usfirst.frc.team6884.robot.subsystems;

import org.usfirst.frc.team6884.robot.RobotMap;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class EncoderSystem extends Subsystem{
	Encoder encoderLeft; 
	Encoder encoderRight;
	
	
	double radiusOfWheel = 3;
	double circumOfWheel = 2 * Math.PI * radiusOfWheel;
	

	double distancePerPulse = circumOfWheel/250;
	
	public EncoderSystem() {
		encoderLeft = new Encoder(RobotMap.L_Encoder_CHANNEL_A , RobotMap.L_Encoder_CHANNEL_B,true, EncodingType.k4X );
		encoderRight = new Encoder(RobotMap.R_Encoder_CHANNEL_A, RobotMap.R_Encoder_CHANNEL_B,true, EncodingType.k4X);
		encoderLeft.setDistancePerPulse(distancePerPulse);
		encoderRight.setDistancePerPulse(distancePerPulse);
		
	}
	//probably need to take abs value then divide

	public double distance1() {
		return Math.abs(encoderLeft.getDistance()); 
	}
	public double distance2() {
		return Math.abs(encoderRight.getDistance()); 
	}
	
	public double distance() {
		return (distance1()+ distance2())/2;
	}
	

	
	public void reset() {
		encoderLeft.reset();
		encoderRight.reset();
	}
	public double getRate() {
		return encoderLeft.getRate();
	}
	public double testA() {
		return encoderLeft.getRaw();
		
	}
	public double testB() {
		return encoderRight.getRaw();
		
	}

	//random method you must use
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}