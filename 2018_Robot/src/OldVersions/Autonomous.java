package OldVersions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;



public class Autonomous {
	
	//Important auto values to increment a counter when autonomous1() is called
	private double CURRENT_AUTO_RUN_TIME = 0;
	private double FINAL_AUTO_RUN_TIME = 3;
	private double AUTO_MOTOR_SPEED = 0.25;
	
	private static final Autonomous instance = new Autonomous();
	private DriveTrain driveTrain = DriveTrain.getInstance();
	
	private Autonomous () {
		
	}
	
	public static Autonomous getInstance() {
		return instance;
	}
	
	
	
	public void autonomous1() {
		if (CURRENT_AUTO_RUN_TIME < FINAL_AUTO_RUN_TIME) {
			//Will increment every time autonomous1() is called to match the robot's on-board timer and allow
			//for an accurate final auto run time value
			driveTrain.arcadeDrive(AUTO_MOTOR_SPEED);
			CURRENT_AUTO_RUN_TIME = CURRENT_AUTO_RUN_TIME + 0.02;
		}	
		else {
			driveTrain.arcadeDrive(0);
		}
	}
	
	
	
	public void resetVariables () {
		CURRENT_AUTO_RUN_TIME = 0;
	}
}
