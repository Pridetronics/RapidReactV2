// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
// import frc.robot.Constants;
// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.drive.MecanumDrive; 
import frc.robot.commands.ExampleCommand;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

import frc.robot.commands.FlyWheelMove;
import frc.robot.subsystems.FlyWheel;

//import edu.wpi.first.wpilibj.PWM; //FOR BUDDY BOT


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here... Examples below
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  //Note to self: Create mappings here. Declare the motor controllers (but do not assign values). Same with buttons

  // public static VictorSP frontLeft;
  // public static VictorSP rearLeft;
  // public static VictorSP frontRight;
  // public static VictorSP rearRight;

  public static CANSparkMax flyWheelMotor;
  //public static PWM flyWheelMotorBB; //Fly wheel for buddy bot

  public JoystickButton flyWheelButton;
  public Joystick m_stick;
  public static FlyWheel flyWheel;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //Assign values for the motors, and for the joysticks. Do not do button bindings, this is below. 
    

    m_stick = new Joystick(Constants.kJoystickChannel);

    // frontLeft = new VictorSP(Constants.kFrontLeftChannel); //Mecanum stuff is currently commented out. Conflicts with the PWM mc for BuddyBot
    // rearLeft = new VictorSP(Constants.kRearLeftChannel);

    // frontRight = new VictorSP(Constants.kFrontRightChannel);
    // frontRight.setInverted(true);
    // rearRight = new VictorSP(Constants.kRearLeftChannel);
    // rearRight.setInverted(true);

    flyWheelMotor = new CANSparkMax(Constants.kFlyWheelChannel, MotorType.kBrushless); //Currently unused. This is code for the real testing, as opposed to just the prototype
    flyWheelMotor.setInverted(true);

    //flyWheelMotorBB = new PWM(Constants.kFlyWheelChannel);

    flyWheel = new FlyWheel();
    
    configureButtonBindings();
  }

  private void configureButtonBindings() {

    flyWheelButton = new JoystickButton(m_stick, Constants.flyWheelButtonNumber); 
    flyWheelButton.whileHeld(new FlyWheelMove(flyWheel));

  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
