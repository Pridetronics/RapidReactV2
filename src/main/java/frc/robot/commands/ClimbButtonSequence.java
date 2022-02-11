// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Climb;

public class ClimbButtonSequence extends CommandBase {
  /** Creates a new ClimbButtonSequence. */
  Climb m_climb;
  private int x = 0;
  
  public ClimbButtonSequence(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (x == 1){
        //Run sequence 1
      sequence1();
    }

    if (x == 2){
        //Run sequence 2

    }

    if (x == 3) {
        //Run sequence 3
      
    }

    if (x == 4) {
        //Run sequence 4

    }

    else {

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
