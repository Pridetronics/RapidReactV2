// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

import frc.robot.commands.DriveJoystick;
import frc.robot.commands.ShooterAdjust;
import frc.robot.commands.FindTarget;
import frc.robot.commands.ShooterMode;
import frc.robot.commands.ShooterRun;
import frc.robot.commands.SimpleShooterRun;
import frc.robot.commands.ReleaseGate;
import frc.robot.commands.VisionMode;
import frc.robot.commands.LimelightDistanceFinder;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.IntakeRun;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.AutoDriveForwards;
import frc.robot.commands.AutoDriveShoot;
import frc.robot.commands.AutoMoveBackwards;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
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
  // The robot's subsystems and commands are defined here...
  // Drive--
  public static CANSparkMax frontLeft; // Creates all four drive motors
  public static CANSparkMax rearLeft;
  public static CANSparkMax frontRight;
  public static CANSparkMax rearRight;

  // Shooter--
  public static CANSparkMax shooterMotor; // Creates Motor for the shooter
  public static RelativeEncoder shooterEncoder;
  public static SparkMaxPIDController shooterMotorPID;
  // public static DoubleSolenoid shooterBallRelease; // Creates Double Solenoid
  // for the shooter (relates to pistons)
  public static Servo shooterServo;

  // Intake--
  public static Compressor intakeCompressor;
  public static DoubleSolenoid intakePiston;
  public static Talon intakeMotor;

  // Subsystems--
  public static Intake m_intake;
  public static Shooter m_shooter; // Creates the subsytem for shooter
  public static Drive m_drive;

  //Buttons
  public JoystickButton shooterButton; // Button for the shooter
  public JoystickButton intakeButton;
  public static JoystickButton climbButton;
  public JoystickButton cancellationButton1;
  public JoystickButton cancellationButton2;
  public JoystickButton visionModeButton;
  public JoystickButton shooterModeButton;
  public JoystickButton findTargetButton;
  public Joystick joystickDriver; // Controller 0 --Ensure that all controllers are in proper ports in Driver
                                  // Station
  public Joystick joystickShooter; // Controller 1

  // Sendable Chooser--
  static SendableChooser<Command> m_chooser;
  private final AutoDriveShoot m_auto2;
  private final AutoMoveBackwards m_auto1;
  private final AutoDriveForwards m_autoDriveForward;
  //Sendable Chooser--

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign values for the motors, and for the joysticks. Do not do button
    // bindings, this is below.
    joystickDriver = new Joystick(Constants.kJoystickDriverID);
    joystickShooter = new Joystick(Constants.kJoystickShooterID); // Sets shooter joystick to port 1

    // Drive Relevant---
    frontLeft = new CANSparkMax(Constants.kFrontLeftCANID, MotorType.kBrushless);

    rearLeft = new CANSparkMax(Constants.kRearLeftCANID, MotorType.kBrushless);

    frontRight = new CANSparkMax(Constants.kFrontRightCANID, MotorType.kBrushless);
    frontRight.setInverted(true);

    rearRight = new CANSparkMax(Constants.kRearRightCANID, MotorType.kBrushless);
    rearRight.setInverted(true);

    m_drive = new Drive(joystickDriver);
    m_chooser = new SendableChooser<>();
    m_auto2 = new AutoDriveShoot(m_drive);
    m_auto1 = new AutoMoveBackwards(m_drive);
    m_autoDriveForward = new AutoDriveForwards(m_drive);

    // Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterCANID, MotorType.kBrushless);
    shooterMotor.setInverted(true);
    shooterEncoder = shooterMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

    shooterMotorPID = shooterMotor.getPIDController();
    shooterMotorPID.setP(Constants.SHOOTER_kP);
    shooterMotorPID.setI(Constants.SHOOTER_kI);
    shooterMotorPID.setD(Constants.SHOOTER_kD);

    shooterMotorPID.setOutputRange(Constants.SHOOTER_MIN_OUTPUT, Constants.SHOOTER_MAX_OUTPUT);
    // shooterBallRelease = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
    // Constants.kShooterGateForwardID, Constants.kShooterGateReleaseID);
    shooterServo = new Servo(Constants.kShooterServoPWMID);
    m_shooter = new Shooter(); // Defines the subsystem

    // Intake Relevant---
    intakeCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kIntakePistonForwardChannel,
        Constants.kIntakePistonReverseChannel);
    intakeMotor = new Talon(Constants.kIntakeCANID);
    m_intake = new Intake();

    // SmartDashboard Relevant-- Remove these during competition time

    SmartDashboard.putData("Shooter Run", new ShooterRun(m_shooter, m_drive)); // Puts data on Shuffleboard to use the
                                                                               // command.
    SmartDashboard.putData("Shooter Adjust", new ShooterAdjust(m_drive));
    SmartDashboard.putData("Release Gate", new ReleaseGate(m_shooter)); // Displays on the screen and can be run by
                                                                        // pushing the square. Pretty neat
    SmartDashboard.putData("Intake Run", new IntakeRun(m_intake));
    SmartDashboard.putData("Extend/Retract Intake", new ExtendIntake(m_intake));
    SmartDashboard.putData("Find Distance", new LimelightDistanceFinder(m_shooter));
    SmartDashboard.putData("Change Vision Modes", new VisionMode(m_shooter));
    SmartDashboard.putData("Find Target", new FindTarget(m_drive));

    m_chooser.setDefaultOption("Auto Move Backwards", m_auto1);
    m_chooser.addOption("Auto Move Forward", m_autoDriveForward);
    m_chooser.addOption("Auto Move and Shoot", m_auto2);
    SmartDashboard.putData("Auto Chooser", m_chooser);

    configureButtonBindings();
    m_drive.setDefaultCommand(new DriveJoystick(joystickDriver, m_drive));
  }

  private void configureButtonBindings() {
    visionModeButton = new JoystickButton(joystickShooter, Constants.visionModeButtonNumber);
    visionModeButton.toggleWhenPressed(new VisionMode(m_shooter)); // Switches between modes. See Shooter subsytem for
                                                                   // function.

    shooterModeButton = new JoystickButton(joystickShooter, Constants.shooterModeButtonNumber);
    shooterModeButton.toggleWhenPressed(new ShooterMode(m_shooter));

    findTargetButton = new JoystickButton(joystickShooter, Constants.findTargetButtonNumber); // Change this to left
                                                                                              // trigger
    findTargetButton.whenPressed(new FindTarget(m_drive));

    // Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(joystickShooter, Constants.shooterButtonNumber); // Eventually change to right
                                                                                        // trigger
    shooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run both the shooter and the release gate
                                                      // commands
        new ShooterRun(m_shooter, m_drive),
        new WaitCommand(0.2),
        new ReleaseGate(m_shooter))); // References the command and inside the needed subsytem
    // Simple Shooter----
    // shooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run
    // both the shooter and the release gate commands
    // new SimpleShooterRun(m_shooter),
    // new WaitCommand(0.2),
    // new ReleaseGate(m_shooter))); // References the command and inside the needed
    // subsytem

    intakeButton = new JoystickButton(joystickShooter, Constants.intakeButtonNumber);
    intakeButton.whileHeld(new SequentialCommandGroup(
        new ExtendRetractIntake(m_intake),
        new IntakeRun(m_intake)));
  }

  // public Command getAutonomousCommand() {
  //   return (Command) m_chooser.getSelected();
  // }
}
