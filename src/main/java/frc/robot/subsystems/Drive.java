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
import com.revrobotics.SparkMaxPIDController;

public class Drive extends SubsystemBase {

  private CANSparkMax m_frontLeftMotor;
  private CANSparkMax m_frontRightMotor;
  private CANSparkMax m_rearLeftMotor;
  private CANSparkMax m_rearRightMotor;

  private RelativeEncoder m_frontLeftEncoder;
  private RelativeEncoder m_frontRightEncoder;
  private RelativeEncoder m_rearLeftEncoder;
  private RelativeEncoder m_rearRightEncoder;

  private SparkMaxPIDController frontLeftPID;
  private SparkMaxPIDController frontRightPID;
  private SparkMaxPIDController rearLeftPID;
  private SparkMaxPIDController rearRightPID;
  private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  private MecanumDrive mecanumDrive;

  public Joystick m_joystickDriver;

  public Drive(Joystick joystickDriver) {
    m_joystickDriver = joystickDriver;

    m_frontLeftMotor = RobotContainer.frontLeft;
    m_frontLeftMotor.setInverted(false);
    m_frontLeftEncoder = m_frontLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    frontLeftPID = m_frontLeftMotor.getPIDController();

    m_frontRightMotor = RobotContainer.frontRight;
    m_frontRightMotor.setInverted(false);
    m_frontRightEncoder = m_frontRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    frontRightPID = m_frontRightMotor.getPIDController();

    m_rearLeftMotor = RobotContainer.rearLeft;
    m_rearLeftMotor.setInverted(false);
    m_rearLeftEncoder = m_rearLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    rearLeftPID = m_rearLeftMotor.getPIDController();

    m_rearRightMotor = RobotContainer.rearRight;
    m_rearRightMotor.setInverted(false);
    m_rearRightEncoder = m_rearRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    rearRightPID = m_rearRightMotor.getPIDController();

    // m_frontLeftEncoder.setPositionConversionFactor(Constants.kEncoderPositionConversionFactor);
    // m_frontRightEncoder.setPositionConversionFactor(Constants.kEncoderPositionConversionFactor);
    // m_rearLeftEncoder.setPositionConversionFactor(Constants.kEncoderPositionConversionFactor);
    // m_rearRightEncoder.setPositionConversionFactor(Constants.kEncoderPositionConversionFactor);
    zeroEncoders();

    mecanumDrive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);
    mecanumDrive.setSafetyEnabled(true); // Safety settings-- Boring
    mecanumDrive.setExpiration(0.1);
    mecanumDrive.setMaxOutput(1.0);

    // kP = 0.1; //Defines values for the PID Controllers
    // kI = 1e-4;
    // kD = 1; 
    // kIz = 0; 
    // kFF = 0; 
    // kMaxOutput = 1; 
    // kMinOutput = -1;

    // frontLeftPID.setP(kP); //Assigns values for each PID Controller
    // frontLeftPID.setI(kI);
    // frontLeftPID.setD(kD);
    // frontLeftPID.setIZone(kIz);
    // frontLeftPID.setFF(kFF);
    // frontLeftPID.setOutputRange(kMinOutput, kMaxOutput);

    // frontRightPID.setP(kP);
    // frontRightPID.setI(kI);
    // frontRightPID.setD(kD);
    // frontRightPID.setIZone(kIz);
    // frontRightPID.setFF(kFF);
    // frontRightPID.setOutputRange(kMinOutput, kMaxOutput);

    // rearLeftPID.setP(kP);
    // rearLeftPID.setI(kI);
    // rearLeftPID.setD(kD);
    // rearLeftPID.setIZone(kIz);
    // rearLeftPID.setFF(kFF);
    // rearLeftPID.setOutputRange(kMinOutput, kMaxOutput);

    // rearRightPID.setP(kP);
    // rearRightPID.setI(kI);
    // rearRightPID.setD(kD);
    // rearRightPID.setIZone(kIz);
    // rearRightPID.setFF(kFF);
    // rearRightPID.setOutputRange(kMinOutput, kMaxOutput);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Front Left Encoder", m_frontLeftEncoder.getVelocity()); 
    SmartDashboard.putNumber("Front Right Encoder", m_frontRightEncoder.getVelocity());
    SmartDashboard.putNumber("Rear Left Encoder", m_rearLeftEncoder.getVelocity());
    SmartDashboard.putNumber("Rear Right Encoder", m_rearRightEncoder.getVelocity());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run when in simulation

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
