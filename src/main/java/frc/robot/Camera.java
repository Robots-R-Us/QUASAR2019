
package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Camera {

    private NetworkTable nt;
    private NetworkTableEntry tx, ty, ta, tv, thor;
    private NetworkTableEntry camMode, ledMode;

    public Camera() {
        nt = NetworkTableInstance.getDefault().getTable(Constants.CAMERA_NAME);
        updateNetworkTables();

        this.cameraOn();
    }

    // this will be called regularly in robotPeriodic()
    protected void updateNetworkTables() {

        tx = nt.getEntry("tx");
        ty = nt.getEntry("ty");
        ta = nt.getEntry("ta");
        tv = nt.getEntry("tv");
        thor = nt.getEntry("thor");
        camMode = nt.getEntry("camMode");
        ledMode = nt.getEntry("ledMode");

    }

    public void adjustSteering(Drivetrain m_driveTrain) {
        
        double steering_adjust = 0;
        double txValue = tx.getDouble(0.0);

        double velocity_adjust = 0;
        double tyValue = ty.getDouble(0.0);
        double taValue = ta.getDouble(0.0);
        double tvValue = tv.getDouble(0.0);
        double thorValue = thor.getDouble(0.0);

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

        if(txValue > 5) {
            steering_adjust = 0.66;
        } else if (txValue < -5) {
            steering_adjust = -0.66;
        } else if(txValue > -5 && txValue < -1) {
            steering_adjust = -0.4;
        } else if(txValue < 5 && txValue > 1) {
            steering_adjust = 0.4;
        } else if(txValue < 1 && txValue > 0.15) {
            steering_adjust = 0.35;
        } else if(txValue > -1 && txValue < -0.15) {
            steering_adjust = -0.35;
        } else if (txValue < 0.15 && txValue > -0.15) { // if it's around zero
            steering_adjust = 0;
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