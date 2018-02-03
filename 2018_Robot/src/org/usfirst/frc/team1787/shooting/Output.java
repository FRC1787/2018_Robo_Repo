package org.usfirst.frc.team1787.shooting;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * The output section of the robot is defined as the 4 mini-CIM motors on the vertical part of the shooter assembly, as well as the pneumatics that push
 * the wheels on the CIMs into the cube (or "tote" as Mr. McMonigle refers to them!)
 */

public class Output {
	
	private final int TOP_SHOOT_MASTER_TALON_ID = 6;
	private final int BOTTOM_SHOOT_MASTER_TALON_ID = 7;
	private final int TOP_SHOOT_FOLLOWER_VICTOR_ID = 8;
	private final int BOTTOM_SHOOT_FOLLOWER_VICTOR_ID = 9;
	private final int OUTPUT_SOLENOID_ID = 3;
	
	private WPI_TalonSRX topMaster = new WPI_TalonSRX(TOP_SHOOT_MASTER_TALON_ID);
	private WPI_VictorSPX topFollower = new WPI_VictorSPX(TOP_SHOOT_FOLLOWER_VICTOR_ID);
	private WPI_TalonSRX bottomMaster = new WPI_TalonSRX(BOTTOM_SHOOT_MASTER_TALON_ID);
	private WPI_VictorSPX bottomFollower = new WPI_VictorSPX(BOTTOM_SHOOT_FOLLOWER_VICTOR_ID);
	
		
	
	private Solenoid outputSolenoid = new Solenoid(OUTPUT_SOLENOID_ID);
	
	private static final Output instance = new Output();
	
	
	
	
	private Output() {
		topFollower.follow(topMaster);
		bottomFollower.follow(bottomMaster);
				
		//Making every talon light up green when forwards
		topMaster.setInverted(false);
		topFollower.setInverted(false);
		bottomMaster.setInverted(false);
		bottomFollower.setInverted(false);
		
		//Voltage Compensation for the talons
		topMaster.configVoltageCompSaturation(12, 10);
		topFollower.configVoltageCompSaturation(12, 10);
		bottomMaster.configVoltageCompSaturation(12, 10);
		bottomFollower.configVoltageCompSaturation(12, 10);
		
		topMaster.enableVoltageCompensation(true);
		topFollower.enableVoltageCompensation(true);
		bottomMaster.enableVoltageCompensation(true);
		bottomFollower.enableVoltageCompensation(true);
	}
	

	
	public void turnOnWheels(double SHOOTER_VOLTAGE) {
		topMaster.set(SHOOTER_VOLTAGE);
		bottomMaster.set(SHOOTER_VOLTAGE);
	}
	
	public void turnOffWheels() {
		topMaster.stopMotor();
		bottomMaster.stopMotor();
	}
	
	
	
	public void squeezeCube() {
		outputSolenoid.set(true);
	}

	public void releaseCube() {
		outputSolenoid.set(false);
	}
	
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putNumber("Top Shooter Speed: ", topMaster.get());
		SmartDashboard.putNumber("Bottom Shooter Speed: ", bottomMaster.get());
	}
	
	public static Output getInstance() {
	    return instance;
	  }

}
