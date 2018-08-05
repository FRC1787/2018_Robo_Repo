package org.usfirst.frc.team1787.vision;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class Targeting {

	private static final Targeting instance = new Targeting();
	
	// Shape Filtering Parameters
	private final double defaultMinArea = 50;
	private final double defaultMinScore = 0.8;
	private final double defaultMaxScore = 2.0;
	
	private Targeting() {
		
	}
	
	
	
	public ArrayList<MatOfPoint> findContours(Mat frame) {
	    ArrayList<MatOfPoint> listOfContours = new ArrayList<MatOfPoint>();
	    
	    // TODO: Add description of what each of these parts do.
	    Mat hierarchy = new Mat();
	    int mode = Imgproc.RETR_EXTERNAL;
	    int method = Imgproc.CHAIN_APPROX_SIMPLE;
	    Imgproc.findContours(frame, listOfContours, hierarchy, mode, method);
	    
	    return listOfContours;
	}
	
	
	
	public MatOfPoint bestContour(ArrayList<MatOfPoint> contours) {
		MatOfPoint largestContour = null;
		double currentMaxArea = defaultMinArea;
		double currentContourArea;
		
		
		for (MatOfPoint i:contours) {
			currentContourArea = Imgproc.contourArea(i);
			if (currentContourArea > currentMaxArea) {
				largestContour = i;
				currentMaxArea = currentContourArea;
			}
		}
		
		return largestContour;
		
	}
	
	public Point centerOfContour(MatOfPoint contour) {
		Moments moments = Imgproc.moments(contour);
	    double centerX = moments.get_m10() / moments.get_m00();
	    double centerY = moments.get_m01() / moments.get_m00();
	    return new Point(centerX, centerY);
	}
	
	public double xOfCenterContour(MatOfPoint contour) {
		Moments moments = Imgproc.moments(contour);
	    double centerX = moments.get_m10() / moments.get_m00();
	    return centerX;
	}
	
	
	public static Targeting getInstance() {
		return instance;
	}
	
	
	

	  
}
