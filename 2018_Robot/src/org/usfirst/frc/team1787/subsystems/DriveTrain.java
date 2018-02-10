package org.usfirst.frc.team1787.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * The drive train encompasses the 4 CIM motors (2 on either side) that are connected to gearboxes, and the gearboxes themselves.
 */

public class DriveTrain {
	
	
	private final int LEFT_DRIVE_MASTER_TALON_ID = 10;
	private final int LEFT_DRIVE_FOLLOWER_VICTOR_ID = 11;
	private final int RIGHT_DRIVE_MASTER_TALON_ID = 9;
	private final int RIGHT_DRIVE_FOLLOWER_VICTOR_ID = 8;

	private WPI_TalonSRX leftMaster = new WPI_TalonSRX(LEFT_DRIVE_MASTER_TALON_ID);
	private WPI_VictorSPX leftFollower = new WPI_VictorSPX(LEFT_DRIVE_FOLLOWER_VICTOR_ID);
	private WPI_TalonSRX rightMaster = new WPI_TalonSRX(RIGHT_DRIVE_MASTER_TALON_ID);
	private WPI_VictorSPX rightFollower = new WPI_VictorSPX(RIGHT_DRIVE_FOLLOWER_VICTOR_ID);
	
	private final int GEAR_BOX_SOLENOID_ID = 5;
	private Solenoid gearboxSolenoid = new Solenoid(GEAR_BOX_SOLENOID_ID);
	
	
	
	private static final DriveTrain instance = new DriveTrain();
	
	
	private DriveTrain() {
		
		//Pairing each master Talon with it's follower Victor
		//leftFollower.follow(leftMaster);
		//rightFollower.follow(rightMaster);
		
		//Inverting all of the talons so that they all light up green when the robot goes forward 
		leftMaster.setInverted(false);
		leftFollower.setInverted(false);
		rightMaster.setInverted(true);
		rightFollower.setInverted(true);
		
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
	
	
	public void tankDrive(double leftInput, double rightInput) {
		leftMaster.set(leftInput);
		leftFollower.set(leftInput);
		rightMaster.set(rightInput);
		rightFollower.set(rightInput);
	}
	
	private double truncateMotorOutput(double motorOutput) {
		if (motorOutput > 1) {
	    	return 1;
	    }
	    else if (motorOutput < -1) {
	    	return -1;
	    }
	    else {
	    	return motorOutput;
	    }
	}
	
	@SuppressWarnings("ParameterName")
	public void arcadeDrive(double yInput, double xInput) {
		//Basic drive class, makes the robot move forwards or backwards with a rotation
		
		
		//Makes driving input feel less sensitive, better control
		yInput = yInput * Math.abs(yInput);
		xInput = xInput * Math.abs(xInput);
		
				
		//Simons algorithm
	    double leftMotorOutput = yInput + xInput;
	    double rightMotorOutput = yInput - xInput;
	    
	    leftMotorOutput = truncateMotorOutput(leftMotorOutput);
	    rightMotorOutput = truncateMotorOutput(rightMotorOutput);
	    
	    leftMaster.set(leftMotorOutput);
	    rightMaster.set(rightMotorOutput);
	    leftFollower.set(leftMotorOutput);
	    rightFollower.set(rightMotorOutput);
	    
	  }
	
	
	public void highGear() 
	{
		gearboxSolenoid.set(true);
	}
	
	
	public void lowGear()
	{
		gearboxSolenoid.set(false);
	}
}

