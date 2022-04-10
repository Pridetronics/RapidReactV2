// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
* This is the Vision Subsystem. At this point it is used for Limelight and 
* functions that interact with it. Because of this, hardware for shooter
* and drive are also created and used within this file. 
*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Network tables are vital for use of Limelight. 
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.Servo;

public class Vision extends SubsystemBase {
  //Hardware Relevant
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

  //The following is all involved in Limelight values. Explained further within functions
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
    // While there are comments, this is a tricky topic-- check out the documentation if you need help
    NetworkTableInstance inst = NetworkTableInstance.getDefault(); //get a reference to the subtable called "datatable"
    NetworkTable table = inst.getTable("limelight");
    inst.startClientTeam(3853); // Make sure you set this to your team number
    inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
    NetworkTableEntry yEntry = table.getEntry("ty"); 
    NetworkTableEntry xEntry = table.getEntry("tx");
    NetworkTableEntry aEntry = table.getEntry("ta");
    NetworkTableEntry vEntry = table.getEntry("tv");

    ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
    ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    tx = xEntry.getDouble(0.0); //Horizontal Offset From Crosshair to Target (-27.5 degrees to 27.5 degrees)
    tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)

    SmartDashboard.putNumber("Limelight Area", ta); //Displays base limelight values to Shuffleboard
    SmartDashboard.putNumber("Limelight X", tx);
    SmartDashboard.putNumber("Limelight Y", ty);
    SmartDashboard.putNumber("Limelight V", tv);
    // This method will be called once per scheduler run
  }

  //Turns out LEDs, was created to be called in Robot Init (so the light wouldn't be on until needed)
  public void lightsOut()
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }

  /*
  * Processing Mode and Camera mode are for toggling between vision modes. 
  * Processing mode prepares it for use with shooter/drive. CameraMode is solely for user vision
  */
  public void processingMode() 
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0); //Sets camMode to processing
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(2); //Tells LEDs to turn on (also there is an alternate 0, follows pipeline settings)
  }

  public void cameraMode()
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1); //Sets camMode to camera vision  
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1); //Forces LEDs off. 
  }

  /* Seek Target: Checks if the Limelight has a target, if it does not, it will utilize PID control to
  *  adjust until it finds a target. PID control is for movement speed. This is meant to keep it from
  *  moving too fast (keeping it at 100 RPM). Then sets to 0 once target is found. 
  */
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

  /* TargetAdjust: Meant to center the robot on the horizontal axis, uses the drive train to turn slightly
  *  based on where it falls within the angles of vision. If the target is too far right, it will turn left.
  *  Too far left, it will turn right. If it is centered, do nothing. 
  */
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

  //findDistance: Calculates distance and converts it into rounded distance for use within the automatic shooter code
  public void findDistance()
  {
    //Equation for distance is d = (h1 - h2)/(tan(a1 + a2)) //Defaults to meters/radian
    heightTotal = 104 - 25.75; //Input inches
    angleTotal = 0.628 + ty; //Input radians
    angleTan = Math.tan(angleTotal);

    initialDistance = heightTotal/angleTan; //Returns negative, so there's a negative on the next line
    distanceInInches =- initialDistance * 13.6; //Converts distance into inches. 
    distanceInFeet = distanceInInches/12; //Converts distance in inches to feet
    roundedDistance = Math.round(distanceInFeet); //Rounds distance in feet
    
    SmartDashboard.putNumber("Initial Distance", initialDistance); //Puts all these values on the smartdashboard when run. This is solely for testing purposes
    SmartDashboard.putNumber("Distance in Inches", distanceInInches);
    SmartDashboard.putNumber("Distance in Feet", distanceInFeet);
    SmartDashboard.putNumber("Rounded Distance", roundedDistance);
  }

  /* Based on distance, program will set the shooter motor to a certain speed. 
  *  Also sets a variable to the same RPM the shooter motor will travel for use
  *  in the AutomaticOpenGate
  */
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
  
  //Checks for a velocity provided by automaticshooter to check if gate can be opened
  // (AND OPENS GATE)
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
