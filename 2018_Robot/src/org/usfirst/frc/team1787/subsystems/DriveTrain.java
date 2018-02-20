package org.usfirst.frc.team1787.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
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
	
	private final int GEAR_BOX_SOLENOID_ID = 3;
	private Solenoid gearboxSolenoid = new Solenoid(GEAR_BOX_SOLENOID_ID);
	
	
	//Added these to control inversion for each motor controller
	public boolean LEFT_DRIVE_MASTER_INVERTED = true;
	public boolean LEFT_DRIVE_FOLLOWER_INVERTED = true;
	public boolean RIGHT_DRIVE_MASTER_INVERTED = false;
	public boolean RIGHT_DRIVE_FOLLOWER_INVERTED = false;
	
	public final int AUTO_RIGHT_TURN_VALUE = 4830;
	public final int AUTO_LEFT_TURN_VALUE = 4830;
	
	
	private final int rightEncoderChannelA = 0;
	private final int rightEncoderChannelB = 1;
	private final int leftEncoderChannelA = 2;
	private final int leftEncoderChannelB = 3;
	
	private Encoder rightEncoder = new Encoder(rightEncoderChannelA, rightEncoderChannelB , true);
	private Encoder leftEncoder = new Encoder(leftEncoderChannelA, leftEncoderChannelB, true);
	
	private final double WHEEL_RADIUS = 0.0254*(6.25/2);
	private final double WHEEL_CIRCUMFERENCE = (2*Math.PI*WHEEL_RADIUS);
	private final double PULSES_PER_ROTATION = 2532;
	private final double DISTANCE_PER_PULSE = WHEEL_CIRCUMFERENCE/PULSES_PER_ROTATION;
	
	private static final DriveTrain instance = new DriveTrain();
	
	
	private DriveTrain() {
		
		//Pairing each master Talon with it's follower Victor
		//leftFollower.follow(leftMaster);
		//rightFollower.follow(rightMaster);
		
		//Inverting all of the talons so that they all light up green when the robot goes forward 
		leftMaster.setInverted(LEFT_DRIVE_MASTER_INVERTED);
		leftFollower.setInverted(LEFT_DRIVE_FOLLOWER_INVERTED);
		rightMaster.setInverted(RIGHT_DRIVE_MASTER_INVERTED);
		rightFollower.setInverted(RIGHT_DRIVE_FOLLOWER_INVERTED);
		
		//Voltage Compensation for the talons
		leftMaster.configVoltageCompSaturation(12, 10);
		leftFollower.configVoltageCompSaturation(12, 10);
		rightMaster.configVoltageCompSaturation(12, 10);
		rightFollower.configVoltageCompSaturation(12, 10);
		
		leftMaster.enableVoltageCompensation(true);
		leftFollower.enableVoltageCompensation(true);
		rightMaster.enableVoltageCompensation(true);
		rightFollower.enableVoltageCompensation(true);
		
		
		//testing stuff
		leftMaster.setNeutralMode(NeutralMode.Coast);
		rightMaster.setNeutralMode(NeutralMode.Coast);
		leftFollower.setNeutralMode(NeutralMode.Coast);
		rightFollower.setNeutralMode(NeutralMode.Coast);
		
		rightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		leftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		
		
		
		
	}
	
	
	public void testSolenoid(boolean boolInput) {
		gearboxSolenoid.set(boolInput);
	}
	
	public static DriveTrain getInstance() {
	    return instance;
	  }
	
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putData("Left Drive: ", leftMaster);
		SmartDashboard.putData("Right Drive: ", rightMaster);
	}
	
	
	public void tankDrive(double leftInput, double rightInput) {
		leftMaster.set(leftInput);
		leftFollower.set(-leftInput);
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
	    leftFollower.set(-leftMotorOutput);
	    rightFollower.set(rightMotorOutput);
	    
	  }
	
	
	
	
	public void highGear() 
	{
		gearboxSolenoid.set(false);
	}
	
	
	public void lowGear()
	{
		gearboxSolenoid.set(true);
	}
	

	
	
	
	
	//Autonomous methods
	
	
	public void moveStraight (double moveDistance, double moveSpeed) {
		
		if (leftEncoder.getDistance() * 3.280839 <= moveDistance && rightEncoder.getDistance() * 3.280839 <= moveDistance) {
			leftMaster.set(moveSpeed);
			leftFollower.set(-moveSpeed);
			rightMaster.set(moveSpeed);
			rightFollower.set(moveSpeed);
		}		
	}
	
	public void turnRight(double turnSpeed) {
		
		if (leftEncoder.get() - rightEncoder.get() < AUTO_RIGHT_TURN_VALUE) {
			this.tankDrive(turnSpeed, -turnSpeed);
		}
		else {
			this.tankDrive(0, 0);
		}
	}
	
	public void turnLeft(double turnSpeed) {
		
		if (rightEncoder.get() - leftEncoder.get() < AUTO_LEFT_TURN_VALUE) {
			this.tankDrive(-turnSpeed, turnSpeed);
		}
		else {
			this.tankDrive(0, 0);
		}
	}
	
	public void resetAuto() {
		leftEncoder.reset();
		rightEncoder.reset();
		this.tankDrive(0, 0);
	}
	
	public double getLeftEncoder() {
		return (leftEncoder.getDistance() * 3.280839);
	}
	
	public double getRightEncoder() {
		return (rightEncoder.getDistance() * 3.280839);
	}

}

