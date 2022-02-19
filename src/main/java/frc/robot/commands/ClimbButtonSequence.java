// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DescendPivotArms;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;

public class ClimbButtonSequence extends CommandBase {
  /** Creates a new ClimbButtonSequence. */
  Climb m_climb;
  private static DescendPivotArms DescendPivotArms;
  private int m_climbValue;

  
  public ClimbButtonSequence(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    addRequirements(m_climb);
    m_climbValue = RobotContainer.climbValue;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
  
  public void sequence1(Climb climb){new SequentialCommandGroup(
    new RaisePivotArms(climb), //Arms are fully extended until upper limit switch is hit
    new ClimbPiston(climb)); //Pistons are extended
  }

  public void sequence2(){
    new SequentialCommandGroup(DescendPivotArms); //Arms are fully retracted until lower limit switch is hit
  }

  public void sequence3(Climb climb){new SequentialCommandGroup(
    new RaisePivotArmsNineInches(climb, climb), //Arms are extended 9 inches
    new ClimbPiston(climb), //Pistons retracted
    new RaisePivotArms23Inches(climb, climb), //Arms are extended to Y (23 inches)
    new ClimbPiston(climb)); //Pistons extended 
  }

    //Need to add encoders, when it is at the bottom you have to make sure the encoders is at 0.
    //There is also no need to do anything for the stationary arm
    //Winches is just the one motor going forward and reverse.

    //Add in checking errors in all the isFinish (commands)

    /*Idea: I need to make it so that the motor runs to get to a certain length, so I can use the encoders.
    From those encorders, I can make an if then statement of when to use certain RaisePivotArms instead
    of just inputting random speeds*/

// Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_climbValue == 1){
        //Runs sequence 1
      sequence1(m_climb);
    }

    if (m_climbValue == 2){
        //Runs sequence 2
      sequence2();
    }

    if (m_climbValue == 3) {
        //Runs sequence 3
      sequence3(m_climb);
    }

    if (m_climbValue == 4) {
        //Runs sequence 2
      sequence2();
    }

    if (m_climbValue == 5) {
      //Runs sequence 3
      sequence3(m_climb);
    }

    if (m_climbValue == 6){
      //Runs sequence 2
      sequence2();
    }

    if (m_climbValue >= 7){
      isFinished();
    }
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
