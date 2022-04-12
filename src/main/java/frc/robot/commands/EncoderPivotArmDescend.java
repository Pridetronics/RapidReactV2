
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class EncoderPivotArmDescend extends CommandBase {
  private Climb m_climb;

  public EncoderPivotArmDescend(Climb climb) {
    m_climb = climb;

    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // References the method "DescendArm()" from the climb subsystem, executing it.
    m_climb.DescendArm();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // References the method "stop()" from the climb subsystem. This will stop the
    // motors once the command ends.
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // This checks when the pivot arms hit the limit switches at the bottom of the
    // climb.
    // If the limit switches are hit, then it will return true. Thus, it would cause
    // the command to finish.
    // If the limit switches are not hit, then it will return false and keep running
    // the command until it gets true.
    if (m_climb.isClimbAtBottom() == true) {
      return true;
    } else {
      return false;
    }
  }
}
