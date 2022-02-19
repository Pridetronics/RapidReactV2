// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climb;

public class RaisePivotArms23Inches extends CommandBase {
  /** Creates a new RaisePivotArmsNineInches. */
  private Climb m_climb;
  private Climb m_climbEncoder;
 
  public RaisePivotArms23Inches(Climb climb, Climb m_ClimbEncoder) {
    // Use addRequirements() here to declare subsystem dependencies.
    //References climb object from Climb subsystem. References encoders from climb
    m_climb = climb;
    m_climbEncoder = m_ClimbEncoder;
    ((RelativeEncoder) m_climbEncoder).setPositionConversionFactor(Constants.kEncoderPositionConversionFactor);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Resets the number every time the raise pivot arm command is running
    ((RelativeEncoder) m_climbEncoder).setPosition(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ((RelativeEncoder) m_climbEncoder).getPositionConversionFactor();
    if (((RelativeEncoder) m_climbEncoder).getPositionConversionFactor() >= 9){
      m_climb.raisePivotArms(Constants.ClimbMotorSpeed);
    }
    else{
      m_climb.raisePivotArms(0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.raisePivotArms(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
