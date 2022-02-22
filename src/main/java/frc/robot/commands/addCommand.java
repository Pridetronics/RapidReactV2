// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class addCommand extends CommandBase {
  /** Creates a new addCommand. */
  private Climb m_climb;

  // int m_kstageLevel = Constants.kstageLevel;
  public addCommand(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = new Climb();
    addRequirements(m_climb);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climb.increaseStage();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putNumber("Stage Level", Climb.stageLevel);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
