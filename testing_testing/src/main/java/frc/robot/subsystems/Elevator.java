
package frc.robot.subsystems;

import com.falconxrobotics.lib.IEncoder;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
    SpeedController motor;
    IEncoder encoder;

    public Elevator(SpeedController motor, IEncoder encoder) {
        this.motor = motor;
        this.encoder = encoder;
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getHeight() {
        // 3 inch diameter drum, 4096 ticks per rev, enocder after gearbox
        return encoder.getPosition() / 4096. * 3. * Math.PI;
    }

	public void setMotor(double output) {
        motor.set(output);
	}
}