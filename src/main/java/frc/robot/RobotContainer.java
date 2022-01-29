// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
// import frc.robot.Constants;
// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ReleaseGate;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.commands.ShooterRun;
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
  

  //Note to self: Create mappings here. Declare the motor controllers (but do not assign values). Same with buttons

  public static CANSparkMax shooterMotor;
  public JoystickButton shooterButton;
  public static Shooter shooter; //Creates the subsytem for FlyWheel

  public static DoubleSolenoid shooterBallRelease;

  public Joystick m_stick; //Controller 1
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() 
  {
    //Assign values for the motors, and for the joysticks. Do not do button bindings, this is below. 
    m_stick = new Joystick(Constants.kJoystickChannel);

 //Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterChannel, MotorType.kBrushless); 
    shooterMotor.setInverted(true);
    shooter = new Shooter(); //Defines the subsystem
    shooterBallRelease = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kShooterGateForwardChannel, Constants.kShooterGateReleaseChannel);
    
    configureButtonBindings();
  }

  private void configureButtonBindings() 
  {

    //Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(m_stick, Constants.shooterButtonNumber); 
    shooterButton.whileHeld(new ParallelCommandGroup(
      new ShooterRun(shooter),
      new WaitCommand(0.2),
      new ReleaseGate(shooter))); //References the command and inside the needed subsytem
    

  }

  public Command getAutonomousCommand() 
  {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
