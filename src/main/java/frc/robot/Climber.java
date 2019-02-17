
package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Climber {

    private Solenoid frontClimber, rearClimber;

    public Climber(int _frontPort, int _rearPort) {
        frontClimber = new Solenoid(_frontPort);
        rearClimber = new Solenoid(_rearPort);

        this.both_retracted();
    }

    public void front_extended() {
        frontClimber.set(true);
    }

    public void front_retracted() {
        frontClimber.set(false);
    }

    public void both_extended() {
        frontClimber.set(true); rearClimber.set(true);
    }

    public void both_retracted() {
        frontClimber.set(false); rearClimber.set(false);
    }

    public void rear_extended() {
        rearClimber.set(true);
    }

    public void rear_retracted() {
        rearClimber.set(false);
    }
    
}