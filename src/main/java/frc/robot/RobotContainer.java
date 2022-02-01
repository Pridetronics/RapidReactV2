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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Autonomous;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.robot.commands.Autonomous;
import frc.robot.commands.ReleaseGate;
import frc.robot.commands.ShooterRun;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer 
{
  // The robot's subsystems and commands are defined here... Examples below
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  public static VictorSP frontLeft;
  public static VictorSP rearLeft;
  public static VictorSP frontRight;
  public static VictorSP rearRight;

  public static CANSparkMax shooterMotor; //Creates Motor for the shooter
  public static DoubleSolenoid shooterBallRelease; //Creates Double Solenoid for the shooter (relates to pistons)
  
  public static Shooter shooter; //Creates the subsytem  for shooter
  public static Drive drive;

  public JoystickButton shooterButton; //Button for the shooter
  public static Joystick joystickDriver; //Changed this to static to reference it within Drive.java
  public Joystick joystickShooter; //Controller 1
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() 
  {
    //Assign values for the motors, and for the joysticks. Do not do button bindings, this is below.
    joystickDriver = new Joystick(Constants.kJoystickDriverChannel); 
    joystickShooter = new Joystick(Constants.kJoystickShooterChannel); //Sets shooter joystick to port 1

    //Drive Relevant---
    frontLeft = new VictorSP(Constants.kFrontLeftChannel);
    rearLeft = new VictorSP(Constants.kRearLeftChannel);
    frontRight = new VictorSP(Constants.kFrontRightChannel);
    frontRight.setInverted(true);
    rearRight = new VictorSP(Constants.kRearRightChannel);
    rearRight.setInverted(true);
    drive = new Drive();

    //Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterChannel, MotorType.kBrushless); 
    shooterMotor.setInverted(true);
    shooter = new Shooter(); //Defines the subsystem
    shooterBallRelease = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kShooterGateForwardChannel, Constants.kShooterGateReleaseChannel);
    
    SmartDashboard.putData("Shooter Run", new ShooterRun(shooter)); //Puts data on Shuffleboard to use the command. Displays
    SmartDashboard.putData("Release Gate", new ReleaseGate(shooter)); //on the screen and can be run by pushing the square. Pretty neat
    SmartDashboard.putData("Autonomous", new Autonomous(drive));

    configureButtonBindings();
  }

  private void configureButtonBindings() 
  {

    //Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(joystickShooter, Constants.shooterButtonNumber); 
    shooterButton.whileHeld(new ParallelCommandGroup( //This is meant to run both the shooter and the release gate commands
      new ReleaseGate(shooter),
      new ShooterRun(shooter))); //References the command and inside the needed subsytem
    

  }

  public Command getAutonomousCommand() 
  {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
