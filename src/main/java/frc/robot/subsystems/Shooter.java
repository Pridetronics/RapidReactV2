// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Shooter extends SubsystemBase {
  private CANSparkMax m_shooterMotor;
  private RelativeEncoder m_shooterEncoder;
  private SparkMaxPIDController m_shooterPID;
  private Servo m_shooterServo;
  //Math values for use in Limelight
  private double aTotal; 
  private double hTotal; 
  private double aTan;
  private double initialDistance;
  private double distance;
  private double distanceInFeet;
  private double roundedDistance;
  private double ty;
  public static double tx;
  static double tv;
  private double ta;
  private double RPMCheck;

  public Shooter() {
    //PID and solenoid information referenced from RobotContainer
    m_shooterMotor = RobotContainer.shooterMotor;
    m_shooterEncoder = RobotContainer.shooterEncoder;
    m_shooterPID = RobotContainer.shooterMotorPID;
    // m_shooterBallRelease = RobotContainer.shooterBallRelease;
    m_shooterServo = RobotContainer.shooterServo;
    //m_shooterServo.setBounds(1300, 1150, 1150, 1150, 1000);
    zeroEncoders();
  }

  @Override
  public void periodic() 
  {
    NetworkTableInstance inst = NetworkTableInstance.getDefault(); //get a reference to the subtable called "datatable"
    NetworkTable table = inst.getTable("limelight");
    inst.startClientTeam(3853); // Make sure you set this to your team number
    inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
    NetworkTableEntry yEntry = table.getEntry("ty"); 
    NetworkTableEntry xEntry = table.getEntry("tx");
    NetworkTableEntry aEntry = table.getEntry("ta");
    NetworkTableEntry vEntry = table.getEntry("tv");

    ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
    tx = xEntry.getDouble(0.0); //Horizontal Offset From Crosshair to Target (-27.5 degrees to 27.5 degrees)
    tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)

    SmartDashboard.putNumber("Limelight Area", ta); //Displays base limelight values to Shuffleboard
    SmartDashboard.putNumber("Limelight X", tx);
    SmartDashboard.putNumber("Limelight Y", ty);
    SmartDashboard.putNumber("Limelight V", tv);
    // This method will be called once per scheduler run
  }

  public void zeroEncoders() //Resets Encoders
  {
    m_shooterEncoder.setPosition(0);
  }

  public void LowSpeedShooter()
  {
    m_shooterPID.setReference(Constants.lowShooterSpeed, ControlType.kVelocity);
    SmartDashboard.putNumber("ShooterRPM", m_shooterEncoder.getVelocity());
  }
  public void HighSpeedShooter()
  { 
    m_shooterPID.setReference(Constants.highShooterSpeed, ControlType.kVelocity);
    SmartDashboard.putNumber("Shooter RPM", m_shooterEncoder.getVelocity());
  }

  public void ShooterStop() //Stops motors
  { 
    m_shooterMotor.set(0);  
  }

  public void OpenGateLow()
  {
    if (m_shooterEncoder.getVelocity() >= Constants.lowShooterSpeed)
    {
      new WaitCommand(7);
      m_shooterServo.setRaw(1000);
    }
  }

  public void OpenGateHigh()
  {
    if (m_shooterEncoder.getVelocity() >= Constants.highShooterSpeed)
    {
      new WaitCommand(7);
      m_shooterServo.setRaw(1000);
    }
  }

  public void CloseGate()
  {
    m_shooterServo.setRaw(1300);
  }
}