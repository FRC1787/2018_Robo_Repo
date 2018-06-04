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
	 * 1.) Start shooting motors (top and uptake motors) and briefly turn on intake
	 * motors Delay 2.) Open up the intake to release the cube Delay 3.) Engage the
	 * uptake by engaging the pistons Delay 4.) Move everything to default
	 * positions.
	 */

	private final int SHOOTER_MOVE_SOLENOID_ID = 4;
	private Solenoid shooterMoveSolenoid = new Solenoid(SHOOTER_MOVE_SOLENOID_ID);

	// private double TOP_SHOOTING_VOLTAGE = 0.1;
	// private double BOTTOM_SHOOTING_VOLTAGE = 0.1;
	// private double INTAKE_VOLTAGE = 0.1;

	// Extra talon
	private final int EXTRA_TALON_ID = 4;
	private WPI_TalonSRX extraTalon = new WPI_TalonSRX(EXTRA_TALON_ID);

	private int rampUpTime = 0;

	private Intake intake = Intake.getInstance();
	private Output output = Output.getInstance();

	private static final Shooter instance = new Shooter();

	public void extendShooter() {
		shooterMoveSolenoid.set(true);
	}

	public void retractShooter() {
		shooterMoveSolenoid.set(false);
	}

	public void shootThoseDankCubes(double TOP_SHOOTING_VOLTAGE, double BOTTOM_SHOOTING_VOLTAGE, double SHOOTER_TIMER,
			double DISENGAGE_TIME) {

		if (SHOOTER_TIMER == 1) {
			output.releaseCube();
			intake.releaseCube();
			output.turnOnWheels(TOP_SHOOTING_VOLTAGE, 0);
			intake.turnOnWheels(0.375, 0.375);
		} else if (SHOOTER_TIMER == 11) {
			intake.squeezeCube();
			output.turnOnWheels(TOP_SHOOTING_VOLTAGE, -0.25);
		} else if (SHOOTER_TIMER == 31) {
			intake.releaseCube();
			intake.turnOffWheels();
		} else if (SHOOTER_TIMER == 45) {
			output.turnOnWheels(TOP_SHOOTING_VOLTAGE, BOTTOM_SHOOTING_VOLTAGE);
		} else if (SHOOTER_TIMER == 55) {
			output.squeezeCube();
		} else if (SHOOTER_TIMER == 64) {
			
		} else if (SHOOTER_TIMER > DISENGAGE_TIME+125) {
			output.turnOffWheels();
			intake.turnOffWheels();
			intake.squeezeCube();
			output.releaseCube();
		}
	}
	
	public void shootThoseDankCubesSideways(double TOP_LEFT_SHOOTING_VOLTAGE, double TOP_RIGHT_SHOOTING_VOLTAGE, double BOTTOM_SHOOTING_VOLTAGE, double SHOOTER_TIMER,
			double DISENGAGE_TIME) {

		if (SHOOTER_TIMER == 1) {
			output.releaseCube();
			// intake.releaseCube();
		} else if (SHOOTER_TIMER == 2) {
			output.turnOnWheelsSideways(TOP_LEFT_SHOOTING_VOLTAGE, TOP_RIGHT_SHOOTING_VOLTAGE, BOTTOM_SHOOTING_VOLTAGE);
		} else if (SHOOTER_TIMER == 3) {
			intake.turnOnWheels(0.375, 0.375);
		} else if (SHOOTER_TIMER > 12 && SHOOTER_TIMER < 14) {
			intake.releaseCube();
		} else if (SHOOTER_TIMER > 20 && SHOOTER_TIMER < 22) {
			intake.turnOffWheels();
		} else if (SHOOTER_TIMER > 22 && SHOOTER_TIMER < 26) {
			output.squeezeCube();
		} else if (SHOOTER_TIMER > DISENGAGE_TIME) {
			output.turnOffWheels();
			intake.squeezeCube();
		}
	}

	public void resetForThoseDankCubes() {
		// Reset everything to default positions upon button release
		output.turnOffWheels();
		output.squeezeCube();
		intake.squeezeCube();
		intake.turnOffWheels();
	}

	public void pushDataToShuffleboard() {
		// shuffleboard data here
		// SmartDashboard.putNumber("Shooter Timer", SHOOTER_TIMER.get());
	}

	public static Shooter getInstance() {
		return instance;
	}

}
