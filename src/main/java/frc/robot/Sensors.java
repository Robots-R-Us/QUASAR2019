package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Sensors {

    DigitalInput limitSwitch_ElevatorBottom;
    DigitalInput floorSensorF, floorSensorR;
    DigitalInput hatchLimitSwitch;

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

        if(floorSensorF.get() && floorSensorR.get()) {
            
            return true;

        } else {

            return false;
        }
    }

}