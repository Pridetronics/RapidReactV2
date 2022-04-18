// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * This is key for understanding the structure of the robot. Not much is added
 * to this class (typically only working with robotInit and autonomous stuff here)
 * For information on how the program runs, see our documentation. 
 */
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.robot.commands.AutoIntakePrep;
import frc.robot.commands.AutoMoveForwards;
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
  SendableChooser<Command> chooser = new SendableChooser<Command>(); //Creates sendable chooser (allows us to choose autonomous programs)

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    /* 
    * Instantiate RobotContainer. This will allow access to hardware and subsystems. BUT the only commands that can be run
    * are those called in autonomous
    */
    m_robotContainer = new RobotContainer();
    SmartDashboard.putString("Program:", "Reorgnized Code"); //This is meant to help identify the code, be sure to change the string
    //The next three lines set the default state for both pistons and the servo. Called upon initialization (when robot is enabled)
    RobotContainer.intakePiston.set(DoubleSolenoid.Value.kForward);
    RobotContainer.climbPiston.set(DoubleSolenoid.Value.kForward);
    RobotContainer.shooterServo.setRaw(1300);

    /*
    * Fully complex autonomous. This shoots the preloaded ball (shooter runs for 2 seconds) [stage 1], 
    * leaves the tarmac [stage 2] runs the intake for four seconds (grabbing the ball) [stage 3]. Then reenters
    * the tarmac, waits one second [stage 4], and then shoots the ball within two seconds [stage 5]. Then quickly
    * leaves the tarmac for the autonomous points (leaving tarmac + balls being shot) [stage 6 and fin]
    */
    chooser.setDefaultOption("Two Ball Auto", new SequentialCommandGroup(
      //This first group runs the shooter system (Stage 1)
      new ParallelCommandGroup( 
        new lowGoalShooterRun(RobotContainer.m_shooter), //Runs shooter motor at 2500 RPM
        new OpenGateLow(RobotContainer.m_shooter) //Opens shooter gate
      ).withTimeout(2), //Runs this entire parallel group for 2 seconds
      //Drives out to 0.3 (dau) (Stage 2)
      new AutoIntakePrep(RobotContainer.m_drive),
      //This group runs the intake (Stage 3)
      new ParallelCommandGroup( 
        new ExtendIntake(RobotContainer.m_intake), //Puts intake down
        new IntakeRun(RobotContainer.m_intake) //Runs intake motor (the wheels)
      ).withTimeout(4), //Runs this entire process for 4 seconds
      //Drives back in 0.2 (dau) (Stage 4)
      new AutoShootPrep(RobotContainer.m_drive), 
      //Waits one second--allows coast and prevents premature shooting (Stage 4)
      new WaitCommand(1), 
      //Identical to first group-- runs shooter system (Stage 5)
      new ParallelCommandGroup( 
        new lowGoalShooterRun(RobotContainer.m_shooter),
        new OpenGateLow(RobotContainer.m_shooter)
      ).withTimeout(2),
      //Leaves tarmac-- drives out 0.75 (Stage 6)
      new AutoMoveForwards(RobotContainer.m_drive)));
    
    /*
    * Simple auto. Used when we're in a less favorable position. Shoots the preload
    * and then leaves the tarmac. 
    */
    chooser.addOption("Drive and Shoot", new SequentialCommandGroup(
      //Runs shooter 
      new ParallelCommandGroup(
        new lowGoalShooterRun(RobotContainer.m_shooter), //Runs motor at 2500 RPM
        new OpenGateLow(RobotContainer.m_shooter) //Opens gate
        ).withTimeout(4), //Runs this command for 4 seconds before ending it
      //Leaves tarmac-- drives out 0.75
      new AutoMoveForwards(RobotContainer.m_drive))); 
    
    //Incredibly basic. Leaves the tarmac. 
    chooser.addOption("Drive Forward", new AutoMoveForwards(RobotContainer.m_drive)); //Drives out 0.75

    SmartDashboard.putData("Auto Choices", chooser); //Puts sendable chooser option on SmartDashboard/Shuffleboard
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
   * sendable chooser. Because of these lines it can run the selected
   * commands or groups of commands. 
   */
  @Override
  public void autonomousInit() {
    //When running autonomous command, it gets the command that is selected by the user. 
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
