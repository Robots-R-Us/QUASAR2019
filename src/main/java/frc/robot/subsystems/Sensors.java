package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

public class Sensors {

    private DigitalInput limitSwitch_ElevatorBottom;
    private DigitalInput floorSensorF, floorSensorR;
    private DigitalInput hatchLimitSwitch;

    public Sensors() {

        limitSwitch_ElevatorBottom = new DigitalInput(0);
        floorSensorF = new DigitalInput(1);
        floorSensorR = new DigitalInput(2);
        hatchLimitSwitch = new DigitalInput(3);

    }

    public boolean getBackHatch() {
        if(hatchLimitSwitch.get()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getElevatorBottom() {
        if(limitSwitch_ElevatorBottom.get()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getFloorTape() {

        if(floorSensorF.get() || floorSensorR.get()) {
            
            return true;

        } else {

            return false;
        }
    }

    public boolean getFrontSensor() {
        if(floorSensorF.get()) return true;
        else return false;
    }

    public boolean getRearSensor() {
        if(floorSensorR.get()) return true;
        else return false;
    }

}