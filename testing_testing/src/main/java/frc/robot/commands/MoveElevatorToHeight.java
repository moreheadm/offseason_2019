
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToHeight extends Command {
    private Elevator elevator;
    private double targetHeight;
    private double kFF = 0.0;
    private double kP = 0.4;

    public MoveElevatorToHeight(double targetHeight, Elevator elevator) {
        requires(elevator);
        this.elevator = elevator;
        this.targetHeight = targetHeight;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double output = kFF + kP * (targetHeight - elevator.getHeight());
        //double output = targetHeight == elevator.getHeight() ? 0 :
        //        (targetHeight > elevator.getHeight() ? 1. : -1);
        output = Math.min(Math.max(-1, output), 1);
        elevator.setMotor(output);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}