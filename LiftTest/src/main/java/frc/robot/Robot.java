/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  double kServoExtend = 0.5;
  double kRightServoRetract = -0.5;
  double kLeftServoRetract = -0.75;
  double kLiftSpeed = 0.3;
  boolean mServoExtend = true;

  WPI_VictorSPX m_leftLiftMotor = new WPI_VictorSPX(5);
  WPI_VictorSPX m_rightLiftMotor = new WPI_VictorSPX(3);

  //Spark m_leftLiftMotor = new Spark(2);
  //Spark m_rightLiftMotor = new Spark(3);

  Servo m_leftServo = new Servo(0);
  Servo m_rightServo = new Servo(1);
  Joystick joystick = new Joystick(0);

  @Override
  public void robotInit() {

    m_leftLiftMotor.configFactoryDefault();
    m_rightLiftMotor.configFactoryDefault();
    m_leftLiftMotor.setInverted(true);
    m_leftServo.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    m_rightServo.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);

  }

  @Override
  public void robotPeriodic() {

    //m_leftLiftMotor.set(joystick.getY() * kLiftSpeed);
    m_rightLiftMotor.set(joystick.getY() * kLiftSpeed);

    if (joystick.getRawButton(11)) {
      mServoExtend = false;
    } else if (joystick.getRawButton(9)) {
      mServoExtend = true;
    }

    if (mServoExtend) {
      m_leftServo.setSpeed(kServoExtend);
      m_rightServo.setSpeed(kServoExtend);
    } else {
      m_rightServo.setSpeed(kRightServoRetract);
      m_leftServo.setSpeed(kLeftServoRetract);
    }
  }

  @Override
  public void teleopPeriodic() {

    
  }

}
