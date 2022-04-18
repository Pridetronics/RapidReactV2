// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//General Command Imports--
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

//Drive Command Imports--
import frc.robot.commands.DriveJoystick;

//Climb Command Imports--
import frc.robot.commands.ClimbInitializationDown;
import frc.robot.commands.ClimbInitializationUp;
import frc.robot.commands.EncoderPivotArmDescend;
import frc.robot.commands.EncoderPivotArmDistanceOne;
import frc.robot.commands.EncoderPivotArmDistanceTwo;
import frc.robot.commands.EncoderPivotArmDistanceThree;

import frc.robot.commands.PivotArmDescendDistance;
import frc.robot.commands.PivotArmDistanceOne;
import frc.robot.commands.PivotArmDistanceTwo;
import frc.robot.commands.RunOpenGate;
import frc.robot.commands.RunShooter;
import frc.robot.commands.PivotArmDistanceThree;

//Shooter Command Imports--
import frc.robot.commands.OpenGateHigh;
import frc.robot.commands.OpenGateLow;
import frc.robot.commands.HighGoalShooterRun;
import frc.robot.commands.lowGoalShooterRun;
import frc.robot.commands.ShootTrigger;
import frc.robot.commands.ShooterMode;
import frc.robot.commands.RunShooter;
import frc.robot.commands.RunOpenGate;

//Intake Command Imports--
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.IntakeRun;

//Vision Command Imports--
import frc.robot.commands.VisionMode;
import frc.robot.commands.FindDistanceLimelight;
import frc.robot.commands.AlignTarget;
import frc.robot.commands.AutomaticShooterRun;
import frc.robot.commands.AutomaticOpenGate;

//Subsytem Imports--
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;

//Hardware Imports--
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;

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

  // Camera--
  public CameraServer server1;

  // Drive--
  public static CANSparkMax frontLeftMotor; // Creates all four drive motors
  public static CANSparkMax rearLeftMotor;
  public static CANSparkMax frontRightMotor;
  public static CANSparkMax rearRightMotor;

  // Shooter--
  public static CANSparkMax shooterMotor; // Creates Motor for the shooter
  public static RelativeEncoder shooterEncoder;
  public static SparkMaxPIDController shooterMotorPID;
  public static Servo shooterServo;

  // Intake--
  public static VictorSP intakeMotor;
  public static DoubleSolenoid intakePiston;

  //Climb--
  public static CANSparkMax climbMotor; // Climb Motor
  public static DoubleSolenoid climbPiston; // Climb Piston
  public static SparkMaxPIDController climbPID;
  public static RelativeEncoder climbEncoder;
  public static DigitalInput lowerClimbLimitSwitch;
  
  // Subsystems--
  public static Drive m_drive;
  public static Climb m_climb;
  public static Shooter m_shooter; // Creates the subsytem for shooter
  public static Intake m_intake;
  public static Vision m_vision;

  //Climb Buttons--
  public JoystickButton honeButton;

  public JoystickButton sequence1Button;
  public JoystickButton sequence2Button;
  public JoystickButton sequence3Button;

  public JoystickButton encoderSequence1Button;
  public JoystickButton encoderSequence2Button;
  public JoystickButton encoderSequence3Button;

  //Shooter Buttons--
  public JoystickButton highSpeedShooterButton; // Button for the shooter
  public JoystickButton lowSpeedShooterButton;
  public JoystickButton automaticShooterButton;
  public JoystickButton shooterModeButton;
  public JoystickButton runShooterButton;

  //Intake Buttons--
  public JoystickButton intakeButton;

  //Vision Buttons--
  public JoystickButton visionModeButton;
  public JoystickButton findTargetButton;

  //Joysticks
  public Joystick joystickDriver; // Controller 0 --Ensure that all controllers are in proper ports in DriverStation
  public Joystick joystickShooter; // Controller 1

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign values for the motors, and for the joysticks. Do not do button
    // bindings, this is below.
    // Camera Relevant--
    CameraServer.startAutomaticCapture(); //ADD A WARNING NOTE
    
    joystickDriver = new Joystick(Constants.kJoystickDriverID);
    joystickShooter = new Joystick(Constants.kJoystickShooterID); 

    // Drive Relevant---
    frontLeftMotor = new CANSparkMax(Constants.kFrontLeftMotorCANID, MotorType.kBrushless);
    frontLeftMotor.setInverted(false);

    rearLeftMotor = new CANSparkMax(Constants.kRearLeftMotorCANID, MotorType.kBrushless);
    rearLeftMotor.setInverted(false);

    frontRightMotor = new CANSparkMax(Constants.kFrontRightMotorCANID, MotorType.kBrushless);
    frontRightMotor.setInverted(true);

    rearRightMotor = new CANSparkMax(Constants.kRearRightMotorCANID, MotorType.kBrushless);
    rearRightMotor.setInverted(true);

    // Climb Relevant--
    climbMotor = new CANSparkMax(Constants.kClimbCANID, MotorType.kBrushless);
    climbMotor.setInverted(true);
    climbEncoder = climbMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.kEncoderCountsPerRev);
    climbPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kPistonForwardClimbChannel, Constants.kPistonReverseClimbChannel);
    lowerClimbLimitSwitch = new DigitalInput(Constants.lowerClimbLimitSwitchChannel);
    climbPID = climbMotor.getPIDController();
    climbPID.setP(Constants.CLIMB_kP);
    climbPID.setI(Constants.CLIMB_kI);
    climbPID.setD(Constants.CLIMB_kD);
    climbPID.setOutputRange(Constants.CLIMB_MIN_OUTPUT, Constants.CLIMB_MAX_OUTPUT);

    //Shooter Relevant--
    shooterMotor = new CANSparkMax(Constants.kShooterCANID, MotorType.kBrushless);
    shooterMotor.setInverted(false);
    shooterEncoder = shooterMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    shooterServo = new Servo(Constants.kShooterServoPWMID);

    shooterMotorPID = shooterMotor.getPIDController();
    shooterMotorPID.setP(Constants.SHOOTER_kP);
    shooterMotorPID.setI(Constants.SHOOTER_kI);
    shooterMotorPID.setD(Constants.SHOOTER_kD);
    shooterMotorPID.setOutputRange(Constants.SHOOTER_MIN_OUTPUT, Constants.SHOOTER_MAX_OUTPUT);
  
    //Intake Relevant--
    intakeMotor = new VictorSP(Constants.kIntakePWMID);
    intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kIntakePistonForwardChannel, Constants.kIntakePistonReverseChannel);
    
    //Subsytems--
    m_drive = new Drive(joystickDriver);
    m_climb = new Climb(); // Defines the subsystem
    m_shooter = new Shooter(); // Defines the subsystem
    m_intake = new Intake();
    m_vision = new Vision();

    // SmartDashboard Relevant-- Remove these during competition time
    // Puts data on Shuffleboard to use the command.
    SmartDashboard.putData("Extend/Retract Intake", new ExtendIntake(m_intake));
    SmartDashboard.putData("Change Vision Modes", new VisionMode(m_vision));

    SmartDashboard.putData("Arm Distance One", new PivotArmDistanceOne(m_climb, Constants.climbDistance1));
    SmartDashboard.putData("Piston Extend", new InstantCommand(m_climb::pistonRelease, m_climb));
    SmartDashboard.putData("Arm Descend", new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance));
    SmartDashboard.putData("Arm Distance Two", new PivotArmDistanceTwo(m_climb, Constants.climbDistance2));
    SmartDashboard.putData("Piston Retract", new InstantCommand(m_climb::pistonRetract, m_climb));
    SmartDashboard.putData("Arm Distance Three", new PivotArmDistanceThree(m_climb, Constants.climbDistance3));

    configureButtonBindings();
    m_drive.setDefaultCommand(new DriveJoystick(joystickDriver, m_drive));
    m_shooter.setDefaultCommand(new ShootTrigger(joystickShooter, m_shooter));
  }

  private void configureButtonBindings() {
    //Climb Commands--
    //THE COMMENTED OUT CHUNK WAS THE OLD CLIMB SEQUENCE THAT UTILIZED PID CONTROL.
    
    // Climb Button Configured
    // climbButton = new JoystickButton(joystickShooter,
    // Constants.climbButtonNumber);
    // climbButton.whileActiveOnce(new ClimbButtonSequence(m_climb));
    // climbButton.whenPressed(new InstantCommand(m_climb::pistonRelease, m_climb));
    // climbButton.whenPressed(new PivotArmDescendDistance(m_climb,
    // Constants.climbDescendDistance));
    // sequence1Button = new JoystickButton(joystickShooter,
    //     Constants.sequence1ButtonNumber);
    // sequence1Button.whenPressed(new SequentialCommandGroup(new SequentialCommandGroup(
    //     new PivotArmDistanceOne(m_climb, Constants.climbDistance1),
    //     new InstantCommand(m_climb::pistonRelease, m_climb))));
    // sequence2Button = new JoystickButton(joystickShooter,
    //     Constants.sequence2ButtonNumber);
    // sequence2Button.whenPressed(new PivotArmDescendDistance(m_climb,
    //     Constants.climbDescendDistance));
    // sequence3Button = new JoystickButton(joystickShooter,
    //     Constants.sequence3ButtonNumber);
    // sequence3Button.whenPressed(new SequentialCommandGroup(
    //     new PivotArmDistanceTwo(m_climb, Constants.climbDistance2),
    //     new InstantCommand(m_climb::pistonRetract, m_climb),
    //     new PivotArmDistanceThree(m_climb, Constants.climbDistance3),
    //     new InstantCommand(m_climb::pistonRelease, m_climb)));

    //Allows drivers to zero encoders before starting the climb sequence
    //Command format == Create button, then when something is done have commands (Below is a good example)
    honeButton = new JoystickButton(joystickDriver, 11);
    honeButton.whenPressed(new SequentialCommandGroup(
        new ClimbInitializationUp(m_climb),
        new ClimbInitializationDown(m_climb)));

    //Climb sequence using encoders. This is a bit lengthy, but covers all three sequences. 
    encoderSequence1Button = new JoystickButton(joystickShooter, Constants.sequence1ButtonNumber);
    encoderSequence1Button.whenPressed(new SequentialCommandGroup(    
      new EncoderPivotArmDistanceOne(m_climb), //raises arms x distance
      new InstantCommand(m_climb::pistonRelease, m_climb))); //releases piston
    encoderSequence2Button = new JoystickButton(joystickShooter, Constants.sequence2ButtonNumber);
    encoderSequence2Button.whenPressed(new EncoderPivotArmDescend(m_climb)); //lowers arms
    encoderSequence3Button = new JoystickButton(joystickShooter, Constants.sequence3ButtonNumber);
    encoderSequence3Button.whenPressed(new SequentialCommandGroup(
      new EncoderPivotArmDistanceTwo(m_climb), //raises arms y distance
      new InstantCommand(m_climb::pistonRetract, m_climb), //retracts piston
      new EncoderPivotArmDistanceThree(m_climb), //raises arms z distance
      new InstantCommand(m_climb::pistonRelease, m_climb))); //retracts piston
      //WE SHOULD MAKE A NOTE ON HOW THE SEQUENCE RUNS (1,2,3,2,3 or whatever)

    //Shooter Commands--
    highSpeedShooterButton = new JoystickButton(joystickShooter, Constants.highSpeedShooterButtonNumber);
    highSpeedShooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run both the shooter and the release gate commands
        new HighGoalShooterRun(m_shooter), //shooter at 5000 RPM
        new OpenGateHigh(m_shooter))); // References the command and inside the needed subsytem
    lowSpeedShooterButton = new JoystickButton(joystickShooter, Constants.lowSpeedShooterButtonNumber);
    lowSpeedShooterButton.whileHeld(new ParallelCommandGroup(
      new lowGoalShooterRun(m_shooter), //shooter at 2500 RPM
      new OpenGateLow(m_shooter)));
    // automaticShooterButton = new JoystickButton(joystickShooter, Constants.automaticShooterButtonNumber);
    // automaticShooterButton.whileHeld(new ParallelCommandGroup(
    //   new AutomaticShooterRun(m_vision), //Motor at calculated distance
    //   new AutomaticOpenGate(m_vision)));
    // runShooterButton = new JoystickButton(joystickShooter, Constants.runShooterButtonNumber);
    // runShooterButton.whileHeld(new ParallelCommandGroup(
    //   new RunShooter(m_shooter, m_vision), //Relies on shooter mode (may be any of the three above)
    //   new RunOpenGate(m_shooter, m_vision)));
    // shooterModeButton = new JoystickButton(joystickShooter, Constants.shooterModeButtonNumber);
    // shooterModeButton.toggleWhenPressed(new ShooterMode(m_shooter)); //Commented out for my safety

    //Intake Commands--
    intakeButton = new JoystickButton(joystickDriver, Constants.intakeButtonNumber);
    intakeButton.whileHeld(new ParallelCommandGroup(
        new ExtendIntake(m_intake),
        new WaitCommand(.4),
        new IntakeRun(m_intake)));

    //Vision Commands--
    visionModeButton = new JoystickButton(joystickDriver, Constants.visionModeButtonNumber);
    visionModeButton.toggleWhenPressed(new VisionMode(m_vision)); // Switches between modes. See Shooter subsytem for function.

    findTargetButton = new JoystickButton(joystickShooter, Constants.findTargetButtonNumber); 
    findTargetButton.whenPressed(new AlignTarget(m_vision)); //Seeks and aligns
  }
}