package org.usfirst.frc.team1787.subsystems;

import java.awt.RenderingHints;

import org.usfirst.frc.team1787.shooting.Intake;
import org.usfirst.frc.team1787.shooting.Shooter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {

	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Shooter shooter = Shooter.getInstance();
	private Intake intake = Intake.getInstance();
	private static final Autonomous instance = new Autonomous();
	private int autonomousActionNumber = 0;
	private double lastRightEncoderValue = -1;
	private double lastLeftEncoderValue = -1;
	private int autoShootingTimer = 0;
	private int intakeTimer = 0;
	
	private final double AUTO_CORRECTION_DISTANCE = 0.2;
	private int timedBaselineCounter = 0;
	
	private final double TIMED_BASELINE_CROSS = 165; //(450/2) - 60
	private final double FOOT_COUNTER_DURATION = 50; //Random guess, change later
	private double totalIncrementsTravelled_A = 0;
	private double totalIncrementsTravelled_B = 0;
	private final double TIMED_TURN_DURATION = 100; //Random guess, change later
	
	
	
	public void autonomousStraight(double moveSpeed, double moveDistance) {
		driveTrain.moveStraight(moveDistance-0.333, moveSpeed);

		if (driveTrain.getLeftEncoder() > moveDistance-0.333 || driveTrain.getRightEncoder() > moveDistance-0.333) {
			autonomousActionNumber++;
			driveTrain.resetAuto();
		}
	}

	/**
	 * Turn method
	 */
	public void autonomousTurn(double turnSpeed, char turnDirection) {
		if (turnDirection == 'L' || turnDirection == 'l') {

			driveTrain.turnLeft(turnSpeed);

			if (driveTrain.getLeftEncoder() == lastLeftEncoderValue
					|| driveTrain.getRightEncoder() == lastRightEncoderValue) {
				autonomousActionNumber++;
				driveTrain.resetAuto();
				lastRightEncoderValue = -1;
				lastLeftEncoderValue = -1;
			}

		}

		else if (turnDirection == 'R' || turnDirection == 'r') {

			driveTrain.turnRight(turnSpeed);

			if (driveTrain.getLeftEncoder() == lastLeftEncoderValue
					|| driveTrain.getRightEncoder() == lastRightEncoderValue) {
				autonomousActionNumber++;
				driveTrain.resetAuto();
				lastRightEncoderValue = -1;
				lastLeftEncoderValue = -1;
			}
		}

		else {
			driveTrain.tankDrive(0.1, -0.1);
		}

		lastRightEncoderValue = driveTrain.getRightEncoder();
		lastLeftEncoderValue = driveTrain.getLeftEncoder();
	}

	
	public void autonomousIntake(double intakeSpeed, int intakeTime) {
		if (intakeTime < 10) {
			intake.turnOnWheels(intakeSpeed, intakeSpeed);
		}
		else {
			intake.turnOffWheels();
			intakeTimer = 0;
			autonomousActionNumber++;
		}
		
	}
	
	
	
	
	/**
	 * Do nothing
	 */
	public void doNothing() {

	}

	
	
	public void timedBaseline() {
		if (timedBaselineCounter < TIMED_BASELINE_CROSS) {
			driveTrain.tankDrive(0.4, 0.4);
			timedBaselineCounter++;
		}
		else {
			driveTrain.tankDrive(0, 0);
		}
	}
	
	public void timedBaselineSameShot() {
		
		//When the bot and the switch are on the same side
		
		if (timedBaselineCounter < TIMED_BASELINE_CROSS) {
			driveTrain.tankDrive(0.4, 0.4);
			//shooter.shootThoseDankCubes(.55, .6, autoShootingTimer, 50);
			//autoShootingTimer++;
		}
		else if (timedBaselineCounter < (TIMED_BASELINE_CROSS + 55) && timedBaselineCounter > TIMED_BASELINE_CROSS) {
			driveTrain.tankDrive(0.001, 0.001);
			shooter.shootThoseDankCubes(.55, .6, autoShootingTimer, 50);
			autoShootingTimer++;
		}
		else {
			shooter.resetForThoseDankCubes();
		}
		timedBaselineCounter++;
	}
	
	public void timedBaselineDiffShotGoL() {
		//When the switch is to the left of the bot
		
		if (timedBaselineCounter < (2*FOOT_COUNTER_DURATION)) { //two feet, change later
			driveTrain.tankDrive(0.4, 0.4);
		}
		else if (timedBaselineCounter > ((2*FOOT_COUNTER_DURATION))) {
			
		}
		
	}
	
	public void timedBaselineDiffShotGoR() {
		//When the switch is to the right of the bot
		
		/*
		 * 2ft forward
		 * turn right
		 * 10ft forward (can be rounded down slightly to get more centered on switch)
		 * turn left
		 * 6.33ft forward (round up to 6.5 or 7 to ensure against wall)
		 */
		
		if (timedBaselineCounter < (2*FOOT_COUNTER_DURATION)) {
			driveTrain.tankDrive(0.4, 0.4);
			totalIncrementsTravelled_A = 2*FOOT_COUNTER_DURATION;
		}
		else if (timedBaselineCounter < (totalIncrementsTravelled_A + TIMED_TURN_DURATION)) {
			driveTrain.tankDrive(0.4, -0.4);
			totalIncrementsTravelled_B = totalIncrementsTravelled_A + TIMED_TURN_DURATION;
		}
		else if (timedBaselineCounter < (totalIncrementsTravelled_B + 10*FOOT_COUNTER_DURATION)) {
			driveTrain.tankDrive(0.4, 0.4);
			totalIncrementsTravelled_A = totalIncrementsTravelled_B + 10*FOOT_COUNTER_DURATION;
		}
		else if (timedBaselineCounter < (totalIncrementsTravelled_A + TIMED_TURN_DURATION)) {
			driveTrain.tankDrive(-0.4, 0.4);
			totalIncrementsTravelled_B = totalIncrementsTravelled_A + TIMED_TURN_DURATION;
		}
		else if (timedBaselineCounter < (totalIncrementsTravelled_B + 6.5*FOOT_COUNTER_DURATION)) {
			driveTrain.tankDrive(0.4, 0.4);
			totalIncrementsTravelled_A = totalIncrementsTravelled_B + 6.5*FOOT_COUNTER_DURATION;
		}
		else if (timedBaselineCounter < (totalIncrementsTravelled_A + 55)) {
			shooter.shootThoseDankCubes(0.55, 0.6, autoShootingTimer, 50);
			autoShootingTimer++;
		}
		
		timedBaselineCounter++;
		
		
	}
	
	
	
	public void pulseDistTest() {
		
		if (autonomousActionNumber == 0) {
			this.autonomousTurn(0.375, 'L');
		}
		else if (autonomousActionNumber == 1) {
			this.autonomousTurn(0.375, 'R');
		}
		
		//driveTrain.turnLeft(0.3);
	}
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * TICK BASED AUTOS
	 * 
	 */
	
	
	
	
	/**
	 * Move straight
	 */
	public void baseline() {
		if (autonomousActionNumber == 0) {
			this.autonomousStraight(0.3, 2); // DANNY IS LAME
		}
		
	}

	
	
	
	/**
	 * Short side corner to switch
	 */
	public void shortSwitch() {
		if (autonomousActionNumber == 0) {
			this.autonomousStraight(0.375, 11.667);
		}
		
		else if (autonomousActionNumber == 1) {
			this.autonomousTurn(0.375, 'R');
		}

		else if (autonomousActionNumber == 2) {
			this.autonomousStraight(0.375, 2.234);
		}
		
		else if (autonomousActionNumber == 3) {
			this.autonomousIntake(0.375, 6);
		}

		else if (autonomousActionNumber == 4) {
			autoShootingTimer++;
			shooter.shootThoseDankCubes(0.45, 0.6, autoShootingTimer, 50);
		}

		else if (autonomousActionNumber == 5) {
			shooter.resetForThoseDankCubes();
		}
	}

	
	
	
	/**
	 * Long side corner to switch
	 */
	public void longSwitch() {
		if (autonomousActionNumber == 0) {
			this.autonomousStraight(0.2, 11.667);
		}
		
		else if (autonomousActionNumber == 1) {
			this.autonomousTurn(0.2, 'L');
		}

		else if (autonomousActionNumber == 2) {
			//this.autonomousStraight(0.375, 2.234);
		}

		else if (autonomousActionNumber == 3) {
			autoShootingTimer++;
			shooter.shootThoseDankCubes(0.45, 0.6, autoShootingTimer, 50);
		}

		else if (autonomousActionNumber == 4) {
			shooter.resetForThoseDankCubes();
		}
	}

	
	
	
	/**
	 * Short side corner to scale
	 */
	public void shortScale() {
		if (autonomousActionNumber == 0) {
			this.autonomousStraight(0.375, 25.250);
		}
		
		else if (autonomousActionNumber == 1) {
			this.autonomousTurn(0.375, 'R');
		}

		else if (autonomousActionNumber == 2) {
			//Be careful of the encoders only counting up, if they do then negate 1.000
			this.autonomousStraight(-0.375, 1.000);
		}

		else if (autonomousActionNumber == 3) {
			autoShootingTimer++;
			shooter.shootThoseDankCubes(0.45, 0.6, autoShootingTimer, 50);
		}

		else if (autonomousActionNumber == 4) {
			shooter.resetForThoseDankCubes();
		}
	}

	
	
	
	/**
	 * Long side corner to scale
	 */
	public void longScale() {
		if (autonomousActionNumber == 0) {
			this.autonomousStraight(0.375, 25.250);
		}
		
		else if (autonomousActionNumber == 1) {
			this.autonomousTurn(0.375, 'L');
		}

		else if (autonomousActionNumber == 2) {
			//Be careful of the encoders only counting up, if they do then negate 1.000
			this.autonomousStraight(-0.375, 1.000);
		}

		else if (autonomousActionNumber == 3) {
			autoShootingTimer++;
			shooter.shootThoseDankCubes(0.45, 0.6, autoShootingTimer, 50);
		}

		else if (autonomousActionNumber == 4) {
			shooter.resetForThoseDankCubes();
		}
	}
	
	
	public void intakeShooterTest() {
		if (autonomousActionNumber == 0) {
			this.autonomousIntake(0.375, 6);
		}
		
		else if (autonomousActionNumber == 1) {
			shooter.shootThoseDankCubes(0.45, 0.6, autoShootingTimer, 50);
		}
		
		else if (autonomousActionNumber == 2) {
			shooter.resetForThoseDankCubes();
		}
	}
	
	
	
	public void pushDataToShuffleboard() {

	}

	public static Autonomous getInstance() {
		return instance;
	}

}
