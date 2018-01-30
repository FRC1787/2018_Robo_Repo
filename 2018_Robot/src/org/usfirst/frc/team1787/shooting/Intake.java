package org.usfirst.frc.team1787.shooting;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;

/* CLASS DEFINITION:
 * The intake is defined as the 775 motors attached to the front of the robot and the pneumatics that push the wheels into the cubes
 */

public class Intake {
	
	private final int INTAKE_MASTER_TALON_ID = 4;
	private final int INTAKE_FOLLOWER_VICTOR_ID = 5;
	
	private WPI_TalonSRX intakeMaster = new WPI_TalonSRX(INTAKE_MASTER_TALON_ID);
	private WPI_VictorSPX intakeFollower = new WPI_VictorSPX(INTAKE_FOLLOWER_VICTOR_ID);
	
	private Solenoid intakeSolenoid = new Solenoid(2);
	
	private static final Intake instance = new Intake();
	
	private Intake() {
		
		//Configuring motor controllers
		intakeFollower.follow(intakeMaster);
		intakeMaster.configVoltageCompSaturation(12, 10);
		intakeFollower.configVoltageCompSaturation(12, 10);
		intakeMaster.enableVoltageCompensation(true);
		intakeFollower.enableVoltageCompensation(true);
	}

}
