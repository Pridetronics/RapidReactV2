// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
*  This is the intake subsystem. It outlines the use of the intake piston 
*  and the intake motor. 
*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

//Hardware
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class Intake extends SubsystemBase {
  private VictorSP m_intakeMotor;
  private DoubleSolenoid m_intakePiston;

  public Intake() {
    m_intakeMotor = RobotContainer.intakeMotor;
    m_intakePiston = RobotContainer.intakePiston;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  //runIntakeMotor: This sets the intake motor to run at 70% voltage. This causes the wheels to spin and intake balls
  public void runIntakeMotor() {
    m_intakeMotor.set(.7);
  }

  //stopIntakeMotor: Sets the intale motor to run at 0%.
  public void stopIntakeMotor() {
    m_intakeMotor.set(0);
  }

  //extendIntake: Puts the intake down to be able to pull in balls (On)
  public void extendIntake() { // Down
    m_intakePiston.set(DoubleSolenoid.Value.kReverse);
  }

  //retractIntake: Pulls the intake up, into rest and starting position.  (Off)
  public void retractIntake() { // Up
    m_intakePiston.set(DoubleSolenoid.Value.kForward);
  }
}