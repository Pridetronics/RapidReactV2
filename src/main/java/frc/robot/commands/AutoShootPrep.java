// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * Executed fourth in autonomous period. This is the part of the 
 * sequence that will allow the robot to drive back to the tarmac to shoot. 
 * Called shooter prep, because it gets you to the position right before
 * the second ball will be shot. 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoShootPrep extends CommandBase {
  
  private Drive m_drive;
  static final int ticks = 42;
  private double shooterTarget;

  public AutoShootPrep(Drive drive) {
    m_drive = drive;

    addRequirements(m_drive);

    shooterTarget = 0.2;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroEncoders(); 
    System.out.println("AutoShootPrep Started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Uses positive encoder values, but if the value is under ".2" runs in the reverse direction.
    if (Drive.m_frontLeftEncoder.getPosition() < shooterTarget)
    {
      m_drive.autoDriveShooterPrep();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveStop();
    System.out.println("AutoShootPrep Finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //Once the encoder position is greater than 0.2, the command ends. 
    if (Drive.m_frontLeftEncoder.getPosition() > shooterTarget) 
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
