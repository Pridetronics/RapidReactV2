// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
*  This is the drive subsytem. Works with the four drive motors, and includes
*  functions relating to manual and autonomous drive. 
*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Hardware
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

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

    m_frontLeftMotor = RobotContainer.frontLeftMotor;
    m_frontLeftEncoder = m_frontLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_frontLeftEncoder.setPositionConversionFactor(0.0378); //FIGURE THIS OUT
    m_frontLeftPIDController = m_frontLeftMotor.getPIDController();

    m_frontRightMotor = RobotContainer.frontRightMotor;
    m_frontRightEncoder = m_frontRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_frontRightEncoder.setPositionConversionFactor(0.0378); //Make this Constant to get mentors to go away
    m_frontRightPIDController = m_frontRightMotor.getPIDController();

    m_rearLeftMotor = RobotContainer.rearLeftMotor;
    m_rearLeftEncoder = m_rearLeftMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_rearLeftEncoder.setPositionConversionFactor(0.0378);
    m_rearLeftPIDController = m_rearLeftMotor.getPIDController();

    m_rearRightMotor = RobotContainer.rearRightMotor;
    m_rearRightEncoder = m_rearRightMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_rearRightEncoder.setPositionConversionFactor(0.0378);
    m_rearRightPIDController = m_rearRightMotor.getPIDController();

    zeroEncoders();

    mecanumDrive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);

    mecanumDrive.setSafetyEnabled(true); //If input is not received, motor will shut down. 
    mecanumDrive.setExpiration(0.1);
    mecanumDrive.setMaxOutput(1.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Encoder", m_frontLeftEncoder.getPosition());
    SmartDashboard.putNumber("Ticks Per Revolution", m_frontLeftEncoder.getCountsPerRevolution());
  }

  /*
  *  cartesianDrive: Drive method for mecanum. Relatively self-explanatory. It creates most of it for you
  *  all that needs to be inputed are the joystick values. It takes the josytick values and applies them to 
  *  the motors referenced in mecanumDrive. 
  */
  public void cartesianDrive(Joystick m_joystickDriver, double yValue, double xValue, double zValue) {
    yValue = m_joystickDriver.getY();
    xValue = m_joystickDriver.getX();
    zValue = m_joystickDriver.getZ();
    mecanumDrive.driveCartesian(yValue, -xValue, -((zValue * Math.abs(zValue)) * 0.8)); //Joystick values adjusted 
    // x value needed to be inverted, and z is adjusted to be less extreme (less sensitive)
  }

  //zeroEncoders: Sets drive motor encoders to 0, calls at subsystem init. 
  public void zeroEncoders() {
    m_frontLeftEncoder.setPosition(0);
    m_frontRightEncoder.setPosition(0);
    m_rearLeftEncoder.setPosition(0);
    m_rearRightEncoder.setPosition(0);
  }

  //getAverageEncoderDistanceFront: Finds the average distance of the front left and right encoders
  public double getAverageEncoderDistanceFront() {
    return (m_frontLeftEncoder.getPosition() + m_frontRightEncoder.getPosition()) / 2;
  }

  //getAverageEncoderDistanceRear: Finds the average distance of the rear left and right encoders
  public double getAverageEncoderDistanceRear() {
    return (m_rearLeftEncoder.getPosition() + m_rearRightEncoder.getPosition()) / 2;
  }

  //driveStop: Sets all four drive motors to 0% (stops motors)
  public void driveStop() {
    m_frontLeftMotor.set(0);
    m_rearLeftMotor.set(0);
    m_frontRightMotor.set(0);
    m_rearRightMotor.set(0);
  }

  //Autonomous--
  /*
  * autoDriveOut: Sets the motors to -60% voltage. Moves in the reverse direction when called. 
  * Meant to allow it to speedily leave the tarmac. 
  */
  public void autoDriveOut() {
    m_frontLeftMotor.set(Constants.autonomousDriveVoltage); 
    m_rearLeftMotor.set(Constants.autonomousDriveVoltage);
    m_frontRightMotor.set(Constants.autonomousDriveVoltage);
    m_rearRightMotor.set(Constants.autonomousDriveVoltage);
  }

  /*
  * autoDriveIntakePrep: Sets the motors to -40% voltage. Moves in the reverse direction when called
  * Meant to drive the motor to the first ball, allowing it to intake later. 
  */
  public void autoDriveIntakePrep() {
    m_frontLeftMotor.set(Constants.autonomousIntakePrepVoltage);
    m_rearLeftMotor.set(Constants.autonomousIntakePrepVoltage);
    m_frontRightMotor.set(Constants.autonomousIntakePrepVoltage);
    m_rearRightMotor.set(Constants.autonomousIntakePrepVoltage);
  }

  /*
  * autoDriveShooterPrep: Sets the motors to 40% voltage. Moves forward when called.
  * Meant to drive the motor back from where it picked up the second ball. Allows it
  * to shoot the next ball. 
  */
  public void autoDriveShooterPrep() {
    m_frontLeftMotor.set(Constants.autonomousShooterPrepVoltage);
    m_rearLeftMotor.set(Constants.autonomousShooterPrepVoltage);
    m_frontRightMotor.set(Constants.autonomousShooterPrepVoltage);
    m_rearRightMotor.set(Constants.autonomousShooterPrepVoltage);
  }

  public void spinRobotRight() 
  {
    m_frontLeftMotor.set(.3);
    m_rearRightMotor.set(.3);
    m_frontRightMotor.set(-.3);
    m_rearLeftMotor.set(-.3);
    // m_frontLeftPIDController.setReference(50, ControlType.kVelocity);
    // m_rearRightPIDController.setReference(50, ControlType.kVelocity);
    // m_frontRightPIDController.setReference(-50, ControlType.kVelocity);
    // m_rearLeftPIDController.setReference(-50, ControlType.kVelocity);
  }

  public void spinRobotLeft()
  {
    m_frontRightMotor.set(.3);
    m_rearLeftMotor.set(.3);
    m_frontLeftMotor.set(-.3);
    m_rearRightMotor.set(-.3);
  }
}

