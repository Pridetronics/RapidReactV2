package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climb;

public class DescendPivotArms extends CommandBase {
  private Climb m_climb;
  /** Creates a new DescendPivotArms. */

  //Passing the object climb
  public DescendPivotArms(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute(){
      //Brings down the telescopic rod
    if(m_climb.isClimbAtBottom() == false){
      m_climb.raisePivotArms(Constants.InverseClimbMotorSpeed);
    }
    else{
      m_climb.raisePivotArms(0.0);
      
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //Stops the telescopic motor at the end of the command
    m_climb.raisePivotArms(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //Ends the command when the limit switch closes
    boolean control = false;
    if(m_climb.isClimbAtBottom() == true){
      control = true;
    }
    return control; 
  }
}
