package org.usfirst.frc.team1787.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * This class handles everything related to climbing, including the 3ft pneumatic cylinder and the backup alternative method of climbing (likely a winch)
 */

public class Climb {
	
	private final int CLIMB_EXTEND_SOLENOID_ID = 1;
	private final int CLIMB_RETRACT_SOLENOID_ID = 2;
	
	public DoubleSolenoid climbingSolenoid = new DoubleSolenoid(CLIMB_EXTEND_SOLENOID_ID, CLIMB_RETRACT_SOLENOID_ID);
	
	public final DoubleSolenoid.Value EXTEND = DoubleSolenoid.Value.kForward;
	public final DoubleSolenoid.Value RETRACT = DoubleSolenoid.Value.kReverse;
	
	private static final Climb instance = new Climb();
	
	
	private Climb() {
		
	}
	
	
	public void extendPiston() {
		climbingSolenoid.set(RETRACT);
	}
	
	
	public void retractPiston() {
		climbingSolenoid.set(EXTEND);
	}
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putBoolean("Climb Extended: ", climbingSolenoid.get() == this.EXTEND);
	}
	
	
	public static Climb getInstance() {
	    return instance;
	  }
}
