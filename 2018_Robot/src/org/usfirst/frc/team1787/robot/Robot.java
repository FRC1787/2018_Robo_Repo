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
	private int INTAKE_FORWARD_BUTTON = 1;
	private int INTAKE_REVERSE_BUTTON = 2;
	private int LOW_OUTPUT_BUTTON_ID = 8;
	private int MED_OUTPUT_BUTTON_ID = 9;
	private int HIGH_OUTPUT_BUTTON_ID = 10;
	private int CLIMB_EXTEND_BUTTON = 13;
	private int CLIMB_RETRACT_BUTTON = 14;
		
	private double LOW_TOP_OUTPUT_SPEED = 1.0;
	private double LOW_BOTTOM_OUTPUT_SPEED = 0.9;
	
	private double MED_TOP_OUTPUT_SPEED = 1.0;
	private double MED_BOTTOM_OUTPUT_SPEED = 0.9;
	
	private double HIGH_TOP_OUTPUT_SPEED = 1.0;
	private double HIGH_BOTTOM_OUTPUT_SPEED = 0.9;
	
	private double REVERSE_OUTPUT_SPEED = -0.2;
	private double INTAKE_SPEED = 0.4;
	private int SHOOTER_RAMP_UP_TIME = 25;
	
	
	
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
		
		/************************
		 *                      *
		 * RIGHT STICK CONTROLS *
		 *                      *
		 ************************
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
		if (leftStick.getRawButtonPressed(INTAKE_FORWARD_BUTTON)) {
			intake.turnOnWheels(INTAKE_SPEED);
		}
		else if (leftStick.getRawButtonReleased(INTAKE_FORWARD_BUTTON)) {
			intake.turnOffWheels();
		}

		
		
		
		//Turn the cubes in the intake
		if (rightStick.getRawAxis(JOYSTICK_ROTATION_AXIS) > 0.1) {
			intake.spinCubeRight(INTAKE_SPEED);
		}
		else if (rightStick.getRawAxis(JOYSTICK_ROTATION_AXIS) < -0.1) {
			intake.spinCubeLeft(INTAKE_SPEED);
		}
		else {
			intake.turnOffWheels();
		}
		
		
		
		
		//Choosing shooting speeds based on buttons, shoots the cubes
		if (rightStick.getRawButtonPressed(LOW_OUTPUT_BUTTON_ID)) {
			shooter.shootThoseDankCubes(LOW_TOP_OUTPUT_SPEED, LOW_BOTTOM_OUTPUT_SPEED, INTAKE_SPEED, SHOOTER_RAMP_UP_TIME);
		}
		else if (rightStick.getRawButtonPressed(MED_OUTPUT_BUTTON_ID)) {
			shooter.shootThoseDankCubes(MED_TOP_OUTPUT_SPEED, MED_BOTTOM_OUTPUT_SPEED, INTAKE_SPEED, SHOOTER_RAMP_UP_TIME);
		}
		else if (rightStick.getRawButtonPressed(HIGH_OUTPUT_BUTTON_ID)) {
			shooter.shootThoseDankCubes(HIGH_TOP_OUTPUT_SPEED, HIGH_BOTTOM_OUTPUT_SPEED, INTAKE_SPEED, SHOOTER_RAMP_UP_TIME);
		}
		else if (rightStick.getRawButtonReleased(LOW_OUTPUT_BUTTON_ID) || rightStick.getRawButtonReleased(MED_OUTPUT_BUTTON_ID) || rightStick.getRawButtonReleased(HIGH_OUTPUT_BUTTON_ID)) {
			shooter.resetForThoseDankCubes();
		}
		
		
		
		
		
		//Climbing code
		if (rightStick.getRawButtonPressed(CLIMB_EXTEND_BUTTON)) {
			climb.extendPiston();
			shooter.moveSolenoid(false);
		}
		else if (rightStick.getRawButtonPressed(CLIMB_RETRACT_BUTTON)) {
			climb.retractPiston();
		}
		
		
		
		
		/*******************
		 *                 *
		 * LEFT STICK CODE *
		 *                 *
		 *******************
		 */
		
		
		//Move shooter assembly
		if (leftStick.getRawButton(5)) {
			shooter.moveSolenoid(true);
		}
		else if (leftStick.getRawButton(10)) {
			shooter.moveSolenoid(false);
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
