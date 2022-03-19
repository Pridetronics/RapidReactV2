// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.Timer;
import frc.robot.subsystems.*;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Climb;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class AutoMoveBackwards extends CommandBase {
  private Drive m_drive;
  static final int ticks = 42; // Buddy Bot has 1120
  // private double encoder;
  private double circumference;
  private double rotationsneeded;
  private double doubledtar;

  public AutoMoveBackwards(Drive drive) {
    m_drive = drive;

    addRequirements(m_drive);
    // encoder = Drive.m_frontLeftEncoder.getPosition();
    circumference = 3.14 * 6;
    // rotationsneeded = 6/circumference;
    doubledtar = 1;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // int drivetarget = doubledtar/4;
    // long target = drivetarget;
    // WORKS JUST HAS A SCALING ISSUE
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) < doubledtar) {
      m_drive.autoDriveBack();
    }
    // m_drive.autoDriveBack();
    // long start = System. currentTimeMillis(); long end = start + 50*1000; while
    // (System. currentTimeMillis() < end);
    // long beginning = System.currentTimeMillis();
    // long m_end = beginning + 5*1000;
    // while (m_end > System.currentTimeMillis()){
    // m_drive.autoDriveBack();
    // }
    // if(m_end == System.currentTimeMillis()){
    // this.cancel();
    // }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return false;
    if (Math.abs(Drive.m_frontLeftEncoder.getPosition()) > doubledtar) {
      return true;
    } else {
      return false;
    }
  }
}

