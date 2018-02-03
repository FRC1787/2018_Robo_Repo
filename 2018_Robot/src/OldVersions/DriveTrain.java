package OldVersions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;

public class DriveTrain {
	//Important talon values with more intuitive names
	private final int FRONT_RIGHT_TALON_ID = 8;
	private final int REAR_RIGHT_TALON_ID = 9;
	private final int FRONT_LEFT_TALON_ID = 6;
	private final int REAR_LEFT_TALON_ID = 7;
	private WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(FRONT_LEFT_TALON_ID);
	private WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(REAR_LEFT_TALON_ID);	
	private WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(FRONT_RIGHT_TALON_ID);
	private WPI_TalonSRX backRightMotor = new WPI_TalonSRX(REAR_RIGHT_TALON_ID);
	
	//Speed controller groups, because arcadeDrive can only take in halves of the robot at a time to make
	//each side go in the same direction.
	private SpeedControllerGroup leftMotors;
	private SpeedControllerGroup rightMotors;
	
	private DifferentialDrive myDrive;
	
	private static final DriveTrain instance = new DriveTrain();

	private DriveTrain() {
		//Inverting all of the talons so that they all light up green when the robot goes forward 
		frontLeftMotor.setInverted(true);
		backLeftMotor.setInverted(true);
		frontRightMotor.setInverted(false);
		backRightMotor.setInverted(false);
	}
	
	public static DriveTrain getInstance() {
	    return instance;
	  }
	
	public void arcadeDrive(double motorSpeed) {
		//A function for Autonomous to just give a motor speed and have the robot move
		arcadeDrive(motorSpeed, 0);
	}
	
	@SuppressWarnings("ParameterName")
	public void arcadeDrive(double xSpeed, double zRotation) {
		//Basic drive class, makes the robot move forwards or backwards with a rotation
	    double leftMotorOutput;
	    double rightMotorOutput;

	    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
	    
	    //This gets the joystick being put to a side to work correctly
	    if (xSpeed <= 0.1 && xSpeed >= -0.1) {
	    	if (zRotation >= 0) {
	    		leftMotorOutput = maxInput;
	    		rightMotorOutput = -maxInput;
	    	}
	    	else {
	    		leftMotorOutput = -maxInput;
	    		rightMotorOutput = maxInput;
	    	}
	    }
	    
	    if (xSpeed >= 0.0) {
	      // First quadrant, else second quadrant
	      if (zRotation >= 0.0) {
	        leftMotorOutput = maxInput;
	        rightMotorOutput = xSpeed - zRotation;
	      } else {
	        leftMotorOutput = xSpeed + zRotation;
	        rightMotorOutput = maxInput;
	      }
	    } else {
	      // Third quadrant, else fourth quadrant
	      if (zRotation >= 0.0) {
	        leftMotorOutput = maxInput;
	        rightMotorOutput = xSpeed + zRotation;
	      } else {
	        leftMotorOutput = xSpeed - zRotation;
	        rightMotorOutput = maxInput;
	      }
	    }
	    
	    frontLeftMotor.set(leftMotorOutput);
	    backLeftMotor.set(leftMotorOutput);
	    System.out.println("Left: " + leftMotorOutput);
	    frontRightMotor.set(rightMotorOutput);
	    backRightMotor.set(rightMotorOutput);
	    System.out.println("Right: " + rightMotorOutput);
	  }
	  

}
