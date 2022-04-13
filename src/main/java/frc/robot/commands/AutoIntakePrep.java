// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
/**
 * Executed first in autonomous period. 
 * 
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
    System.out.println("AutoIntakePrep Started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
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
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) > intakeTarget) {
      return true;
    } else {
      return false;
    }
  }
}
