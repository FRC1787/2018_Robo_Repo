package org.usfirst.frc.team1787.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* CLASS DEFINITION:
 * This class handles everything related to climbing, including the 3ft pneumatic cylinder and the backup alternative method of climbing (likely a winch)
 */

public class Climb {
	
	private final int CLIMB_EXTEND_SOLENOID_ID = 0;
	private final int CLIMB_RETRACT_SOLENOID_ID = 1;
	
	private DoubleSolenoid climbingSolenoid = new DoubleSolenoid(CLIMB_EXTEND_SOLENOID_ID, CLIMB_RETRACT_SOLENOID_ID);
	
	public final DoubleSolenoid.Value EXTEND = DoubleSolenoid.Value.kForward;
	public final DoubleSolenoid.Value RETRACT = DoubleSolenoid.Value.kReverse;
	
	private static final Climb instance = new Climb();
	
	
	private Climb() {
		
	}
	
	
	/**
	 * 
	 * Takes 1 parameter - Can either be EXTEND or RETRACT
	 */
	public void movePiston(DoubleSolenoid.Value desiredState) {
		climbingSolenoid.set(desiredState);
	}
	
	
	public void pushDataToShuffleboard() {
		SmartDashboard.putBoolean("Climb Extended: ", climbingSolenoid.get() == this.EXTEND);
	}
	
	
	public static Climb getInstance() {
	    return instance;
	  }
}
