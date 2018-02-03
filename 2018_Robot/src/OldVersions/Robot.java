/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package OldVersions;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
	
	protected int farfar38;
	
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Autonomous autonomous = Autonomous.getInstance();
	private Flywheel flywheel = Flywheel.getInstance();
	private SlewingRing slewingRing = SlewingRing.getInstance();
	
	private final int RIGHT_JOYSTICK_ID = 0;
	private final int LEFT_JOYSTICK_ID = 1;
	private Joystick rightStick = new Joystick(RIGHT_JOYSTICK_ID);
	private Joystick leftStick = new Joystick(LEFT_JOYSTICK_ID);
	private int JOYSTICK_ROTATION_AXIS = 2;
	private int JOYSTICK_SLIDER_AXIS = 3;
	
	//Input options
	private int FLYWHEEL_ACTIVATE_BUTTON = 1; 
	private int FEEDER_ACTIVATE_BUTTON = 1;	
	
	
	@Override
	public void robotInit() {
		
	}

	@Override
	public void autonomousInit() {
		autonomous.resetVariables();
	}
	
	@Override
	public void autonomousPeriodic() {
		autonomous.autonomous1();
	}
	
	@Override
	public void teleopInit() {
		
	}
	
	@Override
	public void teleopPeriodic() {
		driveTrain.arcadeDrive(-rightStick.getY(), rightStick.getX());
		slewingRing.turnTurret(leftStick.getRawAxis(JOYSTICK_ROTATION_AXIS));
		
		if (leftStick.getRawButtonPressed(FLYWHEEL_ACTIVATE_BUTTON)) {
			flywheel.runFlywheelPID(-leftStick.getRawAxis(JOYSTICK_SLIDER_AXIS));
		}
		else if (leftStick.getRawButtonReleased(FLYWHEEL_ACTIVATE_BUTTON)) {
			flywheel.stopFlywheel();
		}
		
		flywheel.sendShuffleboadData();
		
		
		
		
	}

	@Override
	public void testPeriodic() {
		
	}
}
