// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * This command is meant to run with the openGate command based on
 * which shooterMode (see command) is engaged. If shooterMode == zero, that
 * is automatic and the gate will open based on the distance found (matching
 * the RPM). If shooterMode == one, low is activated and will open at 2500 RPM.
 * If shooterMode == two, high is activated and will open at 5000 RPM. Regardless
 * of which runs, it will close when finished. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.commands.ShooterMode;

public class RunOpenGate extends CommandBase {
  private Shooter m_shooter;
  private Vision m_vision;

  public RunOpenGate(Shooter shooter, Vision vision) {
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
      //ADD IF STATEMENTS TO CHECK?
      m_vision.AutomaticOpenGate();
    }
    else if (ShooterMode.modeNumber == 1)
    {
      m_shooter.OpenGateLow();
    }
    else if (ShooterMode.modeNumber == 2)
    {
      m_shooter.OpenGateHigh();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
