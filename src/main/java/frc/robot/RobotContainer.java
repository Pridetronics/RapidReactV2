// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import frc.robot.Constants;
// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveJoystick;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.robot.commands.Autonomous;
import frc.robot.commands.ReleaseGate;
import frc.robot.commands.ShooterRun;
import frc.robot.commands.addCommand;
import frc.robot.commands.testStage;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.IntakeRun;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

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

  public static CANSparkMax frontLeft;
  public static CANSparkMax rearLeft;
  public static CANSparkMax frontRight;
  public static CANSparkMax rearRight;

  public static CANSparkMax shooterMotor; // Creates Motor for the shooter
  public static DoubleSolenoid shooterBallRelease; // Creates Double Solenoid for the shooter (relates to pistons)

  public static Compressor intakeCompressor;
  public static DoubleSolenoid intakePiston;
  public static Talon intakeMotor;

  public static Intake intake;
  public static Shooter shooter; // Creates the subsytem for shooter
  public static Drive m_drive;
  public static Autonomous m_auto;
  public static SendableChooser m_chooser;
  public static Climb m_climb;

  public JoystickButton shooterButton; // Button for the shooter
  public JoystickButton intakeButton;
  public Joystick joystickDriver; // Controller 0
  public Joystick joystickShooter; // Controller 1

  // Climb--
  public static CANSparkMax climbMotor; // Climb Motor
  public static DoubleSolenoid m_climbPiston; // Climb Piston
  public static RelativeEncoder ClimbEncoder;
  public static DigitalInput upperClimbLimitSwitch;
  public static DigitalInput lowerClimbLimitSwitch;
  public static SparkMaxPIDController climbMotorPID;
  public JoystickButton addButton;
  public JoystickButton sequenceButton;
  public int stageLevel;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign values for the motors, and for the joysticks. Do not do button
    // bindings, this is below.
    joystickDriver = new Joystick(Constants.kJoystickDriverChannel);
    joystickShooter = new Joystick(Constants.kJoystickShooterChannel); // Sets shooter joystick to port 1

    // Drive Relevant---
    frontLeft = new CANSparkMax(Constants.kFrontLeftChannel, MotorType.kBrushless);
    rearLeft = new CANSparkMax(Constants.kRearLeftChannel, MotorType.kBrushless);
    frontRight = new CANSparkMax(Constants.kFrontRightChannel, MotorType.kBrushless);
    frontRight.setInverted(true);
    rearRight = new CANSparkMax(Constants.kRearRightChannel, MotorType.kBrushless);
    rearRight.setInverted(true);
    m_drive = new Drive(joystickDriver);

    // Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterChannel, MotorType.kBrushless);
    shooterMotor.setInverted(true);
    shooter = new Shooter(); // Defines the subsystem
    shooterBallRelease = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kShooterGateForwardChannel,
        Constants.kShooterGateReleaseChannel);

    intakeCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kIntakePistonForwardChannel,
        Constants.kIntakePistonForwardChannel);
    intakeMotor = new Talon(Constants.kIntakeChannel);
    intake = new Intake();

    // Climb Relevant--
    m_climbPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kPistonFirstClimbChannel,
        Constants.kPistonFirstReverseClimbChannel);
    climbMotor = new CANSparkMax(Constants.kClimbCANID, MotorType.kBrushless);
    upperClimbLimitSwitch = new DigitalInput(Constants.upperClimbLimitSwitchChannel);
    lowerClimbLimitSwitch = new DigitalInput(Constants.lowerClimbLimitSwitchChannel);
    ClimbEncoder = climbMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.kEncoderCountsPerRev);
    climbMotorPID = climbMotor.getPIDController();
    climbMotorPID.setP(Constants.CLIMB_kP);
    climbMotorPID.setI(Constants.CLIMB_kI);
    climbMotorPID.setD(Constants.CLIMB_kD);

    m_climb = new Climb(); // Defines the subsystem

    // Command m_taxi = new Autonomous(m_drive);
    // Command m_testCommand = new IntakeRun(intake);

    // SendableChooser<Command> m_chooser = new SendableChooser<>();

    // m_chooser.setDefaultOption("Taxi Autonomous", m_taxi);
    // m_chooser.addOption("Test Command", m_testCommand);

    // // Put the chooser on the dashboard
    // SmartDashboard.putData(m_chooser);

    SmartDashboard.putData("Shooter Run", new ShooterRun(shooter)); // Puts data on Shuffleboard to use the command.
                                                                    // Displays
    SmartDashboard.putData("Release Gate", new ReleaseGate(shooter)); // on the screen and can be run by pushing the
                                                                      // square. Pretty neat
    SmartDashboard.putData("Autonomous", new Autonomous(m_drive));
    SmartDashboard.putData("Intake Run", new IntakeRun(intake));
    SmartDashboard.putData("Extend Intake", new ExtendIntake(intake));

    configureButtonBindings();

    m_drive.setDefaultCommand(new DriveJoystick(joystickDriver, m_drive));

  }

  private void configureButtonBindings() {
    // Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(joystickShooter, Constants.shooterButtonNumber);
    shooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run both the shooter and the release gate
                                                      // commands
        new ReleaseGate(shooter),
        new ShooterRun(shooter))); // References the command and inside the needed subsytem

    intakeButton = new JoystickButton(joystickShooter, Constants.intakeButtonNumber);
    intakeButton.whileHeld(new SequentialCommandGroup(
        new ExtendIntake(intake),
        new IntakeRun(intake)));

    addButton = new JoystickButton(joystickShooter, 8);
    addButton.whileActiveOnce(new addCommand(m_climb));
    sequenceButton = new JoystickButton(joystickShooter, 7);
    sequenceButton.whileActiveOnce(new testStage(m_climb));
  }

  public Command getAutonomousCommand() {
    return (Command) m_chooser.getSelected();
  }
}
