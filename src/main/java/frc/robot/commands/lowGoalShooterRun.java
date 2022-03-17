// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
<<<<<<< HEAD
import frc.robot.subsystems.Shooter;
=======
<<<<<<<< HEAD:src/main/java/frc/robot/commands/AutoDriveForwards.java
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
========
import frc.robot.subsystems.Shooter;
>>>>>>>> origin/Katie:src/main/java/frc/robot/commands/lowGoalShooterRun.java
>>>>>>> origin/Katie

public class lowGoalShooterRun extends CommandBase {
  private Shooter m_shooter;
  public lowGoalShooterRun(Shooter shooter) {
    m_shooter = shooter;

    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
<<<<<<< HEAD
  public void initialize() {}
=======
  public void initialize() {
<<<<<<<< HEAD:src/main/java/frc/robot/commands/AutoDriveForwards.java
    // RobotContainer.climbPID = RobotContainer.climbMotor.getPIDController();
    // RobotContainer.climbPID.setP(Constants.CLIMB_kP);
    // RobotContainer.climbPID.setI(Constants.CLIMB_kI);
    // RobotContainer.climbPID.setD(Constants.CLIMB_kD);

    m_drive.m_frontLeftPIDController.setP(Constants.DRIVE_kP);
    m_drive.m_frontRightPIDController.setP(Constants.DRIVE_kP);
    m_drive.m_rearLeftPIDController.setP(Constants.DRIVE_kP);
    m_drive.m_rearRightPIDController.setP(Constants.DRIVE_kP);



========
    System.out.println("LowGoalShooterRun Running");
>>>>>>>> origin/Katie:src/main/java/frc/robot/commands/lowGoalShooterRun.java
  }
>>>>>>> origin/Katie

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.LowSpeedShooterMode();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.ShooterStop();
    m_shooter.CloseGate();
<<<<<<< HEAD
=======
    System.out.println("LowGoalShooter Finished");
>>>>>>> origin/Katie
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
