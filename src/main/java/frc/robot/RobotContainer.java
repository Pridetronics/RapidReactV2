// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//General Command Imports--
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

//import edu.wpi.first.cameraserver.CameraServer;

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
  //public static CameraServer server1; //yucky

  // Drive--
  public static CANSparkMax frontLeftMotor; // Creates all four drive motors
  public static CANSparkMax rearLeftMotor;
  public static CANSparkMax frontRightMotor;
  public static CANSparkMax rearRightMotor;

  // Shooter--
  public static CANSparkMax shooterMotorLeft; // Creates Motor for the shooter
  public static CANSparkMax shooterMotorRight; //Looking at the robot from behind to determine the place
  public static RelativeEncoder shooterEncoder; //Creates encoder for the shooter
  public static SparkMaxPIDController shooterMotorPID; //Creates PID for the shooter
  public static Servo shooterServo; //Creates servo for the shooter gate

  // Intake--
  public static VictorSP intakeMotor; //Creates motor for intake
  public static DoubleSolenoid intakePiston; //Creates motor for piston

  //Climb--
  public static CANSparkMax climbMotor; // Climb Motor
  public static DoubleSolenoid climbPiston; // Climb Piston
  public static SparkMaxPIDController climbPID; //Climb PID
  public static RelativeEncoder climbEncoder; //Climb PID
  public static DigitalInput lowerClimbLimitSwitch; //Climb Limit Switch (physical hardware)
  
  // Subsystems-- Created for use in RobotContainer
  public static Drive m_drive; 
  public static Climb m_climb;
  public static Shooter m_shooter; 
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
    /**
     * This will allow the camera data to be displayed on SmartDashboard or shuffleboard
     * but this needs to be adjusted because at comp this took up too much bandwith and 
     * therefore caused drive issues. 
     */
    //CameraServer.startAutomaticCapture(); 
    
    /** 
     * These two lines of code create the joystick objects that will be used throughout the life
     * of blah blah whatever --Damon
    */
    joystickDriver = new Joystick(Constants.kJoystickDriverID); //Stick --These can change, but this was used in 2022
    joystickShooter = new Joystick(Constants.kJoystickShooterID); //Gamepad

    // Drive Relevant---
    frontLeftMotor = new CANSparkMax(Constants.kFrontLeftMotorCANID, MotorType.kBrushless);
    frontLeftMotor.setInverted(false);

    rearLeftMotor = new CANSparkMax(Constants.kRearLeftMotorCANID, MotorType.kBrushless);
    rearLeftMotor.setInverted(false);

    frontRightMotor = new CANSparkMax(Constants.kFrontRightMotorCANID, MotorType.kBrushless);
    frontRightMotor.setInverted(true); //With Mecanum drive right motors are inverted

    rearRightMotor = new CANSparkMax(Constants.kRearRightMotorCANID, MotorType.kBrushless);
    rearRightMotor.setInverted(true);

    // Climb Relevant--
    climbMotor = new CANSparkMax(Constants.kClimbCANID, MotorType.kBrushless);
    climbMotor.setInverted(true);

    climbEncoder = climbMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.kEncoderCountsPerRev);
    
    climbPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kPistonForwardClimbChannel, Constants.kPistonReverseClimbChannel);
   
    lowerClimbLimitSwitch = new DigitalInput(Constants.lowerClimbLimitSwitchChannel);
    
    climbPID = climbMotor.getPIDController();
    climbPID.setP(Constants.CLIMB_kP); //these numbers are finnicky but they worked
    climbPID.setI(Constants.CLIMB_kI); //TUNE BETTER
    climbPID.setD(Constants.CLIMB_kD); //I am sad
    climbPID.setOutputRange(Constants.CLIMB_MIN_OUTPUT, Constants.CLIMB_MAX_OUTPUT);

    //Shooter Relevant--
    shooterMotorLeft = new CANSparkMax(Constants.kShooterLeftCANID, MotorType.kBrushless);
    shooterMotorLeft.setInverted(true);

    shooterMotorRight = new CANSparkMax(Constants.kShooterRightCANID, MotorType.kBrushless);
    shooterMotorRight.setInverted(false);

    shooterEncoder = shooterMotorLeft.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

    shooterServo = new Servo(Constants.kShooterServoPWMID); //Referred to in "Gate" commands

    shooterMotorPID = shooterMotorLeft.getPIDController();
    shooterMotorPID.setP(Constants.SHOOTER_kP); //Shooter values from 2020...
    shooterMotorPID.setI(Constants.SHOOTER_kI);
    shooterMotorPID.setD(Constants.SHOOTER_kD);
    shooterMotorPID.setOutputRange(Constants.SHOOTER_MIN_OUTPUT, Constants.SHOOTER_MAX_OUTPUT);

    //Intake Relevant--
    intakeMotor = new VictorSP(Constants.kIntakePWMID);
    intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kIntakePistonForwardChannel, Constants.kIntakePistonReverseChannel);
    
    //Subsytems--
    m_drive = new Drive(joystickDriver); //Defines the Subsystem-- allows them to be used and referenced as objects within commands
    m_climb = new Climb();
    m_shooter = new Shooter(); 
    m_intake = new Intake();
    m_vision = new Vision();

    // SmartDashboard Relevant-- Encouraged to be commented out during competition time
    // Puts data on Shuffleboard to use the command.

    /**
     * The commands that are displayed on SmartDashboard are meant to be useful in a testing scenario. These are most convenient for
     * us. Toggling vision (so the Limelight doesn't blind you when you need to test other systems), Extending Intake (for running climb),
     * and the PID version of climb are all helpful to have when testing. We usually like to run climb to ensure it's efficiency. It could
     * easily be switched out for the encoder version and at some point it may be helpful to have present. But because of the testing nature,
     * commenting them out during competition is more helpful as to avoid clutter this creates.
     * Tim suggests learning how to actually use testing mode, but this works pretty well in a pinch. 
     */
    SmartDashboard.putData("Extend/Retract Intake", new ExtendIntake(m_intake));
    SmartDashboard.putData("Change Vision Modes", new VisionMode(m_vision));

    SmartDashboard.putData("Arm Distance One", new PivotArmDistanceOne(m_climb, Constants.climbDistance1));
    SmartDashboard.putData("Piston Extend", new InstantCommand(m_climb::pistonRetract, m_climb));
    SmartDashboard.putData("Arm Descend", new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance));
    SmartDashboard.putData("Arm Distance Two", new PivotArmDistanceTwo(m_climb, Constants.climbDistance2));
    SmartDashboard.putData("Piston Retract", new InstantCommand(m_climb::pistonRetract, m_climb));
    SmartDashboard.putData("Arm Distance Three", new PivotArmDistanceThree(m_climb, Constants.climbDistance3));

    configureButtonBindings();
    m_drive.setDefaultCommand(new DriveJoystick(joystickDriver, m_drive));
    /** 
     * Shooter trigger is a little tricky and something we created without knowing if it would work (Test Code), because
     * the gamepad triggers are seen as axes, and working with them is a bit more complex then with a normal button. I'd 
     * encourage anyone to try figuring this out. 
    */
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
      new InstantCommand(m_climb::pistonRetract, m_climb))); //releases piston
    encoderSequence2Button = new JoystickButton(joystickShooter, Constants.sequence2ButtonNumber);
    encoderSequence2Button.whenPressed(new EncoderPivotArmDescend(m_climb)); //lowers arms
    encoderSequence3Button = new JoystickButton(joystickShooter, Constants.sequence3ButtonNumber);
    encoderSequence3Button.whenPressed(new SequentialCommandGroup(
      new EncoderPivotArmDistanceTwo(m_climb), //raises arms y distance
      new InstantCommand(m_climb::pistonRelease, m_climb), //retracts piston
      new EncoderPivotArmDistanceThree(m_climb), //raises arms z distance
      new InstantCommand(m_climb::pistonRetract, m_climb))); //retracts piston
      /**
       * This code runs in the following order and allows us to transverse 
       * - Stage One (Button B): Climb arms raise 154 (in encoder-- see note in Constants) and piston is 
       *  retracted (which is the default position-- so it may not move)
       * - Stage Two (Button A): Arms are lowered, pulling the robot onto the rungs
       * - Stage Three (Button X): Arms are raised 40, the piston released, Arms are raised to 140, and the piston is retracted. 
       * - Stage Two (Button A): Repeats
       * - Stage Three (Button X): Repeats
       * - Stage Two (Button A): Repeats
       */


    //Shooter Commands--
    highSpeedShooterButton = new JoystickButton(joystickShooter, Constants.highSpeedShooterButtonNumber);
    highSpeedShooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run both the shooter and the release gate commands
        new HighGoalShooterRun(m_shooter), //shooter at 5000 RPM
        new OpenGateHigh(m_shooter))); // References the command and inside the needed subsytem
    lowSpeedShooterButton = new JoystickButton(joystickShooter, Constants.lowSpeedShooterButtonNumber);
    lowSpeedShooterButton.whileHeld(new ParallelCommandGroup( //Talk about this because Tim said so 25 ms
    //Test Code: If it ain't broke don't fix it (TRy with whenPRessed)
      new lowGoalShooterRun(m_shooter), //shooter at 2500 RPM
      new OpenGateLow(m_shooter)));
    //MAke notes on when pressed, when released (in documentation talk about the semantics of button pressing)

    // //WHY IS BROKEN??
    // automaticShooterButton = new JoystickButton(joystickShooter, Constants.automaticShooterButtonNumber);
    // automaticShooterButton.whileHeld(new ParallelCommandGroup(
    //   new InstantCommand(m_vision :: findDistance, m_vision),
    //   new AutomaticShooterRun(m_vision), //Motor at calculated distance
    //   new AutomaticOpenGate(m_vision)));
    // runShooterButton = new JoystickButton(joystickShooter, Constants.runShooterButtonNumber);
    // runShooterButton.whileHeld(new ParallelCommandGroup(
    //   new RunShooter(m_shooter, m_vision), //Relies on shooter mode (may be any of the three above)
    //   new RunOpenGate(m_shooter, m_vision)));
    shooterModeButton = new JoystickButton(joystickShooter, Constants.shooterModeButtonNumber);
    shooterModeButton.whileHeld(new ShooterMode(m_shooter)); //Commented out for my safety

    //Intake Commands--
    intakeButton = new JoystickButton(joystickDriver, Constants.intakeButtonNumber);
    intakeButton.whileHeld(new ParallelCommandGroup(
        new ExtendIntake(m_intake),
        new IntakeRun(m_intake)));

    //Vision Commands--
    visionModeButton = new JoystickButton(joystickDriver, Constants.visionModeButtonNumber);
    visionModeButton.toggleWhenPressed(new VisionMode(m_vision)); // Switches between modes. See Vision subsytem for function.

    findTargetButton = new JoystickButton(joystickShooter, Constants.findTargetButtonNumber); 
    findTargetButton.toggleWhenPressed(new AlignTarget(m_vision)); //Seeks and aligns //Test Code: TEST THIS
  }
}