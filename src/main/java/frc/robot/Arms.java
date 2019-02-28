
package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Arms {

    private Solenoid arms;
    public boolean isClosed;

    public Arms(int _port) {
        arms = new Solenoid(_port);
    }

    public boolean get_arms() {
        return arms.get();
    }

    public void open()
    {
       arms.set(false);
       this.isClosed = false;
    }

    public void close()
    {
        arms.set(true);
        this.isClosed = true;
    }
}