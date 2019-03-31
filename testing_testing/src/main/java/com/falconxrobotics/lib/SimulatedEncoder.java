
package com.falconxrobotics.lib;

public class SimulatedEncoder implements IEncoder {
    double encoderValue = 0.0;

    @Override
    public double getPosition() {
        return encoderValue;
    }

    public void setPosition(double encoderValue) {
         this.encoderValue = encoderValue;
    }
    
}