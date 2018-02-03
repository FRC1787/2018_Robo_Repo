package org.usfirst.frc.team1787.shooting;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

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
	private double SHOOTER_TIMER = 0;
	private double SHOOTING_VOLTAGE = 0.5;
	private double INTAKE_VOLTAGE = 0.5;
	
	
	
	//Shooting stage times (Currently arbitrary)
	private double SHOOTING_STAGE_1_START_TIME = 0;
	private double SHOOTING_STAGE_1_END_TIME = 1.2;
	
	private double SHOOTING_STAGE_2_START_TIME = 2;
	private double SHOOTING_STAGE_2_END_TIME = 2.2;
	
	private double SHOOTING_STAGE_3_START_TIME = 3;
	private double SHOOTING_STAGE_3_END_TIME = 3.2;
	
	private double SHOOTING_STAGE_4_START_TIME = 4;
	private double SHOOTING_STAGE_4_END_TIME = 4.2;
	
	private double SHOOTING_STAGE_5_START_TIME = 5;
	private double SHOOTING_STAGE_5_END_TIME = 5.2;
	
	
	
	private Intake intake = Intake.getInstance();
	private Output output = Output.getInstance();
	
	private static final Shooter instance = new Shooter();
	
	public void shootThoseDankCubes() {
		
		if (SHOOTER_TIMER > SHOOTING_STAGE_1_START_TIME && SHOOTER_TIMER < SHOOTING_STAGE_1_END_TIME) {
			//Start shooting motors (Stage 1 and 2 motors) and briefly turn on intake
			output.turnOnWheels(SHOOTING_VOLTAGE); //hello my name is nora and I like programming I learned how to type by playing minecraft
			intake.turnOnWheels(INTAKE_VOLTAGE);
		}
		else if (SHOOTER_TIMER > SHOOTING_STAGE_2_START_TIME && SHOOTER_TIMER < SHOOTING_STAGE_2_END_TIME) {
			//Stop intake motors
			intake.turnOffWheels();
		}
		else if (SHOOTER_TIMER > SHOOTING_STAGE_3_START_TIME && SHOOTER_TIMER < SHOOTING_STAGE_3_END_TIME) {
			//Open intake
			intake.releaseCube();
		}
		else if (SHOOTER_TIMER > SHOOTING_STAGE_4_START_TIME && SHOOTER_TIMER < SHOOTING_STAGE_4_END_TIME) {
			//Engage Stage 1 shooting motors
			output.squeezeCube();
		}
		else if (SHOOTER_TIMER > SHOOTING_STAGE_5_START_TIME && SHOOTER_TIMER < SHOOTING_STAGE_5_END_TIME) {
			//Reset everything
			output.turnOffWheels();
			output.releaseCube();
		}
		
		SHOOTER_TIMER = SHOOTER_TIMER + 0.02;
	}
	
	public void resetForThoseDankCubes() {
		//Reset everything to default positions upon button release
		SHOOTER_TIMER = 0;
	}
	
	public void pushDataToShuffleboard() {
		//shuffleboard data here
	}
	
	public static Shooter getInstance() {
		return instance;
	}

}
