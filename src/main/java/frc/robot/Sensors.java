package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Sensors {

    DigitalInput limitSwitch_ElevatorBottom;
    DigitalInput floorSensorF, floorSensorR;

    public Sensors() {

        limitSwitch_ElevatorBottom = new DigitalInput(0);
        floorSensorF = new DigitalInput(1);
        floorSensorR = new DigitalInput(2);

    }

    public boolean elevatorBottom() {
        if(limitSwitch_ElevatorBottom.get()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean floorTapeGet() {

        if(floorSensorF.get() && floorSensorR.get()) {
            
            return true;

        } else {

            return false;
        }
    }

    public void sensor_drive(Drivetrain driveTrain) {
        if(floorTapeGet()) {
            driveTrain.drive_straight();
        }
    }

    public void sensor_drive_reverse(Drivetrain driveTrain) {
        if(floorTapeGet()) {
            driveTrain.drive_straight_reverse();
        }
    }

}