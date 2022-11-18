// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
/*
 * When called upon, executes seek target and Target adjust from vision subsystem consecutively and does 
 * not end because isfinished returns false--> Now is set to toggle. The command will be interrupted and then
 * stopped. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class AlignTarget extends CommandBase {
  private Vision m_vision;

  public AlignTarget(Vision vision) {
    m_vision = vision;

    addRequirements(m_vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_vision.SeekTarget();
    m_vision.TargetAdjust();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_vision.DriveMotorsStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
