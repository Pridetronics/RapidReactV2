// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class Drive extends SubsystemBase {
  private static CANSparkMax m_frontLeftMotor;
  private static CANSparkMax m_rearLeftMotor;
  private static CANSparkMax m_frontRightMotor;
  private static CANSparkMax m_rearRightMotor;
  private MecanumDrive m_robotDrive;
  public static RelativeEncoder m_FrontLeftEncoder;
  public static RelativeEncoder m_FrontRightEncoder;
  public static RelativeEncoder m_RearLeftEncoder;
  public static RelativeEncoder m_RearRightEncoder;

  public Drive() {
    m_frontLeftMotor = RobotContainer.frontLeft;
    m_rearLeftMotor = RobotContainer.rearLeft;
    m_frontRightMotor = RobotContainer.frontRight;
    m_rearRightMotor = RobotContainer.rearRight;

    // m_robotDrive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor,
    // m_frontRightMotor, m_rearRightMotor);

  }

  public void stop() {
    m_frontLeftMotor.set(0);
    m_frontRightMotor.set(0);
    m_rearLeftMotor.set(0);
    m_rearRightMotor.set(0);

  }

  // public void zeroEncoders() {
  // m_FrontLeftEncoder.setPosition(0);
  // m_FrontRightEncoder.setPosition(0);
  // m_RearLeftEncoder.setPosition(0);
  // m_RearRightEncoder.setPosition(0);
  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Back Left Encoder", m_RearLeftEncoder.getPosition());
    SmartDashboard.putNumber("Back Right Encoder", m_RearRightEncoder.getPosition());
    SmartDashboard.putNumber("Front Right Encoder", m_FrontRightEncoder.getPosition());
    SmartDashboard.putNumber("Front Left Encoder", m_FrontLeftEncoder.getPosition());
    SmartDashboard.putNumber("Average Encoder", getAverageEncoderDistance());
    SmartDashboard.putNumber("ticks per rev", m_FrontLeftEncoder.getCountsPerRevolution());
    m_FrontLeftEncoder.getPosition();
    m_FrontRightEncoder.getPosition();
    m_FrontLeftEncoder.getCountsPerRevolution();

  }

  public double getAverageEncoderDistance() {
    return (m_FrontLeftEncoder.getPosition() + m_FrontRightEncoder.getPosition()) / 2;
  }

  public void autoDriveBack() {
    m_frontLeftMotor.set(-0.5);
    m_rearLeftMotor.set(-0.5);
    m_frontRightMotor.set(-0.5);
    m_rearRightMotor.set(-0.5);
  }

  public static void driveStop() {
    m_frontLeftMotor.set(0);
    m_rearLeftMotor.set(0);
    m_frontRightMotor.set(0);
    m_rearRightMotor.set(0);
  }
}
