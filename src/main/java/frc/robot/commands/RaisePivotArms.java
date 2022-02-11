package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climb;

public class RaisePivotArms extends CommandBase {
  /** Creates a new RaisePivotArms. */
  private Climb m_climb;

  public RaisePivotArms(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    //References climb object from Robot Container
    m_climb = climb;
    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    if(m_climb.isClimbAtTop() == false){
      m_climb.raisePivotArms(Constants.ClimbMotorSpeed);
    }
    else{
      m_climb.raisePivotArms(0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted){
    m_climb.raisePivotArms(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished(){
    return false;
  }
}
