// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoShootPrep extends CommandBase {
  
  private Drive m_drive;
  static final int ticks = 42;
  private double shooterTarget;

  public AutoShootPrep(Drive drive) {
    m_drive = drive;

    addRequirements(m_drive);

    shooterTarget = 1;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroEncoders(); //Do I need to zero them if I already did so at the beginning of the sequence?
    System.out.println("AutoShootPrep Started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Drive.m_frontLeftEncoder.getPosition() < shooterTarget){
      m_drive.autoDriveShooterPrep();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveStop();
    System.out.println("AutoShootPrep Finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Drive.m_frontLeftEncoder.getPosition() > shooterTarget) 
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
