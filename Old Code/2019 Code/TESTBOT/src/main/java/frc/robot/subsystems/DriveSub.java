/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Spark l1,l2,r1,r2;
  SpeedControllerGroup mRight, mLeft;
  DifferentialDrive mDrive;

  public DriveSub() {
    l1 = new Spark(RobotMap.FrontLeftMotor);
    l2 = new Spark(RobotMap.BackLeftMotor);
    r1 = new Spark(RobotMap.FrontRightMotor);
    r2 = new Spark(RobotMap.BackRightMotor);

    mRight = new SpeedControllerGroup(r1,r2);
    mLeft = new SpeedControllerGroup(l1, l2);

    mDrive = new DifferentialDrive(mLeft, mRight);
  }

  public void drive(double speed, double rotation) {
    System.out.println(speed);
    mDrive.curvatureDrive(speed, rotation, true);
  }

  public void reduceSpeed() {
    mDrive.curvatureDrive(0.4, 0, true);
  }

  @Override
  public void initDefaultCommand() {

  }
}
