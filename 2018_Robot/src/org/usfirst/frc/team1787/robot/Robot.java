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
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends TimedRobot {
	
	protected int farfar38;
	
	
	// Create single instances of objects for use in the program
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Climb climb = Climb.getInstance();
	private Shooter shooter = Shooter.getInstance();
	private Testing testing = Testing.getInstance();
	private Output output = Output.getInstance();
	private Intake intake = Intake.getInstance();
	private Autonomous autonomous = Autonomous.getInstance();
	Preferences prefs = Preferences.getInstance();
	private final int RIGHT_JOYSTICK_ID = 0;
	private final int LEFT_JOYSTICK_ID = 1;
	private Joystick rightStick = new Joystick(RIGHT_JOYSTICK_ID);
	private Joystick leftStick = new Joystick(LEFT_JOYSTICK_ID);
	private int JOYSTICK_ROTATION_AXIS = 2;
	private int JOYSTICK_SLIDER_AXIS = 3;
	
	//Input options
	private final int INTAKE_BUTTON = 1;
	private final int REVERSE_INTAKE_BUTTON = 2;
	private final int CLIMB_EXTEND_BUTTON = 13;
	private final int CLIMB_RETRACT_BUTTON = 14;
	private final int HIGH_POWER_SHOOTING_BUTTON = 10;
	private final int MED_POWER_SHOOTING_BUTTON = 9;
	private final int LOW_POWER_SHOOTING_BUTTON = 8;
	
	// Shooting motor voltages
	private double switchVoltageTop = 0.45;
	private double switchVoltageBottom = 0.6;
	
	private double scaleVoltageTopMed = 0.8;
	private double scaleVoltageBottomMed = 0.7;
	
	private double scaleVoltageTopHigh = 0.98;
	private double scaleVoltageBottomHigh = 0.88;
	
	private final double RIGHT_INTAKE_VOLTAGE = 0.35;
	private final double LEFT_INTAKE_VOLTAGE = 0.15;
	private final double INTAKE_OUT_VOLTAGE = -0.25;
	
	

	
	private int disengageTime = 50;
	private int rampUpTime = 0;
	
	private boolean shootingTime = false;
	private boolean buttonPressedHigh = false;
	private boolean buttonPressedMed = false;
	private boolean buttonPressedswitch = false;
	
	private boolean encoderReset = true;

	
	
	
	//Timer runs some of the below methods every 20ms

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
	}

	@Override
	public void autonomousInit() {
		output.turnOffWheels();
		intake.testSolenoid(true);
		shooter.testSolenoid(false);
		driveTrain.resetAuto();
	}

	@Override
	public void autonomousPeriodic() { 
		autonomous.autonomousPeriodic01();
	}
	
	@Override
	public void teleopInit() {
		output.turnOffWheels();
		intake.testSolenoid(true);
		shooter.testSolenoid(false);
		
		
	}

	@Override
	public void teleopPeriodic() {

		
		
		/************************
		 *                      *
		 * RIGHT STICK CONTROLS *
		 *                      *
		 ************************
		 */
		
			/*		
		// engage intake when not shooting 
		if (shootingTime == false) {
			intake.testSolenoid(false);
		}
		// disengage intake when shooting
		else if (shootingTime == true) {
			intake.testSolenoid(true);
		}
		*/
		
		//drive the robot
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());
		
		//Set the gear based on the right slider
		if (rightStick.getRawAxis(JOYSTICK_SLIDER_AXIS) > 0) {
			driveTrain.highGear();
		}
		else if (rightStick.getRawAxis(JOYSTICK_SLIDER_AXIS) < 0) {
			driveTrain.lowGear();
		}
		
		
		
		//Shoot cubes
		if (leftStick.getRawButtonPressed(1)) {
			intake.testSolenoid(false);
			shooter.shootThoseDankCubes(switchVoltageTop, switchVoltageBottom, disengageTime);
		}
		else if (leftStick.getRawButtonReleased(1)) {
			shooter.resetForThoseDankCubes();
		}
		
		
		
		// pull cube in
		if (rightStick.getRawButtonPressed(INTAKE_BUTTON)) {
			intake.turnOnWheels(RIGHT_INTAKE_VOLTAGE, LEFT_INTAKE_VOLTAGE);
			//intake.releaseCube();
		}
		else if (rightStick.getRawButtonReleased(INTAKE_BUTTON)) {
			intake.turnOffWheels();
			//intake.releaseCube();
		}
				
		intake.releaseCube();
		
		// dispense cube
		if (rightStick.getRawButtonPressed(REVERSE_INTAKE_BUTTON)) {
			intake.turnOnWheels(INTAKE_OUT_VOLTAGE, INTAKE_OUT_VOLTAGE);
			//intake.squeezeCube();
		}
		else if (rightStick.getRawButtonReleased(REVERSE_INTAKE_BUTTON)) {
			intake.turnOffWheels();
		}
			
		
		
		
		
		// engage intake
		if (leftStick.getRawButtonPressed(4)) {
			intake.squeezeCube();
		}
		else if (leftStick.getRawButtonPressed(3)) {
			intake.releaseCube();
		}
		
		// hold cube in place for driving
		if (rightStick.getRawButtonPressed(11)) {
			output.squeezeCube();
		}
		else if (rightStick.getRawButtonPressed(16)) {
			output.releaseCube();
		}

		//Move shooter assembly
		if (leftStick.getRawButtonPressed(3)) {
			shooter.testSolenoid(true);
		}
		if (leftStick.getRawButtonPressed(4)) {
			shooter.testSolenoid(false);
		} 
		
		
		//Climbing 
		if (leftStick.getRawButtonPressed(CLIMB_EXTEND_BUTTON)) {
			climb.extendPiston();
			shooter.testSolenoid(true);
			
		}
		else if (leftStick.getRawButtonPressed(CLIMB_RETRACT_BUTTON)) {
			climb.retractPiston();
			shooter.testSolenoid(true);
		}
		
	
		
		
		

		
		
		/*******************
		 *                 *
		 * LEFT STICK CODE *
		 *                 *
		 *******************
		 */
		
		
		SmartDashboard.putNumber("topMotorVoltageHighScale", scaleVoltageTopHigh);
		SmartDashboard.putNumber("bottomMotorVoltageHighScale", scaleVoltageBottomHigh);
		SmartDashboard.putNumber("topMotorVoltageMedscale", scaleVoltageTopMed);
		SmartDashboard.putNumber("bottomMotorVoltageMedScale", scaleVoltageBottomMed);
		SmartDashboard.putNumber("topMotorVoltageSwitch", switchVoltageTop);
		SmartDashboard.putNumber("bottomMotorVoltageSwitch", switchVoltageBottom);
		
		
		//Putting everything on shuffleboard 
		driveTrain.pushDataToShuffleboard();
		climb.pushDataToShuffleboard();
		intake.pushDataToShuffleboard();
		output.pushDataToShuffleboard();
		shooter.pushDataToShuffleboard();
		autonomous.pushDataToShuffleboard();
		//#########################################################################
					
			
			
			if (rampUpTime >20 && rampUpTime < 22) {
				output.testSolenoid(true);
			}
			if (rampUpTime > disengageTime) {
				output.turnOffWheels();
				output.testSolenoid(false);
				buttonPressedHigh = false;
				shootingTime = false;
				rampUpTime =0;
			}
			
		}
	
		
		
	
	
	@Override
	public void testInit() {
		output.turnOffWheels();
	}

	@Override
	public void testPeriodic() {
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());	
	}
	
	public void disabledInit() {
		output.turnOffWheels();
	}
	
	public void disabledPeriodic() {
		output.turnOffWheels();
		shooter.testSolenoid(true);
		driveTrain.resetAuto();
	}
}
