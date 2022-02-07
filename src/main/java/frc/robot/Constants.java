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
public final class Constants // Note for self: Define integers here. Button numbers and the addresses of the
                             // motors.
{
    public static final int kJoystickDriverID = 0; // For Joystick 0
    public static final int kJoystickShooterID = 1; // For Joystick 1
    public static final int kShooterID = 4; // For the Shooter Motor
    public static final int shooterButtonNumber = 4; // For Shooter Button on the Gamepad
    public static final int intakeButtonNumber = 2;
    public static final int kShooterGateForwardID = 4; // For use in pneumatics system (same with the one below)
    public static final int kShooterGateReleaseID = 5;

    public static final int kFrontLeftID = 0; // For all of the drive motors
    public static final int kRearLeftID = 2;
    public static final int kFrontRightID = 1;
    public static final int kRearRightID = 3;

    public static final int kIntakeID = 1;

}
