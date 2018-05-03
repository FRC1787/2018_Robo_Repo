package org.usfirst.frc.team1787.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Gyroscope {
	private AnalogGyro gyro0 = new AnalogGyro(0);
	private static final Gyroscope instance = new Gyroscope();
	private double fixedGyroOutput = 0;
	private double fixedGyroCounter = 0;
	private double gyroDriftHalfSecond = 0;
	private final double GYRO_OFFSET_COEFFICIENT = 0.0485;//0.002400075;
	
	
	
	
	public void updateGyro() {
		fixedGyroCounter = fixedGyroCounter + 0.02;
		fixedGyroOutput = (gyro0.getAngle()) + (GYRO_OFFSET_COEFFICIENT * fixedGyroCounter);
	}
	
	public double returnGyro() {
		return fixedGyroOutput;
	}
	
	public double gyroDrift() {
		fixedGyroCounter = fixedGyroCounter + 0.02;
		if (fixedGyroCounter > 1.75 && fixedGyroCounter < 1.77) {
			this.resetGyro();
		}
		else if (fixedGyroCounter > 2.25 && fixedGyroCounter < 2.27) {
			gyroDriftHalfSecond = gyro0.getAngle();
		}
		else if (fixedGyroCounter > 2.3) {
			this.resetGyro();
		}
		
		
		return fixedGyroCounter;
	}
	
	public void pushDataToShuffleboard() {	
		SmartDashboard.putNumber("New Gyro Angle", fixedGyroOutput);
	}
	
	public void resetGyro() {
		gyro0.reset();
		fixedGyroCounter = 0;
	}

	public static Gyroscope getInstance() {
		return instance;
	}
}
