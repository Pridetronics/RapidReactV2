// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
* Grabs the joystick values from the controller, and implements them into 
*  "cartesianDrive" -a WPILib method used to control mecanum robots.
* This command is constantly running- giving the driver access to control the robot.
*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;

public class DriveJoystick extends CommandBase {
  private Joystick m_joystickDriver;
  private Drive m_drive;
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
    double yValue = m_joystickDriver.getY(); //Gets the Y value from the joystick
    double xValue = m_joystickDriver.getX(); //Gets the X value from the joystick
    double zValue = m_joystickDriver.getZ(); //Gets the Z value from the joystick
    m_drive.cartesianDrive(m_joystickDriver, yValue, xValue, zValue); //Uses the inputs above to implement into "cartesianDrive" method.

    if (false) { //If (true), this block will display the X,Y,Z, values to SmartDashboard
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
