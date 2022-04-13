// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * When the button for LowSpeedShooter is pressed, the function for
 * LowSpeedShooter to run. Allowing the ball to make the low goal. 
 * When finished, it turns off the shooter motor and closes the gate. 
 * This one is considered more accurate than the HighGoalShooter because
 * of the nature of our design (not a programming problem ;))
 */
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
    m_shooter.LowSpeedShooter();
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
