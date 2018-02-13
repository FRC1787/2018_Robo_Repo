package org.usfirst.frc.team1787.robot;

import java.sql.Time;

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
	private int START_INTAKE_BUTTON = 1;
	
	private double REVERSE_OUTPUT_SPEED = -0.2;
	private double INTAKE_SPEED = 0.4;
	private int SHOOTER_RAMP_UP_TIME = 25;
	
	
	
	
	
	//Top and bottom output speed selection between the four groups below
	private int OUTPUT_SPEED_SELECTION = 1;
	
	
	/*
	* 1 = Switch power level
	* 2 = 4' Scale position power level
	* 3 = 5' Scale position power level
	* 4 = 6' Scale position power level
	* 
	* All current values except the high scale output speeds are fillers
	* 
	* */
	
	//Selection ID 1
	private int SWITCH_OUTPUT_BUTTON_ID = 6;
	private double SWITCH_TOP_OUTPUT_SPEED = 1.0;
	private double SWITCH_BOTTOM_OUTPUT_SPEED = 0.9;
	
	//Selection ID 2
	private int LOW_SCALE_OUTPUT_BUTTON_ID = 7;
	private double LOW_SCALE_TOP_OUTPUT_SPEED = 1.0;
	private double LOW_SCALE_BOTTOM_OUTPUT_SPEED = 0.9;
	
	//Selection ID 3
	private int MED_SCALE_OUTPUT_BUTTON_ID = 8;
	private double MED_SCALE_TOP_OUTPUT_SPEED = 1.0;
	private double MED_SCALE_BOTTOM_OUTPUT_SPEED = 0.9;
	
	//Selection ID 4
	private int HIGH_SCALE_OUTPUT_BUTTON_ID = 9;
	private double HIGH_SCALE_TOP_OUTPUT_SPEED = 1.0;
	private double HIGH_SCALE_BOTTOM_OUTPUT_SPEED = 0.9;
	
	
	
	
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
		
		/* 
		 * To Do list
		 * Be the best, like no one ever was
		 */
		
		 
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());
		//driveTrain.tankDrive(-leftStick.getY(), -rightStick.getY());
		
		
		
		
		//Set the gear based on the right slider
		if (rightStick.getRawAxis(JOYSTICK_SLIDER_AXIS) > 0) {
			driveTrain.highGear();
		}
		else if (rightStick.getRawAxis(JOYSTICK_SLIDER_AXIS) < 0) {
			driveTrain.lowGear();
		}
		
		
		
		
		//Start intake
		if (leftStick.getRawButtonPressed(START_INTAKE_BUTTON)) {
			intake.turnOnWheels(INTAKE_SPEED);
		}
		else if (leftStick.getRawButtonReleased(START_INTAKE_BUTTON)) {
			intake.turnOffWheels();
		}

		
		
		
		//Turn the cubes in the intake
		if (leftStick.getRawAxis(JOYSTICK_ROTATION_AXIS) > 0.1) {
			intake.spinCubeRight(INTAKE_SPEED);
		}
		else if (leftStick.getRawAxis(JOYSTICK_ROTATION_AXIS) < -0.1) {
			intake.spinCubeLeft(INTAKE_SPEED);
		}
		else {
			intake.turnOffWheels();
		}
		
		
		
		
		//Move shooter assembly
		if (leftStick.getRawButton(5)) {
			shooter.moveSolenoid(true);
		}
		else if (leftStick.getRawButton(10)) {
			shooter.moveSolenoid(false);
		}
		
		
		
		
		//Choosing shooting speeds based on buttons, corresponds to list above in the code
		if (rightStick.getRawButtonPressed(SWITCH_OUTPUT_BUTTON_ID)) {
			OUTPUT_SPEED_SELECTION = 1;
		}
		else if (rightStick.getRawButtonPressed(LOW_SCALE_OUTPUT_BUTTON_ID)) {
			OUTPUT_SPEED_SELECTION = 2;
		}
		else if (rightStick.getRawButtonPressed(MED_SCALE_OUTPUT_BUTTON_ID)) {
			OUTPUT_SPEED_SELECTION = 3;
		}
		else if (rightStick.getRawButtonPressed(HIGH_SCALE_OUTPUT_BUTTON_ID)) {
			OUTPUT_SPEED_SELECTION = 4;
		}
		
		
		
		
		//Shooting cubes
		if (rightStick.getRawButtonPressed(SHOOT_CUBES_BUTTON)) {
			
			
			
			/*
			 * Choosing shooting speeds based on speed selection. ID's are listed at the top
			 */
			
			if (OUTPUT_SPEED_SELECTION == 1) {
				shooter.shootThoseDankCubes(SWITCH_TOP_OUTPUT_SPEED, SWITCH_BOTTOM_OUTPUT_SPEED, INTAKE_SPEED, SHOOTER_RAMP_UP_TIME);
			}
			
			else if (OUTPUT_SPEED_SELECTION == 2) {
				shooter.shootThoseDankCubes(LOW_SCALE_TOP_OUTPUT_SPEED, LOW_SCALE_BOTTOM_OUTPUT_SPEED, INTAKE_SPEED, SHOOTER_RAMP_UP_TIME);
			}
			
			else if (OUTPUT_SPEED_SELECTION == 3) {
				shooter.shootThoseDankCubes(MED_SCALE_TOP_OUTPUT_SPEED, MED_SCALE_BOTTOM_OUTPUT_SPEED, INTAKE_SPEED, SHOOTER_RAMP_UP_TIME);
			}
			
			else if (OUTPUT_SPEED_SELECTION == 4) {
				shooter.shootThoseDankCubes(HIGH_SCALE_TOP_OUTPUT_SPEED, HIGH_SCALE_BOTTOM_OUTPUT_SPEED, INTAKE_SPEED, SHOOTER_RAMP_UP_TIME);
			}
			
			
			
		}
		else if (rightStick.getRawButtonReleased(SHOOT_CUBES_BUTTON)) {
			shooter.resetForThoseDankCubes();
		}
		
		
		
		
		
		//Climbing code
		if (rightStick.getRawButtonPressed(CLIMB_EXTEND_BUTTON)) {
			climb.extendPiston();
		}
		else if (rightStick.getRawButtonPressed(CLIMB_RETRACT_BUTTON)) {
			climb.retractPiston();
		}
		
		
		
		
		//Putting everything on shuffleboard
		driveTrain.pushDataToShuffleboard();
		climb.pushDataToShuffleboard();
		intake.pushDataToShuffleboard();
		output.pushDataToShuffleboard();
		shooter.pushDataToShuffleboard();
		
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
		shooter.moveSolenoid(true);
	}
}
