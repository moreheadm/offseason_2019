
package com.falconxrobotics.lib;

public class ElevatorMechanism extends MotorMechanism {
    private static final double GRAV_ACCEL = 9.8;

    /** Mass in kg */
    public final double mass;
    public final double gearing;
    public final double drumRadius;
    public final double efficiency = 0.8;
    private SimulatedEncoder encoder;

    /**
     * 
     */
    public ElevatorMechanism(double mass, double gearing, double drumDiameter,
            SimulatedSpeedController controller, MotorModel motorModel, 
            SimulatedEncoder encoder) {
        super(controller, motorModel);
        this.mass = mass;
        this.gearing = gearing;
        this.drumRadius = drumDiameter / 2.;
        this.encoder = encoder;
    }

    @Override
    public double getExternalTorques(double position, double velocity) {
        double gravity = mass * GRAV_ACCEL * drumRadius * gearing;
        return gravity;
    }

    @Override
    public double getMechanismMOI(double position, double velocity) {
        return mass * drumRadius * drumRadius;
    }

    @Override
    public double getEfficiency() {
        return efficiency;
    }

    @Override
    public void simulateTimestep(double timestep) {
        super.simulateTimestep(timestep);
        encoder.setPosition(getPosition() / gearing / 2. / Math.PI * 4096.);;
    }

    /**
     * Gets the elevator's height in meters.
     */
    public double getHeight() {
        return getPosition() / gearing * drumRadius;
    }

}