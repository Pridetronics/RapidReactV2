// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class EncoderPivotArmDistanceOne extends CommandBase {
  private Climb m_climb;

  public EncoderPivotArmDistanceOne(Climb climb) {
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
    // References the method "ExtendArm()" from the climb subsystem, executing it.
    m_climb.ExtendArm();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // References the method "stop()" from the climb subsystem.
    // Climb motor stops when the command ends.
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // This will check if the climb encoder is less than the encoder distance 1
    // constant.
    // If it is less than distance 1, then it means that the pivot arms have not
    // reached distance 1 yet.
    // It will continue running using the return false.
    // Then it will stop when the climb encoder is greater than distance 1 (return
    // true).
    if (RobotContainer.climbEncoder.getPosition() < Constants.encoderClimbDistance1) {
      return false;
    } else {
      return true;
    }
  }
}
