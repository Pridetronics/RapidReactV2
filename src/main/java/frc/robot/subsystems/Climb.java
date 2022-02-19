package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.ClimbPiston;
import frc.robot.commands.DescendPivotArms;
import frc.robot.commands.RaisePivotArms;

public class Climb extends SubsystemBase {
    //private static DescendPivotArms DescendPivotArms;
    //public static Climb sequence1;
    //public static Climb sequence2;
    //public static Climb sequence3;
    private CANSparkMax m_climbMotor; //Initalizes motor
    private DoubleSolenoid m_climbPiston; //Initalizes pistons
    private DigitalInput m_upperClimbLimitSwitch; //Initalizes upper limit switch
    private DigitalInput m_lowerClimbLimitSwitch; //Initalizes lower limit switch
    public RelativeEncoder m_ClimbEncoder; //Initalizes encoder
    private int m_climbValue; //Initalizes variable x

    public Climb(Climb climb) {
        m_climbMotor = RobotContainer.climbMotor; //References the object from Robot Container so I don't need to recreate the motor
        m_climbPiston = RobotContainer.m_climbPiston; //References the object from Robot Container so I don't need to recreate the pistons
        m_upperClimbLimitSwitch = RobotContainer.upperClimbLimitSwitch; // limit switch to test if Pivot Arms is extended
        m_lowerClimbLimitSwitch = RobotContainer.lowerClimbLimitSwitch; // limit switch to test if Pivot Arms has descended
        m_ClimbEncoder = m_climbMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, Constants.kEncoderCountsPerRev);
        zeroEncoders();
        m_climbValue = RobotContainer.climbValue;
    }

    //0 is closed, 1 is open
    public void periodic() {
        // Limit Switches display on SmartDashboard
    SmartDashboard.putBoolean("Upper Climb Limit Switch", m_upperClimbLimitSwitch.get());
    SmartDashboard.putBoolean("Lower Climb Limit Switch", m_lowerClimbLimitSwitch.get());
        //Climb Encoder display on SmartDashboard
    SmartDashboard.putNumber("Climb Encoder", m_ClimbEncoder.getPosition()); //Measures in units of rotations by default
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

    public void zeroEncoders() {
        m_ClimbEncoder.setPosition(0);   
      }
}