// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
* This is the shooter subsytem. Here are the functions for controlling
* the shooter on the 2022 robot. This includes one motor (plus its built in
* encoder and PID Controller) as well as a Servo. Other functions involving
* complex shooter use can be found in Vision(AUTOSHOOTER)
*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;

//Imported relevant hardware to reference when creating functions. 
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Shooter extends SubsystemBase {
  //Hardware is created so it can be used within this subsytem. 
  private CANSparkMax m_shooterMotor;
  private RelativeEncoder m_shooterEncoder;
  private SparkMaxPIDController m_shooterPID;
  private Servo m_shooterServo;
 
  public Shooter() {
    //Motor, Encoder, PID, and Servo referenced from RobotContainer for use in subsytem.
    m_shooterMotor = RobotContainer.shooterMotor;
    m_shooterEncoder = RobotContainer.shooterEncoder;
    m_shooterPID = RobotContainer.shooterMotorPID;
    m_shooterServo = RobotContainer.shooterServo;
    zeroEncoders(); //Resets the encoders (for accuracy-- better explained in Climb)
  }

  @Override
  public void periodic() 
  {}

  //zeroEncoders: Resets Encoders-- Calls when subsytem is created. 
  public void zeroEncoders() 
  {
    m_shooterEncoder.setPosition(0);
  }

  //LowSpeedShooter: Sets shooter motor to 2500 RPM. Also displays this on SmartDashboard
  public void LowSpeedShooter() 
  {
    /* 
    *  The line below sets the speeed for the motor using a PID loop. The first value is the RPM desired (human input),
    *  and the second number is the ControlType (in this case velocity), which tells the encoder (Position is the other 
    *  type-- see climb)
    */
    m_shooterPID.setReference(Constants.lowShooterSpeed, ControlType.kVelocity);
    SmartDashboard.putNumber("Shooter RPM", m_shooterEncoder.getVelocity());
  }

  //HightSpeedShooter: Sets shooter motor to 5000 RPM and displays on SmartDashboard
  public void HighSpeedShooter() 
  { 
    m_shooterPID.setReference(Constants.highShooterSpeed, ControlType.kVelocity);
    SmartDashboard.putNumber("Shooter RPM", m_shooterEncoder.getVelocity());
  }

  //ShooterStop: Stops Shooter Motor
  public void ShooterStop() 
  { 
    m_shooterMotor.set(0); 
    //Set measures in voltage-- power outputted to the motor. In this case, 0. (Range = -1 to 1) 
  }

  /* 
  *  OpenGateLow checks for a certain velocity (2500 in this case) and if the conditions are met, 
  *  moves the servo gate to a position in which the ball can move through. Both open gate commands
  *  ensure the motor is able to reach the proper speed so the ball is launched properly.
  */
  public void OpenGateLow() 
  {
    m_shooterServo.setRaw(Constants.shooterServoOpenPosition); //This is measured in ms-- see Pridetronics documentation for more information
    
  }

  /* 
  *  OpenGateHigh checks for a certain velocity (5000 in this case) and if the conditions are met, 
  *  moves the servo gate to a position in which the ball can move through.
  */
  public void OpenGateHigh()
  {
    new WaitCommand(7); //Test Code. Remove this in certain places
    m_shooterServo.setRaw(Constants.shooterServoOpenPosition);
  }

  //CloseGate: Moves servo to a position in which the ball is unable to be released.
  public void CloseGate()
  {
    m_shooterServo.setRaw(Constants.shooterServoClosedPosition);
  }
}