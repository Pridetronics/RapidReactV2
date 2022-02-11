package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.ClimbPiston;
import frc.robot.commands.DescendPivotArms;
import frc.robot.commands.RaisePivotArms;

public class Climb extends SubsystemBase {
    private CANSparkMax m_climbMotor; //Initalizes motor
    private DoubleSolenoid m_climbPiston; //Initalizes pistons
    private DigitalInput m_upperClimbLimitSwitch; //Initalizes upper limit switch
    private DigitalInput m_lowerClimbLimitSwitch; //Initalizes lower limit switch
    private final RelativeEncoder m_ClimbEncoder; //Initalizes encoder

    public Climb(Climb climb) {
        m_climbMotor = RobotContainer.climbMotor; //References the object from Robot Container so I don't need to recreate the motor
        m_climbPiston = RobotContainer.m_climbPiston; //References the object from Robot Container so I don't need to recreate the pistons
        m_upperClimbLimitSwitch = RobotContainer.upperClimbLimitSwitch; // limit switch to test if Pivot Arms is extended
        m_lowerClimbLimitSwitch = RobotContainer.lowerClimbLimitSwitch; // limit switch to test if Pivot Arms has descended
        m_ClimbEncoder = m_climbMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, Constants.kEncoderCountsPerRev);
        zeroEncoders();
    }

    //0 is closed, 1 is open
    public void periodic() {
        // Limit Switches display on SmartDashboard
    SmartDashboard.putBoolean("Upper Climb Limit Switch", m_upperClimbLimitSwitch.get());
    SmartDashboard.putBoolean("Lower Climb Limit Switch", m_lowerClimbLimitSwitch.get());
        //Climb Encoder display on SmartDashboard
    SmartDashboard.putNumber("Climb Encoder", m_ClimbEncoder.getPosition());
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

    public void sequence1(Climb climb){new SequentialCommandGroup(
        new RaisePivotArms(climb), //Fully extended until upper limit switch is hit
        new ClimbPiston(climb)); //Extended
    }

    public void sequence2(){
        DescendPivotArms(); //Fully retracted until lower limit switch is hit
    }

    public void sequence3(Climb climb){new SequentialCommandGroup(
        new RaisePivotArms(climb), //Needs to make this extend 9 inches
        new ClimbPiston(climb), //Retracted
        new RaisePivotArms(climb), //Extended to Y (23 inches)
        new ClimbPiston(climb)); //Extended 
    }
    
        //Need to add encoders, when it is at the bottom you have to make sure the encoders is at 0.
        //There is also no need to do anything for the stationary arm
        //Winches is just the one motor going forward and reverse.

        /*Idea: I need to make it so that the motor runs to get to a certain length, so I can use the encoders.
        From those encorders, I can make an if then statement of when to use certain RaisePivotArms instead
        of just inputting random speeds*/

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
    return (!m_lowerClimbLimitSwitch.get());
    }
    

    public void zeroEncoders() {
        m_ClimbEncoder.setPosition(0);   
      }
}