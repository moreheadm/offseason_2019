
package com.falconxrobotics.lib;

public class StandardMotorModel implements MotorModel {
    public static final StandardMotorModel _775_PRO =
            createFromSpec(18730., 0.71);
    public static final StandardMotorModel CIM =
            createFromSpec(5330, 2.41);
    public static final StandardMotorModel MINI_CIM =
            createFromSpec(5840, 1.4);
    public static final StandardMotorModel BAG = 
            createFromSpec(13180, 0.4);
    public static final StandardMotorModel NEO = 
            createFromSpec(5676, 2.6);

    public final double freeSpeed;
    public final double stallTorque;
    public final double referenceVoltage;

    public StandardMotorModel(double referenceVoltage, double freeSpeed,
            double stallTorque) {
        this.referenceVoltage = referenceVoltage;
        this.freeSpeed = freeSpeed;
        this.stallTorque = stallTorque;
    }

    @Override
    public double getTorque(double voltage, double velocity) {
        double factor = voltage / referenceVoltage;
        double adjFreeSpeed = freeSpeed * factor;
        double adjStallTorque = freeSpeed * factor;

        double percentFreeSpeed = velocity / adjFreeSpeed;
        double outputTorque = adjStallTorque * (1 - percentFreeSpeed);
        return outputTorque;
    }

    @Override
    public double getCurrent(double voltage, double velocity) {
        throw new UnsupportedOperationException(
            "Motor current not yet supported for " + 
            this.getClass().getName());
    }

    /**
     * Creates a standard motor model using standard spec units.
     * 
     * Units for this method match what most manufacturs provide.
     * 
     * @param freeSpeedRPM the free speed of the motor in RPM
     * @param stallTorque the stall torque in N*m
     * 
     */
    public static StandardMotorModel createFromSpec(double freeSpeedRPM,
            double stallTorque) {
        return new StandardMotorModel(
                12.,
                convertRPMToRadsPerSec(freeSpeedRPM),
                stallTorque);
    }
    
    private static double convertRPMToRadsPerSec(double rpm) {
        return rpm / 60 * 2 * Math.PI;
    }
}