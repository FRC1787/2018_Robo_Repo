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
	  
   
   
   public void autonomousStraight(double moveDistance, double moveSpeed) {
	   driveTrain.moveStraight(moveDistance, moveSpeed);
	   
	   if (driveTrain.getLeftEncoder() > moveDistance || driveTrain.getRightEncoder() > moveDistance) {
		   autonomousActionNumber++;
		   driveTrain.resetAuto();
	   }
   }
   
   
   /**
    * First boolean is left, second boolean is right
    */
   public void autonomousTurn(double turnSpeed, boolean leftTurn, boolean rightTurn) {
	   if (leftTurn == true) {
		   
		   driveTrain.turnLeft(turnSpeed);
		   
		   if (driveTrain.getLeftEncoder() == lastLeftEncoderValue || driveTrain.getRightEncoder() == lastRightEncoderValue) {
			   autonomousActionNumber++;
			   driveTrain.resetAuto();
			   lastRightEncoderValue = -1;
			   lastLeftEncoderValue = -1;
		   }
		   
		   
	   }
	   
	   else if (rightTurn == true) {
		   
		   driveTrain.turnRight(turnSpeed);
		   
		   if (driveTrain.getLeftEncoder() == lastLeftEncoderValue || driveTrain.getRightEncoder() == lastRightEncoderValue) {
			   autonomousActionNumber++;
			   driveTrain.resetAuto();
			   lastRightEncoderValue = -1;
			   lastLeftEncoderValue = -1;
		   }
	   }
	   
	   lastRightEncoderValue = driveTrain.getRightEncoder();
	   lastLeftEncoderValue = driveTrain.getLeftEncoder();
   }
   
   
	public void autonomousPeriodic01() {
	   
	   if (autonomousActionNumber == 0) {
		   this.autonomousStraight(0.25, 0.375);
	   }
	   
	   else if (autonomousActionNumber == 1) {
		   this.autonomousTurn(0.375, true, false);
	   }
	   
	   else if (autonomousActionNumber == 2) {
		   this.autonomousTurn(0.375, false, true);
	   }
	   
	   
	   
	   
	   
	   
   }
	
	
	

   
   
   public void pushDataToShuffleboard() {
	   
   }
   
   
   public static Autonomous getInstance() {
	    return instance;
	  }
	
	
	
}


