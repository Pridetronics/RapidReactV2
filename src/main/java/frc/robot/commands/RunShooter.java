// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * This controls the shooter motor for the variety of shooter modes. First the 
 * program checks the value of shooterMode. If shooterMode == 0, then automatic 
 * shooter is run (this is based on the calculations provided within FindDistance). If 
 * shooterMode == 1, then lowSpeedShooter is run (2500 RPM). If shooterMode == 2, then
 * highSpeedShooter is run (5000 RPM). Also a similar openGate command for system efficiency
 * When command ends, the gate closes and the shooter motor stops. 
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
//import frc.robot.commands.ShooterMode;

public class RunShooter extends CommandBase {
  private Shooter m_shooter;
  private Vision m_vision;

  public RunShooter(Shooter shooter, Vision vision) {
    m_shooter = shooter;
    m_vision = vision;

    addRequirements(m_shooter);
    addRequirements(m_vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (ShooterMode.modeNumber == 0)
    {
      m_vision.AutomaticShooter();
    }
    else if (ShooterMode.modeNumber == 1)
    {
      m_shooter.LowSpeedShooter();
    }
    else if (ShooterMode.modeNumber == 2)
    {
      m_shooter.HighSpeedShooter();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop();
    m_shooter.CloseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
