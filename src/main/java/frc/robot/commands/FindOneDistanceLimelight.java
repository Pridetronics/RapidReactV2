// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
* Runs Vision's function "findDistance". This utilizes the Limelight, by
* having it run an equation that allows for it to estimate how far from the 
* target it is. This is used as a testing command (more for ensuring accuracy)
* because in the chance that we go to different fields, having a simple command
* that can be used to tune Limelight is more responsible than spontaneously running
* the shooter motor. 
* TL:DR--> Do we need it technically? No. But it's nice to have.
*/
package frc.robot.commands; //Later once I'm fully tested this, I want to remove this command as the function is being utilized in another command

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class FindOneDistanceLimelight extends CommandBase {
  private Vision m_vision;

  public FindOneDistanceLimelight(Vision vision) {
    m_vision = vision;

    addRequirements(m_vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_vision.findDistance();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
