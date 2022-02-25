// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.RelativeEncoder;
public class Drive extends SubsystemBase {

  private CANSparkMax m_frontLeftMotor;
  private CANSparkMax m_frontRightMotor;
  private CANSparkMax m_rearLeftMotor;
  private CANSparkMax m_rearRightMotor;

  private RelativeEncoder m_frontLeftEncoder;
  private RelativeEncoder m_frontRightEncoder;
  private RelativeEncoder m_rearLeftEncoder;
  private RelativeEncoder m_rearRightEncoder;

  private MecanumDrive mecanumDrive;

  public Joystick m_joystickDriver;

  public Drive(Joystick joystickDriver) {
    m_joystickDriver = joystickDriver;

    m_frontLeftMotor = RobotContainer.frontLeft;
    m_frontLeftMotor.setInverted(false);
    m_frontLeftEncoder = m_frontLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

    m_frontRightMotor = RobotContainer.frontRight;
    m_frontRightMotor.setInverted(false);
    m_frontRightEncoder = m_frontRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

    m_rearLeftMotor = RobotContainer.rearLeft;
    m_rearLeftMotor.setInverted(false);
    m_rearLeftEncoder = m_rearLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

    m_rearRightMotor = RobotContainer.rearRight;
    m_rearRightMotor.setInverted(false);
    m_rearRightEncoder = m_rearRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

    zeroEncoders();

    mecanumDrive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);
    mecanumDrive.setSafetyEnabled(true); // Safety settings-- Boring
    mecanumDrive.setExpiration(0.1);
    mecanumDrive.setMaxOutput(1.0);
  }
  public void stop() {
    m_frontLeftMotor.set(0);
    m_frontRightMotor.set(0);
  }
  public void zeroEncoders() {
    m_FrontLeftEncoder.setPosition(0);
    m_FrontRightEncoder.setPosition(0);

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Right Encoder", m_FrontRightEncoder.getPosition());
    SmartDashboard.putNumber("Left Encoder", m_FrontLeftEncoder.getPosition());
    SmartDashboard.putNumber("Average Encoder", getAverageEncoderDistance());
    SmartDashboard.putNumber("ticks per rev", m_FrontLeftEncoder.getCountsPerRevolution());
    m_FrontLeftEncoder.getPosition();
    m_FrontRightEncoder.getPosition();
    m_FrontLeftEncoder.getCountsPerRevolution();  
 
  }
  public double getAverageEncoderDistance() {
    return (m_FrontLeftEncoder.getPosition() + m_FrontRightEncoder.getPosition()) / 2;
  }
  public void autoDriveBack(){
    m_frontLeftMotor.set(-0.5);
    m_rearLeftMotor.set(-0.5);
    m_frontRightMotor.set(-0.5);
    m_rearRightMotor.set(-0.5);
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void cartesianDrive(double yValue, double xValue, double zValue) {
    yValue = m_joystickDriver.getY();
    xValue = m_joystickDriver.getX();
    zValue = m_joystickDriver.getZ();
    mecanumDrive.driveCartesian(yValue, xValue, zValue);
  }

  public void autoDriveBack() {
  }

  public void driveStop() {
    m_frontLeftMotor.set(0);
    m_rearLeftMotor.set(0);
    m_frontRightMotor.set(0);
    m_rearRightMotor.set(0);
  }

  public void zeroEncoders() {
    m_frontLeftEncoder.setPosition(0);
    m_frontRightEncoder.setPosition(0);
    m_rearLeftEncoder.setPosition(0);
    m_rearRightEncoder.setPosition(0);
  }

}
