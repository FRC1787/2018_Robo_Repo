package OldVersions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SlewingRing {
	//Turret talon IDs
	private final int TURRET_TALON_ID = 1;
	private WPI_TalonSRX turretMotor = new WPI_TalonSRX(TURRET_TALON_ID);
	private static final SlewingRing instance = new SlewingRing();
	
	private double SLEWING_RING_SPEED_COEFFICIENT = 2.0;
	
	public void turnTurret(double turnValue) {
		if (turnValue > 0.025 || turnValue < -0.025) {
			turretMotor.set(SLEWING_RING_SPEED_COEFFICIENT*turnValue);
		}
		else  {
			turretMotor.set(0);
		}
	}
	
	
	public static SlewingRing getInstance() {
		return instance;
	}
}
