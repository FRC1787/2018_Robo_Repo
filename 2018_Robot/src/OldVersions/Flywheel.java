package OldVersions;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel {
	
	private static final Flywheel instance = new Flywheel();
	
	//Setting up IDs with more intuitive names
	private final int TURRET_FLYWHEEL_TALON_ID = 5;
	private final int FLYWHEEL_ENCODER_A_CHANNEL = 6;
	private final int FLYWHEEL_ENCODER_B_CHANNEL = 7;
	
	private final double FLYWHEEL_VOLTAGE_SPEED_VALUE = 0.99;
	
	
	private WPI_TalonSRX flywheelMotor = new WPI_TalonSRX(TURRET_FLYWHEEL_TALON_ID);
	private Encoder flywheelEncoder = new Encoder(FLYWHEEL_ENCODER_A_CHANNEL, FLYWHEEL_ENCODER_B_CHANNEL);
	
	//PID Variables
	private double FLYWHEEL_KP = 0.0005;
	private double FLYWHEEL_KI = 0; //is not used in velocity PID loops
	private double FLYWHEEL_KD = 0; //tune after tuning P
	private double FLYWHEEL_KF = 1.0/75.0;
	private double FLYWHEEL_ENCODER_REVOLUTIONS_PER_PULSE = 1.0/2048;
	private double PID_ERROR_TOLERANCE = 0; //play around with this to make the 'correctness' value true	
	private PIDController flywheelController = new PIDController(FLYWHEEL_KP, FLYWHEEL_KI, FLYWHEEL_KD, FLYWHEEL_KF, flywheelEncoder, flywheelMotor, PIDController.kDefaultPeriod);
	
	private Flywheel() {
		
		flywheelEncoder.setReverseDirection(true);
		flywheelEncoder.setDistancePerPulse(FLYWHEEL_ENCODER_REVOLUTIONS_PER_PULSE);
	    flywheelEncoder.setPIDSourceType(PIDSourceType.kRate);
	    flywheelController.setAbsoluteTolerance(PID_ERROR_TOLERANCE);
	    
		flywheelMotor.configVoltageCompSaturation(12, 10);
		flywheelMotor.enableVoltageCompensation(true);
	}
	
	public void runFlywheelVoltage() {
		flywheelMotor.set(FLYWHEEL_VOLTAGE_SPEED_VALUE);
	}
	
	public void runFlywheelPID(double SETPOINT_INPUT) {
		flywheelController.enable();
		flywheelController.setSetpoint(SETPOINT_INPUT);
	}
	
	public void stopFlywheel() {
		flywheelController.reset();
		flywheelEncoder.reset();
		
	}
	
	public void sendShuffleboadData() {
		SmartDashboard.putData("Flywheel PID Controller", flywheelController);
		SmartDashboard.putData("Flywheel Encoder", flywheelEncoder);
		SmartDashboard.putNumber("Flywheel Encoder Ticks", flywheelEncoder.getRaw());
		SmartDashboard.putNumber("Flywheel Error", flywheelController.getError());
		SmartDashboard.putBoolean("Flywheel Correctness", flywheelController.onTarget());
	}
	
	public static Flywheel getInstance() {
		return instance;
	}
	
	public PIDController getPIDController() {
		return flywheelController;
	}
	
	

}
