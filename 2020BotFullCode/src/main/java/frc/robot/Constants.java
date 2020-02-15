/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public s static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //Constant Values
    public static final double kDriveSlow = 0.5;
    public static final double kDriveFast = 0.8;
    public static final double kDeadband = 0.10;
    public static final double kDriveTurnGain = 1;
    public static final double kGyroDriveTurnGain = 1;
    public static final double kDriveSpeedDefault = 0.5;
    public static final double kIntakeSpeed = 0.4;
    public static final double kLauncherSpeed = 0.5;

    //Controllers & Input
    public static final int kDriveControllerChannel = 0;
    public static final int kDriveForwardAxis = 3;
    public static final int kDriveReverseAxis = 2;
    public static final int kDriveTurnAxis = 0;
    public static final int kDriveTurnVerticalAxis = 1;
    public static final int kLeftBumper = 5;
    public static final int kRightBumper = 6;
    public static final int kButtonA = 1;
    public static final int kButtonB = 2;

    //Motor Controller Channels
    public static final int kLeftDriveMaster = 2; //Talon SRX
    public static final int kLeftDriveFollower = 20; //Victor SPX
    public static final int kRightDriveMaster = 1; //Talon SRX
    public static final int kRightDriveFollower = 10; // Victor SPX
    public static final int kIntakeMotor = 3; // Victor SPX
    public static final int kRevolverMotor = 4;
    public static final int kLauncherMotor = 5;

    //Sensor Channels
    public static final int kPotChannel = 1; //Potentiometer

    //PID Constants
    public static final class GyroPID {
        public static final double P = -0.005;
        public static final double I = 0.0;
        public static final double D = 0.0;
    }

    public static final class LauncherPID {
        public static final double P = -0.005;
        public static final double I = 0.0;
        public static final double D = 0.0;
    }

    public static final class RevolverPID { //TUNE BEFORE USE
        public static final double P = 0.05;
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double[] kIntakeSetPoints = {1, 1, 1, 1, 1}; //FIND SETPOINTS BEFORE USE
        public static final double[] kLauncherSetPoints = {1, 1, 1, 1, 1}; //FIND SETPOINTS BEFORE USE
    }

}
