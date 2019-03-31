
package com.falconxrobotics.lib;

import edu.wpi.first.wpilibj.SpeedController;

public class SimulatedSpeedController implements SpeedController {

    private double lastSpeed = 0.0;
    private boolean disabled = false;
    private boolean inverted = false;


    @Override
    public void pidWrite(double output) {
        set(Math.max(Math.min(output,1.), -1.));
    }

    @Override
    public void set(double speed) {
        disabled = false;
        lastSpeed = speed;
    }

    @Override
    public double get() {
        return disabled ? 0.0 : lastSpeed;
    }

    @Override
    public void setInverted(boolean isInverted) {
        inverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public void disable() {
        disabled = false;
    }

    @Override
    public void stopMotor() {
        set(0);
    }

   

}