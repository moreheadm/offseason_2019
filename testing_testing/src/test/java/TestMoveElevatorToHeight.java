import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;

import org.junit.Test;

import edu.wpi.first.wpilibj.SpeedController;

import org.junit.Before;

import com.falconxrobotics.lib.ElevatorMechanism;
import com.falconxrobotics.lib.StandardMotorModel;
import com.falconxrobotics.lib.*;
import frc.robot.commands.MoveElevatorToHeight;
import frc.robot.subsystems.Elevator;
import java.io.*;

public class TestMoveElevatorToHeight {
    private Elevator elevatorSubsystem;
    private ElevatorMechanism elevatorMechanism;
    private MoveElevatorToHeight moveCommand;

    @Before
    public void setup() {
        SimulatedSpeedController speedController = new SimulatedSpeedController();
        SimulatedEncoder encoder = new SimulatedEncoder();
        elevatorSubsystem = new Elevator(speedController, encoder);
        elevatorMechanism = new ElevatorMechanism(5, 50, 0.0762, speedController,
                StandardMotorModel._775_PRO, encoder);
        moveCommand = new MoveElevatorToHeight(50, elevatorSubsystem);
    }

    @Test
    public void testMoveToPosition() {
        StringBuilder sb = new StringBuilder("Time,Position,Velocity,Acceration\n");
        moveCommand.initialize();
        double time = 0.0;
        for (int i = 0; i < 500; i++) {
            moveCommand.execute();
            for (int j = 0; j < 20; j++) {
                elevatorMechanism.simulateTimestep(0.02 / 20);
                sb.append(time + "," + elevatorMechanism.getPosition() + "," +
                        elevatorMechanism.getVelocity() + "," +
                        elevatorMechanism.getAcceleration() + "\n");
                time += 0.02 / 20.;
            }
        }
        System.out.println("Mechanism " + elevatorMechanism.getHeight());
        System.out.println("Subsystem " + elevatorSubsystem.getHeight());

        String logString = sb.toString();
        try (PrintWriter out = new PrintWriter("/tmp/test_log.csv")) {
            out.print(logString);
        } catch (FileNotFoundException e) {

        }
    }
}