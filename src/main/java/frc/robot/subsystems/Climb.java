// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
*  This is the climb system. Within are functions for the use of the climb
*  motors (encoders and PID included) and the limit switches. This is likely
*  the most complex system that there is on the 2022 robot. Read the bottom for
*  a more in depth explanation of the system in place. 
*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.Constants;

//Hardware
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climb extends SubsystemBase {
  private CANSparkMax m_climbMotor; // Initalizes motor
  public RelativeEncoder m_climbEncoder; // Initalizes encoder
  private static SparkMaxPIDController m_climbPID; // Initalizes PID
  private static DoubleSolenoid m_climbPiston; // Initalizes pistons
  private DigitalInput m_lowerClimbLimitSwitch; // Initalizes lower limit switch

  public Climb() 
  {
    m_climbMotor = RobotContainer.climbMotor; // References the object from Robot Container
    m_climbEncoder = RobotContainer.climbEncoder; // References the object from Robot Container
    m_climbPID = RobotContainer.climbPID;
    m_climbPiston = RobotContainer.climbPiston; // References the object from Robot Container
    m_lowerClimbLimitSwitch = RobotContainer.lowerClimbLimitSwitch; // Limit switch to test if Pivot Arms has descended
    // m_climbEncoder.setPositionConversionFactor(Constants.kEncoderPositionConversionFactor);
    zeroEncoder();
  }

  // Gets called every 20 miliseconds
  // 0 is closed, 1 is open
  public void periodic() {
    if (true) { // True will enable -- (If false, won't show on Smart Dashboard, meant for resource management)
      //Limit Switches display on SmartDashboard
      SmartDashboard.putBoolean("Lower Climb Limit Switch", RobotContainer.lowerClimbLimitSwitch.get());
      // Climb Encoder display on SmartDashboard
      SmartDashboard.putNumber("Climb Encoder", RobotContainer.climbEncoder.getPosition()); // Measures in units of rotations by default
    }
  }

  /*
  * zeroEncoder: Sets encoders to 0, essential with climb. If the encoders were not zeroed before the climb
  * sequence was run, there'd be a potential for it to misread the values andoverextend (therefore
  * breaking the climb). When using encoders, especially with position PID, you want to ensure that
  * some action is taken to 0 the encoder.
  */
  public void zeroEncoder() {
    m_climbEncoder.setPosition(0.0);
  }

  // stop: Sets climb motors to 0% (off)
  public void stop() 
  {
    m_climbMotor.set(0);
  }

  /*
  * extendPivotArmDistanceOne: Uses position control to reach a certain height for the first stage of climb. 
  * Disclaimer, the numbers are not precise and were found through testing. If we figure out where the numbers 
  * come from, this will be removed. 
  */
  public void extendPivotArmDistanceOne(double climbGoal) {
    m_climbPID.setReference(Constants.climbDistance1, ControlType.kPosition);
  }

  // extendPivotArmDistanceTwo: Uses position control to reach a certain height for the second stage of climb.
  public void extendPivotArmDistanceTwo(double climbGoal) {
    m_climbPID.setReference(Constants.climbDistance2, ControlType.kPosition);
  }

  // extendPivotArmDistanceThree: Uses position control to reach a certain height for the third stage of climb.
  public void extendPivotArmDistanceThree(double climbGoal) {
    m_climbPID.setReference(Constants.climbDistance3, ControlType.kPosition);
  }

  // descendPivotArmDistance: Uses position control to reach a certain height (negative), to pull up the robot.
  public void descendPivotArmDistance(double climbGoal) {
    m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
  }

  // ExtendArm: Raises arm at 40% voltage. Used in active climb.
  public void ExtendArm() {
    m_climbMotor.set(.4);
  }

  // DescendArm: Pulls are down at 100% voltage. Used in active climb.
  public void DescendArm() {
    m_climbMotor.set(-1);
  }

  // This is the inches per revolutions conversion. This is used for PID. It is not very accurate. It is off by x5.
  public double inchesToRevs(double INCHES) {
    return INCHES / Constants.kEncoderPositionConversionFactor;
  }

  // This is to get the current climb encoder number.
  public double getClimbRevs() {
    return m_climbEncoder.getPosition();
  }

  // pistonRelease: Moves piston forward. (Extends)
  public void pistonRetract() {
    m_climbPiston.set(DoubleSolenoid.Value.kForward);
  }

  // pistonRetract: Moves piston backwards. (Retracts)
  public void pistonRelease() {
    m_climbPiston.set(DoubleSolenoid.Value.kReverse);
  }

  // isClimbAtTop: Soft limit switch to ensure that the limit switch does not run too high.
  public boolean isClimbAtTop() {
    // Returns the state of the upper limit switch
    boolean isClimbAtTop;
    m_climbEncoder.getPosition();
    if (m_climbEncoder.getPosition() >= Constants.CLIMB_MAX_OUTPUT) {
      isClimbAtTop = true;
    } 
    else 
    {
      isClimbAtTop = false;
    }
    return isClimbAtTop;
  }

  /*
  * isClimbAtBottom: Checks the activation of the limit switch (hardware) and returns a true
  * or false. This looks a little contradictory, and because of the nature of the hardware
  * this exists as a wrapper class to make it readable for the programmer. While the hardware
  * reads it the same, it is much easier to know that the limit switch is active == true, as
  * opposed to being false as it would naturally read.
  */

  public boolean isClimbAtBottom() { // Might have to switch true/false because of magnetic limit switch
    // Returns the state of the lower limit switch
    boolean isClimbAtBottom;
    if (m_lowerClimbLimitSwitch.get() == false) {
      isClimbAtBottom = true;
    } 
    else {
      isClimbAtBottom = false;
    }
    return isClimbAtBottom;
  }

  /*
  * Used for climb honing. ClimbUpSlowly sets the motor to 10% and if the limit
  * switch is active
  * it will raise it off the switch and zero the encoder (creating the starting
  * position)
  */

  public void ClimbUpSlowly() {
    if (isClimbAtBottom() == true) {
      System.out.println("Go up slowly");
      m_climbMotor.set(0.1);
    } 
    else {
      stop();
      zeroEncoder();
    }
  }

  /*
  * Also for climb honing. If the limit switch is not hit, the motor will run at negative 10% until it hits 
  * the limit switch before zeroing. Ensures the proper position is available for the climb sequence.
  */
  public void ClimbDownSlowly() {
    if (isClimbAtBottom() == false) {
      System.out.println("Climb DOwn slowly");
      m_climbMotor.set(-0.1);
    } 
    else {
      stop();
      zeroEncoder();
    }
  }

  public void DancyArmsDown(){
    m_climbMotor.set(-0.5);
  }
  public void DancyArmsUp(){
    m_climbMotor.set(0.5);
  }
}