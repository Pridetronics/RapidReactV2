// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.IncreaseStage;
import frc.robot.commands.CancellationButtonsClimb;
import frc.robot.commands.AutoIntakePrep;
import frc.robot.commands.AutoMoveBackwards;
import frc.robot.commands.AutoShootPrep;
import frc.robot.commands.CancelClimb;
import frc.robot.commands.CancelStage;
import frc.robot.commands.ClimbButtonSequence;
import frc.robot.commands.ClimbInitializationDown;
import frc.robot.commands.ClimbInitializationUp;
import frc.robot.commands.DriveJoystick;
import frc.robot.commands.OpenGate;
import frc.robot.commands.OpenGateLow;
import frc.robot.commands.PivotArmDescendDistance;
import frc.robot.commands.PivotArmDistanceOne;
import frc.robot.commands.PivotArmDistanceThree;
import frc.robot.commands.PivotArmDistanceTwo;
import frc.robot.commands.ShootTrigger;
import frc.robot.commands.ShooterAdjust;
import frc.robot.commands.ShooterRun;
import frc.robot.commands.SimpleShooterRun;
import frc.robot.commands.lowGoalShooterRun;
import frc.robot.commands.ShooterMode;
import frc.robot.commands.VisionMode;
import frc.robot.commands.lowGoalShooterRun;
import frc.robot.commands.LimelightDistanceFinder;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.FindTarget;
import frc.robot.commands.IntakeRun;

import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
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
  public static VictorSP intakeMotor;

  // Subsystems--
  public static Intake m_intake;
  public static Shooter m_shooter; // Creates the subsytem for shooter
  public static Climb m_climb;
  public static Drive m_drive;
  public static CANSparkMax climbMotor; // Climb Motor
  public static DoubleSolenoid climbPiston; // Climb Piston
  public static RelativeEncoder climbEncoder;
  public static DigitalInput upperClimbLimitSwitch;
  public static DigitalInput lowerClimbLimitSwitch;
  public static SparkMaxPIDController climbPID;

  public static Command ClimbButtonSequence;
  public static Command CancelClimb;
  public static Command AddOne;

  // Creates the subsystem for climb

  public JoystickButton highSpeedShooterButton; // Button for the shooter
  public JoystickButton lowSpeedShooterButton;
  public JoystickButton intakeButton;
  public static JoystickButton climbButton;
  public JoystickButton addButton;
  public JoystickButton cancellationButton1;
  public JoystickButton cancellationButton2;
  public JoystickButton cancelStageButton;
  public JoystickButton visionModeButton;
  public JoystickButton shooterModeButton;
  public JoystickButton findTargetButton;

  public JoystickButton sequence1Button;
  public JoystickButton sequence2Button;
  public JoystickButton sequence3Button;

  public Joystick joystickDriver; // Controller 0 --Ensure that all controllers are in proper ports in Driver
                                  // Station
  public Joystick joystickShooter; // Controller 1
  public static Command HoneClimb;
  public JoystickButton honeButton;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign values for the motors, and for the joysticks. Do not do button
    // bindings, this is below.
    joystickDriver = new Joystick(Constants.kJoystickDriverID);
    joystickShooter = new Joystick(Constants.kJoystickShooterID); // Sets shooter joystick to port 1
    // Climb Relevant
    climbPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kPistonClimbChannel,
        Constants.kPistonReverseClimbChannel);
    climbMotor = new CANSparkMax(Constants.kClimbChannel, MotorType.kBrushless);
    climbMotor.setInverted(true);
    climbEncoder = climbMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.kEncoderCountsPerRev);
    upperClimbLimitSwitch = new DigitalInput(Constants.upperClimbLimitSwitchChannel);
    lowerClimbLimitSwitch = new DigitalInput(Constants.lowerClimbLimitSwitchChannel);

    climbPID = climbMotor.getPIDController();
    climbPID.setP(Constants.CLIMB_kP);
    climbPID.setI(Constants.CLIMB_kI);
    climbPID.setD(Constants.CLIMB_kD);
    climbPID.setOutputRange(Constants.CLIMB_MIN_OUTPUT, Constants.CLIMB_MAX_OUTPUT);
    m_climb = new Climb(); // Defines the subsystem

    // Drive Relevant---
    frontLeft = new CANSparkMax(Constants.kFrontLeftCANID, MotorType.kBrushless);
    frontLeft.setInverted(false);

    rearLeft = new CANSparkMax(Constants.kRearLeftCANID, MotorType.kBrushless);
    rearLeft.setInverted(false);

    frontRight = new CANSparkMax(Constants.kFrontRightCANID, MotorType.kBrushless);
    frontRight.setInverted(true);

    rearRight = new CANSparkMax(Constants.kRearRightCANID, MotorType.kBrushless);
    rearRight.setInverted(true);

    // Intake Relevant---
    intakeCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kIntakePistonForwardChannel,
        Constants.kIntakePistonReverseChannel);
    intakeMotor = new VictorSP(Constants.kIntakePWMID);
    m_intake = new Intake();

    m_drive = new Drive(joystickDriver);

    // Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterCANID, MotorType.kBrushless);
    shooterMotor.setInverted(false);
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

    // Climb Releveant---

    // SmartDashboard Relevant-- Remove these during competition time

    // SmartDashboard.putData("Shooter Run", new ShooterRun(m_shooter, m_drive)); //
    // Puts data on Shuffleboard to use the
    // // command.
    // SmartDashboard.putData("Shooter Adjust", new ShooterAdjust(m_drive));
    // SmartDashboard.putData("Open Gate", new OpenGate(m_shooter)); // Displays on the screen and can be run by pushing the square. Pretty neat
    // SmartDashboard.putData("Intake Run", new IntakeRun(m_intake));
    // SmartDashboard.putData("Extend/Retract Intake", new ExtendIntake(m_intake));
    // SmartDashboard.putData("Find Distance", new
    // LimelightDistanceFinder(m_shooter));
    // SmartDashboard.putData("Change Vision Modes", new VisionMode(m_shooter));
    // SmartDashboard.putData("Find Target", new FindTarget(m_drive));
    // SmartDashboard.putData("Shooter Mode", new ShooterMode(m_shooter));

    SmartDashboard.putData("Sequence 1", new SequentialCommandGroup(
        new PivotArmDistanceOne(m_climb, Constants.climbDistance1),
        new InstantCommand(m_climb::pistonRelease, m_climb)));
    SmartDashboard.putData("Sequence 2", new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance));
    SmartDashboard.putData("Sequence 3", new SequentialCommandGroup(
        new PivotArmDistanceTwo(m_climb, Constants.climbDistance2),
        new InstantCommand(m_climb::pistonRetract, m_climb),
        new PivotArmDistanceThree(m_climb, Constants.climbDistance3),
        new InstantCommand(m_climb::pistonRelease, m_climb)));

    SmartDashboard.putData("Arm Distance One", new PivotArmDistanceOne(m_climb, Constants.climbDistance1));
    SmartDashboard.putData("Piston Extend", new InstantCommand(m_climb::pistonRelease, m_climb));
    SmartDashboard.putData("Arm Descend", new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance));
    SmartDashboard.putData("Arm Distance Two", new PivotArmDistanceTwo(m_climb, Constants.climbDistance2));
    SmartDashboard.putData("Piston Retract", new InstantCommand(m_climb::pistonRetract, m_climb));
    SmartDashboard.putData("Arm Distance Three", new PivotArmDistanceThree(m_climb, Constants.climbDistance3));
 
  //Complex Autonomous data testing
    SmartDashboard.putData("Auto Intake Prep", new AutoIntakePrep(m_drive));
    SmartDashboard.putData("Intake Parallel Command", new ParallelCommandGroup(new ExtendIntake(m_intake), new IntakeRun(m_intake)));
    SmartDashboard.putData("Auto Shoot Prep", new AutoShootPrep(m_drive));
    SmartDashboard.putData("Shooter Parallel Command",  new ParallelCommandGroup(
      new lowGoalShooterRun(RobotContainer.m_shooter),
      new OpenGateLow(RobotContainer.m_shooter)));
    SmartDashboard.putData("Auto Move Backwards", new AutoMoveBackwards(m_drive));

    configureButtonBindings();
    m_drive.setDefaultCommand(new DriveJoystick(joystickDriver, m_drive));
    m_shooter.setDefaultCommand(new ShootTrigger(joystickShooter, m_shooter));
  }

  private void configureButtonBindings() {
    visionModeButton = new JoystickButton(joystickDriver, Constants.visionModeButtonNumber);
    visionModeButton.toggleWhenPressed(new VisionMode(m_shooter)); // Switches between modes. See Shooter subsytem for
                                                                   // function.

    // shooterModeButton = new JoystickButton(joystickShooter, Constants.shooterModeButtonNumber);
    // shooterModeButton.toggleWhenPressed(new ShooterMode(m_shooter));

    findTargetButton = new JoystickButton(joystickShooter, Constants.findTargetButtonNumber); // Change this to left
                                                                                              // trigger
    findTargetButton.whenPressed(new FindTarget(m_drive));
    // Climb Button Configured
    // climbButton = new JoystickButton(joystickShooter,
    // Constants.climbButtonNumber);
    // climbButton.whileActiveOnce(new ClimbButtonSequence(m_climb));
    // climbButton.whenPressed(new InstantCommand(m_climb::pistonRelease, m_climb));
    // climbButton.whenPressed(new PivotArmDescendDistance(m_climb,
    // Constants.climbDescendDistance));
    sequence1Button = new JoystickButton(joystickShooter,
        Constants.sequence1ButtonNumber);
    sequence1Button.whenPressed(new SequentialCommandGroup(new SequentialCommandGroup(
        new PivotArmDistanceOne(m_climb, Constants.climbDistance1),
        new InstantCommand(m_climb::pistonRelease, m_climb))));
    sequence2Button = new JoystickButton(joystickShooter,
        Constants.sequence2ButtonNumber);
    sequence2Button.whenPressed(new PivotArmDescendDistance(m_climb,
        Constants.climbDescendDistance));
    sequence3Button = new JoystickButton(joystickShooter,
        Constants.sequence3ButtonNumber);
    sequence3Button.whenPressed(new SequentialCommandGroup(
        new PivotArmDistanceTwo(m_climb, Constants.climbDistance2),
        new InstantCommand(m_climb::pistonRetract, m_climb),
        new PivotArmDistanceThree(m_climb, Constants.climbDistance3),
        new InstantCommand(m_climb::pistonRelease, m_climb)));

    // addButton = new JoystickButton(joystickShooter, Constants.addButtonNumber);
    // addButton.whileActiveOnce(new IncreaseStage(m_climb));

    cancellationButton1 = new JoystickButton(joystickShooter, Constants.cancellationButton1);
    cancellationButton2 = new JoystickButton(joystickShooter, Constants.cancellationButton2);
    CancellationButtonsClimb cancellationButtons = new CancellationButtonsClimb(cancellationButton1,
        cancellationButton2);
    cancellationButtons.whenPressed(new CancelClimb(m_climb));

    // cancelStageButton = new JoystickButton(joystickShooter,
    // Constants.cancelStageButtonNumber);
    // cancelStageButton.whileActiveOnce(new CancelStage(m_climb));

    // Shooter Button Configured and Command Assigned to Button
    highSpeedShooterButton = new JoystickButton(joystickShooter, Constants.highSpeedShooterButtonNumber);
    highSpeedShooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run both the shooter and the release gate commands
        new SimpleShooterRun(m_shooter),
        new OpenGate(m_shooter))); // References the command and inside the needed subsytem

    lowSpeedShooterButton = new JoystickButton(joystickShooter, Constants.lowSpeedShooterButtonNumber);
    lowSpeedShooterButton.whileHeld(new ParallelCommandGroup(
      new lowGoalShooterRun(m_shooter),
      new OpenGateLow(m_shooter)));

    intakeButton = new JoystickButton(joystickDriver, Constants.intakeButtonNumber);
    intakeButton.whileHeld(new ParallelCommandGroup(
        new ExtendIntake(m_intake),
        new WaitCommand(.4),
        new IntakeRun(m_intake)));

    HoneClimb = new SequentialCommandGroup(
        new ClimbInitializationUp(m_climb),
        new ClimbInitializationDown(m_climb));

    honeButton = new JoystickButton(joystickDriver, 11);
    honeButton.whenPressed(new SequentialCommandGroup(
        new ClimbInitializationUp(m_climb),
        new ClimbInitializationDown(m_climb)));
  }
  // public Command getAutonomousCommand() {
  // return (Command) m_chooser.getSelected();
  // }
}