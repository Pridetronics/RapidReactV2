// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlyWheel;
import frc.robot.RobotContainer;
import frc.robot.Constants;


public class FlyWheelMove extends CommandBase {
  
  private FlyWheel m_flyWheel;

  public FlyWheelMove(FlyWheel flyWheel) {
    m_flyWheel = flyWheel; 
    
    addRequirements(m_flyWheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    m_flyWheel.flyWheelRun();
  }

  @Override
  public void end(boolean interrupted) {
  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
