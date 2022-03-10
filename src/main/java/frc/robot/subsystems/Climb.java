package frc.robot.subsystems;

import com.fasterxml.jackson.databind.util.TypeKey;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import com.revrobotics.SparkMaxRelativeEncoder;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.ClimbInitializationDown;
import frc.robot.commands.ClimbInitializationUp;
import frc.robot.commands.PivotArmDescendDistance;
import frc.robot.commands.PivotArmDistanceOne;


public class Climb extends SubsystemBase {
    private CANSparkMax m_climbMotor; // Initalizes motor
    private static DoubleSolenoid m_climbPiston; // Initalizes pistons
    private DigitalInput m_upperClimbLimitSwitch; // Initalizes upper limit switch
    private DigitalInput m_lowerClimbLimitSwitch; // Initalizes lower limit switch
    private RelativeEncoder m_climbEncoder; // Initalizes encoder
    public static int climbValue; // Initalizes value
    private static SparkMaxPIDController m_climbPID; // Initalizes PID
    public static String climbMessage = "Example";
    private String m_climbMessage;


    public Climb() {
        m_climbMotor = RobotContainer.climbMotor; // References the object from Robot Container
        m_climbPiston = RobotContainer.climbPiston; // References the object from Robot Container
        m_climbEncoder = RobotContainer.climbEncoder; // References the object from Robot Container
        m_upperClimbLimitSwitch = RobotContainer.upperClimbLimitSwitch; // Limit switch to test if Pivot Arms is
                                                                        // extended
        m_lowerClimbLimitSwitch = RobotContainer.lowerClimbLimitSwitch; // Limit switch to test if Pivot Arms has
                                                                        // descended
        m_climbPID = RobotContainer.climbPID;
        // m_climbEncoder.setPositionConversionFactor(Constants.kEncoderPositionConversionFactor);
        zeroEncoder();
    }

    public void zeroEncoder() { // Resets encoder
        m_climbEncoder.setPosition(0.0);
    }

    // Gets called every 20 miliseconds
    // 0 is closed, 1 is open
    public void periodic() {
        if (true) { // True will enable
            // Limit Switches display on SmartDashboard
            SmartDashboard.putBoolean("Lower Climb Limit Switch", RobotContainer.lowerClimbLimitSwitch.get());
            SmartDashboard.putBoolean("Upper Climb Limit Switch", RobotContainer.upperClimbLimitSwitch.get());
            // Climb Encoder display on SmartDashboard
            SmartDashboard.putNumber("Climb Encoder", RobotContainer.climbEncoder.getPosition()); // Measures in
                                                                                                  // units
            // of rotations by default
        }
    }

    public void raisePivotArms(double speed) {
        m_climbMotor.set(speed);
    }

    public void descendPivotArms(double speed) {
        m_climbMotor.set(speed);
    }

    public void stop() {
        m_climbMotor.set(0);
    }

    public void extendPivotArmDistanceOne(double climbGoal) {
        m_climbPID.setReference(Constants.climbDistance1, ControlType.kPosition);
    }

    public void extendPivotArmDistanceTwo(double climbGoal) {
        m_climbPID.setReference(Constants.climbDistance2, ControlType.kPosition);
    }

    public void extendPivotArmDistanceThree(double climbGoal) {
        m_climbPID.setReference(Constants.climbDistance3, ControlType.kPosition);
    }

    public void descendPivotArmDistance(double climbGoal) {
        m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
    }

    public double inchesToRevs(double INCHES) {
        return INCHES / Constants.kEncoderPositionConversionFactor;
    }

    public double getClimbRevs() {
        return m_climbEncoder.getPosition();
    }

    public void pistonRelease() {
        m_climbPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void pistonRetract() {
        m_climbPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void increaseStage() {
        climbValue++;
    }

    public void decreaseStage() {
        climbValue--;
    }

    public void cancelClimb() {
        if (climbValue <= 10) {
            climbValue = 0; // Doesn't show unless you push another button by either increasing or
                            // decreasing
            climbMessage = "Restarting Climb Sequence";
            SmartDashboard.putString("Climb", climbMessage);
            m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
            pistonRelease();
        }
    }

    public void cancelStage() { // One time push button (no need to push climb button after it)
        // Check the constants and distances to make sure they are the same amount as
        // the previous stage.
        if (climbValue <= 0) {
            climbValue = 0;
        } else if (climbValue == 1) {
            climbMessage = "Descending Pivot Arms";
            SmartDashboard.putString("ClimbArms", climbMessage);
            m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
        } else if (climbValue == 2) {
            climbMessage = "Raising Pivot Arms";
            SmartDashboard.putString("ClimbArms", climbMessage);
            m_climbPID.setReference(Constants.climbDistance1, ControlType.kPosition);
        } else if (climbValue == 3) { // Needs to be looked over at.
            pistonRelease();
            climbMessage = "Descending Pivot Arms";
            SmartDashboard.putString("ClimbArms", climbMessage);
            m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
            pistonRetract();
            m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
        } else if (climbValue == 4) {
            climbMessage = "Raising Pivot Arms";
            SmartDashboard.putString("ClimbArms", climbMessage);
            m_climbPID.setReference(Constants.climbDistance1, ControlType.kPosition);
        } else if (climbValue == 5) {
            pistonRelease();
            climbMessage = "Descending Pivot Arms";
            SmartDashboard.putString("ClimbArms", climbMessage);
            m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
            pistonRetract();
            m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
        } else if (climbValue == 6) {
            climbMessage = "Raising Pivot Arms";
            SmartDashboard.putString("ClimbArms", climbMessage);
            m_climbPID.setReference(Constants.climbDistance3, ControlType.kPosition);
        } else {
            climbMessage = "Raising Pivot Arms";
            SmartDashboard.putString("ClimbArms", climbMessage);
            m_climbPID.setReference(Constants.climbDescendDistance, ControlType.kPosition);
        }
    }

    public boolean isClimbAtTop() {
        // Returns the state of the upper limit switch
        boolean isClimbAtTop;
        m_climbEncoder.getPosition();
        if (m_climbEncoder.getPosition() >= Constants.CLIMB_MAX_OUTPUT) {
            isClimbAtTop = true;
        } else {
            isClimbAtTop = false;
        }
        return isClimbAtTop;
    }

    public boolean isClimbAtBottom() { // Might have to switch true/false because of magnetic limit switch
        // Returns the state of the lower limit switch
        boolean isClimbAtBottom;
        if (m_lowerClimbLimitSwitch.get() == false) {
            isClimbAtBottom = true;
        } else {
            isClimbAtBottom = false;
        }
        return isClimbAtBottom;
    }

    public void ClimbUpSlowly() {
        if (isClimbAtBottom() == true) {
            System.out.println("Go up slowly");
            m_climbMotor.set(0.1);
        } else {
            stop();
            zeroEncoder();
        }
    }

    public void ClimbDownSlowly() {
        if (isClimbAtBottom() == false) {
            System.out.println("Climb DOwn slowly");
            m_climbMotor.set(-0.1);
        } else {
            stop();
            zeroEncoder();
        }
    }
}