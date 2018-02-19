package org.usfirst.frc.team1787.subsystems;

import java.awt.RenderingHints;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
	
public class Autonomous {
	
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private static final Autonomous instance = new Autonomous();
	private int autonomousActionNumber = 0;
	private double lastRightEncoderValue = -1;
	private double lastLeftEncoderValue = -1;
	  
   
   
   
	public void autonomousPeriodic01() {
	   
	   if (autonomousActionNumber == 0) {
		   
		   driveTrain.moveStraight(0.25, 0.375);
		   
		   if (driveTrain.getLeftEncoder() > 0.5 || driveTrain.getRightEncoder() > 0.5) {
			   autonomousActionNumber = 1;
			   driveTrain.resetAuto();
		   }
	   }
	   /*
	   else if (autonomousActionNumber == 1) {
		   
		   driveTrain.moveStraight(0.25, -0.375);
		   
		   if (driveTrain.getLeftEncoder() < -0.5 || driveTrain.getRightEncoder() < -0.5) {
			   autonomousActionNumber = 2;
			   driveTrain.resetAuto();
		   }
	   }
	   */
	   
	   else if (autonomousActionNumber == 1) {
		   
		   driveTrain.turnLeft(0.375);
		   
		   if (driveTrain.getLeftEncoder() == lastLeftEncoderValue || driveTrain.getRightEncoder() == lastRightEncoderValue) {
			   autonomousActionNumber = 2;
			   driveTrain.resetAuto();
			   lastRightEncoderValue = -1;
			   lastLeftEncoderValue = -1;
		   }
		   
		   lastRightEncoderValue = driveTrain.getRightEncoder();
		   lastLeftEncoderValue = driveTrain.getLeftEncoder();
	   }
	   
	   else if (autonomousActionNumber == 2) {
		   
		   driveTrain.turnRight(0.375);
		   
		   if (driveTrain.getLeftEncoder() == lastLeftEncoderValue || driveTrain.getRightEncoder() == lastRightEncoderValue) {
			   autonomousActionNumber = 3;
			   driveTrain.resetAuto();
			   lastRightEncoderValue = -1;
			   lastLeftEncoderValue = -1;
		   }
		   
		   lastRightEncoderValue = driveTrain.getRightEncoder();
		   lastLeftEncoderValue = driveTrain.getLeftEncoder();
	   }
	   
	   
	   
	   
	   
	   
   }
   
   
   public void pushDataToShuffleboard() {
	   
   }
   
   
   public static Autonomous getInstance() {
	    return instance;
	  }
	
	
	
}


