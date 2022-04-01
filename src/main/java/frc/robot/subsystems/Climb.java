package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.Constants;
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

    public Climb() {
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
        if (true) { // True will enable --WHY IS THIS HERE? (If false, won't show on Smart Dashboard)
            // Limit Switches display on SmartDashboard
            SmartDashboard.putBoolean("Lower Climb Limit Switch", RobotContainer.lowerClimbLimitSwitch.get());
            // Climb Encoder display on SmartDashboard
            SmartDashboard.putNumber("Climb Encoder", RobotContainer.climbEncoder.getPosition()); // Measures in units of rotations by default
        }
    }
    
    public void zeroEncoder() // Resets encoder
    { 
        m_climbEncoder.setPosition(0.0);
    }

    public void stop() 
    {
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
    public void ExtendArm(){
        m_climbMotor.set(.4);
    }
    public void DescendArm(){
        m_climbMotor.set(-1);
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