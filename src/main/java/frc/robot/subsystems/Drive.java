// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

public class Drive extends SubsystemBase {

  private CANSparkMax m_frontLeftMotor;
  private CANSparkMax m_frontRightMotor;
  private CANSparkMax m_rearLeftMotor;
  private CANSparkMax m_rearRightMotor;

  public static RelativeEncoder m_frontLeftEncoder;
  public static RelativeEncoder m_frontRightEncoder;
  public static RelativeEncoder m_rearLeftEncoder;
  public static RelativeEncoder m_rearRightEncoder;

  public SparkMaxPIDController m_frontLeftPIDController;
  public SparkMaxPIDController m_rearLeftPIDController;
  public SparkMaxPIDController m_frontRightPIDController;
  public SparkMaxPIDController m_rearRightPIDController;

  private MecanumDrive mecanumDrive;

  // public Joystick m_joystickDriver;

  public Drive(Joystick m_joystickDriver) {

    m_frontLeftMotor = RobotContainer.frontLeft;
    m_frontLeftEncoder = m_frontLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_frontLeftEncoder.setPositionConversionFactor(0.0378);
    m_frontLeftPIDController = m_frontLeftMotor.getPIDController();

    m_frontRightMotor = RobotContainer.frontRight;
    m_frontRightEncoder = m_frontRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_frontRightEncoder.setPositionConversionFactor(0.0378);
    m_frontRightPIDController = m_frontRightMotor.getPIDController();

    m_rearLeftMotor = RobotContainer.rearLeft;
    m_rearLeftEncoder = m_rearLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_rearLeftEncoder.setPositionConversionFactor(0.0378);
    m_rearLeftPIDController = m_rearLeftMotor.getPIDController();

    m_rearRightMotor = RobotContainer.rearRight;
    m_rearRightEncoder = m_rearRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_rearRightEncoder.setPositionConversionFactor(0.0378);
    m_rearRightPIDController = m_rearRightMotor.getPIDController();

    zeroEncoders();

    mecanumDrive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);
    mecanumDrive.setSafetyEnabled(true); // Safety settings-- Boring
    mecanumDrive.setExpiration(0.1);
    mecanumDrive.setMaxOutput(1.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Right Encoder", m_frontRightEncoder.getPosition());
    SmartDashboard.putNumber("Left Encoder", m_frontLeftEncoder.getPosition());
    SmartDashboard.putNumber("Average Encoder FRONT", getAverageEncoderDistanceFront());
    SmartDashboard.putNumber("Average Encoder BACK", getAverageEncoderDistanceFront());
    SmartDashboard.putNumber("Ticks Per Revolution", m_frontLeftEncoder.getCountsPerRevolution());
    m_frontLeftEncoder.getPosition();
    m_frontRightEncoder.getPosition();
    m_frontLeftEncoder.getCountsPerRevolution();

  }

  public double getAverageEncoderDistanceFront() {
    return (m_frontLeftEncoder.getPosition() + m_frontRightEncoder.getPosition()) / 2;
  }

  public double getAverageEncoderDistanceRear() {
    return (m_rearLeftEncoder.getPosition() + m_rearRightEncoder.getPosition()) / 2;
  }

  public void cartesianDrive(Joystick m_joystickDriver, double yValue, double xValue, double zValue) {
    yValue = m_joystickDriver.getY();
    xValue = m_joystickDriver.getX();
    zValue = m_joystickDriver.getZ();
    //mecanumDrive.driveCartesian(yValue, -xValue, -zValue);
    mecanumDrive.driveCartesian(yValue, -xValue, -((zValue * Math.abs(zValue)) * 0.8));
    // mecanumDrive.driveCartesian((yValue * Math.abs(yValue)), -(xValue * Math.abs(xValue)), -(zValue * Math.abs(zValue)));
  }
  public void autoDriveBack() {
    m_frontLeftMotor.set(-0.6);
    m_rearLeftMotor.set(-0.6);
    m_frontRightMotor.set(-0.6);
    m_rearRightMotor.set(-0.6);
  }

  public void autoDriveIntakePrep() {
    m_frontLeftMotor.set(-0.4);
    m_rearLeftMotor.set(-0.4);
    m_frontRightMotor.set(-0.4);
    m_rearRightMotor.set(-0.4);
  }

  public void autoDriveShooterPrep() {
    m_frontLeftMotor.set(0.4);
    m_rearLeftMotor.set(0.4);
    m_frontRightMotor.set(0.4);
    m_rearRightMotor.set(0.4);
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

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

  public void shooterAdjust() {
    if (Shooter.tx > 3 && Shooter.tx < 28) // turning left
    {
      m_frontRightPIDController.setReference(50, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(50, ControlType.kVelocity);
      m_frontLeftPIDController.setReference(-50, ControlType.kVelocity);
      m_rearRightPIDController.setReference(-50, ControlType.kVelocity);
    } else if (Shooter.tx < -3 && Shooter.tx > -28) // turning right
    {
      m_frontLeftPIDController.setReference(50, ControlType.kVelocity);
      m_rearRightPIDController.setReference(50, ControlType.kVelocity);
      m_frontRightPIDController.setReference(-50, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(-50, ControlType.kVelocity);
    } else {
      m_frontLeftPIDController.setReference(0, ControlType.kVelocity);
      m_frontRightPIDController.setReference(0, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(0, ControlType.kVelocity);
      m_rearRightPIDController.setReference(0, ControlType.kVelocity);
    }
  }

  public void seekTarget() {
    if (Shooter.tv == 0) {
      m_frontLeftPIDController.setReference(100, ControlType.kVelocity);
      m_rearRightPIDController.setReference(100, ControlType.kVelocity);
      m_frontRightPIDController.setReference(-100, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(-100, ControlType.kVelocity);
    } else if (Shooter.tv == 1) {
      m_frontLeftPIDController.setReference(0, ControlType.kVelocity);
      m_frontRightPIDController.setReference(0, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(0, ControlType.kVelocity);
      m_rearRightPIDController.setReference(0, ControlType.kVelocity);
    }

  }

}
