// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * Meant to switch between camera modes. This is a toggle, so the execute acts as one
 * setting (the cameraMode, as it already defaults to processing), where the end acts as the 
 * other. So once the command is called (by user pushing a button), it will switch and remain 
 * as one camera mode until the button is pressed again. 
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class VisionMode extends CommandBase {

  private Vision m_vision;

  public VisionMode(Vision vision) {
    m_vision = vision;

    addRequirements(m_vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_vision.cameraMode();
    SmartDashboard.putString("Vision Mode", "Camera");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_vision.processingMode();
    m_vision.findDistance(); //Makes sense to put this here, because if I put it in processing mode, I assume that I will be prepping to shoot
    SmartDashboard.putString("Vision Mode", "Processing");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
