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

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends TimedRobot {

	protected int farfar37;

	
	/*
	 * TO DO AT PITTSBURGH
	 * 
	 * -Test autonomous distances and shooting power
	 * -Test all shooting power levels
	 * -Test intake on both even and uneven power levels
	 * -Set up shuffleboard
	 * -Update all talons and victors
	 * -Test reverse intake into exchange
	 * -Test reverse shooter
	 * -Test all button mappings
	 * -Drink coffee to stay going
	 * -Lots of practice matches
	 * -Lots of practice fields
	 */
	
	
	
	
	// Create single instances of objects for use in the program
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Climb climb = Climb.getInstance();
	private Shooter shooter = Shooter.getInstance();
	private Output output = Output.getInstance();
	private Intake intake = Intake.getInstance();
	private Autonomous autonomous = Autonomous.getInstance();
	Preferences prefs = Preferences.getInstance();

	// Joystick setup
	private final int RIGHT_JOYSTICK_ID = 0;
	private final int LEFT_JOYSTICK_ID = 1;
	private Joystick rightStick = new Joystick(RIGHT_JOYSTICK_ID);
	private Joystick leftStick = new Joystick(LEFT_JOYSTICK_ID);
	private int JOYSTICK_ROTATION_AXIS = 2;
	private int JOYSTICK_SLIDER_AXIS = 3;

	// Right Stick
	private final int INTAKE_BUTTON = 1;
	private final int REVERSE_INTAKE_BUTTON = 2;
	private final int CLIMB_EXTEND_BUTTON = 13;
	private final int CLIMB_RETRACT_BUTTON = 14;
	private final int HIGH_POWER_SHOOTING_BUTTON = 10;
	private final int BALANCED_POWER_SHOOTING_BUTTON = 9;
	private final int SWITCH_POWER_SHOOTING_BUTTON = 8;
	private final int OUTPUT_SQUEEZE_BUTTON = 11;
	private final int OUTPUT_RELEASE_BUTTON = 16;

	// Left Stick
	private final int SHOOTER_ASSEMBLY_EXTEND_BUTTON = 7;
	private final int SHOOTER_ASSEMBLY_RETRACT_BUTTON = 8;
	private final int REVERSE_OUTPUT_BUTTON = 2;
	private final int INTAKE_SQUEEZE_BUTTON = 3;
	private final int INTAKE_RELEASE_BUTTON = 4;
	private final int CLIMB_SOLENOID_EXTEND_BUTTON = 13;
	private final int CLIMB_SOLENOID_RETRACT_BUTTON = 14;

	// Shooting motor voltages
	private double HIGH_POWER_TOP_WHEELS = 0.98;
	private double HIGH_POWER_BOT_WHEELS = 0.88;

	private double BALANCED_POWER_TOP_WHEELS = 0.8;
	private double BALANCED_POWER_BOT_WHEELS = 0.7;

	private double SWITCH_POWER_TOP_WHEELS = 0.45;
	private double SWITCH_POWER_BOT_WHEELS = 0.6;

	// Intake motor voltages
	private final double RIGHT_INTAKE_VOLTAGE = 0.35;
	private final double LEFT_INTAKE_VOLTAGE = 0.15;
	private final double INTAKE_OUT_VOLTAGE = -0.25;
	private final double REVERSE_OUTPUT_VOLTAGE = -0.4;

	// Shooter disengage time
	private final int DISENGAGE_TIME = 50;

	// Variable variables
	private int rampUpTime = 0;
	private int shootingTimer = 0;
	private int intakeTimer = 0;
	private String nullZone = "";
	
	// Auto selection stuff
	SendableChooser<Integer> autoChooser;
	private String gameData;
	private int autonomousTimer;
	
	//Camera
	
	

	// Timer runs the periodic methods below every 20ms

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		
		autoChooser = new SendableChooser<Integer>();
		autoChooser.addDefault("Move Straight", 1);
		autoChooser.addObject("Do Nothing", 0);
		autoChooser.addObject("Short/Left Side", 2);
		autoChooser.addObject("Long/Right Side", 3);
		SmartDashboard.putData("Auto Chooser", autoChooser);
		
		autonomousTimer = 0;
	}

	@Override
	public void autonomousInit() {
		output.turnOffWheels();
		intake.squeezeCube();
		shooter.extendShooter();
		driveTrain.resetAuto();
		driveTrain.highGear();
		output.squeezeCube();
		
		autonomousTimer = 0;
		
	}

	@Override
	public void autonomousPeriodic() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	    
	    if (gameData.length() > 0) {
	    	//Short/Left is 2, Long/Right is 3
	    	if (autoChooser.getSelected() == 0) {
	    		autonomous.doNothing();
	    	}
	    	else if (autoChooser.getSelected() == 1) {
	    		autonomous.baseline();
	    	}
	    	else if (gameData.charAt(0) == 'L' && autoChooser.getSelected() == 2) {
	    		autonomous.shortSwitch();
	    	}
	    	else if (gameData.charAt(1) == 'L' && autoChooser.getSelected() == 2) {
	    		autonomous.shortScale();
	    	}
	    	else if (gameData.charAt(0) == 'R' && autoChooser.getSelected() == 3) {
	    		autonomous.longSwitch();
	    	}
	    	else if (gameData.charAt(1) == 'R' && autoChooser.getSelected() == 3) {
	    		autonomous.longScale();
	    	}
	    	else {
	    		autonomous.doNothing();
	    	}
	    }
	    
	    
	    
	    
	    if (gameData.charAt(1) == 'R') {
	    	nullZone = "RIGHT";
	    }
	    else if (gameData.charAt(1) == 'L') {
	    	nullZone = "LEFT";
	    }
	    else {
	    	nullZone = "USE FIELD";
	    }
	}

	@Override
	public void teleopInit() {
		output.turnOffWheels();
		intake.squeezeCube();
		shooter.extendShooter();
		driveTrain.highGear();
		output.squeezeCube();
	}

	@Override
	public void teleopPeriodic() {

		driveTrain.highGear();

		/************************
		 * * RIGHT STICK CONTROLS * *
		 ************************
		 */

		// Arcade Drive
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());
		// driveTrain.tankDrive(-leftStick.getY(), -rightStick.getY());

		// Shoot cubes
		if (rightStick.getRawButton(HIGH_POWER_SHOOTING_BUTTON)) {

			if (rightStick.getRawButtonPressed(HIGH_POWER_SHOOTING_BUTTON)) {
				shootingTimer = 0;
			}

			shooter.shootThoseDankCubes(HIGH_POWER_TOP_WHEELS, HIGH_POWER_BOT_WHEELS, shootingTimer, DISENGAGE_TIME);

		}

		else if (rightStick.getRawButton(BALANCED_POWER_SHOOTING_BUTTON)) {

			if (rightStick.getRawButtonPressed(BALANCED_POWER_SHOOTING_BUTTON)) {
				shootingTimer = 0;
			}

			shooter.shootThoseDankCubes(BALANCED_POWER_TOP_WHEELS, BALANCED_POWER_BOT_WHEELS, shootingTimer,
					DISENGAGE_TIME);

		}

		else if (rightStick.getRawButton(SWITCH_POWER_SHOOTING_BUTTON)) {

			if (rightStick.getRawButtonPressed(SWITCH_POWER_SHOOTING_BUTTON)) {
				shootingTimer = 0;
			}

			shooter.shootThoseDankCubes(SWITCH_POWER_TOP_WHEELS, SWITCH_POWER_BOT_WHEELS, shootingTimer,
					DISENGAGE_TIME);
		}

		else if (rightStick.getRawButtonReleased(HIGH_POWER_SHOOTING_BUTTON)
				|| rightStick.getRawButtonReleased(BALANCED_POWER_SHOOTING_BUTTON)
				|| rightStick.getRawButtonReleased(SWITCH_POWER_SHOOTING_BUTTON)) {
			shooter.resetForThoseDankCubes();
		}

		shootingTimer++;

		// Forward intake
		if (rightStick.getRawButtonPressed(INTAKE_BUTTON)) {

			output.releaseCube();
			intakeTimer = 0;
			intake.turnOnWheels(RIGHT_INTAKE_VOLTAGE, LEFT_INTAKE_VOLTAGE);
		}

		else if (rightStick.getRawButton(INTAKE_BUTTON)) {

			intakeTimer++;

			if (intakeTimer % 35 == 0) {
				intake.turnOffWheels();
			} else if ((intakeTimer % 35) == 10) {
				intake.turnOnWheels(RIGHT_INTAKE_VOLTAGE, LEFT_INTAKE_VOLTAGE);
			}

		}

		else if (rightStick.getRawButtonReleased(INTAKE_BUTTON)) {
			intake.turnOffWheels();
			intakeTimer = 0;
			output.squeezeCube();
		}

		// Reverse intake
		if (rightStick.getRawButtonPressed(REVERSE_INTAKE_BUTTON)) {
			intake.turnOnWheels(INTAKE_OUT_VOLTAGE, INTAKE_OUT_VOLTAGE);
			output.releaseCube();
			// intake.squeezeCube();
		}

		else if (rightStick.getRawButtonReleased(REVERSE_INTAKE_BUTTON)) {
			intake.turnOffWheels();
			output.squeezeCube();
		}

		// Output solenoid
		if (rightStick.getRawButtonPressed(OUTPUT_SQUEEZE_BUTTON)) {
			output.squeezeCube();
		}

		else if (rightStick.getRawButtonPressed(OUTPUT_RELEASE_BUTTON)) {
			output.releaseCube();
		}

		// Climbing
		if (rightStick.getRawButtonPressed(CLIMB_EXTEND_BUTTON)) {
			climb.extendPiston();
			shooter.retractShooter();
			intake.releaseCube();
			output.releaseCube();
		}

		else if (rightStick.getRawButtonPressed(CLIMB_RETRACT_BUTTON)) {
			climb.retractPiston();
		}

		/*******************
		 * * LEFT STICK CODE * *
		 *******************
		 */

		// Intake cube when in vertical position
		if (leftStick.getRawButton(INTAKE_BUTTON)) {
			intakeTimer++;
			output.releaseCube();

			if (intakeTimer % 50 == 0) {
				intake.turnOffWheels();
			}

			else if ((intakeTimer % 50) == 10) {
				intake.turnOnWheels(0.35, 0.35);
			}
		}

		else if (leftStick.getRawButtonReleased(INTAKE_BUTTON)) {
			intake.turnOffWheels();
			intakeTimer = 0;
			output.squeezeCube();
		}

		// Intake solenoid
		if (leftStick.getRawButtonPressed(INTAKE_SQUEEZE_BUTTON)) {
			intake.squeezeCube();
		}

		else if (leftStick.getRawButtonPressed(INTAKE_RELEASE_BUTTON)) {
			intake.releaseCube();
		}

		// Reverse output
		if (leftStick.getRawButtonPressed(REVERSE_OUTPUT_BUTTON)) {
			output.turnOnWheels(REVERSE_OUTPUT_VOLTAGE, REVERSE_OUTPUT_VOLTAGE);
		}

		else if (leftStick.getRawButtonReleased(REVERSE_OUTPUT_BUTTON)) {
			output.turnOffWheels();
		}

		// Move shooter assembly
		if (leftStick.getRawButtonPressed(SHOOTER_ASSEMBLY_EXTEND_BUTTON)) {
			shooter.extendShooter();
		}

		else if (leftStick.getRawButtonPressed(SHOOTER_ASSEMBLY_RETRACT_BUTTON)) {
			shooter.retractShooter();
		}

		// Climb without shooter movement
		if (leftStick.getRawButtonPressed(CLIMB_SOLENOID_EXTEND_BUTTON)) {
			climb.extendPiston();
		}

		else if (leftStick.getRawButtonReleased(CLIMB_SOLENOID_RETRACT_BUTTON)) {
			climb.retractPiston();
		}

		SmartDashboard.putNumber("HIGH top wheels", HIGH_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("HIGH bottom wheels", HIGH_POWER_BOT_WHEELS);
		SmartDashboard.putNumber("BALANCED top wheels", BALANCED_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("BALANCED bottom wheels", BALANCED_POWER_BOT_WHEELS);
		SmartDashboard.putNumber("SWITCH top wheels", SWITCH_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("SWITCH bottom wheels", SWITCH_POWER_BOT_WHEELS);
		SmartDashboard.putString("NULL ZONE", nullZone);

		// Putting everything on shuffleboard
		driveTrain.pushDataToShuffleboard();
		climb.pushDataToShuffleboard();
		intake.pushDataToShuffleboard();
		output.pushDataToShuffleboard();
		shooter.pushDataToShuffleboard();
		autonomous.pushDataToShuffleboard();
		// #########################################################################

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
		shooter.extendShooter();
		driveTrain.resetAuto();
	}
}