// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class LowerArmsDP extends CommandBase {
  private Climb m_climb;
  private double climbDistance;
  /** Creates a new LowerArmsDP. */
  public LowerArmsDP(Climb climb) {
    m_climb = climb;

    addRequirements(m_climb);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climb.zeroEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climb.DancyArmsDown();
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_climb.isClimbAtBottom();
  }
}
