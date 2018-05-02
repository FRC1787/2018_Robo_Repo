package org.usfirst.frc.team1787.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Gyroscope {
	private AnalogGyro gyro0 = new AnalogGyro(0);
	private static final Gyroscope instance = new Gyroscope();
	private double fixedGyroOutput = 0;
	private double fixedGyroCounter = 0;
	private final double GYRO_OFFSET_COEFFICIENT = 0;//0.0024000675;//0.002400075;
	
	
	public void pushDataToShuffleboard() {	
		SmartDashboard.putNumber("Gyro Angle", fixedGyroOutput);
	}
	
	public void updateGyro() {
		fixedGyroCounter = fixedGyroCounter + 0.02;
		fixedGyroOutput = (gyro0.getAngle()) + (GYRO_OFFSET_COEFFICIENT * fixedGyroCounter);
	}
	
	public double returnGyro() {
		return fixedGyroOutput;
	}
	
	public void resetGyro() {
		gyro0.reset();
	}

	public static Gyroscope getInstance() {
		return instance;
	}
}
