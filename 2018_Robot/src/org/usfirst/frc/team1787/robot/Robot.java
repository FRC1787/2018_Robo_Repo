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
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
		
	private double switchVoltageTop = 0.68;
	private double switchVoltageBottom = 0.5;
	
	private double highScaleVoltageTopMed = 0.8;
	private double highScaleVoltageBottomMed = 0.7;
	
	private double highScaleVoltageTopHigh = 0.98;
	private double highScaleVoltageBottomHigh = 0.88;
	
	private double REVERSE_OUTPUT_SPEED = -0.2;
	private double intakeVoltage = 0.4;
	private double intakeOutVoltage = 0.2;
	private int SHOOTER_RAMP_UP_TIME = 25;
	
	private double outputspeedTop = 0.97;
	private double outputspeedBottom = 0.88;
	
	private boolean timerStart = false ;
	private int disengageTime = 50;
	private int turnUpTime = 0;
	
	private boolean buttonPressedHigh = false;
	private boolean buttonPressedMed = false;
	private boolean buttonPressedswitch = false;
	
	private final int rightEncoderChannelA = 1;
	private final int rightEncoderChannelB = 0;
	private final int leftEncoderChannelA = 2;
	private final int leftEncoderChannelB = 3;
	private int leftEncoderCount = 0;
	private int rightEncoderCount = 0;
	
	private Encoder rightEncoder = new Encoder(rightEncoderChannelA, rightEncoderChannelB , true);
	private Encoder leftEncoder = new Encoder(leftEncoderChannelA, leftEncoderChannelB, true);
	 private boolean encoderReset = true;
	
	
	
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
		intake.testSolenoid(true);
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
		
		
		if (encoderReset == true) {
			leftEncoder.reset();
			rightEncoder.reset();
			encoderReset = false;
		}
		
		leftEncoderCount = leftEncoder.get();
		rightEncoderCount = rightEncoder.get();
		/*
		if (leftEncoderCount > 1 ) {
		driveTrain.testDrive(0.5);
		}
		
		//if (leftEncoderCount > 1 || rightEncoderCount > 1) {
			//driveTrain.arcadeDrive(0, 0);
			
		//}
		*/
		 
		
		
		
		   
		 /*
		//Start intake and turn the cubes in the intake
		if (leftStick.getRawButtonPressed(INTAKE_FORWARD_BUTTON)) {
			intake.turnOnWheels(INTAKE_SPEED);
		}
		else if (rightStick.getRawAxis(JOYSTICK_ROTATION_AXIS) > 0.25) {
			intake.spinCubeRight(INTAKE_SPEED);
		}
		else if (rightStick.getRawAxis(JOYSTICK_ROTATION_AXIS) < -0.25) {
			intake.spinCubeLeft(INTAKE_SPEED);
		}
		else {
			intake.turnOffWheels();
		}
		*/
		
		if (rightStick.getRawButtonPressed(10) && buttonPressedHigh == false) {
			buttonPressedHigh = true;
			}
		if (buttonPressedHigh == true) {
			output.turnOnWheels(highScaleVoltageTopHigh, highScaleVoltageBottomHigh);
			turnUpTime++;
			if (turnUpTime >20 && turnUpTime < 22) {
				output.testSolenoid(true);
			}
			if (turnUpTime > disengageTime) {
				output.turnOffWheels();
				output.testSolenoid(false);
				buttonPressedHigh = false;
				turnUpTime =0;
			}
			
		}
		if (rightStick.getRawButtonPressed(9) && buttonPressedMed == false) {
			buttonPressedMed = true;
		}
		if (buttonPressedMed == true) {
			output.turnOnWheels(highScaleVoltageTopMed, highScaleVoltageBottomMed);
			turnUpTime++;
			if (turnUpTime > 20 && turnUpTime < 22) {
				output.testSolenoid(true);
			}
			if (turnUpTime > disengageTime) {
				output.turnOffWheels();
				output.testSolenoid(false);
				buttonPressedMed = false;
				turnUpTime =0;
			}
		
		}
		if (rightStick.getRawButtonPressed(8) && buttonPressedswitch == false) {
			buttonPressedswitch = true;
		
		}
		if (buttonPressedswitch == true) {
			output.turnOnWheels(switchVoltageTop, switchVoltageBottom);
			turnUpTime++;
			if (turnUpTime > 20 && turnUpTime < 22) {
				output.testSolenoid(true);
			}
			if (turnUpTime > disengageTime) {
				output.turnOffWheels();
				output.testSolenoid(false);
				buttonPressedswitch = false;
				turnUpTime =0;
			}
		}
		
		if(rightStick.getRawButtonPressed(1)) {
			intake.testSolenoid(false);
			intake.turnOnWheels(intakeVoltage);
		}
		else if(rightStick.getRawButtonReleased(1)) {
			intake.testSolenoid(true);
			intake.turnOffWheels();
		}
		
		if (rightStick.getRawButtonPressed(2)) {
			intake.testSolenoid(false);
			intake.turnOnWheels(-intakeOutVoltage);
		}
		else if(rightStick.getRawButtonReleased(2)) {
			intake.testSolenoid(true);
			intake.turnOffWheels();
		}
		
			
		
		if (rightStick.getRawButtonPressed(4)) {
			intake.testSolenoid(false);
		}
		else if (rightStick.getRawButtonPressed(3)) {
			intake.testSolenoid(true);
		}
		
		
		if (rightStick.getRawButtonPressed(11)) {
			output.squeezeCube();
		}
		else if (rightStick.getRawButtonPressed(16)) {
			output.releaseCube();
		}
		
		
		
		/*
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
		*/
		
		
		
		
		
		//Climbing code
		if (leftStick.getRawButtonPressed(CLIMB_EXTEND_BUTTON)) {
			climb.extendPiston();
			shooter.testSolenoid(true);
			
		}
		else if (leftStick.getRawButtonPressed(CLIMB_RETRACT_BUTTON)) {
			climb.retractPiston();
			shooter.testSolenoid(false);
		}
		
		// Intake test Code
		
		
		

		
		
		/*******************
		 *                 *
		 * LEFT STICK CODE *
		 *                 *
		 *******************
		 */
		
		
		//Move shooter assembly
		
		
		
		SmartDashboard.putNumber("leftEncoderValue", leftEncoder.get());
		SmartDashboard.putNumber("rightEncoderValue", rightEncoder.get());
		
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
		shooter.testSolenoid(true);
	}
}
