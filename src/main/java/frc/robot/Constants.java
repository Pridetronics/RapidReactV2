// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants
 * should be declared globally (i.e. public static). Do not put anything
 * functional in this class.
 *
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants // Note for self: Define integers here. Button numbers and the addresses of the motors.
{
    // Joysticks and Button Numbers--
    public static final int kJoystickDriverID = 0; // For Joystick 0
    public static final int kJoystickShooterID = 1; // For Joystick 1

    public static final int shooterButtonNumber = 8; // All values will need to be adjusted for true values. 
    public static final int intakeButtonNumber = 1;
    public static final int visionModeButtonNumber = 7;
    public static final int shooterModeButtonNumber = 7;
    public static final int findTargetButtonNumber = 9;
    public static final int climbHoningButtonNumber = 11;

    // Drive--
    public static final int kFrontLeftCANID = 2; // For all of the drive motors
    public static final int kRearLeftCANID = 4;
    public static final int kFrontRightCANID = 1; 
    public static final int kRearRightCANID = 3;

    public static final double kDriveDistance = 24; // In Inches - wont go exactly due to mecanum wheel loss
    // Not used yet
    // public static final double gearRatio = 0;// drive gear ratio
    // public static final double circumference = 0;
    // public static final double kDriveWHeelDiameter = 0;
    // public static final double kEncoderPositionConversionFactorDrive =
    // kDriveWHeelDiameter * Math.PI;
    // public static final double inchesPerRevolution = circumference / gearRatio;
    // ** Climb **//
    public static final int kClimbChannel = 6; // For Climb Motor
    public static final double ClimbMotorSpeed = 1.0; // Sets speed for climb motor
    public static final double InverseClimbMotorSpeed = -1.0; // Sets speed for climb motor

    // Shooter--
    public static final int kShooterCANID = 5; // For the Shooter Motor
    public static final int shooterDefaultSpeed = 5000; // This will act as a minimum speed if no target is found. Allows the Gate to release.
    public static final double SHOOTER_kP = 0.0001;
    public static final double SHOOTER_kI = 0.000001;
    public static final double SHOOTER_kD = 0.0004;
    public static final double MAX_NEO_RPM = 5676.0;
    public static final double SHOOTER_MAX_OUTPUT = 6000.0;
    public static final double SHOOTER_MIN_OUTPUT = -6000.0;
    public static final int kShooterServoPWMID = 0;
    // public static final int kShooterGateReleaseID = 5;
    // RPM Values for Shooter-- Needs to be adjusted later. (HOPEFULLY I CAN GET RID
    // OF THIS... Ask me how later)
    public static final int shooterRPM17 = 5000;
    public static final int shooterRPM16 = 4750;
    public static final int shooterRPM15 = 4500;
    public static final int shooterRPM14 = 4250;
    public static final int shooterRPM13 = 4000;
    public static final int shooterRPM12 = 3750;
    public static final int shooterRPM11 = 3500;
    public static final int shooterRPM10 = 3250;

    // Intake--
    public static final int kIntakePWMID = 1;
    public static final double kIntakeMotorSpeed = 0.6;
    public static final int kIntakePistonForwardChannel = 2;
    public static final int kIntakePistonReverseChannel = 3;

    public static final int kPistonClimbChannel = 0; // For piston
    public static final int kPistonReverseClimbChannel = 1; // For piston

    public static final int climbButtonNumber = 3; // For climb Button on the Gamepad
    public static final int cancellationButton1 = 5; // For climb's cancellation button on the Gamepad
    public static final int cancellationButton2 = 6; // For climb's cancellation button on the Gamepad
    public static final int addButtonNumber = 1; // For climb's adding one button on the Gamepad
    public static final int cancelStageButtonNumber = 2; // For climb's decrease stage button on the Gamepad

    public static final int upperClimbLimitSwitchChannel = 1; // Upper Limit Switch Channel
    public static final int lowerClimbLimitSwitchChannel = 0; // Lower Limit Switch Channel

    public static final double CLIMB_kP = 0.0002;
    public static final double CLIMB_kI = 0.000001;
    public static final double CLIMB_kD = 0.0004;
    public static final double CLIMB_MIN_OUTPUT = -6000.0;
    public static final double CLIMB_MAX_OUTPUT = 6000.0;

    public static final double climbDistance1 = 25; // Distance for fully extended
    public static final double climbDistance2 = 9; // Distance for 9 inches
    public static final double climbDistance3 = 23; // Distance for 23 inches
    public static final double climbDescendDistance = 2.5; // Distance for fully retracted

    public static final int kEncoderCountsPerRev = 42; // Ask about the encoder
    private static final double kArmDiameter = 0.75; // Arms' diameter in inches
    private static final double kGearBox = 0.0625; // Gear Box ratio
    public static final double kCircumference = kArmDiameter * Math.PI; // Sets units to inches
    public static final double kEncoderPositionConversionFactor = kCircumference / kGearBox; // Sets encoder to read
                                                                                             // position in inches

}
