
package frc.robot.subsystems;

//#region Imports
import frc.robot.Constants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//#endregion

public class Camera {

    private NetworkTable nt;
    private NetworkTableEntry tx, tv, thor;
    private NetworkTableEntry camMode, ledMode;

    public Camera() {
        nt = NetworkTableInstance.getDefault().getTable(Constants.CAMERA_NAME);
        updateNetworkTables();
    }

    // this will be called regularly in robotPeriodic()
    public void updateNetworkTables() {

        tx = nt.getEntry("tx");
        tv = nt.getEntry("tv");
        thor = nt.getEntry("thor");
        camMode = nt.getEntry("camMode");
        ledMode = nt.getEntry("ledMode");

    }

    public void adjustSteering(Drivetrain m_driveTrain) {
        
        double steering_adjust = 0;
        double txValue = tx.getDouble(0.0);

        double velocity_adjust = 0;
        double tvValue = tv.getDouble(0.0);
        double thorValue = thor.getDouble(0.0);

        // if a target is seen
        if(tvValue > 0) {

            // we adjust our velocity based on the read of tHor value
            if(thorValue < 60 && thorValue > 0) {
                velocity_adjust = 0.88;

            } else if(thorValue < 120 && thorValue > 60) {
                velocity_adjust = 0.66;

            } else if(thorValue < 150 && thorValue > 120) {
                velocity_adjust = 0.55;

            } else if(thorValue > 150) {
                velocity_adjust = 0;

            } else { 
                velocity_adjust = 0;
            }

            // then do the same with our steering and the tx value
            if(txValue > 5) {
                steering_adjust = 0.66;

            } else if (txValue < -5) {
                steering_adjust = -0.66;

            } else if(txValue > -5 && txValue < -1) {
                steering_adjust = -0.35;

            } else if(txValue < 5 && txValue > 1) {
                steering_adjust = 0.35;

            } else if(txValue < 1 && txValue > 0.15) {
                steering_adjust = 0.3;

            } else if(txValue > -1 && txValue < -0.15) {
                steering_adjust = -0.3;

            } else if (txValue < 0.15 && txValue > -0.15) {
                steering_adjust = 0;

            } else {
                steering_adjust = 0;
            }
        }

        m_driveTrain.differentialDrive.arcadeDrive(velocity_adjust, steering_adjust);

    }

    public void cameraOn() {

        camMode.setValue(0);
        ledMode.setValue(0);

    }

    public void cameraOff() {
        
        camMode.setValue(1);
        ledMode.setValue(1);

    }
}