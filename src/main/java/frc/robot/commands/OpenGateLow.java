// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * This checks for a speed requirement, and if the conditions are met
 * will run the OpenGateLow function, allowing the gate to open and 
 * let the ball through. If 2500 RPM is not met, the gate will not open.
 */

package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.RobotContainer;

public class OpenGateLow extends CommandBase {

  private Shooter m_shooter;
  private RelativeEncoder m_shooterEncoder;

  public OpenGateLow(Shooter shooter) {
    m_shooter = new Shooter();
    addRequirements(m_shooter);
    m_shooterEncoder = RobotContainer.shooterEncoder;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("OpenGateLow Running");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    if (m_shooterEncoder.getVelocity() >= Constants.lowShooterSpeed){
      new WaitCommand(7);
      m_shooter.OpenGateLow();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.CloseGate();
    System.out.println("OpenGateLow Finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

