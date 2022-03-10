// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.PivotArmDescendDistance;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.subsystems.Climb;

public class ClimbButtonSequence extends CommandBase {
  /** Creates a new ClimbButtonSequence. */
  private Climb m_climb;
  private String m_climbMessage;
  private SequentialCommandGroup climbSequenceOne;
  private Command m_pivotArmDescendDistance;
  private SequentialCommandGroup climbSequenceThree;

  public ClimbButtonSequence(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    m_climbMessage = Climb.climbMessage;
    addRequirements(m_climb);
  }

  public void climbSequence() {
    if (Climb.climbValue <= -1) {
      Climb.climbValue = 0;
    } else if (Climb.climbValue == 0) {
      m_climbMessage = "Ready to raise Pivot Arms?";
      SmartDashboard.putString("Climb", m_climbMessage);
    } else if (Climb.climbValue == 1) { // Pivot arm fully extended and piston extends
      // new SequentialCommandGroup(
      // new PivotArmDistanceOne(m_climb, Constants.climbDistance1),
      // new InstantCommand(m_climb::pistonRelease, m_climb));
      climbSequenceOne.schedule();
      m_climbMessage = "Ready to descend Pivot Arms?";
      SmartDashboard.putString("Climb", m_climbMessage);
    } else if (Climb.climbValue == 2) { // Pivot arm fully retracted
      // new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance);
      m_pivotArmDescendDistance.schedule();
      m_climbMessage = "Ready to raise Pivot Arms?";
      SmartDashboard.putString("Climb", m_climbMessage);
    } else if (Climb.climbValue == 3) {
      // new SequentialCommandGroup(
      // new PivotArmDistanceTwo(m_climb, Constants.climbDistance2),
      // new InstantCommand(m_climb::pistonRetract, m_climb),
      // new PivotArmDistanceThree(m_climb, Constants.climbDistance3),
      // new InstantCommand(m_climb::pistonRelease, m_climb));
      climbSequenceThree.schedule();
      m_climbMessage = "Ready to descend Pivot Arms?";
      SmartDashboard.putString("Climb", m_climbMessage);
    } else if (Climb.climbValue == 4) { // Pivot arm fully retracted
      // new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance);
      m_pivotArmDescendDistance.schedule();
      m_climbMessage = "Ready to raise Pivot Arms?";
      SmartDashboard.putString("Climb", m_climbMessage);
    } else if (Climb.climbValue == 5) { // Pivot arm extends 9 inches, extends pistons, pivot arm extends 23 inches,
      // retract pistons
      // new SequentialCommandGroup(
      // new PivotArmDistanceTwo(m_climb, Constants.climbDistance2),
      // new InstantCommand(m_climb::pistonRetract, m_climb),
      // new PivotArmDistanceThree(m_climb, Constants.climbDistance3),
      // new InstantCommand(m_climb::pistonRelease, m_climb));
      climbSequenceThree.schedule();
      m_climbMessage = "Ready to descend Pivot Arms?";
      SmartDashboard.putString("Climb", m_climbMessage);
    } else if (Climb.climbValue == 6) { // Pivot arm fully retracted
      // new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance);
      m_pivotArmDescendDistance.schedule();
      m_climbMessage = "Press Add Button Once Done, Climb Finished";
      SmartDashboard.putString("Climb", m_climbMessage);
    } else { // Ask about piston postion for when climb isn't being used. Extended or
             // retracted?
      // new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance);
      m_pivotArmDescendDistance.schedule();
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climbSequenceOne = new SequentialCommandGroup(new PivotArmDistanceOne(m_climb, Constants.climbDistance1),
        new InstantCommand(m_climb::pistonRelease, m_climb));
    m_pivotArmDescendDistance = new PivotArmDescendDistance(m_climb, Constants.climbDescendDistance);
    climbSequenceThree = new SequentialCommandGroup( // Pivot arm extends 9 inches, extends pistons, pivot arm extends
                                                     // 23 inches, retract pistons
        new PivotArmDistanceTwo(m_climb, Constants.climbDistance2),
        new InstantCommand(m_climb::pistonRetract, m_climb),
        new PivotArmDistanceThree(m_climb, Constants.climbDistance3),
        new InstantCommand(m_climb::pistonRelease, m_climb));

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.intakePiston.set(DoubleSolenoid.Value.kForward);
    climbSequence();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Climb.climbValue == 7) {
      return true;
    } else {
      return false;
    }
  }
}
