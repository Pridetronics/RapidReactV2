// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * Opens the gate for shooter when it is in automatic mode. This will check
 * for a certain velocity (which depends largely on the distance), and after 
 * this will let the gate open. See the vision subsystem. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Vision;

public class AutomaticOpenGate extends CommandBase {
  private Vision m_vision;
  public AutomaticOpenGate(Vision vision) {
    m_vision = vision;

    addRequirements(m_vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Ensures that the shooter motor velocity is high enough to avoid prematurely releasing balls
    if (RobotContainer.shooterEncoder.getVelocity() >= Vision.adjustableShooterRPM){
    m_vision.AutomaticOpenGate();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_vision.AutomaticCloseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
