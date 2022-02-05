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

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

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
  
  public static CANSparkMax shooterMotor; //Creates Motor for the shooter
  public static DoubleSolenoid shooterBallRelease; //Creates Double Solenoid for the shooter (relates to pistons)
  
  public static Shooter shooter; //Creates the subsytem  for shooter

  public JoystickButton shooterButton; //Button for the shooter
  public Joystick joystickShooter; //Controller 1

  public JoystickButton limelightTrackingButton; // Button to activate limelight thingy
  public Joystick joystickDrive; // Controller 0
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() 
  {
    //Assign values for the motors, and for the joysticks. Do not do button bindings, this is below. 
    joystickShooter = new Joystick(Constants.kJoystickShooterChannel); //Sets shooter joystick to port 1
    joystickDrive = new Joystick(Constants.kJoystickDriveChannel); // sets drive joystick to port 0
 //Shooter Relevant---
    shooterMotor = new CANSparkMax(Constants.kShooterChannel, MotorType.kBrushless); 
    shooterMotor.setInverted(true);
    shooter = new Shooter(); //Defines the subsystem
    shooterBallRelease = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kShooterGateForwardChannel, Constants.kShooterGateReleaseChannel);
    
    SmartDashboard.putData("Shooter Run", new ShooterRun(shooter)); //Puts data on Shuffleboard to use the command. Displays
    SmartDashboard.putData("Release Gate", new ReleaseGate(shooter)); //on the screen and can be run by pushing the square. Pretty neat

    configureButtonBindings();
  }

  private void configureButtonBindings() 
  {

    //Shooter Button Configured and Command Assigned to Button
    shooterButton = new JoystickButton(joystickShooter, Constants.shooterButtonNumber); 
    shooterButton.whileHeld(new ParallelCommandGroup( //This is meant to run both the shooter and the release gate commands
      new ShooterRun(shooter),
      new ReleaseGate(shooter))); //References the command and inside the needed subsytem
    

      //Limelight Button Configured and Command Assigned to Button
      limelightTrackingButton = new JoystickButton(joystickDrive, Constants.kJoystickDriveButtonNumber);

  }
  public class Robot extends TimedRobot {

    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
    
    
  
    
  
    private boolean m_LimelightHasValidTarget = false;
    private double m_LimelightDriveCommand = 0.0;
    private double m_LimelightSteerCommand = 0.0;
  
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
          m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
          m_chooser.addOption("My Auto", kCustomAuto);
          SmartDashboard.putData("Auto choices", m_chooser);
    }
  
    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
    }
  
    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
          m_autoSelected = m_chooser.getSelected();
    }
  
    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }
  
    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
  
          Update_Limelight_Tracking();
  
          double steer = Constants.kRearLeftChannel; 
          double drive = Constants.kRearRightChannel;
          boolean auto = 


          steer *= 0.70;
          drive *= 0.70;
  
          if (auto)
          {
            if (m_LimelightHasValidTarget)
            {
                  m_Drive.arcadeDrive(m_LimelightDriveCommand,m_LimelightSteerCommand);
            }
            else
            {
                  m_Drive.arcadeDrive(0.0,0.0);
            }
          }
          else
          {
            m_Drive.arcadeDrive(drive,steer);
          }
    }
  
    @Override
    public void testPeriodic() {
    }
  
    /**
     * This function implements a simple method of generating driving and steering commands
     * based on the tracking data from a limelight camera.
     */
    public void Update_Limelight_Tracking()
    {
          // These numbers must be tuned for your Robot!  Be careful!
          final double STEER_K = 0.03;                    // how hard to turn toward the target
          final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
          final double DESIRED_TARGET_AREA = 13.0;        // Area of the target when the robot reaches the wall
          final double MAX_DRIVE = 0.7;                   // Simple speed limit so we don't drive too fast
  
          double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
          double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
          double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
          double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  
          if (tv < 1.0)
          {
            m_LimelightHasValidTarget = false;
            m_LimelightDriveCommand = 0.0;
            m_LimelightSteerCommand = 0.0;
            return;
          }
  
          m_LimelightHasValidTarget = true;
  
          // Start with proportional steering
          double steer_cmd = tx * STEER_K;
          m_LimelightSteerCommand = steer_cmd;
  
          // try to drive forward until the target area reaches our desired area
          double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;
  
          // don't let the robot drive too fast into the goal
          if (drive_cmd > MAX_DRIVE)
          {
            drive_cmd = MAX_DRIVE;
          }
          m_LimelightDriveCommand = drive_cmd;

    }
  }
  public Command getAutonomousCommand() 
  {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
