package org.usfirst.frc.team1787.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Testing {
	
	private WPI_TalonSRX talon1 = new WPI_TalonSRX(1);
	private WPI_TalonSRX talon2 = new WPI_TalonSRX(2);
	private WPI_TalonSRX talon3 = new WPI_TalonSRX(3);
	private WPI_TalonSRX talon4 = new WPI_TalonSRX(4);
	private WPI_TalonSRX talon5 = new WPI_TalonSRX(5);
	private WPI_TalonSRX talon6 = new WPI_TalonSRX(6);
	private WPI_VictorSPX victor7 = new WPI_VictorSPX(7);
	private WPI_VictorSPX victor8 = new WPI_VictorSPX(8);
	private WPI_VictorSPX victor9 = new WPI_VictorSPX(9);
	private WPI_VictorSPX victor10 = new WPI_VictorSPX(10);
	private WPI_VictorSPX victor11 = new WPI_VictorSPX(11);
	
	
	private Testing() {
		talon1.configVoltageCompSaturation(12, 10);
		talon1.enableVoltageCompensation(true);
		talon2.configVoltageCompSaturation(12, 10);
		talon2.enableVoltageCompensation(true);
		talon3.configVoltageCompSaturation(12, 10);
		talon3.enableVoltageCompensation(true);
		talon4.configVoltageCompSaturation(12, 10);
		talon4.enableVoltageCompensation(true);
		talon5.configVoltageCompSaturation(12, 10);
		talon5.enableVoltageCompensation(true);
		talon6.configVoltageCompSaturation(12, 10);
		talon6.enableVoltageCompensation(true);
	}
	
	private static final Testing instance = new Testing();
	
	public void testMotorControllerID () {
		
		//Setting motors to 0
		talon1.set(0);
		talon2.set(0);
		talon3.set(0);
		talon4.set(0.25);
		talon5.set(0);
		talon6.set(0);
		victor7.set(0);
		victor8.set(0);
		victor9.set(0);
		victor10.set(0);
		victor11.set(0);
		
		
		
	}
	
	public static Testing getInstance() {
		return instance;
	}

}
