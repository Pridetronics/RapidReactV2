// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Shooter;

public class HoningCommand extends CommandBase {
  private Climb m_climb;
  private Shooter m_shooter;
  Command sequentialHoneCommand;
  private RelativeEncoder m_climbEncoder;

  /** Creates a new HoningCommand. */
  public HoningCommand(Climb climb, Shooter shooter) {
    m_climb = climb;
    addRequirements(m_climb);

    m_shooter = shooter;
    addRequirements(m_shooter);

    m_climbEncoder = RobotContainer.climbEncoder;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //m_shooter.lightsOut();
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
