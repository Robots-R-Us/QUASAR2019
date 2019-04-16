
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class Arms {

    private Solenoid arms;
    private String state;

    public Arms(int _port) {
        arms = new Solenoid(_port);

        this.state = "closed";
        this.execute();
    }

    public void toggle() {
        if(this.state == "closed") {
            this.state = "open";
            this.execute();
        } else {
            this.state = "closed";
            this.execute();
        }
    }

    public boolean getArmsClosed() {
        return this.state == "closed" ? true : false;
    }

    public void open()
    {
       this.state = "open";
       this.execute();
    }

    public void close()
    {
        this.state = "closed";
        this.execute();
    }

    private void execute() {
        switch(this.state) {
            case "closed":
                arms.set(false);
            break;

            case "open":
                arms.set(true);
            break;
        }
    }
}