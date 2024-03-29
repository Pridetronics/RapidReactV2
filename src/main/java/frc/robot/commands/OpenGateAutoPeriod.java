// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * This checks for a speed requirement, and if the conditions are met
 * will run the OpenGateHigh function, allowing the gate to open and 
 * let the ball through. If 5000 RPM is not met, the gate will not open.
 */

package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.RobotContainer;

public class OpenGateAutoPeriod extends CommandBase {

  private Shooter m_shooter;
  private RelativeEncoder m_shooterEncoder;

  public OpenGateAutoPeriod(Shooter shooter) {
    m_shooter = new Shooter();
    addRequirements(m_shooter);
    m_shooterEncoder = RobotContainer.shooterEncoder;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("OpenGateAutohigh Running");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    if (m_shooterEncoder.getVelocity() >= Constants.shooterRPMHigh){
      new WaitCommand(7);
      m_shooter.OpenAutoGateHigh();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.CloseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
