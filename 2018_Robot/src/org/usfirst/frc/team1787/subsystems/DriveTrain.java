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

	private WPI_TalonSRX leftMaster = new WPI_TalonSRX(10);
	private WPI_VictorSPX leftFollower = new WPI_VictorSPX(11);
	private WPI_TalonSRX rightMaster = new WPI_TalonSRX(9);
	private WPI_VictorSPX rightFollower = new WPI_VictorSPX(8);

	private Solenoid gearboxSolenoid = new Solenoid(3)

	private final int rightEncoderChannelA = 0;
	private final int rightEncoderChannelB = 1;
	private final int leftEncoderChannelA = 2;
	private final int leftEncoderChannelB = 3;

	private Encoder rightEncoder = new Encoder(rightEncoderChannelA, rightEncoderChannelB, true);
	private Encoder leftEncoder = new Encoder(leftEncoderChannelA, leftEncoderChannelB, true);

	private final double WHEEL_RADIUS = (6.05/2)/12; //3in converted to feet
	private final double WHEEL_CIRCUMFERENCE = (2 * Math.PI * WHEEL_RADIUS);
	private final double PULSES_PER_ROTATION = 2532;
	private final double DISTANCE_PER_PULSE = WHEEL_CIRCUMFERENCE / PULSES_PER_ROTATION;

	private static final DriveTrain instance = new DriveTrain();
	

	private DriveTrain() {

		// Pairing each master Talon with it's follower Victor
		// leftFollower.follow(leftMaster);
		// rightFollower.follow(rightMaster);

		// Inverting all of the talons so that they all light up green when the robot
		// goes forward
		leftMaster.setInverted(true);
		leftFollower.setInverted(true);
		rightMaster.setInverted(false);
		rightFollower.setInverted(false);

		// Voltage Compensation for the talons
		leftMaster.configVoltageCompSaturation(12, 10);
		leftFollower.configVoltageCompSaturation(12, 10);
		rightMaster.configVoltageCompSaturation(12, 10);
		rightFollower.configVoltageCompSaturation(12, 10);

		// Enable voltage compensation
		leftMaster.enableVoltageCompensation(true);
		leftFollower.enableVoltageCompensation(true);
		rightMaster.enableVoltageCompensation(true);
		rightFollower.enableVoltageCompensation(true);

		// testing stuff
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
		leftFollower.setNeutralMode(NeutralMode.Brake);
		rightFollower.setNeutralMode(NeutralMode.Brake);

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
		SmartDashboard.putNumber("Left Enc. Dist.", leftEncoder.getDistance());
		SmartDashboard.putNumber("Right Enc. Dist.", rightEncoder.getDistance());
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
		} else if (motorOutput < -1) {
			return -1;
		} else {
			return motorOutput;
		}
	}

	@SuppressWarnings("ParameterName")
	public void arcadeDrive(double yInput, double xInput) {
		// Basic drive class, makes the robot move forwards or backwards with a rotation

		// Makes driving input feel less sensitive, better control
		int y = yInput * Math.abs(yInput);
		int x = xInput * Math.abs(xInput);

		// Simons algorithm
		double leftMotorOutput = y + x;
		double rightMotorOutput = y - x;

		// Truncate values
		leftMotorOutput = truncateMotorOutput(leftMotorOutput);
		rightMotorOutput = truncateMotorOutput(rightMotorOutput);

		// Set left motor outputs
		leftMaster.set(leftMotorOutput);
		leftFollower.set(leftMotorOutput);
		
		// Set right motor outputs
		rightMaster.set(rightMotorOutput);
		rightFollower.set(rightMotorOutput);

	}

	public void highGear() {
		gearboxSolenoid.set(false);
	}

	public void lowGear() {
		gearboxSolenoid.set(true);
	}
	

	public void resetAuto() {
		leftEncoder.reset();
		rightEncoder.reset();
		this.tankDrive(0, 0);
	}

	public double getLeftEncoderDistance() {
		return leftEncoder.getDistance();
	}

	public double getRightEncoderDistance() {
		return rightEncoder.getDistance();
	}
	
	public int getRightEncoderValue() {
		return rightEncoder.get();
	}
	public int getLeftEncoderValue() {
		return leftEncoder.get();
	}

}
