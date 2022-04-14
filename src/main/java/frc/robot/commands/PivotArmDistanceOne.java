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
    inch_goal = inches; //inch_goal is used for climb goal (goal in inches)
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.climbPID = RobotContainer.climbMotor.getPIDController(); //References the climb PID from Robot Container
    RobotContainer.climbPID.setP(Constants.CLIMB_kP); //Sets P value
    RobotContainer.climbPID.setI(Constants.CLIMB_kI); //Sets I value
    RobotContainer.climbPID.setD(Constants.CLIMB_kD); //Sets D value
    RobotContainer.climbPID.setOutputRange(Constants.CLIMB_MIN_OUTPUT, Constants.CLIMB_MAX_OUTPUT); //Sets the max and min values that PID needs to be in range of
    climbGoal = m_climb.inchesToRevs(inch_goal); //climbGoal is used to check for the distance you want it to go to (it is not very accurate)
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //This references the "extendPivotArmDistanceOne(climbGoal)" from the climb subsystem, executing it.
    m_climb.extendPivotArmDistanceOne(climbGoal);
    // SmartDashboard.putNumber("Goal to travel to", climbGoal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // References the method "stop()" from the climb subsystem. This will stop the motors once the command ends.
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // This checks when the pivot arms hits the highest extension of the climb's arms.
    // The method "getClimbRevs()" is used to find the climb encoder's number. 
    // || means "or".
    // If either the climb encoder's number is higher than/equal to distance1 or "isClimbAtTop() == true", then it will return true. 
    // Thus, it would cause the command to finish.
    // If not, then it will return false and keep running the command until it gets true.
    if (m_climb.getClimbRevs() >= Constants.climbDistance1 || m_climb.isClimbAtTop() == true) {
      return true;
    } else {
      return false;
    }
  }
}
