
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain {

    private WPI_TalonSRX Fl, Fr, Rl, Rr;
    private SpeedControllerGroup LeftTank, RightTank;
    public DifferentialDrive differentialDrive;

    public boolean FullSpeedEnabled;

    public Drivetrain() {

        this.FullSpeedEnabled = false;

        Fl = new WPI_TalonSRX(Constants.LEFT_FRONT_MOTOR);
        Fr = new WPI_TalonSRX(Constants.RIGHT_FRONT_MOTOR);
        Rl = new WPI_TalonSRX(Constants.LEFT_REAR_MOTOR);
        Rr = new WPI_TalonSRX(Constants.RIGHT_REAR_MOTOR);

        LeftTank = new SpeedControllerGroup(Fl, Rl);
        RightTank = new SpeedControllerGroup(Fr, Rr);

        differentialDrive = new DifferentialDrive(LeftTank, RightTank);
    }

    public void drive(Joystick joystick) {

        double speed = -joystick.getRawAxis(1);
        double rotation = joystick.getRawAxis(0);
        
        differentialDrive.arcadeDrive(speed, rotation);

    }

    public void drive_66(Joystick joystick) {

        double speed = -joystick.getRawAxis(1);
        double rotation = joystick.getRawAxis(0);
        
        differentialDrive.arcadeDrive((speed) * 0.66, (rotation) * 0.66);
    }

    public void drive_straight() {

        differentialDrive.arcadeDrive(0.66, 0);

    }

    public void drive_straight_reverse() {

        differentialDrive.arcadeDrive(-0.66, 0);

    }

}