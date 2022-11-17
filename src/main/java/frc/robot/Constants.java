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
    //IF YOU ALTER ANYTHING, CHANGE THE COMMENT WITH IT. KEEP EVERYTHING CONSISTENT!!
    //Write button numbers + function comments like so: //Button Number: What it does
    
    // Joysticks and Button Numbers--
    public static final int kJoystickDriverID = 0; // For Driver
    public static final int kJoystickShooterID = 1; // For Co-Driver (handles shooting)

    //Button Numbers for 0 (joystickDriver)--
    public static final int intakeButtonNumber = 1; //Joystick Trigger: Runs intake motor and extends piston 
    public static final int climbHoningButtonNumber = 11; //Button 11: Adjusts climb motors to starting position
    public static final int visionModeButtonNumber = 7; //Button 7: Toggles CameraMode and ProcessingMode on LL

    //Button Numbers for 1 (joystickShooter)--
    public static final int sequence1ButtonNumber = 2; //B: Runs sequence one (see RC for stage explanation)
    public static final int sequence2ButtonNumber = 1; //A: Runs sequence two
    public static final int sequence3ButtonNumber = 3; //X: Runs sequence three
    public static final int highSpeedShooterButtonNumber = 6; //Right Bumper: Runs shooter at 5000 RPM (and opens gate)
    public static final int lowSpeedShooterButtonNumber = 5; //Left Bumper: Runs shooter at 2500 RPM (and opens gate)
    public static final int automaticShooterButtonNumber = 7; //"Back" Button: Runs shooter at RPM determined by distance 
    public static final int runShooterButtonNumber = 10; //Right Joystick Press: Runs shooter based on which mood is set
    public static final int shooterModeButtonNumber = 8; //"Start" Button: Changes shooter mode
    public static final int findTargetButtonNumber = 9; //Bottom Left Joystick Press: Spins to find target
    public static final int intakeToggleButtonNumber = 4; //Y: Toggles intake

    // Drive--
    public static final int kFrontLeftMotorCANID = 2; // For all of the drive motors
    public static final int kRearLeftMotorCANID = 4;
    public static final int kFrontRightMotorCANID = 1;
    public static final int kRearRightMotorCANID = 3;

    public static final double autonomousDriveVoltage = -0.6;
    public static final double autonomousIntakePrepVoltage = -0.4;
    public static final double autonomousShooterPrepVoltage = 0.4;

    // Climb--
    public static final int kClimbCANID = 6; // For Climb Motor
    public static final int lowerClimbLimitSwitchChannel = 0; 
    public static final int kPistonForwardClimbChannel = 0; // For piston-- double solenoid, it has two channels. This is forward
    public static final int kPistonReverseClimbChannel = 1; // For piston

    // Climb PID--
    public static final double CLIMB_kP = 0.0002;
    public static final double CLIMB_kI = 0.000001;
    public static final double CLIMB_kD = 0.0004;
    public static final double CLIMB_MIN_OUTPUT = -6000.0; //These are the maximum and minimum RPMs that the PID can run in. 
    public static final double CLIMB_MAX_OUTPUT = 6000.0;

    //Climb distances-- meant for each stage (1,2,3 respectively) (there are two climbs)
    public static final double climbDistance1 = 145; // Distance for fully extended (climb's pivot arms)
    public static final double climbDistance2 = 40; // Distance 2 for climb's pivot arms
    public static final double climbDistance3 = 140; // Distance 3 for climb's pivot arms
    public static final double climbDescendDistance = -4; // Distance for fully retracted (climb's pivot arms)

    public static final double encoderClimbDistance1 = 154; //This was altered in the competition to make up for a build problem...
    public static final double encoderClimbDistance2 = 40;
    public static final double encoderClimbDistance3 = 140;
    public static final double encoderClimbDescendDistance = -4;

    //This is broke-- please fix it (times it by five to get an okay number)
    public static final int kEncoderCountsPerRev = 42; // Ask about the encoder
    private static final double kArmDiameter = 0.75; // Arms' diameter in inches
    private static final double kGearBox = 0.0625; // Gear Box ratio
    public static final double kCircumference = kArmDiameter * Math.PI; // Sets units to inches
    public static final double kEncoderPositionConversionFactor = kCircumference / kGearBox; // Sets encoder to read position in inches

    // Shooter--
    public static final int kShooterLeftCANID = 5; // For the Shooter Motor 1 (Original Shooter Motor)
    public static final int kShooterRightCANID = 7; //For Shooter Motor 2
    public static final int kShooterServoPWMID = 0;
    public static final int highShooterSpeed = 3000; //2500 ori, 2925 barely works
    public static final int lowShooterSpeed = 1600;

    //Shooter PID--
    public static final double SHOOTER_kP = 0.00008;
    public static final double SHOOTER_kI = 0.000001;
    public static final double SHOOTER_kD = 0.0004;
    public static final double SHOOTER_MAX_OUTPUT = 6000.0;
    public static final double SHOOTER_MIN_OUTPUT = -6000.0;
    
    //Shooter RPMs (for automatic shooter-- see vision)
    public static final int shooterRPMHigh = 2575;//2500  ori
    public static final int shooterRPMMedium = 2000;
    public static final int shooterRPMLow = 1250;

    //Shooter Servo--
    public static final int shooterServoOpenPosition = 1000;
    public static final int shooterServoClosedPosition = 1300;

    // Intake--
    public static final int kIntakePWMID = 1;
    public static final double kIntakeMotorSpeed = 0.6;
    public static final int kIntakePistonForwardChannel = 2;
    public static final int kIntakePistonReverseChannel = 3;
    public static final double intakeRunningVoltage = 0.7;
    public static final double intakeOffVoltage = 0;
} 
