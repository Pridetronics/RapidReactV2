// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.RobotContainer;

public class ReleaseGate extends CommandBase {

  private Shooter m_shooter;
  private RelativeEncoder m_shooterEncoder;

  public ReleaseGate(Shooter shooter) {
    m_shooter = new Shooter();
    addRequirements(m_shooter);
    m_shooterEncoder = RobotContainer.shooterEncoder;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.ReleaseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_shooterEncoder.getVelocity() >= Constants.shooterSpeed);
  }
}
