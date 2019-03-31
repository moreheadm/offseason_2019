
package com.falconxrobotics.lib;

public interface MotorModel {
    /**
     * Calculates the torque of the motor at a given voltage and angular
     * velocity.
     */
    double getTorque(double voltage, double velocity);
    /**
     * Calculates the current draw of the motor at a given voltage and angular
     * velocity.
     */
    double getCurrent(double voltage, double velocity);
}