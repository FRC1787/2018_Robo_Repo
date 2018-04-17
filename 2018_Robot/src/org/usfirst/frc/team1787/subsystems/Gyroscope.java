package org.usfirst.frc.team1787.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Gyroscope {
	private AnalogGyro gyro0 = new AnalogGyro(1);
	private static final Gyroscope instance = new Gyroscope();
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putNumber("Gyro Angle", gyro0.getAngle());
		//SmartDashboard.putNumber("Gyro", gyro.)
	}
	
	public static Gyroscope getInstance() {
		return instance;
	}
	
	public void resetGyro() {
		gyro0.reset();
	}
}
