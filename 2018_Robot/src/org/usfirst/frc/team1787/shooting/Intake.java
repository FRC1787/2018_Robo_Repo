package org.usfirst.frc.team1787.shooting;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * The intake is defined as the 775 motors attached to the front of the robot and the pneumatics that push the wheels into the cubes
 */

public class Intake {
	
	private final int INTAKE_MASTER_TALON_ID = 4;
	private final int INTAKE_FOLLOWER_VICTOR_ID = 5;
	private final int INTAKE_SOLENOID_ID = 2;
	
	private WPI_TalonSRX intakeMaster = new WPI_TalonSRX(INTAKE_MASTER_TALON_ID);
	private WPI_VictorSPX intakeFollower = new WPI_VictorSPX(INTAKE_FOLLOWER_VICTOR_ID);
	
	private Solenoid intakeSolenoid = new Solenoid(INTAKE_SOLENOID_ID);
	
	private static final Intake instance = new Intake();
	
	private Intake() {
		
		//Configuring motor controllers
		intakeFollower.follow(intakeMaster);
		
		intakeMaster.setInverted(false);
		intakeFollower.setInverted(false);
		
		//Making every talon light up green when forwards
		intakeMaster.configVoltageCompSaturation(12, 10);
		intakeFollower.configVoltageCompSaturation(12, 10);
		intakeMaster.enableVoltageCompensation(true);
		intakeFollower.enableVoltageCompensation(true);
	}
	
	
	
	public void turnOnWheels(double INTAKE_VOLTAGE) {
		intakeMaster.set(INTAKE_VOLTAGE);
	}
	
	public void turnOffWheels() {
		intakeMaster.stopMotor();
	}
	
	
	
	public void squeezeCube() {
		intakeSolenoid.set(true);
	}

	public void releaseCube() {
		intakeSolenoid.set(false);
	}
	
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putBoolean("Gripped: ", intakeSolenoid.get());
		SmartDashboard.putNumber("Intake Motor Speed: ", intakeMaster.get());
	}
	
	public static Intake getInstance() {
	    return instance;
	  }

}
