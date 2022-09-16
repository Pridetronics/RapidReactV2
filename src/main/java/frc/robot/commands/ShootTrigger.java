// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * I have no clue if this one works, but it is meant to allow the shooter to run 
 * off of a trigger. As of this being written (04/12/22), this has not been tested
 * and may or may not work. Depending on your ambitions-- I would love to see a 
 * world in which this works. 
 * 
 * Currently it is commented out (as to ensure competition safety), but it is supposed 
 * to get the axis from the trigger and register is as an input to run the command. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class ShootTrigger extends CommandBase {
  private Shooter m_shooter;
  private Joystick m_joystickShooter;
  private ParallelCommandGroup shooterSequence;

  public ShootTrigger(Joystick joystickShooter, Shooter shooter) {
    m_joystickShooter = joystickShooter;
    m_shooter = shooter;

    addRequirements(m_shooter);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //if (m_joystickShooter.getRawAxis(3) > .1){
      //m_shooter.HighSpeedShooterMode();
      //m_shooter.OpenGate();
    //}
    //else
    //{
      SmartDashboard.putString("Shooter Status", "Shooter Not Running");
    //}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop();
    m_shooter.CloseGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
