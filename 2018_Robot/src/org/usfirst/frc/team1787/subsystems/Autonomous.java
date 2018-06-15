package org.usfirst.frc.team1787.subsystems;

import java.awt.RenderingHints;
import java.security.PrivilegedActionException;

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
	private int waitTimer = 0;
	public final int AUTO_TURN_VALUE = 4830;
	private final double SHOOTING_DISENGAGE_TIME = 50;
	
	private final double AUTO_CORRECTION_DISTANCE = 0; //0.333;	
	
	
	
	public void autonomousStraight(double moveSpeed, double moveDistance) {

		if (Math.abs(driveTrain.getLeftEncoderDistance()) > moveDistance-AUTO_CORRECTION_DISTANCE && Math.abs(driveTrain.getRightEncoderDistance()) > moveDistance-AUTO_CORRECTION_DISTANCE) {
			autonomousActionNumber++;
			driveTrain.resetAuto();
			driveTrain.tankDrive(0, 0);
		}
		
		else {
			driveTrain.tankDrive(moveSpeed, moveSpeed);
		}
	}

	/**
	 * Turn method
	 */
	public void autonomousTurn(double turnSpeed, char turnDirection) {
		if (turnDirection == 'L' || turnDirection == 'l') {

			if ( Math.abs(driveTrain.getLeftEncoderValue()) - driveTrain.getRightEncoderValue() <= AUTO_TURN_VALUE  || Math.abs(driveTrain.getRightEncoderValue()) - driveTrain.getLeftEncoderValue() <= AUTO_TURN_VALUE) {
				driveTrain.tankDrive(-turnSpeed, turnSpeed);
				
			} else if (Math.abs(driveTrain.getLeftEncoderValue()) - driveTrain.getRightEncoderValue() >= AUTO_TURN_VALUE  || Math.abs(driveTrain.getRightEncoderValue()) - driveTrain.getLeftEncoderValue() >= AUTO_TURN_VALUE) {
				driveTrain.resetAuto();
				autonomousActionNumber++;
			}

		}

		else if (turnDirection == 'R' || turnDirection == 'r') {

			if ( Math.abs(driveTrain.getLeftEncoderValue()) + driveTrain.getRightEncoderValue() <= AUTO_TURN_VALUE  || Math.abs(driveTrain.getRightEncoderValue()) + driveTrain.getLeftEncoderValue() <= AUTO_TURN_VALUE ) {
				driveTrain.tankDrive(turnSpeed, -turnSpeed);
				
			} else if (Math.abs(driveTrain.getLeftEncoderValue()) + driveTrain.getRightEncoderValue() >= AUTO_TURN_VALUE  || Math.abs(driveTrain.getRightEncoderValue()) + driveTrain.getLeftEncoderValue() >= AUTO_TURN_VALUE) {
				driveTrain.resetAuto();
				autonomousActionNumber++;
			}
		}
	}

	public void autonomousIntake(double intakeSpeed, int intakeTime) {
		if (intakeTime < 100) {
			intake.turnOnWheels(intakeSpeed, intakeSpeed);
		}
		else {
			intake.turnOffWheels();
			intakeTimer = 0;
			autonomousActionNumber++;
		}
		
	}
	
	public void autonomousShoot(char shootingPower, int shootingTime) {
		if (shootingPower == 'H') {
			shooter.shootThoseDankCubes(1.0, 0.9, autoShootingTimer, SHOOTING_DISENGAGE_TIME);
		}
		else if (shootingPower == 'M') {
			shooter.shootThoseDankCubes(0.8, 0.7, autoShootingTimer, SHOOTING_DISENGAGE_TIME);
		}
		else if (shootingPower == 'L') {
			shooter.shootThoseDankCubes(0.55, 0.6, autoShootingTimer, SHOOTING_DISENGAGE_TIME);

		}
		autoShootingTimer++;
	}
	
	
	public void autonomousWait(int waitTime) {
		if (waitTimer < waitTime) {
			waitTimer++;
		}
		else {
			waitTimer = 0;
			autonomousActionNumber++;
		}
	}
	
	/**
	 * Do nothing
	 */
	public void doNothing() {
		driveTrain.tankDrive(0, 0);
	}
	
	
	
	
	
	
	
	public void screwYouVan() {
		if (autonomousActionNumber == 0) {
			this.autonomousWait(100);
		}
		else if (autonomousActionNumber == 1) {
			this.autonomousStraight(0.2, 10*12);
		}
		else if (autonomousActionNumber == 2) {
			this.doNothing();
		}
			
	}
		
	
	public void pulseStraightTest() {
		if (autonomousActionNumber == 0) {
			autonomousStraight(0.2, 2);
		}
		else if (autonomousActionNumber == 1) {
			autonomousTurn(0.2, 'L');
		}
		else if (autonomousActionNumber == 2) {
			autonomousTurn(0.2, 'R');
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
