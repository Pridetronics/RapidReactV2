// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


public class lowGoalShooterRun extends CommandBase {
  private Shooter m_shooter;
  public lowGoalShooterRun(Shooter shooter) {
    m_shooter = shooter;

    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override

  public void initialize() {
    System.out.println("LowGoalShooterRun Running");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.LowSpeedShooterMode();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop();
    m_shooter.CloseGate();
    System.out.println("LowGoalShooter Finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
