package org.usfirst.frc.team1787.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1787.subsystems.Autonomous;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoCamera.WhiteBalance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	private UsbCamera topCam;
	private UsbCamera bottomCam;
	
	private static final Vision instance = new Vision();
	
	private CvSource outputStream;
	private CvSink topFrameGrabber;
	private CvSink bottomFrameGrabber;
	private int STANDARD_IMG_WIDTH = 160;
	private int STANDARD_IMG_HEIGHT = 120;
	private Mat originalFrame;
	private Mat processedFrame;
	private final Scalar FILTER_UPPER_BOUND = new Scalar(100, 255, 255);
	private final Scalar FILTER_LOWER_BOUND = new Scalar(60, 0, 20);
	
	private Vision() {
		CameraServer server = CameraServer.getInstance();
		  
		topCam = server.startAutomaticCapture(1);
		bottomCam = server.startAutomaticCapture(0);
		
		bottomCam.setResolution(STANDARD_IMG_WIDTH, STANDARD_IMG_HEIGHT);
		bottomCam.setFPS(20);
		bottomCam.setExposureManual(0);
		bottomCam.setBrightness(100);
		bottomCam.setWhiteBalanceManual(WhiteBalance.kFixedIndoor);
		
		topCam.setResolution(STANDARD_IMG_WIDTH, STANDARD_IMG_HEIGHT);
		topCam.setFPS(20);
		topCam.setExposureManual(0);
		bottomCam.setBrightness(100);
		bottomCam.setWhiteBalanceManual(WhiteBalance.kFixedIndoor);
		
		topFrameGrabber = server.getVideo(topCam);
		bottomFrameGrabber = server.getVideo(bottomCam);
		
		outputStream = server.putVideo("Processed Video Stream", STANDARD_IMG_WIDTH, STANDARD_IMG_HEIGHT);
		
		originalFrame = new Mat();
		processedFrame = new Mat();
	}
	
	
	public void pushFrameToOutput(Mat currentFrame) {
		if (!currentFrame.empty()) {
			outputStream.putFrame(currentFrame);
		}
	}
	
	
	public void visionProcessing() {
		
		//processedFrame is initally empty, and it is then populated by the top camera's frame
		//because grabFrame takes a frame location as input and then writes to that location
		topFrameGrabber.grabFrame(originalFrame, 1.0);
				
		Imgproc.cvtColor(originalFrame, processedFrame, Imgproc.COLOR_BGR2HSV);
	    
	    // This is the HSV filter
	    Core.inRange(processedFrame, FILTER_LOWER_BOUND, FILTER_UPPER_BOUND, processedFrame);
		
		pushFrameToOutput(processedFrame);
	}
	
	

	public static Vision getInstance() {
		return instance;
	}
}
