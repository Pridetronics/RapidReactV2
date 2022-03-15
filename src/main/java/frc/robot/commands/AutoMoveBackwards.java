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
  private Climb m_climb;
  static final int ticks = 1120;
public AutoMoveBackwards(Drive drive, Climb climb) 
{
  m_drive = drive;
  m_climb = climb;
  
  addRequirements(m_drive);
<<<<<<< Updated upstream
  addRequirements(m_climb);
=======
  //encoder = Drive.m_frontLeftEncoder.getPosition();
  circumference = 3.14*6;
  // rotationsneeded = 6/circumference;
  doubledtar = .90; //Measured in dau
>>>>>>> Stashed changes
}

  // Called when the command is initially scheduled.
@Override
public void initialize() 
{
  m_drive.zeroEncoders();
  System.out.println("AutoMoveBackwards Started");
}
// Called every time the scheduler runs while the command is scheduled.
@Override
public void execute() 
{
  double encoder = Drive.m_frontLeftEncoder.getPosition();
  double circumference = 3.14*4 ;
  double rotationsneeded = 4/circumference; // 4 to 1 ft` ratio probably 2 to 1 with 6" wheels
  int doubledtar = (int)(rotationsneeded*39);
  //int drivetarget = doubledtar/4;
  //long target = drivetarget;
  if (encoder < doubledtar){
    m_drive.autoDriveBack();
  }
  if (encoder == doubledtar){
    this.cancel();
  }

  //long start = System. currentTimeMillis(); long end = start + 50*1000; while (System. currentTimeMillis() < end);
  //long beginning = System.currentTimeMillis();
  //long end = beginning + 5*1000;
  //while (end > System.currentTimeMillis()){
  //m_drive.autoDriveBack();
  // }
  //if(end == System.currentTimeMillis()){
  //this.cancel();
  //}

}
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveStop();
    System.out.println("AutoMoveBackwards Finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
