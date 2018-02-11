package org.usfirst.frc.team1787.robot;

import org.usfirst.frc.team1787.shooting.Intake;
import org.usfirst.frc.team1787.shooting.Output;
import org.usfirst.frc.team1787.shooting.Shooter;

/* CLASS DEFINITION:
 * This class is the main class for the robot, which ties all parts together and is automatically called upon by the RoboRIO
 */

import org.usfirst.frc.team1787.subsystems.Autonomous;
import org.usfirst.frc.team1787.subsystems.Climb;
import org.usfirst.frc.team1787.subsystems.DriveTrain;
import org.usfirst.frc.team1787.subsystems.Testing;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
	
	protected int farfar38;
	
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Climb climb = Climb.getInstance();
	private Shooter shooter = Shooter.getInstance();
	private Testing testing = Testing.getInstance();
	private Output output = Output.getInstance();
	private Intake intake = Intake.getInstance();
	
	private final int RIGHT_JOYSTICK_ID = 0;
	private final int LEFT_JOYSTICK_ID = 1;
	private Joystick rightStick = new Joystick(RIGHT_JOYSTICK_ID);
	private Joystick leftStick = new Joystick(LEFT_JOYSTICK_ID);
	private int JOYSTICK_ROTATION_AXIS = 2;
	private int JOYSTICK_SLIDER_AXIS = 3;
	
	//Input options
	private int SHOOT_CUBES_BUTTON = 1;
	private int CLIMB_EXTEND_BUTTON = 10;
	private int CLIMB_RETRACT_BUTTON = 5;
	
	private int TEST_MOTOR_BUTTON_1 = 1;
	private int TEST_MOTOR_BUTTON_2 = 2;
	
	
	private double TOP_OUTPUT_SPEED = 1.0;
	private double BOTTOM_OUTPUT_SPEED = 0.9;
	private double REVERSE_OUTPUT_SPEED = -0.2;
	int time = 0;
	
	
	//Timer runs some of the below methods every 20ms

	@Override
	public void robotInit() {

	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {

	}
	
	@Override
	public void teleopInit() {
		output.turnOffWheels();
	}

	@Override
	public void teleopPeriodic() {
		
		
		 
		//driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());
		
		//leftMaster is commented out and so the left side is only run by leftFollower
		driveTrain.tankDrive(-leftStick.getY(), -rightStick.getY());
		
		
		
		if (leftStick.getRawButtonPressed(TEST_MOTOR_BUTTON_1)) {
			output.turnOnWheels(TOP_OUTPUT_SPEED, BOTTOM_OUTPUT_SPEED);
		}
		else if (leftStick.getRawButtonReleased(TEST_MOTOR_BUTTON_1)) {
			output.turnOffWheels();
		}
		
		
		if (leftStick.getRawButtonPressed(TEST_MOTOR_BUTTON_2)) {
			output.turnOnWheels(REVERSE_OUTPUT_SPEED, REVERSE_OUTPUT_SPEED);
		}
		else if (leftStick.getRawButtonReleased(TEST_MOTOR_BUTTON_2)) {
			output.turnOffWheels();
		}
		
		
		
		if (climb.climbingSolenoid.get() == DoubleSolenoid.Value.kReverse) {
			
		}
		shooter.testSolenoid(true);
		
		
		if (time > 6) {
			output.testSolenoid(false);
		}
		
		if (rightStick.getRawButtonPressed(TEST_MOTOR_BUTTON_1)) {
			//intake.testSolenoid(true); not hooked up
			output.testSolenoid(true);
			//driveTrain.testSolenoid(true);
			//shooter.testSolenoid(true);
			//climb.extendPiston();
			time = 0;
			
		}
		time++;
		
		 if (rightStick.getRawButtonReleased(TEST_MOTOR_BUTTON_2)) {
			//intake.testSolenoid(false); not hooked up
			output.testSolenoid(false);
			//driveTrain.testSolenoid(false);
			//shooter.testSolenoid(false);
			//climb.retractPiston();
		}
		
		
		
		
		/*
		if (leftStick.getRawButtonPressed(SHOOT_CUBES_BUTTON)) {
			shooter.shootThoseDankCubes();
		}
		else if (leftStick.getRawButtonReleased(SHOOT_CUBES_BUTTON)) {
			shooter.resetForThoseDankCubes();
		}
		
		
		
		if (leftStick.getRawButtonPressed(CLIMB_EXTEND_BUTTON)) {
			climb.extendPiston();
		}
		else if (leftStick.getRawButtonPressed(CLIMB_RETRACT_BUTTON)) {
			climb.retractPiston();
		}
		*/
		
		//Putting everything on shuffleboard
		driveTrain.pushDataToShuffleboard();
		climb.pushDataToShuffleboard();
		intake.pushDataToShuffleboard();
		output.pushDataToShuffleboard();
		shooter.pushDataToShuffleboard();
		SmartDashboard.putNumber("Top Shooter Speed:", TOP_OUTPUT_SPEED);
		SmartDashboard.putNumber("Bottom Shooter Speed:", BOTTOM_OUTPUT_SPEED);
		SmartDashboard.putNumber("Reverse Shooter Speed:", REVERSE_OUTPUT_SPEED);
		
	}
	
	@Override
	public void testInit() {
		output.turnOffWheels();
	}

	@Override
	public void testPeriodic() {
				
	}
	
	public void disabledInit() {
		output.turnOffWheels();
	}
	
	public void disabledPeriodic() {
		output.turnOffWheels();
		shooter.testSolenoid(true);
	}
}
