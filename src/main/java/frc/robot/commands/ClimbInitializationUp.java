// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class ClimbInitializationUp extends CommandBase {
  // This command is for honing climb.
  private Climb m_climb;

  public ClimbInitializationUp(Climb climb) {
    m_climb = climb;

    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Prints a line in the terminal that says "Running Initialization Up"
    System.out.println("Running Initialization Up");

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // References the method "ClimbUpSlowly()" from the climb subsystem, executing
    // it.
    m_climb.ClimbUpSlowly();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // References the method "stop()," which makes climb motor stop when the command
    // ends.
    m_climb.stop();
    // Referenes the the method "zeroEncoder()," which makes the climb's encoder set
    // to 0.
    m_climb.zeroEncoder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // This checks when the pivot arms hit the limit switches at the bottom of the
    // climb.
    // If the limit switches are hit, then it will return false. Thus, it would
    // cause the command to continue.
    // If the limit switches are not hit, then it will return true. Thus, it would
    // cause the command to finish.
    if (m_climb.isClimbAtBottom() == true) {
      return false;
    } else {
      return true;
    }
  }
}
