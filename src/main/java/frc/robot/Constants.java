// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants //Note for self: Define integers here. Button numbers and the addresses of the motors. 
{
        //** Drive **//
    public static final int kFrontLeftChannel = 2; //For all of the drive motors
    public static final int kRearLeftChannel = 4;
    public static final int kFrontRightChannel = 1;
    public static final int kRearRightChannel = 3;
    public static final int kJoystickDriverChannel = 0; //For Joystick 0

        //** Intake **//
    public static final int intakeButtonNumber = 2;
    public static final int kIntakeChannel = 6;

        //** Shooter **//
    public static final int kJoystickShooterChannel = 1; //For Joystick 1 
    public static final int kShooterChannel = 5; //For the Shooter Motor
    public static final int shooterButtonNumber = 4; // For Shooter Button on the Gamepad
    public static final int kShooterGateForwardChannel = 4; //For use in pneumatics system (same with the one below)
    public static final int kShooterGateReleaseChannel = 5;

        //** Climb **//
     public static final int kClimbChannel = 7; //For Climb Motor
     public static final int kPistonFirstClimbChannel = 7; //For piston one
     public static final int kPistonFirstReverseClimbChannel = 8; //For piston one
     public static final int kPistonSecondClimbChannel = 9; //For piston two
     public static final int kPistonSecondReverseClimbChannel = 10; //For piston two
     public static final int climbButtonNumber = 3; //For climb Button on the Gamepad
     public static final double ClimbMotorSpeed = 1.0; //Sets speed for climb motor
     public static final double InverseClimbMotorSpeed = -1.0; //Sets speed for climb motor
     public static final int upperClimbLimitSwitchChannel = 1; //Upper Limit Switch Channel
     public static final int lowerClimbLimitSwitchChannel = 0; //Lower Limit Switch Channel
     public static final int kEncoderCountsPerRev = 1120; 
     //Ask about the encoder 

}

