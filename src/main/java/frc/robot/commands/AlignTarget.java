// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_vision.SeekTarget();
    m_vision.TargetAdjust();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; //I'd like to add a command that checks when shooter run is finished or when it's toggled off. 
    //That implies that some end command needs to exist. This one might be messy. 
  }
}