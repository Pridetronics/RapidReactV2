// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import frc.robot.Constants;
// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.PWM;

import frc.robot.commands.ReleaseGate;
import frc.robot.commands.ShooterRun;
import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here... Examples below
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  public static CANSparkMax shooterMotor; // Creates Motor for the shooter
  public static DoubleSolenoid shooterBallRelease; // Creates Double Solenoid for the shooter (relates to pistons)

  public static Shooter shooter; // Creates the subsytem for shooter

  public JoystickButton shooterButton; // Button for the shooter
  public Joystick joystickShooter; // Controller 1

  public JoystickButton limelightTrackingButton; // Button to activate limelight thingy
  public Joystick joystickDrive; // Controller 0

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign values for the motors, and for the joysticks. Do not do button
    // bindings, this is below.
    joystickShooter = new Joystick(Constants.kJoystickShooterChannel); // Sets shooter joystick to port 1

    // Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterChannel, MotorType.kBrushless);
    shooterMotor.setInverted(true);
    shooter = new Shooter(); // Defines the subsystem
    shooterBallRelease = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kShooterGateForwardChannel,
        Constants.kShooterGateReleaseChannel);

    SmartDashboard.putData("Shooter Run", new ShooterRun(shooter)); // Puts data on Shuffleboard to use the command.
                                                                    // Displays
    SmartDashboard.putData("Release Gate", new ReleaseGate(shooter)); // on the screen and can be run by pushing the
                                                                      // square. Pretty neat

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(joystickShooter, Constants.shooterButtonNumber);
    shooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run both the shooter and the release gate
                                                      // commands
        new ShooterRun(shooter),
        new ReleaseGate(shooter))); // References the command and inside the needed subsytem

    // Limelight Button Configured and Command Assigned to Button

  }

  /**
   * This function implements a simple method of generating driving and steering
   * commands
   * based on the tracking data from a limelight camera.
   */

  public class limelightValues {
    // Communicates horizontal offset from crosshair to target, vertical offset, and
    // target area from limelight to SmartDashboard
    public limelightValues() {
      NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
      NetworkTableEntry tx = table.getEntry("tx");
      NetworkTableEntry ty = table.getEntry("ty");
      NetworkTableEntry ta = table.getEntry("ta");

      // read values periodically
      double x = tx.getDouble(0.0);
      double y = ty.getDouble(0.0);
      double area = ta.getDouble(0.0);

      // post to smart dashboard periodically
      SmartDashboard.putNumber("LimelightX", x);
      SmartDashboard.putNumber("LimelightY", y);
      SmartDashboard.putNumber("LimelightArea", area);
    }
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

}
