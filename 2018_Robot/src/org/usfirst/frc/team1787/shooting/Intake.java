package org.usfirst.frc.team1787.shooting;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * The intake is defined as the 775 motors attached to the front of the robot and the pneumatics that push the wheels into the cubes
 */

public class Intake {
	
	private final int INTAKE_RIGHT_TALON_ID = 3;
	private final int INTAKE_LEFT_VICTOR_ID = 7;
	private final int INTAKE_SOLENOID_ID = 6;
	
	private WPI_TalonSRX intakeRight = new WPI_TalonSRX(INTAKE_RIGHT_TALON_ID);
	private WPI_VictorSPX intakeLeft = new WPI_VictorSPX(INTAKE_LEFT_VICTOR_ID);
	
	private Solenoid intakeSolenoid = new Solenoid(INTAKE_SOLENOID_ID);
	
	private static final Intake instance = new Intake();
	
	private Intake() {
		
		//Configuring motor controllers
		//intakeLeft.follow(intakeRight);
		
		intakeRight.setInverted(false);
		intakeLeft.setInverted(false);
		
		//Making every talon light up green when forwards
		intakeRight.configVoltageCompSaturation(12, 10);
		intakeLeft.configVoltageCompSaturation(12, 10);
		intakeRight.enableVoltageCompensation(true);
		intakeLeft.enableVoltageCompensation(true);
	}
	
	
	public void testSolenoid(boolean boolInput) {
		intakeSolenoid.set(boolInput);
	}
	
	
	public void turnOnWheels(double rightIntakeVoltage, double leftIntakeVoltage ) {
		intakeRight.set(rightIntakeVoltage);
		intakeLeft.set(leftIntakeVoltage);
	}
	
	public void turnOffWheels() {
		intakeRight.stopMotor();
		intakeLeft.stopMotor();
	}
	
	
	
	public void squeezeCube() {
		intakeSolenoid.set(true);
	}

	public void releaseCube() {
		intakeSolenoid.set(false);
	}
	
	
	
	public void spinCubeLeft(double INTAKE_VOLTAGE) {
		intakeLeft.set(-INTAKE_VOLTAGE);
		intakeRight.set(INTAKE_VOLTAGE);
	}
	
	public void spinCubeRight(double INTAKE_VOLTAGE) {
		intakeLeft.set(-INTAKE_VOLTAGE);
		intakeRight.set(-INTAKE_VOLTAGE);
	}
	
	public void pullCubeIn (double rightIntakeVoltage, double leftIntakeVoltage) {
		intakeSolenoid.set(false);
		intakeRight.set( rightIntakeVoltage );
		intakeLeft.set(leftIntakeVoltage);
	}
	
	public void pushCubeOut(double intakeOutVoltage) {
		intakeSolenoid.set(false);
		intakeRight.set(intakeOutVoltage);
		intakeLeft.set(intakeOutVoltage);
	}
	
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putBoolean("Gripped: ", intakeSolenoid.get());
		SmartDashboard.putData("Intake Motor Speed: ", intakeRight);
	}
	
	public static Intake getInstance() {
	    return instance;
	  }

}
