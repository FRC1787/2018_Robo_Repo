package org.usfirst.frc.team1787.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * The drive train encompasses the 4 CIM motors (2 on either side) that are connected to gearboxes, and the gearboxes themselves.
 */

public class DriveTrain {
	
	
	private final int LEFT_DRIVE_MASTER_TALON_ID = 0;
	private final int LEFT_DRIVE_FOLLOWER_VICTOR_ID = 2;
	private final int RIGHT_DRIVE_MASTER_TALON_ID = 1;
	private final int RIGHT_DRIVE_FOLLOWER_VICTOR_ID = 3;

	private WPI_TalonSRX leftMaster = new WPI_TalonSRX(LEFT_DRIVE_MASTER_TALON_ID);
	private WPI_VictorSPX leftFollower = new WPI_VictorSPX(LEFT_DRIVE_FOLLOWER_VICTOR_ID);
	private WPI_TalonSRX rightMaster = new WPI_TalonSRX(RIGHT_DRIVE_MASTER_TALON_ID);
	private WPI_VictorSPX rightFollower = new WPI_VictorSPX(RIGHT_DRIVE_FOLLOWER_VICTOR_ID);
	
	
	private static final DriveTrain instance = new DriveTrain();
	
	
	private DriveTrain() {
		
		//Pairing each master Talon with it's follower Victor
		leftFollower.follow(leftMaster);
		rightFollower.follow(rightMaster);
		
		//Inverting all of the talons so that they all light up green when the robot goes forward 
		leftMaster.setInverted(false);
		leftFollower.setInverted(false);
		rightMaster.setInverted(false);
		rightFollower.setInverted(false);
		
		//Voltage Compensation for the talons
		leftMaster.configVoltageCompSaturation(12, 10);
		leftFollower.configVoltageCompSaturation(12, 10);
		rightMaster.configVoltageCompSaturation(12, 10);
		rightFollower.configVoltageCompSaturation(12, 10);
		
		leftMaster.enableVoltageCompensation(true);
		leftFollower.enableVoltageCompensation(true);
		rightMaster.enableVoltageCompensation(true);
		rightFollower.enableVoltageCompensation(true);
	}
	
	
	public static DriveTrain getInstance() {
	    return instance;
	  }
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putNumber("Left Output: ", leftMaster.get());
		SmartDashboard.putNumber("Right Output: ", rightMaster.get());
	}
	
	
	@SuppressWarnings("ParameterName")
	public void arcadeDrive(double xSpeed, double zRotation) {
		//Basic drive class, makes the robot move forwards or backwards with a rotation
	    double leftMotorOutput;
	    double rightMotorOutput;

	    
	    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
	    
	    
	    //This gets the joystick being put to a side to work correctly by putting the two sides in opposite directions
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
	    } 
	    
	    else {
	      // Third quadrant, else fourth quadrant
	      if (zRotation >= 0.0) {
	        leftMotorOutput = maxInput;
	        rightMotorOutput = xSpeed + zRotation;
	      } else {
	        leftMotorOutput = xSpeed - zRotation;
	        rightMotorOutput = maxInput;
	      }
	    }
	    
	    
	    
	    leftMaster.set(leftMotorOutput);
	    rightMaster.set(rightMotorOutput);
	    
	    System.out.println("Left: " + leftMotorOutput);
	    System.out.println("Right: " + rightMotorOutput);
	  }
}
