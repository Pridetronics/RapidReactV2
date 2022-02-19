// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterRun extends CommandBase {

  private Shooter m_shooter;

  public ShooterRun(Shooter Shooter) {
    m_shooter = Shooter;

    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    //I moved the find distance command into vision modes, hoping to improve efficiency. 
    m_shooter.ShooterRun(); //Looks for function within shooter.  
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop(); //Zeroes motors. 
    m_shooter.RetractGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
