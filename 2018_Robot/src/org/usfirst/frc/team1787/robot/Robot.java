package org.usfirst.frc.team1787.robot;

import org.usfirst.frc.team1787.subsystems.Autonomous;
import org.usfirst.frc.team1787.subsystems.DriveTrain;


/* CLASS DEFINITION:
 * This class is the main class for the robot, which ties all parts together and is automatically called upon by the RoboRIO
 */

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	
	protected int farfar38;
	
	private DriveTrain driveTrain = DriveTrain.getInstance();
	
	private final int RIGHT_JOYSTICK_ID = 0;
	private final int LEFT_JOYSTICK_ID = 1;
	private Joystick rightStick = new Joystick(RIGHT_JOYSTICK_ID);
	private Joystick leftStick = new Joystick(LEFT_JOYSTICK_ID);
	private int JOYSTICK_ROTATION_AXIS = 2;
	private int JOYSTICK_SLIDER_AXIS = 3;
	
	//Input options
	private int SHOOT_CUBES_BUTTON;

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
	public void teleopPeriodic() {
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());
	}

	@Override
	public void testPeriodic() {
		
	}
}
