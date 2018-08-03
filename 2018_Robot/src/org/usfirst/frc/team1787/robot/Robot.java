package org.usfirst.frc.team1787.robot;

import java.io.PushbackInputStream;
import java.util.ArrayList;

import org.usfirst.frc.team1787.shooting.Intake;
import org.usfirst.frc.team1787.shooting.Output;
import org.usfirst.frc.team1787.shooting.Shooter;

/* CLASS DEFINITION:
 * This class is the main class for the robot, which ties all parts together and is automatically called upon by the RoboRIO yeet
 */

import org.usfirst.frc.team1787.subsystems.Autonomous;
import org.usfirst.frc.team1787.subsystems.Climb;
import org.usfirst.frc.team1787.subsystems.DriveTrain;
import org.usfirst.frc.team1787.subsystems.Gyroscope;
import org.usfirst.frc.team1787.vision.Vision;

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
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends TimedRobot {

	protected int farfar37;	
	
	
	// Create single instances of objects for use in the program
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Climb climb = Climb.getInstance();
	private Shooter shooter = Shooter.getInstance();
	private Output output = Output.getInstance();
	private Intake intake = Intake.getInstance();
	private Autonomous autonomous = Autonomous.getInstance();
	private Gyroscope gyroscope = Gyroscope.getInstance();
	private Vision vision = Vision.getInstance();

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
	private double HIGH_POWER_TOP_WHEELS = 1.00;
	private double HIGH_POWER_BOT_WHEELS = 0.9;

	private double BALANCED_POWER_TOP_WHEELS = 0.8;
	private double BALANCED_POWER_BOT_WHEELS = 0.7;

	private double SWITCH_POWER_TOP_WHEELS = 0.55;
	private double SWITCH_POWER_BOT_WHEELS = 0.6;

	// Intake motor voltages
	private final double RIGHT_INTAKE_VOLTAGE = 0.35;
	private final double LEFT_INTAKE_VOLTAGE = 0.15;
	private final double INTAKE_OUT_VOLTAGE = -0.5;
	private final double REVERSE_OUTPUT_VOLTAGE = -0.4;

	// Shooter disengage time
	private final double DISENGAGE_TIME = 50;

	// Variable variables
	private double shootingTimer = 0;
	private int intakeTimer = 0;
	private String nullZone = "";
	
	// Auto selection stuff
	SendableChooser<Integer> autoChooser;
	CameraServer server = CameraServer.getInstance();
	  
	
	
	// Timer runs the periodic methods below every 20ms

	@Override
	public void robotInit() {
			gyroscope.resetGyro();
	}
	
	@Override
	public void robotPeriodic() {
		//vision.visionProcessing();
	}

	@Override
	public void autonomousInit() {
		output.turnOffWheels();
		intake.squeezeCube();
		shooter.extendShooter();
		driveTrain.resetAuto();
		autonomous.resetAuto();
		driveTrain.highGear();
		output.squeezeCube();
	
	}

	@Override
	public void autonomousPeriodic() {

		autonomous.screwYouVan();
		
		SmartDashboard.putNumber("HIGH top wheels", HIGH_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("HIGH bottom wheels", HIGH_POWER_BOT_WHEELS);
		SmartDashboard.putNumber("BALANCED top wheels", BALANCED_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("BALANCED bottom wheels", BALANCED_POWER_BOT_WHEELS);
		SmartDashboard.putNumber("SWITCH top wheels", SWITCH_POWER_TOP_WHEELS);
		SmartDashboard.putNumber("SWITCH bottom wheels", SWITCH_POWER_BOT_WHEELS);
		SmartDashboard.putString("NULL ZONE", nullZone);
		SmartDashboard.putNumber("RIGHT ENCODER VALUE", driveTrain.getRightEncoderValue());
		SmartDashboard.putNumber("LEFT ENCODER VALUE", driveTrain.getLeftEncoderValue());
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

		gyroscope.updateGyro();
		
		
		
		vision.visionProcessing();
		
		
		
		
		
		/************************
		 * * RIGHT STICK CONTROLS * *
		 ************************
		 */
		
	
		if (rightStick.getRawAxis(3) > 0) {
			driveTrain.lowGear();
		}
		else if (rightStick.getRawAxis(3) < 0) {
			driveTrain.highGear();
		}
		
		
		
		// Arcade Drive
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());

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
		
		
		
		
		
		if (rightStick.getRawButton(7)) {
			//Right is faster
			if (rightStick.getRawButtonPressed(7)) {
				shootingTimer = 0;
			}

			shooter.shootThoseDankCubesSideways(0.55, 0.8, BALANCED_POWER_BOT_WHEELS, shootingTimer, DISENGAGE_TIME);

		}
 
		else if (rightStick.getRawButton(5)) {
			//Left is faster
			if (rightStick.getRawButtonPressed(5)) {
				shootingTimer = 0;
			}

			shooter.shootThoseDankCubesSideways(0.8, 0.55,  BALANCED_POWER_BOT_WHEELS, shootingTimer,
					DISENGAGE_TIME);
		}

		else if (rightStick.getRawButtonReleased(5) || rightStick.getRawButtonReleased(7)) {
			shooter.resetForThoseDankCubes();
		}

		shootingTimer = shootingTimer+1;

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
		}

		else if (rightStick.getRawButtonReleased(REVERSE_INTAKE_BUTTON)) {
			intake.turnOffWheels();
			output.squeezeCube();
		}
		
		//Fast Reverse
		if (leftStick.getRawButtonPressed(10)) {
			intake.turnOnWheels(-1.0, -1.0);
			output.releaseCube();
		}

		else if (leftStick.getRawButtonReleased(10)) {
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
		SmartDashboard.putNumber("RIGHT ENCODER VALUE", driveTrain.getRightEncoderValue());
		SmartDashboard.putNumber("LEFT ENCODER VALUE", driveTrain.getLeftEncoderValue());
		
		
		// Putting everything on shuffleboard
		driveTrain.pushDataToShuffleboard();
		climb.pushDataToShuffleboard();
		intake.pushDataToShuffleboard();
		output.pushDataToShuffleboard();
		shooter.pushDataToShuffleboard();
		autonomous.pushDataToShuffleboard();
		gyroscope.pushDataToShuffleboard();
		// #########################################################################

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
		driveTrain.resetAuto();
		gyroscope.resetGyro();
	}

	public void disabledPeriodic() {
		output.turnOffWheels();
		//shooter.extendShooter();
		driveTrain.resetAuto();
	}
}