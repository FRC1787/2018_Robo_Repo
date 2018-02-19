
/*
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
*/

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
	
	public void testSolenoid(boolean boolInput) {
		shooterMoveSolenoid.set(boolInput);
	}
	
	
	
	
	
	/*
//  shoot into high scale 
	public void shootHighScale (double scaleVoltageTopHigh, double scaleVoltageBottomHigh, boolean buttonPressedHigh, boolean shootingTime, int rampUpTime, int disengageTime )
		 {
			buttonPressedHigh = true;
			shootingTime = true;
			
		if (buttonPressedHigh == true) {
			output.turnOnWheels(scaleVoltageTopHigh, scaleVoltageBottomHigh);
			rampUpTime++;
			if (rampUpTime >20 && rampUpTime < 22) {
				output.testSolenoid(true);
			}
		if (rampUpTime > disengageTime) {
				output.turnOffWheels();
				output.testSolenoid(false);
				buttonPressedHigh = false;
				shootingTime = false;
				rampUpTime =0;
			}
			
		}
	}
		// shoot into medium scale
	public void shootMediumScale (double scaleVoltageTopMed, double scaleVoltageBottomMed, boolean buttonPressedMed, boolean shootingTime, int rampUpTime, int disengageTime ) {
			
		
		 
			buttonPressedMed = true;
			shootingTime = true;
		
		if (buttonPressedMed == true) {
			output.turnOnWheels(scaleVoltageTopMed, scaleVoltageBottomMed);
			rampUpTime++;
			
			if (rampUpTime > 20 && rampUpTime < 22) {
				output.testSolenoid(true);
			}
			if (rampUpTime > disengageTime) {
				output.turnOffWheels();
				output.testSolenoid(false);
				buttonPressedMed = false;
				shootingTime = false;
				rampUpTime =0;
			}
		
		}
	}
		// shoot into switch
		public void shootSwitch (double switchVoltageTop, double switchVoltageBottom, boolean buttonPressedswitch, boolean shootingTime, int rampUpTime, int disengageTime ) {
			buttonPressedswitch = true;
			shootingTime = true;
		
		if (buttonPressedswitch == true) {
			output.turnOnWheels(switchVoltageTop, switchVoltageBottom);
			rampUpTime++;
			
			if (rampUpTime > 20 && rampUpTime < 22) {
				output.testSolenoid(true);
			}
			if (rampUpTime > disengageTime) {
				output.turnOffWheels();
				output.testSolenoid(false);
				buttonPressedswitch = false;
				shootingTime = false;
				rampUpTime =0;
			}
		}
		}
	*/
	
	public void shootThoseDankCubes(double TOP_SHOOTING_VOLTAGE, double BOTTOM_SHOOTING_VOLTAGE, double INTAKE_VOLTAGE) {
		
		
		
	}
	
	
	
	
	public void resetForThoseDankCubes() {
		//Reset everything to default positions upon button release
		SHOOTER_TIMER.stop();
		SHOOTER_TIMER.reset();
	}
	
	public void pushDataToShuffleboard() {
		//shuffleboard data here
		//SmartDashboard.putNumber("Shooter Timer", SHOOTER_TIMER.get());
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



