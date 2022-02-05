// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class Drive extends SubsystemBase {
  private VictorSP m_frontLeftMotor;
  private VictorSP m_rearLeftMotor;
  private VictorSP m_frontRightMotor;
  private VictorSP m_rearRightMotor;
  private MecanumDrive m_robotDrive;

  public Drive() {
    m_frontLeftMotor = RobotContainer.frontLeft;
    m_rearLeftMotor = RobotContainer.rearLeft;
    m_frontRightMotor = RobotContainer.frontRight;
    m_rearRightMotor = RobotContainer.rearRight;

    m_robotDrive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void autoDriveBack(){
    m_frontLeftMotor.set(-0.5);
    m_rearLeftMotor.set(-0.5);
    m_frontRightMotor.set(-0.5);
    m_rearRightMotor.set(-0.5);
  }
  public void driveStop(){
    m_frontLeftMotor.set(0);
    m_rearLeftMotor.set(0);
    m_frontRightMotor.set(0);
    m_rearRightMotor.set(0);
  }
}
