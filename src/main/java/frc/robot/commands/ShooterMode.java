// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * This command creates a variable for shooterMode, and when user input is received
 * changes the variable to the next number up. In this case, this is meant to symbolise
 * the type of shooter that will be run. When the number is at two, and the user sends
 * an input, the cycle will reset, ensuring that there are only three modes present.
 * shooterMode == 0 --> Automatic Shooter
 * shooterMode == 1 --> LowSpeedShooter
 * shooterMode == 2 --> HighSpeedShooter
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterMode extends CommandBase {
  /** Creates a new ShooterMode. */
  private Shooter m_shooter;
  public static int modeNumber;

  public ShooterMode(Shooter shooter) {
    m_shooter = shooter;

    addRequirements(m_shooter);
    modeNumber = 0;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    modeNumber++; //when called the command is scheduled, the variable is increased and then value is checked. 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    if (modeNumber == 1)
    {
      SmartDashboard.putString("Mode", "lowSpeedShooter");
    } 
    else if (modeNumber == 2)
    {
      SmartDashboard.putString("Mode", "highSpeedShooter");
    }
    else if (modeNumber == 3)
    {
      modeNumber = 0;
      SmartDashboard.putString("Mode", "automaticShooter");
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
