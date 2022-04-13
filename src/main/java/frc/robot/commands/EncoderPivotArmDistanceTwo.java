// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * These were created as a faster alternative to the initial
 * PID controller climb sequence. This relies on the encoder values
 * and while they return a position that is under the set amount,
 * the motor will run at a specified speed. In this case, the arm runs upwards
 * until it reaches the proper encoder distance. Much like one and three.
 * The only thing that changes in all of these is the distance it seeks. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class EncoderPivotArmDistanceTwo extends CommandBase {
  private Climb m_climb; 
  public EncoderPivotArmDistanceTwo(Climb climb) {
    m_climb = climb;

    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climb.ExtendArm();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (RobotContainer.climbEncoder.getPosition() < Constants.encoderClimbDistance2){
      return false;
    }
    else {
      return true;
    }
  }
}
