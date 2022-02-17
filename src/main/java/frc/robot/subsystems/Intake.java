// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class Intake extends SubsystemBase {
  private Compressor m_intakeCompressor;
  private Solenoid m_intakePiston;
  private Talon m_intakeMotor;

  public Intake() {
    m_intakeCompressor = RobotContainer.intakeCompressor;
    m_intakePiston = RobotContainer.intakePiston;
    m_intakeMotor = RobotContainer.intakeMotor;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void runIntakeMotor() {
    m_intakeMotor.set(0.6);
  }

  public void stopIntakeMotor() {
    m_intakeMotor.set(0);
  }

  public void extendIntake() {
    m_intakePiston.set(true);
  }

  public void retractIntake() {
    m_intakePiston.set(false);
  }
}
