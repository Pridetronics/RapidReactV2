// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//import frc.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoIntakePrep;
import frc.robot.commands.AutoMoveBackwards;
import frc.robot.commands.AutoShootPrep;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.IntakeRun;
import frc.robot.commands.OpenGateLow;
import frc.robot.commands.lowGoalShooterRun;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    SmartDashboard.putString("Program:", "Reorgnized Code"); //This is meant to help identify the code, be sure to change the string
    //The next three lines set the default state for both pistons and the servo. Called upon initialization (when robot is enabled)
    RobotContainer.intakePiston.set(DoubleSolenoid.Value.kForward);
    RobotContainer.climbPiston.set(DoubleSolenoid.Value.kForward);
    RobotContainer.shooterServo.setRaw(1300);

    chooser.setDefaultOption("Two Ball Auto", new SequentialCommandGroup(
      new ParallelCommandGroup(
        new lowGoalShooterRun(RobotContainer.m_shooter),
        new OpenGateLow(RobotContainer.m_shooter)).withTimeout(2),
      new AutoIntakePrep(RobotContainer.m_drive),
      new ParallelCommandGroup(
        new ExtendIntake(RobotContainer.m_intake),
        new IntakeRun(RobotContainer.m_intake)).withTimeout(4),
      new AutoShootPrep(RobotContainer.m_drive),
      new WaitCommand(1),
      new ParallelCommandGroup(
        new lowGoalShooterRun(RobotContainer.m_shooter),
        new OpenGateLow(RobotContainer.m_shooter)).withTimeout(2),
      new AutoMoveBackwards(RobotContainer.m_drive)));
    
    chooser.addOption("Drive and Shoot", new SequentialCommandGroup(new ParallelCommandGroup(
      new lowGoalShooterRun(RobotContainer.m_shooter),
      new OpenGateLow(RobotContainer.m_shooter)).withTimeout(4), 
      new AutoMoveBackwards(RobotContainer.m_drive)));
      
    chooser.addOption("Drive Forward", new AutoMoveBackwards(RobotContainer.m_drive));

    SmartDashboard.putData("Auto Choices", chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and
   * test.3333
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
