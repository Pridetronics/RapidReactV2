package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.ClimbPiston;
import frc.robot.commands.DescendPivotArms;
import frc.robot.commands.RaisePivotArms;
import frc.robot.commands.RaisePivotArms23Inches;
import frc.robot.commands.RaisePivotArmsNineInches;

public class Climb extends SubsystemBase {
    private CANSparkMax m_climbMotor; //Initalizes motor
    private DoubleSolenoid m_climbPiston; //Initalizes pistons
    private DigitalInput m_upperClimbLimitSwitch; //Initalizes upper limit switch
    private DigitalInput m_lowerClimbLimitSwitch; //Initalizes lower limit switch
    private RelativeEncoder m_ClimbEncoder; //Initalizes encoder
    private int m_climbValue = 0; //Initalizes value

    public Climb() {
        m_climbMotor = RobotContainer.climbMotor; //References the object from Robot Container so I don't need to recreate the motor
        m_climbPiston = RobotContainer.m_climbPiston; //References the object from Robot Container so I don't need to recreate the pistons
        m_ClimbEncoder = RobotContainer.ClimbEncoder; //References the encoder from Robot Container
        m_upperClimbLimitSwitch = RobotContainer.upperClimbLimitSwitch; //Limit switch to test if Pivot Arms is extended
        m_lowerClimbLimitSwitch = RobotContainer.lowerClimbLimitSwitch; //Limit switch to test if Pivot Arms has descended
        m_climbValue = RobotContainer.climbValue;
    }

    //0 is closed, 1 is open
    public void periodic() {
        // Limit Switches display on SmartDashboard
    SmartDashboard.putBoolean("Lower Climb Limit Switch", RobotContainer.lowerClimbLimitSwitch.get());
    SmartDashboard.putBoolean("Upper Climb Limit Switch", RobotContainer.upperClimbLimitSwitch.get());
        //Climb Encoder display on SmartDashboard
    SmartDashboard.putNumber("Climb Encoder", RobotContainer.ClimbEncoder.getPosition()); //Measures in units of rotations by default
    }

    public void raisePivotArms(double speed){
        m_climbMotor.set(speed);
    }

    public void descendPivotArms(double speed){
        m_climbMotor.set(speed);
    }

    public void pistonRelease(){
        m_climbPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void pistonRetract(){
        m_climbPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public int addOne(){
        m_climbValue++;
        return m_climbValue;
    }
    
    public boolean isClimbAtTop(){
    //Returns the state of the upper limit switch
    boolean isClimbAtTop;
    if(m_upperClimbLimitSwitch.get()){
      isClimbAtTop = false;
    }
    else{
      isClimbAtTop = true;
    }
    return isClimbAtTop;
    }
  
    
    public boolean isClimbAtBottom(){
    //Returns the state of the lower limit switch
    boolean isClimbAtBottom;
    if (m_lowerClimbLimitSwitch.get()) {
        isClimbAtBottom = false;
    }
    else {
        isClimbAtBottom = true;
    }
    return isClimbAtBottom;
    }
}