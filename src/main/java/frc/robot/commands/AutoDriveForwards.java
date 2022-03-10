// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class AutoDriveForwards extends CommandBase {
  Drive m_drive;

  /** Creates a new AutoDriveForwards. */
  public AutoDriveForwards(Drive drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    // m_shooter = new Shooter();
    // addRequirements(m_shooter);
    // m_shooterEncoder = RobotContainer.shooterEncoder;

    m_drive = drive; // can this be null??
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // RobotContainer.climbPID = RobotContainer.climbMotor.getPIDController();
    // RobotContainer.climbPID.setP(Constants.CLIMB_kP);
    // RobotContainer.climbPID.setI(Constants.CLIMB_kI);
    // RobotContainer.climbPID.setD(Constants.CLIMB_kD);

    m_drive.m_frontLeftPIDController.setP(Constants.DRIVE_kP);
    m_drive.m_frontRightPIDController.setP(Constants.DRIVE_kP);
    m_drive.m_rearLeftPIDController.setP(Constants.DRIVE_kP);
    m_drive.m_rearRightPIDController.setP(Constants.DRIVE_kP);



  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.autoDriveFwd();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
