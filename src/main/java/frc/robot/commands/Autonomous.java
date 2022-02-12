// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class Autonomous extends CommandBase {
  private Drive m_drive;

  public Autonomous(Drive drive) {
    m_drive = drive;

    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.autoDriveBack();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    long beginning = System.currentTimeMillis();
    long end = beginning + 5*1000;
    while (end > System.currentTimeMillis()){
      m_drive.autoDriveBack();
    }
  if(end == System.currentTimeMillis()){
  this.cancel();
  
  }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
