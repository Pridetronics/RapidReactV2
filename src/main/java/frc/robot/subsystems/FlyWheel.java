// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.PWM;


public class FlyWheel extends SubsystemBase {
  // private CANSparkMax m_flyWheel;
  private PWM m_flyWheelBB;

  public FlyWheel() {
    m_flyWheelBB = RobotContainer.flyWheelMotorBB;
    //m_flyWheel = RobotContainer.flyWheelMotor;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void flyWheelRun(){
    // m_flyWheel.set(1);
    m_flyWheelBB.setSpeed(1);
  }
}
