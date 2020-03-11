/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ArcadeDrive;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveSystem extends SubsystemBase {
  
  private WPI_TalonSRX _leftFront = new WPI_TalonSRX(Constants.kLeftDriveMaster);
  private WPI_VictorSPX _leftFollower = new WPI_VictorSPX(Constants.kLeftDriveFollower);
  private WPI_TalonSRX _rghtFront = new WPI_TalonSRX(Constants.kRightDriveMaster);
  private WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(Constants.kRightDriveFollower);

  private DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);

  //private final ADIS16448_IMU m_imu = new ADIS16448_IMU();
  private ADIS16448_IMU.IMUAxis m_yawActiveAxis;
  private PIDController m_pidController;

  private double m_turn;
  private double m_speed;
  private static double m_prevJoystickAngle = 0;
  private double kSpeed = Constants.kDriveSpeedDefault;
  private double kTurn = Constants.kDriveTurnGain;

  private double kP = Constants.GyroPID.P;
  private double kI = Constants.GyroPID.I;
  private double kD = Constants.GyroPID.D;
  private double pidOut;

  public static double calculateJoystickAngle( double controllerX, double controllerY) {

    double x = controllerX;
    double y = -controllerY;
    double returnAngle = 0;

    if (Math.pow(x, 2) + Math.pow(y, 2) < Math.pow(Constants.kDeadband, 2)) {
      returnAngle = m_prevJoystickAngle;
    }

    if (x > 0 && y > 0) {
      returnAngle = Math.atan( y / x) * (180/Math.PI);
    } else if (x < 0 && y > 0) {
      returnAngle = 180 + Math.atan( y / x) * (180/Math.PI);
    } if (x < 0 && y < 0) {
      returnAngle = 180 + Math.atan( y / x) * (180/Math.PI);
    } else if (x > 0 && y < 0) {
      returnAngle = 360 + Math.atan( y / x) * (180/Math.PI);
    }

    m_prevJoystickAngle = returnAngle;
    return returnAngle;

  }

  public DriveSystem() {

    _rghtFront.configFactoryDefault();
    _rghtFollower.configFactoryDefault();
    _leftFront.configFactoryDefault();
    _leftFollower.configFactoryDefault();

    /* set up followers */
    _rghtFollower.follow(_rghtFront);
    _leftFollower.follow(_leftFront);

    /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
    _rghtFront.setInverted(true); // !< Update this
    _leftFront.setInverted(false); // !< Update this

    /*
      * set the invert of the followers to match their respective master controllers
      */
    _rghtFollower.setInverted(InvertType.FollowMaster);
    _leftFollower.setInverted(InvertType.FollowMaster);

    /*
      * [4] adjust sensor phase so sensor moves positive when Talon LEDs are green
      */
    _rghtFront.setSensorPhase(true);
    _leftFront.setSensorPhase(true);

    /*
      * WPI drivetrain classes defaultly assume left and right are opposite. call
      * this so we can apply + to both sides when moving forward. DO NOT CHANGE
      */
    _diffDrive.setRightSideInverted(false);
    
    //m_imu.configCalTime(8);
    //m_imu.reset();
    //m_imu.calibrate();
    m_pidController = new PIDController(kP, kI, kD);
    m_pidController.enableContinuousInput(0, 360); //CHECK OUTPUT RANGE
    //m_yawActiveAxis = ADIS16448_IMU.IMUAxis.kZ;

  }

  public void ArcadeDrive(double turn, double forw) {

    m_turn = turn * kTurn;
    m_speed = forw * kSpeed;

        /* deadband gamepad 10% */
        if (Math.abs(forw) < Constants.kDeadband) {
            m_speed = 0;
        }
        if (Math.abs(turn) < Constants.kDeadband) {
            m_turn = 0;
        }

        /* drive robot */
        _diffDrive.arcadeDrive(m_speed, m_turn);
    }
  
  public void setSpeedCoefficient(double coef) {
    kSpeed = coef;
  }

  public void GyroDrive (double targetAngle, double forw) {
    m_speed = forw * kSpeed;
    m_pidController.setSetpoint(targetAngle);
   // pidOut = m_pidController.calculate(m_imu.getAngle());
    _diffDrive.arcadeDrive(m_speed, pidOut);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void initDefaultCommand() {

    setDefaultCommand(new ArcadeDrive());

  }

}
