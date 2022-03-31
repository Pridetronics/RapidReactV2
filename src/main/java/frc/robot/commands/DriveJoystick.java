// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;

public class DriveJoystick extends CommandBase {
  private Joystick m_joystickDriver;
  private Drive m_drive;
    // private final Joystick m_joystickDriver;
  public DriveJoystick(Joystick joystickDriver, Drive drive) 
  {
    m_joystickDriver = joystickDriver;
    m_drive = drive;

    addRequirements(m_drive);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute(){
    double yValue = m_joystickDriver.getY(); //Gets values of the joysticks to use with drive
    double xValue = m_joystickDriver.getX();
    double zValue = m_joystickDriver.getZ();
    m_drive.cartesianDrive(m_joystickDriver, yValue, xValue, zValue);

    if (false) {
      SmartDashboard.putNumber("joyStickY", yValue);
      SmartDashboard.putNumber("joyStickX", xValue);
      SmartDashboard.putNumber("joyStickZ", zValue);
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
