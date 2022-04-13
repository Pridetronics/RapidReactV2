// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
/**
 * When run, this will prepare the climb arms to complete a climb sequence
 * First it makes sure it is not below the limit switch (adjusting if needed),
 * then ensures it is not above the limit switch. After this process finishes,
 * the encoders will be zeroed and the arms resting on the limit switch. Without this
 * the climb sequence would be off and could potentially run the winch the opposite
 * direction.
 */
package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class HoningCommand extends CommandBase {
  private Climb m_climb;
  Command sequentialHoneCommand;

  /** Creates a new HoningCommand. */
  public HoningCommand(Climb climb) {
    m_climb = climb;

    addRequirements(m_climb);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climb.ClimbUpSlowly();
    m_climb.ClimbDownSlowly();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_climb.isClimbAtBottom() == true){
      return true;
    }
    else{
      return false;
    }
  }
}
