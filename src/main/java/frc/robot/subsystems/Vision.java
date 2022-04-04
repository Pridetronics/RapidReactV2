// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.Servo;

public class Vision extends SubsystemBase {
  private CANSparkMax m_frontLeftMotor;
  private CANSparkMax m_rearLeftMotor;
  private CANSparkMax m_frontRightMotor;
  private CANSparkMax m_rearRightMotor;

  private SparkMaxPIDController m_frontLeftPIDController;
  private SparkMaxPIDController m_rearLeftPIDController;
  private SparkMaxPIDController m_frontRightPIDController;
  private SparkMaxPIDController m_rearRightPIDController;

  private CANSparkMax m_shooterMotor;
  private RelativeEncoder m_shooterEncoder;
  private SparkMaxPIDController m_shooterPIDController;
  private Servo m_shooterServo;

  private double ty;
  private double tx;
  private double tv;
  private double ta;

  private double heightTotal;
  private double angleTotal;
  private double angleTan;
  private double initialDistance;
  private double distanceInInches;
  private double distanceInFeet;
  private double roundedDistance;

  private double adjustableShooterRPM;

  public Vision() {
    m_frontLeftMotor = RobotContainer.frontLeftMotor;
    m_frontLeftPIDController = m_frontLeftMotor.getPIDController();

    m_frontRightMotor = RobotContainer.frontRightMotor;
    m_frontRightPIDController = m_frontRightMotor.getPIDController();

    m_rearLeftMotor = RobotContainer.rearLeftMotor;
    m_rearLeftPIDController = m_rearLeftMotor.getPIDController();

    m_rearRightMotor = RobotContainer.rearRightMotor;
    m_rearRightPIDController = m_rearRightMotor.getPIDController();

    m_shooterMotor = RobotContainer.shooterMotor;
    m_shooterEncoder = RobotContainer.shooterEncoder;
    m_shooterPIDController = m_shooterMotor.getPIDController();
    m_shooterServo = RobotContainer.shooterServo;
  }

  @Override
  public void periodic() {
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

  public void SeekTarget() 
  {
    if (tv == 0) 
    {
      m_frontLeftPIDController.setReference(100, ControlType.kVelocity);
      m_rearRightPIDController.setReference(100, ControlType.kVelocity);
      m_frontRightPIDController.setReference(-100, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(-100, ControlType.kVelocity);
    } 
    else if (tv == 1) 
    {
      m_frontLeftPIDController.setReference(0, ControlType.kVelocity);
      m_frontRightPIDController.setReference(0, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(0, ControlType.kVelocity);
      m_rearRightPIDController.setReference(0, ControlType.kVelocity);
    }
  }

  public void TargetAdjust() {
    if (tx > 3 && tx < 28) // turning left
    {
      m_frontRightPIDController.setReference(50, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(50, ControlType.kVelocity);
      m_frontLeftPIDController.setReference(-50, ControlType.kVelocity);
      m_rearRightPIDController.setReference(-50, ControlType.kVelocity);
    } 
    else if (tx < -3 && tx > -28) // turning right
    {
      m_frontLeftPIDController.setReference(50, ControlType.kVelocity);
      m_rearRightPIDController.setReference(50, ControlType.kVelocity);
      m_frontRightPIDController.setReference(-50, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(-50, ControlType.kVelocity);
    } 
    else 
    {
      m_frontLeftPIDController.setReference(0, ControlType.kVelocity);
      m_frontRightPIDController.setReference(0, ControlType.kVelocity);
      m_rearLeftPIDController.setReference(0, ControlType.kVelocity);
      m_rearRightPIDController.setReference(0, ControlType.kVelocity);
    }
  }

  public void findDistance()
  {
    //Put the full equation for reference of breakdown below
    heightTotal = 104 - 25.75; //Measures in meters. Change these to the official field values later-- This is for testing INPUT IN INCHES *****
    angleTotal = 0.628 + ty; //MEASURES IN RADIANS NOT DEGREES. DO NOT MAKE THE SAME MISTAKES (PUT THE VALUES IN AS RADIANS)
    angleTan = Math.tan(angleTotal);

    initialDistance = heightTotal/angleTan; //Outputs in meters/radian. Returns negative, so there's a negative on the next line
    distanceInInches =- initialDistance * 13.6; //Converts distance into inches. 
    distanceInFeet = distanceInInches/12; //Converts distance in inches to feet
    roundedDistance = Math.round(distanceInFeet); //Rounds distance in feet-- I'm hoping to make this process a little smoother but for now this will do.
    
    SmartDashboard.putNumber("Initial Distance", initialDistance); //Puts all these values on the smartdashboard when run. This is solely for testing purposes
    SmartDashboard.putNumber("Distance in Inches", distanceInInches);
    SmartDashboard.putNumber("Distance in Feet", distanceInFeet);
    SmartDashboard.putNumber("Rounded Distance", roundedDistance);
  }

  public void AutomaticShooter()
  { 
    if (roundedDistance >= 15) //This if statement checks for the distance (see find distance) and picks RPM based on this
    {
      //This line sets the PID controller to listed RPM (first number), add control type so controller knows which value is being impacted
      m_shooterPIDController.setReference(Constants.shooterRPMHigh, ControlType.kVelocity);
      adjustableShooterRPM = Constants.shooterRPMHigh;
    }
    else if (roundedDistance >= 13 && roundedDistance <= 11)
    {
      m_shooterPIDController.setReference(Constants.shooterRPMMedium, ControlType.kVelocity);
      adjustableShooterRPM = Constants.shooterRPMMedium;
    }
    else if (roundedDistance == 10 && roundedDistance < 10)
    {
      m_shooterPIDController.setReference(Constants.shooterRPMLow, ControlType.kVelocity);
      adjustableShooterRPM = Constants.shooterRPMLow;
    }
  }

  public void ShooterMotorStop()
  {
    m_shooterMotor.set(0);
  }
  
  public void AutomaticOpenGate()
  {
    if (m_shooterEncoder.getVelocity() >= adjustableShooterRPM)
    {
      new WaitCommand(7);
      m_shooterServo.setRaw(1000);
    }
  }
  public void AutomaticCloseGate()
  {
    m_shooterServo.setRaw(1300);
  }

}
