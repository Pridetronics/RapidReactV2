// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//import frc.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoDriveForwards;
import frc.robot.commands.AutoDriveShoot;
import frc.robot.commands.AutoMoveBackwards;
import frc.robot.commands.ClimbInitializationDown;
import frc.robot.commands.ClimbInitializationUp;
import frc.robot.commands.HoningCommand;
import frc.robot.commands.IntakeRun;
import frc.robot.commands.SimpleShooterRun;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.Climb;
// import edu.wpi.first.wpilibj2.command.button.*;
// import edu.wpi.first.wpilibj2.command.button.Button;
// import edu.wpi.first.wpilibj.Joystick;

// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
// import edu.wpi.first.wpilibj.drive.MecanumDrive;
// import edu.wpi.first.wpilibj.PowerDistribution;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private Command m_taxi;
  private Command m_testCommand;
  private Command m_honeCommand;
  private RobotContainer m_robotContainer;
  // private SendableChooser chooser;
  SendableChooser<Command> chooser = new SendableChooser<Command>();

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    m_honeCommand = RobotContainer.HoneClimb;
    SmartDashboard.putString("Program:", "Testing 03/07/22");
    RobotContainer.intakePiston.set(DoubleSolenoid.Value.kForward);
    RobotContainer.climbPiston.set(DoubleSolenoid.Value.kForward);
    RobotContainer.shooterServo.setRaw(1300);

    chooser.setDefaultOption("Drive Forward", new AutoMoveBackwards(RobotContainer.m_drive));
    chooser.addOption("HoneClimb",new HoningCommand(RobotContainer.m_climb, RobotContainer.m_shooter));
    chooser.addOption("Drive and Shoot", new SequentialCommandGroup(
      new SimpleShooterRun(RobotContainer.m_shooter).withTimeout(4), 
      new AutoMoveBackwards(RobotContainer.m_drive)));

    SmartDashboard.putData("Auto Choices", chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and
   * test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = chooser.getSelected();

    // schedule the autonomous command (example)
    if (chooser.getSelected() != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
