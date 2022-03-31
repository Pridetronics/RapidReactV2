// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoMoveForwards extends CommandBase {
  private Drive m_drive;
  static final int ticks = 42; // Buddy Bot has 1120
  private double circumference;
  private double rotationsneeded;
  private double targetDistance;

  public AutoMoveForwards(Drive drive) {
    m_drive = drive;

    addRequirements(m_drive);

    // encoder = Drive.m_frontLeftEncoder.getPosition();
    circumference = 3.14 * 6;
    // rotationsneeded = 6/circumference;
    targetDistance = .75;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    m_drive.zeroEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    // int drivetarget = doubledtar/4;
    // long target = drivetarget;
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) < targetDistance)
    {
      m_drive.autoDriveOut();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    m_drive.driveStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return false;
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) > targetDistance) 
    {
      return true;
    } 
    else 
    {
      return false;
    }
  }
}

