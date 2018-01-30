package org.usfirst.frc.team1787.shooting;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * The output section of the robot is defined as the 4 mini-CIM motors on the vertical part of the shooter assembly, as well as the pneumatics that push
 * the wheels on the CIMs into the cube (or "tote" as Mr. McMonigle refers to them!)
 */

public class Output {
	
	private final int LEFT_SHOOT_MASTER_TALON_ID = 6;
	private final int RIGHT_SHOOT_MASTER_TALON_ID = 7;
	private final int LEFT_SHOOT_FOLLOWER_VICTOR_ID = 8;
	private final int RIGHT_SHOOT_FOLLOWER_VICTOR_ID = 9;
	private final int OUTPUT_SOLENOID_ID = 3;
	
	private WPI_TalonSRX leftMaster = new WPI_TalonSRX(LEFT_SHOOT_MASTER_TALON_ID);
	private WPI_VictorSPX leftFollower = new WPI_VictorSPX(LEFT_SHOOT_FOLLOWER_VICTOR_ID);
	private WPI_TalonSRX rightMaster = new WPI_TalonSRX(RIGHT_SHOOT_MASTER_TALON_ID);
	private WPI_VictorSPX rightFollower = new WPI_VictorSPX(RIGHT_SHOOT_FOLLOWER_VICTOR_ID);
	
	private SpeedControllerGroup shooterMotors = new SpeedControllerGroup(leftMaster, rightMaster);
	
	
	//PID variables
	private final int OUTPUT_ENCODER_A_CHANNEL = 284; //Find actual values later
	private final int OUTPUT_ENCODER_B_CHANNEL = 129;
	
	private Encoder outputEncoder = new Encoder(OUTPUT_ENCODER_A_CHANNEL, OUTPUT_ENCODER_B_CHANNEL);
	
	
	private double OUTPUT_KP = 0;
	private double OUTPUT_KI = 0; //is not used in velocity PID loops
	private double OUTPUT_KD = 0; //tune after tuning P
	private double OUTPUT_KF = 0;
	private double OUTPUT_ENCODER_REVOLUTIONS_PER_PULSE = 1.0/2048;
	private double PID_ERROR_TOLERANCE = 0; //play around with this to make the 'correctness' value true	
	private PIDController outputController = new PIDController(OUTPUT_KP, OUTPUT_KI, OUTPUT_KD, OUTPUT_KF, outputEncoder, shooterMotors, PIDController.kDefaultPeriod);

	
	
	private Solenoid outputSolenoid = new Solenoid(OUTPUT_SOLENOID_ID);
	
	private static final Output instance = new Output();
	
	
	
	
	private Output() {
		leftFollower.follow(leftMaster);
		rightFollower.follow(rightMaster);
		
		outputEncoder.setReverseDirection(true);
		outputEncoder.setDistancePerPulse(OUTPUT_ENCODER_REVOLUTIONS_PER_PULSE);
		outputEncoder.setPIDSourceType(PIDSourceType.kRate);
		outputController.setAbsoluteTolerance(PID_ERROR_TOLERANCE);
		
		//Making every talon light up green when forwards
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
	

	
	public void turnOnWheels(double SETPOINT) {
		outputController.enable();
		outputController.setSetpoint(SETPOINT);
	}
	
	public void turnOffWheels() {
		outputController.reset();
		outputEncoder.reset();
	}
	
	
	
	public void squeezeCube() {
		outputSolenoid.set(true);
	}

	public void releaseCube() {
		outputSolenoid.set(false);
	}
	
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putNumber("Left Shooter Speed: ", leftMaster.get());
		SmartDashboard.putNumber("Right Shooter Speed: ", rightMaster.get());
	}
	
	public static Output getInstance() {
	    return instance;
	  }

}
