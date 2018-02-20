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
	private final int OUTPUT_SQUEEZE_BUTTON = 11;
	private final int OUTPUT_RELEASE_BUTTON = 16;
	
	// Shooting motor voltages
	private double HIGH_POWER_TOP_WHEELS = 0.98;
	private double HIGH_POWER_BOT_WHEELS = 0.88;
	
	private double MED_POWER_TOP_WHEELS = 0.8;
	private double MED_POWER_BOT_WHEELS = 0.7;
	
	private double LOW_POWER_TOP_WHEELS = 0.45;
	private double LOW_POWER_BOT_WHEELS = 0.6;
	
	
	
	private final double RIGHT_INTAKE_VOLTAGE = 0.35;
	private final double LEFT_INTAKE_VOLTAGE = 0.15;
	private final double INTAKE_OUT_VOLTAGE = -0.25;
	
	private int disengageTime = 50;
	private int rampUpTime = 0;
	private int shootingTimer = 0;
	private int intakeTimer = 0;
	
	
	
	//Timer runs some of the below methods every 20ms

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
	}

	@Override
	public void autonomousInit() {
		output.turnOffWheels();
		intake.squeezeCube();
		shooter.extendShooter();
		driveTrain.resetAuto();
	}

	@Override
	public void autonomousPeriodic() { 
		autonomous.autonomousPeriodic01();
	}
	
	@Override
	public void teleopInit() {
		output.turnOffWheels();
		intake.squeezeCube();
		shooter.extendShooter();
		
		
	}

	@Override
	public void teleopPeriodic() {

		
		
		/************************
		 *                      *
		 * RIGHT STICK CONTROLS *
		 *                      *
		 ************************
		 */
		
		
		
		
		//Arcade Drive
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());
		//driveTrain.tankDrive(-leftStick.getY(), -rightStick.getY());
		
		
		
		//Set the gear based on the right slider
		if (rightStick.getRawAxis(JOYSTICK_SLIDER_AXIS) > 0) {
			driveTrain.highGear();
		}
		else if (rightStick.getRawAxis(JOYSTICK_SLIDER_AXIS) < 0) {
			driveTrain.lowGear();
		}
		
		
		
		
		//Shoot cubes
		if (leftStick.getRawButtonPressed(1)) {
			shootingTimer = 0;
		}
		else if (leftStick.getRawButton(1)) {
			shootingTimer++;
			shooter.shootThoseDankCubes(LOW_POWER_TOP_WHEELS, LOW_POWER_BOT_WHEELS, shootingTimer, disengageTime);
		}
		else if (leftStick.getRawButtonReleased(1)) {
			shooter.resetForThoseDankCubes();
		}
		
		
		
		
		//Forward intake
		if (rightStick.getRawButtonPressed(INTAKE_BUTTON)) {
			intakeTimer = 0;
			intake.turnOnWheels(RIGHT_INTAKE_VOLTAGE, LEFT_INTAKE_VOLTAGE);
			
		}
		else if (rightStick.getRawButton(INTAKE_BUTTON)) {
			
			intakeTimer++;
			
			if (intakeTimer % 50 == 0) {
				intake.turnOffWheels();
			}
			else if ((intakeTimer % 50) == 10) {
				intake.turnOnWheels(RIGHT_INTAKE_VOLTAGE, LEFT_INTAKE_VOLTAGE);
			}
			
		}
		else if (rightStick.getRawButtonReleased(INTAKE_BUTTON)) {
			intake.turnOffWheels();
			intakeTimer = 0;
			
		}
				
		
		
		
		//Reverse intake
		if (rightStick.getRawButtonPressed(REVERSE_INTAKE_BUTTON)) {
			intake.turnOnWheels(INTAKE_OUT_VOLTAGE, INTAKE_OUT_VOLTAGE);
			//intake.squeezeCube();
		}
		else if (rightStick.getRawButtonReleased(REVERSE_INTAKE_BUTTON)) {
			intake.turnOffWheels();
		}
			
		
		
		
		
		
		
		//Output solenoid
		if (rightStick.getRawButtonPressed(OUTPUT_SQUEEZE_BUTTON)) {
			output.squeezeCube();
		}
		else if (rightStick.getRawButtonPressed(OUTPUT_RELEASE_BUTTON)) {
			output.releaseCube();
		}

		
		

		
		
		/*******************
		 *                 *
		 * LEFT STICK CODE *
		 *                 *
		 *******************
		 */
		
		
		//Intake solenoid
		if (leftStick.getRawButtonPressed(4)) {
			intake.squeezeCube();
		}
		else if (leftStick.getRawButtonPressed(3)) {
			intake.releaseCube();
		}	
		
		
		
		
		//Climbing 
		if (leftStick.getRawButtonPressed(CLIMB_EXTEND_BUTTON)) {
			climb.extendPiston();
			shooter.extendShooter();
			
		}
		else if (leftStick.getRawButtonPressed(CLIMB_RETRACT_BUTTON)) {
			climb.retractPiston();
			shooter.retractShooter();
		}
		
		
		
		
		//Move shooter assembly
		if (leftStick.getRawButtonPressed(3)) {
			shooter.extendShooter();
		}
		if (leftStick.getRawButtonPressed(4)) {
			shooter.retractShooter();
		} 
		
		
		SmartDashboard.putNumber("High top wheels", HIGH_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("High bottom wheels", HIGH_POWER_BOT_WHEELS);
		SmartDashboard.putNumber("Medium top wheels", MED_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("Medium bottom wheels", MED_POWER_BOT_WHEELS);
		SmartDashboard.putNumber("Low top wheels", LOW_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("Low bottom wheels", LOW_POWER_BOT_WHEELS);
		
		
		//Putting everything on shuffleboard 
		driveTrain.pushDataToShuffleboard();
		climb.pushDataToShuffleboard();
		intake.pushDataToShuffleboard();
		output.pushDataToShuffleboard();
		shooter.pushDataToShuffleboard();
		autonomous.pushDataToShuffleboard();
		//#########################################################################
			
		}
	
		
		
	
	
	@Override
	public void testInit() {
		output.turnOffWheels();
	}

	@Override
	public void testPeriodic() {
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());	
		
		
		
		if (rightStick.getRawButtonPressed(1)) {
			//output.squeezeCube();
		}
		else if (rightStick.getRawButtonReleased(1)) {
			//output.releaseCube();
		}
	}
	
	public void disabledInit() {
		output.turnOffWheels();
	}
	
	public void disabledPeriodic() {
		output.turnOffWheels();
		shooter.extendShooter();
		driveTrain.resetAuto();
	}
}
