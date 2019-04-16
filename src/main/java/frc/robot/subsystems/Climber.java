
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class Climber {

    private Solenoid frontClimber, rearClimber;
    private String state;

    public Climber(int _frontPort, int _rearPort) {
        frontClimber = new Solenoid(_frontPort);
        rearClimber = new Solenoid(_rearPort);

        this.state="none";
        this.execute();
    }

    public boolean getFrontClimber() {
        return frontClimber.get();
    }

    public boolean getRearClimber() {
        return rearClimber.get();
    }

    public void front_extended() {
        this.state="front out";
        this.execute();
    }

    public void front_retracted() {
        this.state="front in";
        this.execute();
    }

    public void rear_extended() {
        this.state="rear out";
        this.execute();
    }

    public void rear_retracted() {
        this.state="rear in";
        this.execute();
    }

    private void execute() {
        switch(this.state) {

            case "front out":
                frontClimber.set(true);
            break;

            case "front in":
                frontClimber.set(false);
            break;

            case "rear out":
                rearClimber.set(true);
            break;

            case "rear in":
                rearClimber.set(false);
            break;

            default:
                frontClimber.set(false);
                rearClimber.set(false);
            break;

        }
    }
    
}