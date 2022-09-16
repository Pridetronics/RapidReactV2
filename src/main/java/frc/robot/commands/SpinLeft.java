// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class SpinLeft extends CommandBase {
  private Drive m_drive;
  private double spinDistance;
  /** Creates a new SpinLeft. */
  public SpinLeft(Drive drive) {
    m_drive = drive;

    addRequirements(m_drive);
    spinDistance = 1; 
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) < spinDistance)
    {
      m_drive.spinRobotLeft();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) > spinDistance) 
    {
      return true;
    } 
    else 
    {
      return false;
    }
  }
}