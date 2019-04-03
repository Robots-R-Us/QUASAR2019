package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class BackHatch {

    private Solenoid backHatchFlower, backHatchKick;
    public String state;

    public BackHatch(int _port1, int _port2) {
        this.backHatchFlower = new Solenoid(_port1);
        this.backHatchKick = new Solenoid(_port2);
        this.state = "closed";

        this.execute();
    }

    public void open() {
        this.state = "open";
        this.execute();
    }

    public void close() {
        this.state = "closed";
        this.execute();
    }

    public void kick() {
        this.state = "kick";
        this.execute();
    }

    public void unkick() {
        this.state = "unkick";
        this.execute();
    }
    
    private void execute() {
        switch(this.state) {
            case "closed":
                this.backHatchFlower.set(false);
            break;

            case "open":
                this.backHatchFlower.set(true);
            break;

            case "kick":
                this.backHatchKick.set(true);
            break;

            case "unkick":
                this.backHatchKick.set(false);
            break;
        }
    }
    
}