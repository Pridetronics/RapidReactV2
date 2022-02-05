// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Drive extends SubsystemBase {

  private CANSparkMax m_frontLeftMotor;
  private CANSparkMax m_frontRightMotor;
  private CANSparkMax m_rearLeftMotor;
  private CANSparkMax m_rearRightMotor;
  private MecanumDrive mecanumDrive;

  public Joystick m_joystickDriver;

  public Drive(Joystick joystickDriver) {
    m_joystickDriver = joystickDriver;

    m_frontLeftMotor = RobotContainer.frontLeft;
    m_frontLeftMotor.setInverted(false);

    m_frontRightMotor = RobotContainer.frontRight;
    m_frontRightMotor.setInverted(false);

    m_rearLeftMotor = RobotContainer.rearLeft;
    m_rearLeftMotor.setInverted(false);

    m_rearRightMotor = RobotContainer.rearRight;
    m_rearRightMotor.setInverted(false);

    mecanumDrive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor,
        m_frontRightMotor, m_rearRightMotor);
    mecanumDrive.setSafetyEnabled(true);
    mecanumDrive.setExpiration(0.1);
    mecanumDrive.setMaxOutput(1.0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

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
    m_frontLeftMotor.set(-0.5);
    m_rearLeftMotor.set(-0.5);
    m_frontRightMotor.set(-0.5);
    m_rearRightMotor.set(-0.5);
  }

  public void driveStop() {
    m_frontLeftMotor.set(0);
    m_rearLeftMotor.set(0);
    m_frontRightMotor.set(0);
    m_rearRightMotor.set(0);
  }

}
