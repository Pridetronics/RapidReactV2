// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * When the button for highSpeedShooter is pressed, the function for
 * HighSpeedShooter to run. Allowing the ball to (attempt) to make the
 * high goal. Runs at 5000 RPM. When finished, it turns off the shooter
 * motor and closes the gate. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class HighGoalShooterRun extends CommandBase {

  private Shooter m_shooter;

  public HighGoalShooterRun(Shooter Shooter) {
    m_shooter = Shooter;

    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_shooter.HighSpeedShooter();
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop(); //Zeroes motors. 
    m_shooter.CloseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
