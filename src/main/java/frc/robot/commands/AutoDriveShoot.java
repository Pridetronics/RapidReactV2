// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
public class AutoDriveShoot extends CommandBase {
  private Drive m_drive;
  private Shooter m_shooter;
  static final int ticks = 42; //Buddy Bot has 1120
  //private double encoder;
  private double circumference;
  private double rotationsneeded;
  private double doubledtar;
  /** Creates a new Auto_drive_shoot. */
  public AutoDriveShoot(Drive drive, Shooter shooter) {
  m_drive = drive;
  m_shooter = shooter;  

  addRequirements(m_drive);  
  addRequirements(m_shooter);

  //encoder = Drive.m_frontLeftEncoder.getPosition();
  circumference = 3.14*6;
  // rotationsneeded = 6/circumference;
  doubledtar = .75;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.LowSpeedShooterMode();
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) < doubledtar){
      m_drive.autoDriveBack();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop();
    m_drive.driveStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) > doubledtar)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
