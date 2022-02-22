// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase {
  /** Creates a new Climb. */

  public static int stageLevel;

  public Climb() {
    stageLevel = 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Stage Level", stageLevel);
  }

  public void increaseStage() {
    stageLevel++;
  }

  public void raiseOuterArms() {
    System.out.println("RAISING OUTER ARMS");
  }

  public void lowerOuterArms() {
    System.out.println("LOWERING OUTER ARMS");
  }

  public void testForStage() {
    if (stageLevel == 0) {
      System.out.println("Ready for next Stage? continue with 'Start'");
    } else if (stageLevel == 1) {
      raiseOuterArms();
    } else if (stageLevel == 2) {
      System.out.println("Ready for next Stage? continue with 'Start'");
    } else if (stageLevel == 3) {
      lowerOuterArms();
    } else if (stageLevel == 4) {
      System.out.println("Ready for next Stage? continue with 'Start'");
    }
  }
}
