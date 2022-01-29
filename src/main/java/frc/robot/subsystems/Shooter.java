// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.PWM;


public class Shooter extends SubsystemBase {
  private CANSparkMax m_shooterMotor;
  //private PWM m_flyWheelBB;

  public Shooter() {
    //m_flyWheelBB = RobotContainer.flyWheelMotorBB;
    m_shooterMotor = RobotContainer.shooterMotor;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void ShooterRun(){
    m_shooterMotor.set(0.9);
    //m_flyWheelBB.setSpeed(1); //Full Speed for PWM motor
  }
  public void ShooterStop(){
    m_shooterMotor.set(0);
    //m_flyWheelBB.setSpeed(0);
  }
}
