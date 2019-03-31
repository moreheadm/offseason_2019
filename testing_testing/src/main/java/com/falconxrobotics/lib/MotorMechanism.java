
package com.falconxrobotics.lib;

public abstract class MotorMechanism implements Simulated {
    /**
     * Gets the value in N*m of any external torques on the motor.
     * 
     * For an elevator or arm, this would involve gravity and friction. For a
     * flywheel, just friction would be a factor.
     */
    public abstract double getExternalTorques(double position, double velocity);

    /**
     * Gets the mechanisms moment of inertia in SI units.
     * 
     * For an elevator, this 
     */
    public abstract double getMechanismMOI(double position, double velocity);
    
    /**
     * Get an efficiency factor between 0 and 1.
     * 
     * Override this method to add friction.
     */
    public double getEfficiency() {
        return 1.;
    }

    private SimulatedSpeedController controller;
    private MotorModel motorModel;

    /** Position in radians */
    private double position = 0.0;
    /** Angular velocity in radians per second */
    private double velocity = 0.0;
    /** Angular acceleration in radians per second ^ 2 */
    private double acceleration = 0.0;

    public MotorMechanism(SimulatedSpeedController controller, MotorModel motorModel) {
        this.controller = controller;
        this.motorModel = motorModel;
    }

    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    @Override
    public void simulateTimestep(double timestep) {
        boolean inverted = controller.getInverted();
        double voltage = controller.get() * (inverted ? -12. : 12);

        double position = getPosition();
        double velocity = getVelocity();
        double motorTorque = motorModel.getTorque(voltage, velocity);
        double totalTorque = motorTorque + getExternalTorques(position,
                velocity);
        double torqueWithFriction = calculateTorqueWithFriction(velocity,
                totalTorque, getEfficiency());
        
        double moi = getMechanismMOI(position, velocity);
        double newAcceleration = torqueWithFriction / moi;

        double newVelocity = velocity + newAcceleration * timestep;
        if (velocity > 0 && newVelocity < 0 || velocity < 0 && newVelocity > 0) {
            // ignore friction if friction changes movement direction
            // TODO: improve
            newAcceleration = totalTorque / moi;
            newVelocity = velocity + newAcceleration * timestep;
        }

        double newPosition = position + (velocity + newVelocity) / 2 * timestep;

        this.velocity = newVelocity;
        this.position = newPosition;
        this.acceleration = newAcceleration;
    }

    private static double calculateTorqueWithFriction(double velocity,
            double torque, double efficiency) {
        if (velocity * torque >= 0) {
            return torque *= efficiency;
        } else {
            return torque /= efficiency;
        }
    }
}