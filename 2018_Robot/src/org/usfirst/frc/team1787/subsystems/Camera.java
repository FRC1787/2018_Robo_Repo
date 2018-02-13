package org.usfirst.frc.team1787.subsystems;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	
	
	private UsbCamera mainCam = new UsbCamera("main camera", 0);
	private CameraServer camServer = CameraServer.getInstance();
	public final int IMAGE_WIDTH_PIXELS = 160;
	public final int IMAGE_HEIGHT_PIXELS = 120;
	private static final Camera instance = new Camera();
	private CvSource outputStream;
	
	private Camera() {
		camServer.startAutomaticCapture(mainCam);
		mainCam.setResolution(IMAGE_WIDTH_PIXELS, IMAGE_HEIGHT_PIXELS);
	    mainCam.setFPS(30);
		
		outputStream = camServer.putVideo("Custom Camera Stream", IMAGE_WIDTH_PIXELS, IMAGE_HEIGHT_PIXELS);
	}
	
	public void pushDataToShuffleboard() {
		
	}
	
	public static Camera getInstance() {
	    return instance;
	  }
	

}
