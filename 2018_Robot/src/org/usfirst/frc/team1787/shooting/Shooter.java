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
	
	public int SHOOTER_TIMER = 0;
	
	//private double TOP_SHOOTING_VOLTAGE = 0.1;
	//private double BOTTOM_SHOOTING_VOLTAGE = 0.1;
	//private double INTAKE_VOLTAGE = 0.1;
	
	//Extra talon
	private final int EXTRA_TALON_ID = 4;
	private WPI_TalonSRX extraTalon = new WPI_TalonSRX(EXTRA_TALON_ID);
	
	
	
	
	private Intake intake = Intake.getInstance();
	private Output output = Output.getInstance();
	
	private static final Shooter instance = new Shooter();
	
	
	private Shooter() {

	}
	
	
	
	public void moveSolenoid(boolean boolInput) {
		shooterMoveSolenoid.set(boolInput);
	}
	
	
	public void shootThoseDankCubes(double TOP_SHOOTING_POWER, double BOTTOM_SHOOTING_POWER, double INTAKE_POWER, int SHOOTING_RAMP_UP_TIME) {
		
		
		output.turnOnWheels(TOP_SHOOTING_POWER, BOTTOM_SHOOTING_POWER);
		
		 //hello my name is nora and I like programming I learned how to type by playing minecraft
		
		if (SHOOTER_TIMER == 0) {
			intake.turnOnWheels(INTAKE_POWER);
		}
		else if (SHOOTER_TIMER == 5) {
			intake.turnOffWheels();
		}
		else if (SHOOTER_TIMER == SHOOTING_RAMP_UP_TIME) {
			output.squeezeCube();
		}
		else if (SHOOTER_TIMER == (SHOOTING_RAMP_UP_TIME + 8)) {
			output.releaseCube();
		}
		
		SHOOTER_TIMER++;
		
	}
	
	public void resetForThoseDankCubes() {
		//Reset everything to default positions upon button release
		output.turnOffWheels();
		SHOOTER_TIMER = 0;
	}
	
	public void pushDataToShuffleboard() {
		//shuffleboard data here
		SmartDashboard.putNumber("Shooter Timer", SHOOTER_TIMER);

	}
	
	public static Shooter getInstance() {
		return instance;
	}

}
