// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants // Note for self: Define integers here. Button numbers and the addresses of the motors.
{
    public static final int kJoystickDriverID = 0; // For Joystick 0
    public static final int kJoystickShooterID = 1; // For Joystick 1
    public static final int shooterButtonNumber = 4; // For Shooter Button on the Gamepad
    public static final int intakeButtonNumber = 2;
   
    public static final int kFrontLeftID = 0; // For all of the drive motors
    public static final int kRearLeftID = 2;
    public static final int kFrontRightID = 4; //Change to 1 later
    public static final int kRearRightID = 3;

    public static final int kShooterID = 1; // For the Shooter Motor
    public static final int shooterDefaultSpeed = 3000;
    public static final double SHOOTER_kP = 0.0002;
    // public static final double SHOOTER_kI = 0.000001;
    // public static final double SHOOTER_kD = 0.0004;
    public static final double MAX_NEO_RPM = 5676.0;
    public static final double SHOOTER_MAX_OUTPUT = 6000.0;
    public static final double SHOOTER_MIN_OUTPUT = -6000.0;
    public static final int kShooterGateForwardID = 4; // For use in pneumatics system (same with the one below)
    public static final int kShooterGateReleaseID = 5;

    //Shooter RPM Values-- I'm a terrible person. I will try to make a list later but I'm stupid so bad naming conventions.
    public static final int shooterRPM17 = 5000;
    public static final int shooterRPM16 = 4750;
    public static final int shooterRPM15 = 4500;
    public static final int shooterRPM14 = 4250;
    public static final int shooterRPM13 = 4000;
    public static final int shooterRPM12 = 3750;
    public static final int shooterRPM11 = 3500;
    public static final int shooterRPM10 = 3250;

    public static final int kIntakeID = 1;
    //Create constants for intake pistons later

}
