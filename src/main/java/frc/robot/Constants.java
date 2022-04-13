// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. 
 * 
 * Essentially this holds all the static values for certain systems (button numbers.
 * motor controller IDs, etc). If it's an unchanging number, throw it here. Many of these numbers
 * will be based on the Wiring Contract (VERY IMPORTANT)
 * 
 * should be declared globally (i.e. public static). Do not put anything
 * functional in this class.
 */

package frc.robot;
public final class Constants 
{
    // Joysticks and Button Numbers--
    public static final int kJoystickDriverID = 0; // For Driver
    public static final int kJoystickShooterID = 1; // For Co-Driver (handles shooting)

    //Button Numbers for 0--
    public static final int intakeButtonNumber = 1; 
    public static final int climbHoningButtonNumber = 11;
    public static final int visionModeButtonNumber = 7;

    //Button Numbers for 1--
    public static final int sequence1ButtonNumber = 2; 
    public static final int sequence2ButtonNumber = 1; 
    public static final int sequence3ButtonNumber = 3; 
    public static final int highSpeedShooterButtonNumber = 6; 
    public static final int lowSpeedShooterButtonNumber = 5;
    public static final int automaticShooterButtonNumber = 7;
    public static final int runShooterButtonNumber = 10;
    public static final int shooterModeButtonNumber = 8;
    public static final int findTargetButtonNumber = 9;

    // Drive--
    public static final int kFrontLeftMotorCANID = 2; // For all of the drive motors
    public static final int kRearLeftMotorCANID = 4;
    public static final int kFrontRightMotorCANID = 1;
    public static final int kRearRightMotorCANID = 3;

    // Climb--
    public static final int kClimbCANID = 6; // For Climb Motor
    public static final int lowerClimbLimitSwitchChannel = 0; 
    public static final int kPistonForwardClimbChannel = 0; // For piston-- double solenoid, it has two channels. This is forward
    public static final int kPistonReverseClimbChannel = 1; // For piston

    // Climb PID--
    public static final double CLIMB_kP = 0.0002;
    public static final double CLIMB_kI = 0.000001;
    public static final double CLIMB_kD = 0.0004;
    public static final double CLIMB_MIN_OUTPUT = -6000.0;
    public static final double CLIMB_MAX_OUTPUT = 6000.0;

    //Climb distances-- meant for each stage (1,2,3 respectively) (there are two climbs)
    public static final double climbDistance1 = 145; // Distance for fully extended (climb's pivot arms)
    public static final double climbDistance2 = 40; // Distance 2 for climb's pivot arms
    public static final double climbDistance3 = 140; // Distance 3 for climb's pivot arms
    public static final double climbDescendDistance = -4; // Distance for fully retracted (climb's pivot arms)

    public static final double encoderClimbDistance1 = 154;
    public static final double encoderClimbDistance2 = 40;
    public static final double encoderClimbDistance3 = 140;
    public static final double encoderClimbDescendDistance = -4;

    public static final int kEncoderCountsPerRev = 42; // Ask about the encoder
    private static final double kArmDiameter = 0.75; // Arms' diameter in inches
    private static final double kGearBox = 0.0625; // Gear Box ratio
    public static final double kCircumference = kArmDiameter * Math.PI; // Sets units to inches
    public static final double kEncoderPositionConversionFactor = kCircumference / kGearBox; // Sets encoder to read position in inches

    // Shooter--
    public static final int kShooterCANID = 5; // For the Shooter Motor
    public static final int kShooterServoPWMID = 0;
    public static final int highShooterSpeed = 5000; 
    public static final int lowShooterSpeed = 2500;

    //Shooter PID--
    public static final double SHOOTER_kP = 0.0001;
    public static final double SHOOTER_kI = 0.000001;
    public static final double SHOOTER_kD = 0.0004;
    public static final double MAX_NEO_RPM = 5676.0;
    public static final double SHOOTER_MAX_OUTPUT = 6000.0;
    public static final double SHOOTER_MIN_OUTPUT = -6000.0;
    
    //Shooter RPMs (for automatic shooter-- see vision)
    public static final int shooterRPMHigh = 5000;
    public static final int shooterRPMMedium = 4500;
    public static final int shooterRPMLow = 4000;

    // Intake--
    public static final int kIntakePWMID = 1;
    public static final double kIntakeMotorSpeed = 0.6;
    public static final int kIntakePistonForwardChannel = 2;
    public static final int kIntakePistonReverseChannel = 3;
} 
