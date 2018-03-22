package org.usfirst.frc.team1787.subsystems;

import java.awt.RenderingHints;

import org.usfirst.frc.team1787.shooting.Shooter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {

	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Shooter shooter = Shooter.getInstance();
	private static final Autonomous instance = new Autonomous();
	private int autonomousActionNumber = 0;
	private double lastRightEncoderValue = -1;
	private double lastLeftEncoderValue = -1;
	private int autoShootingTimer = 0;

	public void autonomousStraight(double moveSpeed, double moveDistance) {
		driveTrain.moveStraight(moveDistance, moveSpeed);

		if (driveTrain.getLeftEncoder() > moveDistance || driveTrain.getRightEncoder() > moveDistance) {
			autonomousActionNumber++;
			driveTrain.resetAuto();
		}
	}

	/**
	 * First boolean is left, second boolean is right
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

	
	public void autonomousShoot() {
		
	}
	
	
	
	
	/**
	 * Do nothing
	 */
	public void doNothing() {

	}

	
	
	
	
	/**
	 * Move straight 11.667 feet
	 */
	public void baseline() {
		this.autonomousStraight(0.375, 11.667);
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
			autoShootingTimer++;
			shooter.shootThoseDankCubes(0.45, 0.6, autoShootingTimer, 50);
		}

		else if (autonomousActionNumber == 4) {
			shooter.resetForThoseDankCubes();
		}
	}

	
	
	
	/**
	 * Long side corner to switch
	 */
	public void longSwitch() {
		if (autonomousActionNumber == 0) {
			this.autonomousStraight(0.375, 11.667);
		}
		
		else if (autonomousActionNumber == 1) {
			this.autonomousTurn(0.375, 'L');
		}

		else if (autonomousActionNumber == 2) {
			this.autonomousStraight(0.375, 2.234);
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
	
	
	
	public void pushDataToShuffleboard() {

	}

	public static Autonomous getInstance() {
		return instance;
	}

}
