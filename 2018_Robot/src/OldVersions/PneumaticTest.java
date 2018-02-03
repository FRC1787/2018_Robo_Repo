package OldVersions;

import OldVersions.PneumaticTest;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class PneumaticTest {
	
	private DoubleSolenoid superDankSolenoid = new DoubleSolenoid(1, 0);
	
	private static final PneumaticTest instance = new PneumaticTest();
	
	private int testArmExtendButton;
	private int testArmRetractButton;
	public final DoubleSolenoid.Value OUT = DoubleSolenoid.Value.kForward;
	public final DoubleSolenoid.Value IN = DoubleSolenoid.Value.kReverse;
	
	public PneumaticTest() {
		testArmExtendButton = 1;
		testArmRetractButton = 2;
	}
	
	public void pistonControl(Joystick joystickInput) {
		if (joystickInput.getRawButton(testArmExtendButton)) {
			superDankSolenoid.set(OUT);
		}
		
		else if (joystickInput.getRawButton(testArmRetractButton)) {
			superDankSolenoid.set(IN);
		}
	}
	
	public static PneumaticTest getInstance() {
		return instance;
	}
	
	

}
