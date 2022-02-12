// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import edu.wpi.first.wpilibj.PWM;

public class Shooter extends SubsystemBase {
  //Motor and solenoid are created privately for use in this class
  private SparkMaxPIDController m_shooterPID;
  private DoubleSolenoid m_shooterBallRelease;
  private double distance;
  private double atotal;
  private double ty;

  public Shooter() {
    //PID and solenoid information referenced from RobotContainer
    m_shooterPID = RobotContainer.shooterMotorPID;
    m_shooterBallRelease = RobotContainer.shooterBallRelease;
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

    double ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    double ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
    double tx = xEntry.getDouble(0.0); 
    double tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)

    SmartDashboard.putNumber("Limelight Area", ta);
    SmartDashboard.putNumber("Limelight X", tx);
    SmartDashboard.putNumber("Limelight Y", ty);
    SmartDashboard.putNumber("Limelight V", tv);
    // This method will be called once per scheduler run
  }
  public void ShooterRun(){ //Function created to run the motor-- referenced later in ShooterRun.java
    m_shooterPID.setReference(Constants.shooterSpeed, ControlType.kVelocity);
  }
  public void ShooterStop(){
   m_shooterPID.setReference(0.0, ControlType.kVelocity);
  }

  public void ReleaseGate(){
    m_shooterBallRelease.set(DoubleSolenoid.Value.kForward);
  }
  public void RetractGate(){
    m_shooterBallRelease.set(DoubleSolenoid.Value.kReverse);
  }
  public void findDistance(){
    atotal = 30 + ty;
    //distance = (104-15.3)/Math.tan(atotal); (official values, I'm gonna add testing values)
    distance = (30-14)/Math.tan(0 + ty);
    SmartDashboard.putNumber("Distance", distance);
  }
}