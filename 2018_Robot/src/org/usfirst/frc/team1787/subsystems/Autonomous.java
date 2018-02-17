package org.usfirst.frc.team1787.subsystems;

import java.awt.RenderingHints;

import edu.wpi.first.wpilibj.Encoder;
	
public class Autonomous {
	private final int rightEncoderChannelA = 2;
	private final int rightEncoderChannelB = 3;
	private final int lefhtEncoderChannelA = 1;
	private final int lefhtEncoderChannelB = 0;
	private int leftEncoderCount = 0;
	private int rightEncoderCount = 0;
	//Oh boy	
	
	
	private Encoder rightEncoder = new Encoder(rightEncoderChannelA, rightEncoderChannelB);
	private Encoder leftEncoder = new Encoder(lefhtEncoderChannelA, lefhtEncoderChannelB);
	
   public void Distance () {
	   leftEncoderCount = leftEncoder.get();
	   rightEncoderCount = rightEncoder.get();
	   System.out.print(leftEncoderCount);
	   System.out.print(rightEncoderCount);
   }
	
	
	
}


