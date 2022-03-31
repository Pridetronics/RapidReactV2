// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class HighGoalShooterRun extends CommandBase {

  private Shooter m_shooter;

  public HighGoalShooterRun(Shooter Shooter) {
    m_shooter = Shooter;

    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_shooter.HighSpeedShooter();
    // m_drive.shooterAdjust();
    // if (ShooterMode.autoShooter == true) //Checks the variable, hopefully to help select a shooter mode
    // {
    //   m_shooter.AutoShooterMode(); //Complex, if Limelight is present
    // }
    // else{
    //   m_shooter.SimpleShooterMode(); //Manual-- one distance
    // }
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop(); //Zeroes motors. 
    m_shooter.CloseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
