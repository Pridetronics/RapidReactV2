// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
// import frc.robot.Constants;
// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Autonomous;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.robot.commands.Autonomous;
import frc.robot.commands.ReleaseGate;
import frc.robot.commands.ShooterRun;
import frc.robot.commands.ExtendRetractIntake;
import frc.robot.commands.IntakeRun;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer 
{
  // The robot's subsystems and commands are defined here...

  public static VictorSP frontLeft;
  public static VictorSP rearLeft;
  public static VictorSP frontRight;
  public static VictorSP rearRight;

  public static CANSparkMax shooterMotor; //Creates Motor for the shooter
  public static DoubleSolenoid shooterBallRelease; //Creates Double Solenoid for the shooter (relates to pistons)

  public static Compressor intakeCompressor;
  public static Solenoid intakePiston;
  public static VictorSP intakeMotor;
  
  public static Intake intake;
  public static Shooter shooter; //Creates the subsytem  for shooter
  public static Drive drive;
  public static Autonomous m_auto; 

  public JoystickButton shooterButton; //Button for the shooter
  public JoystickButton intakeButton;
  public Joystick joystickDriver; //Controller 0
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

    intakeCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    intakePiston = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);
    intakeMotor = new VictorSP(Constants.kIntakeChannel);
    intake = new Intake();
    
    SmartDashboard.putData("Shooter Run", new ShooterRun(shooter)); //Puts data on Shuffleboard to use the command. Displays
    SmartDashboard.putData("Release Gate", new ReleaseGate(shooter)); //on the screen and can be run by pushing the square. Pretty neat
    SmartDashboard.putData("Autonomous", new Autonomous(drive));
    SmartDashboard.putData("Intake Run", new IntakeRun(intake));
    SmartDashboard.putData("Extend/Retract Intake", new ExtendRetractIntake(intake));

    configureButtonBindings();
  }

  private void configureButtonBindings() 
  {
    //Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(joystickShooter, Constants.shooterButtonNumber); 
    shooterButton.whileHeld(new ParallelCommandGroup( //This is meant to run both the shooter and the release gate commands
      new ReleaseGate(shooter),
      new ShooterRun(shooter))); //References the command and inside the needed subsytem

    intakeButton = new JoystickButton(joystickShooter, Constants.intakeButtonNumber);
    intakeButton.whileHeld(new SequentialCommandGroup(
      new ExtendRetractIntake(intake),
      new WaitCommand(3),
      new IntakeRun(intake)));
  }

  public Command getAutonomousCommand() 
  {
    return m_auto;
  }
}
