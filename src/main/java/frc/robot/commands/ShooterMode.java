// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterMode extends CommandBase {
  /** Creates a new ShooterMode. */
  private Shooter m_shooter;
  public static Boolean autoShooter;

  public ShooterMode(Shooter shooter) {
    m_shooter = shooter;

    addRequirements(m_shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.SimpleShooterMode();
    autoShooter = false;
    SmartDashboard.putString("Shooting Mode", "Manual Shooter");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   // m_shooter.AutoShooterMode();
    autoShooter = true;
    SmartDashboard.putString("Shooting Mode", "Automatic Shooter");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
