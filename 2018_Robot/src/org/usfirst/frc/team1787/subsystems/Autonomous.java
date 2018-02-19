package org.usfirst.frc.team1787.subsystems;

import java.awt.RenderingHints;

import edu.wpi.first.wpilibj.Encoder;
	
public class Autonomous {
	private final int rightEncoderChannelA = 0;
	private final int rightEncoderChannelB = 1;
	private final int leftEncoderChannelA = 2;
	private final int leftEncoderChannelB = 3;
	private double wheelRadius = (6.25/2);
	private double wheelCircumfrence = (2*Math.PI*wheelRadius);
	private double pulsesPerRotation = 2532;
	private double distancePerPulse = wheelCircumfrence/pulsesPerRotation;
	
	private Encoder rightEncoder = new Encoder(rightEncoderChannelA, rightEncoderChannelB);
	private Encoder leftEncoder = new Encoder(leftEncoderChannelA, leftEncoderChannelB);
	
	
   public void Distance () {
	   
	   rightEncoder.setDistancePerPulse(distancePerPulse);
	   
   }
	
	
	
}


