// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveJoystick;
import frc.robot.commands.ReleaseGate;
import frc.robot.commands.ShooterRun;
import frc.robot.commands.ExtendRetractIntake;
import frc.robot.commands.IntakeRun;
import frc.robot.commands.LimelightDistanceFinder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;

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
  public static RelativeEncoder shooterEncoder;
  public static SparkMaxPIDController shooterMotorPID;
  public static DoubleSolenoid shooterBallRelease; // Creates Double Solenoid for the shooter (relates to pistons)

  public static Compressor intakeCompressor;
  public static Solenoid intakePiston;
  public static VictorSP intakeMotor;

  public static Intake m_intake;
  public static Shooter m_shooter; // Creates the subsytem for shooter
  public static Drive m_drive;
  public static Autonomous m_auto;

  public JoystickButton shooterButton; // Button for the shooter
  public JoystickButton intakeButton;
  public Joystick joystickDriver; // Controller 0
  public Joystick joystickShooter; // Controller 1

  private double distance;
  private double atotal;
  private double ty;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign values for the motors, and for the joysticks. Do not do button
    // bindings, this is below.
    joystickDriver = new Joystick(Constants.kJoystickDriverID);
    joystickShooter = new Joystick(Constants.kJoystickShooterID); // Sets shooter joystick to port 1

    // //DISTANCE CODE-- TESTING ONLY DO NOT PUT THIS IN PRODUCTION
    // NetworkTableInstance inst = NetworkTableInstance.getDefault(); //get a reference to the subtable called "datatable"
    // NetworkTable table = inst.getTable("limelight");
    // inst.startClientTeam(3853); // Make sure you set this to your team number
    // inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
    // NetworkTableEntry yEntry = table.getEntry("ty");
    // NetworkTableEntry xEntry = table.getEntry("tx");
    // NetworkTableEntry aEntry = table.getEntry("ta");
    // NetworkTableEntry vEntry = table.getEntry("tv");

    // ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    // double ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
    // double tx = xEntry.getDouble(0.0); 
    // double tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)

    // SmartDashboard.putNumber("Limelight Area", ta);
    // SmartDashboard.putNumber("Limelight X", tx);
    // SmartDashboard.putNumber("Limelight Y", ty);
    // SmartDashboard.putNumber("Limelight V", tv);
    // atotal = 30 + ty;
    // //distance = (104-15.3)/Math.tan(atotal); (official values, I'm gonna add testing values)
    // distance = (30-14)/Math.tan(0 + ty);
    // SmartDashboard.putNumber("Distance", distance);

    // Drive Relevant---
    frontLeft = new CANSparkMax(Constants.kFrontLeftID, MotorType.kBrushless);
    rearLeft = new CANSparkMax(Constants.kRearLeftID, MotorType.kBrushless);
    frontRight = new CANSparkMax(Constants.kFrontRightID, MotorType.kBrushless);
    frontRight.setInverted(true);
    rearRight = new CANSparkMax(Constants.kRearRightID, MotorType.kBrushless);
    rearRight.setInverted(true);
    m_drive = new Drive(joystickDriver);

    // Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterID, MotorType.kBrushless);
    shooterMotor.setInverted(true);
    shooterEncoder = shooterMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    shooterMotorPID = shooterMotor.getPIDController();
    shooterMotorPID.setP(Constants.SHOOTER_kP);
    // shooterMotorPID.setI(Constants.SHOOTER_kI);
    // shooterMotorPID.setD(Constants.SHOOTER_kD);
    shooterMotorPID.setOutputRange(Constants.SHOOTER_MIN_OUTPUT, Constants.SHOOTER_MAX_OUTPUT);
    shooterBallRelease = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kShooterGateForwardID, Constants.kShooterGateReleaseID);
    m_shooter = new Shooter(); // Defines the subsystem
    
    // Intake Relevant---
    intakeCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    intakePiston = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);
    intakeMotor = new VictorSP(Constants.kIntakeID);
    m_intake = new Intake();

    SmartDashboard.putData("Shooter Run", new ShooterRun(m_shooter)); // Puts data on Shuffleboard to use the command.
    SmartDashboard.putData("Release Gate", new ReleaseGate(m_shooter)); // Displays on the screen and can be run by pushing the square. Pretty neat
    SmartDashboard.putData("Autonomous", new Autonomous(m_drive));
    SmartDashboard.putData("Intake Run", new IntakeRun(m_intake));
    SmartDashboard.putData("Extend/Retract Intake", new ExtendRetractIntake(m_intake));
    SmartDashboard.putData("Find Distance", new LimelightDistanceFinder(m_shooter));
    SmartDashboard.putNumber("RPM", shooterEncoder.getVelocity());


    configureButtonBindings();

    m_drive.setDefaultCommand(new DriveJoystick(m_drive));
  }

  private void configureButtonBindings() {
    // Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(joystickShooter, Constants.shooterButtonNumber);
    shooterButton.whileHeld(new ParallelCommandGroup( // This is meant to run both the shooter and the release gate commands
      new ShooterRun(m_shooter),
      new WaitCommand(0.2),
      new ReleaseGate(m_shooter))); // References the command and inside the needed subsytem

    intakeButton = new JoystickButton(joystickShooter, Constants.intakeButtonNumber);
    intakeButton.whileHeld(new ParallelCommandGroup(
        new ExtendRetractIntake(m_intake),
        new WaitCommand(3),
        new IntakeRun(m_intake)));
  }

  public Command getAutonomousCommand() {
    return m_auto;
  }
}
