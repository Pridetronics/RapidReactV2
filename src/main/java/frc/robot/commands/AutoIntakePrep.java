// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
/**
 * Executed second in autonomous period. This is the part of the 
 * sequence that will allow the robot to drive out to the ball.
 * Called intake prep, because it gets you to the position right before
 * intake will be run. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoIntakePrep extends CommandBase {
  private Drive m_drive;
  static final int ticks = 42;
  private double intakeTarget;

  public AutoIntakePrep(Drive drive) {
    m_drive = drive;

    addRequirements(m_drive);

    intakeTarget = 0.3;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroEncoders();
    //These console messages are really helpful for ensuring functions are working. 
    System.out.println("AutoIntakePrep Started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Checks if the encoders have reached a position (distance), and if not, runs the motors at 40%
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) < intakeTarget) {
      m_drive.autoDriveIntakePrep();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveStop();
    System.out.println("AutoIntakePrep Finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //Checks the position of the encoders, if at the proper position, the command should end
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) > intakeTarget) {
      return true;
    } else {
      return false;
    }
  }
}
