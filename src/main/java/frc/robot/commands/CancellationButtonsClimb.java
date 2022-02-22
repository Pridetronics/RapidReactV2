// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.button.Button;

/** Add your docs here. */
public class CancellationButtonsClimb extends Button {
  private final Button button1;
  private final Button button2;  

  public CancellationButtonsClimb (Button buttonOne, Button buttonTwo){
    button1 = buttonOne;
    button2 = buttonTwo;
  }
    @Override
    public boolean get() {
      return button1.get() && button2.get();
    }

}
