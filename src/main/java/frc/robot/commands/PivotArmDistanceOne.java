// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class PivotArmDistanceOne extends CommandBase {
  /** Creates a new PivotArmDistanceTwo. */
  Climb m_climb;

  double climbGoal;
  double inch_goal;

  public PivotArmDistanceOne(Climb climb, double inches) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    
    addRequirements(m_climb);
    inch_goal = inches;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.climbPID = RobotContainer.climbMotor.getPIDController();
    RobotContainer.climbPID.setP(Constants.CLIMB_kP);
    RobotContainer.climbPID.setI(Constants.CLIMB_kI);
    RobotContainer.climbPID.setD(Constants.CLIMB_kD);
    RobotContainer.climbPID.setOutputRange(Constants.CLIMB_MIN_OUTPUT, Constants.CLIMB_MAX_OUTPUT);
    climbGoal = m_climb.inchesToRevs(inch_goal);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climb.extendPivotArmDistanceOne(climbGoal);
    // SmartDashboard.putNumber("Goal to travel to", climbGoal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_climb.getClimbRevs() >= Constants.climbDistance1 || m_climb.isClimbAtTop() == true) {
      // System.out.println("CLIMBPID END");
      return true;
    } else {
      return false;
    }
  }
}
