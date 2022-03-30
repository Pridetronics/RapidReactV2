// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Shooter extends SubsystemBase {
  private CANSparkMax m_shooterMotor;
  //Motor and solenoid are created privately for use in this class
  private SparkMaxPIDController m_shooterPID;
  // private DoubleSolenoid m_shooterBallRelease;
  private RelativeEncoder m_shooterEncoder;
  private Servo m_shooterServo;
  //Math values for use in Limelight
  private double aTotal; 
  private double hTotal; 
  private double aTan;
  private double initialDistance;
  private double distance;
  private double distanceInFeet;
  private double roundedDistance;
  private double ty;
  public static double tx;
  static double tv;
  private double ta;
  private double RPMCheck;

  public Shooter() {
    //PID and solenoid information referenced from RobotContainer
    m_shooterMotor = RobotContainer.shooterMotor;
    m_shooterPID = RobotContainer.shooterMotorPID;
    // m_shooterBallRelease = RobotContainer.shooterBallRelease;
    m_shooterServo = RobotContainer.shooterServo;
    //m_shooterServo.setBounds(1300, 1150, 1150, 1150, 1000);
    m_shooterEncoder = RobotContainer.shooterEncoder;
    zeroEncoders();
  }

  @Override
  public void periodic() 
  {
    NetworkTableInstance inst = NetworkTableInstance.getDefault(); //get a reference to the subtable called "datatable"
    NetworkTable table = inst.getTable("limelight");
    inst.startClientTeam(3853); // Make sure you set this to your team number
    inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
    NetworkTableEntry yEntry = table.getEntry("ty"); 
    NetworkTableEntry xEntry = table.getEntry("tx");
    NetworkTableEntry aEntry = table.getEntry("ta");
    NetworkTableEntry vEntry = table.getEntry("tv");

    ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
    tx = xEntry.getDouble(0.0); //Horizontal Offset From Crosshair to Target (-27.5 degrees to 27.5 degrees)
    tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)

    SmartDashboard.putNumber("Limelight Area", ta); //Displays base limelight values to Shuffleboard
    SmartDashboard.putNumber("Limelight X", tx);
    SmartDashboard.putNumber("Limelight Y", ty);
    SmartDashboard.putNumber("Limelight V", tv);
    // This method will be called once per scheduler run
  }
  // public void HighSpeedShooterMode(){ //Very basic (a manual mode in case the Limelight/Automatic Shooter is faulty)
  //   m_shooterPID.setReference(Constants.highShooterSpeed, ControlType.kVelocity);
  //   SmartDashboard.putNumber("Shooter RPM", m_shooterEncoder.getVelocity());
  // }
  public void zeroEncoders() //Resets Encoders
  {
    m_shooterEncoder.setPosition(0);
  }

  public void LowSpeedShooter()
  {
    m_shooterPID.setReference(Constants.lowShooterSpeed, ControlType.kVelocity);
    SmartDashboard.putNumber("ShooterRPM", m_shooterEncoder.getVelocity());
  }

  public void ShooterStop() //Stops motors
  { 
    m_shooterMotor.set(0);  
  }

  public void OpenGateLow()
  {
    if (m_shooterEncoder.getVelocity() >= Constants.lowShooterSpeed)
    {
      new WaitCommand(7);
      m_shooterServo.setRaw(1000);
    }
  }

  public void CloseGate()
  {
    m_shooterServo.setRaw(1300);
  }



  public void HighSpeedShooterMode(){ //Function created to run the motor-- referenced later in ShooterRun.java
    if (tv == 1){
      if (roundedDistance == 17) //This if statement checks for the distance (see find distance) and picks RPM based on this
      {
        //This line sets the PID controller to listed RPM (first number), add control type so controller knows which value is being impacted
        m_shooterPID.setReference(Constants.shooterRPM17, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM17;
      }
      else if (roundedDistance == 16)
      {
        m_shooterPID.setReference(Constants.shooterRPM16, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM16;
      }
      else if (roundedDistance == 15)
      {
        m_shooterPID.setReference(Constants.shooterRPM15, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM15;
      }
      else if (roundedDistance == 14)
      {
        m_shooterPID.setReference(Constants.shooterRPM14, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM14;
      }
      else if (roundedDistance == 13)
      {
        m_shooterPID.setReference(Constants.shooterRPM13, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM13;
      }
      else if (roundedDistance == 12)
      {
        m_shooterPID.setReference(Constants.shooterRPM12, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM12;
      }
      else if (roundedDistance == 11)
      {
        m_shooterPID.setReference(Constants.shooterRPM11, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM11;
      }
      else if (roundedDistance == 10)
      {
        m_shooterPID.setReference(Constants.shooterRPM10, ControlType.kVelocity);
        RPMCheck = Constants.shooterRPM10;
      }
    }
      else 
      {
        m_shooterPID.setReference(Constants.lowShooterSpeed, ControlType.kVelocity); 
        RPMCheck = Constants.lowShooterSpeed;
      }
    SmartDashboard.putNumber("RPM", m_shooterEncoder.getVelocity());
    }



  public void OpenGate(){
    if (m_shooterEncoder.getVelocity() >= RPMCheck){
      new WaitCommand(7);
      m_shooterServo.setRaw(1000);
      //FOR AUTO Shooter: Create a variable and have it set based on distance. Then allow this to seek based on that variable in question. I want to make a separate vision class...
    }
  }

  public void findDistance(){
    hTotal = 104 - 25.75; //Measures in meters. Change these to the official field values later-- This is for testing INPUT IN INCHES *****
    aTotal = 0.628 + ty; //MEASURES IN RADIANS NOT DEGREES. DO NOT MAKE THE SAME MISTAKES (PUT THE VALUES IN AS RADIANS)
    aTan = Math.tan(aTotal);

    initialDistance = hTotal/aTan; //Outputs in meters/radian. Returns negative, so there's a negative on the next line
    distance =- initialDistance * 13.6; //Converts distance into inches. 
    distanceInFeet = distance/12; //Converts distance in inches to feet
    roundedDistance = Math.round(distanceInFeet); //Rounds distance in feet-- I'm hoping to make this process a little smoother but for now this will do.
    
    SmartDashboard.putNumber("Initial Distance", initialDistance); //Puts all these values on the smartdashboard when run. This is solely for testing purposes
    SmartDashboard.putNumber("Distance in Inches", distance);
    SmartDashboard.putNumber("Distance in Feet", distanceInFeet);
    SmartDashboard.putNumber("Rounded Distance", roundedDistance);
  }
  public void lightsOut()
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }
  public void processingMode() //For the toggle between vision modes. 
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0); //Sets camMode to processing
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(2); //Tells LEDs to turn on
  }
  public void cameraMode()
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1); //Sets camMode to camera vision  
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1); //Forces LEDs off. 
  }
}