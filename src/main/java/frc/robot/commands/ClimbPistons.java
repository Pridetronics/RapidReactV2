// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class ClimbPistons extends CommandBase {
  /** Creates a new ClimbPistons. */
  Climb m_climb;

  public ClimbPistons(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;

    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // References the method "pistonRetract()" from the climb subsystem, executing
    // it.
    // When the command starts, pistons extends.
    m_climb.pistonRelease();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // References the method "pistonRelease()" from the climb subsystem.
    // When the command ends, pistons retracts.
    m_climb.pistonRetract();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
