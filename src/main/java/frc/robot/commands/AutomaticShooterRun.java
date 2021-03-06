// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * This command is meant to run the shooter automatically while the button is held
 * When held, the Limelight will calculate the distance from the high goal, and based
 * on what it finds, will adjust the RPM. Both run consistently while this command runs.
 * Processing mode must be engaged for findDistance to run. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class AutomaticShooterRun extends CommandBase {
  private Vision m_vision; 
  public AutomaticShooterRun(Vision vision) {
    m_vision = vision;

    addRequirements(m_vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_vision.findDistance();
    m_vision.AutomaticShooter();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_vision.ShooterMotorStop();
    m_vision.AutomaticCloseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
