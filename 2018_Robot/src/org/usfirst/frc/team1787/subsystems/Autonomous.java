package org.usfirst.frc.team1787.subsystems;

public class Autonomous {
	//Oh boy	
	
	//private String position = "M";
	private double autoLength = 2;
	private double autoVoltage = 1;
	private double currentAutoTime = 0;
	
	private DriveTrain driveTrain = DriveTrain.getInstance();
	
	public void autonomous1() {
		if (currentAutoTime < autoLength) {
			driveTrain.tankDrive(autoVoltage, autoVoltage);
			currentAutoTime = currentAutoTime + 0.02;
		}
	}
	public void autonomousFlexible() {
		
	}
	
}


