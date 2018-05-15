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
	
	private final double AUTO_CORRECTION_DISTANCE = 0.333;	
	
	
	
	public void autonomousStraight(double moveSpeed, double moveDistance) {
		driveTrain.moveStraight(moveDistance-AUTO_CORRECTION_DISTANCE, moveSpeed);

		if (driveTrain.getLeftEncoder() > moveDistance-AUTO_CORRECTION_DISTANCE || driveTrain.getRightEncoder() > moveDistance-AUTO_CORRECTION_DISTANCE) {
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

	
	
	
	public void pulseStraightTest() {
		if (autonomousActionNumber == 0) {
			autonomousStraight(0.2, 6);
		}
	}
	
	
	
	
	public void resetAuto() {
		autonomousActionNumber = 0;
	}
	
	public void pushDataToShuffleboard() {

	}

	public static Autonomous getInstance() {
		return instance;
	}

}
