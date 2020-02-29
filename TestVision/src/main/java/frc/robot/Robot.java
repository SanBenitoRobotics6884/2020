/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  WPI_TalonSRX _rghtFront = new WPI_TalonSRX(1);
  WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(10);
  WPI_TalonSRX _leftFront = new WPI_TalonSRX(2);
  WPI_VictorSPX _leftFollower = new WPI_VictorSPX(20);

  //DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);

  Joystick m_controller = new Joystick(0);

  NetworkTableEntry xEntry;
  NetworkTableEntry yEntry;
  NetworkTableEntry Width;
  NetworkTableEntry Height;

  double x = 0;
  double y = 0;
  double width = 0;
  double height = 0;
  double centerX = 0;

  final int WIDTH = 320;
  final int HEIGHT = 240;
  final double kSpeed = 0.1;
  final double kTurnGain = (2/HEIGHT) * 0.2;


  public void robotInit() {
    //Get the default instance of NetworkTables that was created automatically
    //when your program starts
    NetworkTableInstance inst = NetworkTableInstance.getDefault();

    //Get the table within that instance that contains the data. There can
    //be as many tables as you like and exist to make it easier to organize
    //your data. In this case, it's a table called datatable.
    NetworkTable table = inst.getTable("VisionInfo");

    //Get the entries within that table that correspond to the X and Y values
    //for some operation in your program.
    xEntry = table.getEntry("xEntry");
    yEntry = table.getEntry("yEntry");
    Width = table.getEntry("Width");
    Height = table.getEntry("Height");

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
    //_diffDrive.setRightSideInverted(false);
  }   

  public void teleopPeriodic() {
    //Using the entry objects, set the value to a double that is constantly
    //increasing. The keys are actually "/datatable/X" and "/datatable/Y".
    //If they don't already exist, the key/value pair is added.

    x = xEntry.getDouble(0);
    y = yEntry.getDouble(0);
    width = Width.getDouble(0);
    height = Height.getDouble(0);

    centerX = x - (WIDTH / 2);
    SmartDashboard.putNumber("CenterX", (-centerX) * kTurnGain);

    //if (m_controller.getRawButton(1)) _diffDrive.arcadeDrive(kSpeed, (-centerX) * kTurnGain);
  }
}