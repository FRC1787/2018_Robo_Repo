package org.usfirst.frc.team1787.shooting;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * This is the class that ties the intake and the output together for the purpose of coordinating their movement. Both of those sub-systems are defined in
 * their respective classes.
 * 
 * This class also controls the movement of the entire shooter assembly on rails
 */

public class Shooter {
	
	/*
	 * Steps to synchronize
	 * 
	 * 1.) Start shooting motors (top and uptake motors) and briefly turn on intake motors
	 * Delay
	 * 2.) Open up the intake to release the cube
	 * Delay
	 * 3.) Engage the uptake by engaging the pistons
	 * Delay
	 * 4.) Move everything to default positions.
	 */
	
	private final int SHOOTER_MOVE_SOLENOID_ID = 4;
	private Solenoid shooterMoveSolenoid = new Solenoid(SHOOTER_MOVE_SOLENOID_ID);
	
	private Timer SHOOTER_TIMER;
	//private double TOP_SHOOTING_VOLTAGE = 0.1;
	//private double BOTTOM_SHOOTING_VOLTAGE = 0.1;
	//private double INTAKE_VOLTAGE = 0.1;
	
	//Extra talon
	private final int EXTRA_TALON_ID = 4;
	private WPI_TalonSRX extraTalon = new WPI_TalonSRX(EXTRA_TALON_ID);
	
	
	
	//Shooting stage times
	private double SHOOTING_STAGE_1_START_TIME = 0;
	private double SHOOTING_STAGE_1_END_TIME = 0.04;
	
	private double SHOOTING_STAGE_2_START_TIME = 0.06;
	private double SHOOTING_STAGE_2_END_TIME = 0.08;
	
	private double SHOOTING_STAGE_3_START_TIME = 0.08;
	private double SHOOTING_STAGE_3_END_TIME = 0.10;
	
	private double SHOOTING_STAGE_4_START_TIME = 0.12;
	private double SHOOTING_STAGE_4_END_TIME = 0.14;
	
	private double SHOOTING_STAGE_5_START_TIME = 0.16;
	private double SHOOTING_STAGE_5_END_TIME = 0.18;
	
	
	
	private Intake intake = Intake.getInstance();
	private Output output = Output.getInstance();
	
	private static final Shooter instance = new Shooter();
	
	
	private Shooter() {
		Timer.SetImplementation(new HardwareTimer());
		SHOOTER_TIMER = new Timer();
	}
	
	
	
	public void testSolenoid(boolean boolInput) {
		shooterMoveSolenoid.set(boolInput);
	}
	
	
	public void shootThoseDankCubes(double TOP_SHOOTING_VOLTAGE, double BOTTOM_SHOOTING_VOLTAGE, double INTAKE_VOLTAGE) {
		
		if (SHOOTER_TIMER.get() == 0) {
			SHOOTER_TIMER.start();
		}
		
		
		if (SHOOTER_TIMER.get() > SHOOTING_STAGE_1_START_TIME && SHOOTER_TIMER.get() < SHOOTING_STAGE_1_END_TIME) {
			//Start shooting motors (Stage 1 and 2 motors) and briefly turn on intake
			output.turnOnWheels(TOP_SHOOTING_VOLTAGE, BOTTOM_SHOOTING_VOLTAGE); //hello my name is nora and I like programming I learned how to type by playing minecraft
			intake.turnOnWheels(INTAKE_VOLTAGE);
		}
		else if (SHOOTER_TIMER.get() > SHOOTING_STAGE_2_START_TIME && SHOOTER_TIMER.get() < SHOOTING_STAGE_2_END_TIME) {
			//Stop intake motors
			intake.turnOffWheels();
		}
		else if (SHOOTER_TIMER.get() > SHOOTING_STAGE_3_START_TIME && SHOOTER_TIMER.get() < SHOOTING_STAGE_3_END_TIME) {
			//Open intake
			intake.releaseCube();
		}
		else if (SHOOTER_TIMER.get() > SHOOTING_STAGE_4_START_TIME && SHOOTER_TIMER.get() < SHOOTING_STAGE_4_END_TIME) {
			//Engage Stage 1 shooting motors
			output.squeezeCube();
		}
		else if (SHOOTER_TIMER.get() > SHOOTING_STAGE_5_START_TIME && SHOOTER_TIMER.get() < SHOOTING_STAGE_5_END_TIME) {
			//Reset everything
			output.turnOffWheels();
			output.releaseCube();
		}
		
		//SHOOTER_TIMER = SHOOTER_TIMER + 0.02;
	}
	
	public void resetForThoseDankCubes() {
		//Reset everything to default positions upon button release
		SHOOTER_TIMER.stop();
		SHOOTER_TIMER.reset();
	}
	
	public void pushDataToShuffleboard() {
		//shuffleboard data here
		SmartDashboard.putNumber("Shooter Timer", SHOOTER_TIMER.get());
		SmartDashboard.putNumber("Shooting S1 Start", SHOOTING_STAGE_1_START_TIME);
		SmartDashboard.putNumber("Shooting S1 END", SHOOTING_STAGE_1_END_TIME);
		SmartDashboard.putNumber("Shooting S2 Start", SHOOTING_STAGE_2_START_TIME);
		SmartDashboard.putNumber("Shooting S2 END", SHOOTING_STAGE_2_END_TIME);
		SmartDashboard.putNumber("Shooting S3 Start", SHOOTING_STAGE_3_START_TIME);
		SmartDashboard.putNumber("Shooting S3 END", SHOOTING_STAGE_3_END_TIME);
		SmartDashboard.putNumber("Shooting S4 Start", SHOOTING_STAGE_4_START_TIME);
		SmartDashboard.putNumber("Shooting S4 END", SHOOTING_STAGE_4_END_TIME);
		SmartDashboard.putNumber("Shooting S5 Start", SHOOTING_STAGE_5_START_TIME);
		SmartDashboard.putNumber("Shooting S5 END", SHOOTING_STAGE_5_END_TIME);
	}
	
	public static Shooter getInstance() {
		return instance;
	}

}
