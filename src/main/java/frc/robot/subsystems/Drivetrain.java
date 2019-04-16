
package frc.robot.subsystems;

//#region Imports
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//#endregion

public class Drivetrain {

    private WPI_TalonSRX Fl, Fr, Rl, Rr;
    private SpeedControllerGroup LeftTank, RightTank;
    public DifferentialDrive differentialDrive;

    private String state;

    public Drivetrain() {

        Fl = new WPI_TalonSRX(Constants.LEFT_FRONT_MOTOR);
        Fr = new WPI_TalonSRX(Constants.RIGHT_FRONT_MOTOR);
        Rl = new WPI_TalonSRX(Constants.LEFT_REAR_MOTOR);
        Rr = new WPI_TalonSRX(Constants.RIGHT_REAR_MOTOR);

        LeftTank = new SpeedControllerGroup(Fl, Rl);
        RightTank = new SpeedControllerGroup(Fr, Rr);

        differentialDrive = new DifferentialDrive(LeftTank, RightTank);

        this.state = "66 percent";
    }

    public void fullSpeedDrive(Joystick joystick) {

        this.state = "fullspeed";
        this.execute(joystick);

    }

    public void regularDrive(Joystick joystick) {

        this.state = "66percent";
        this.execute(joystick);
    }

    public void driveStraight(Joystick joystick) {

        this.state = "straight forward";
        this.execute(joystick);

    }

    public void driveStraightBackward(Joystick joystick) {

        this.state = "straight backward";
        this.execute(joystick);

    }

    public void reverseDriveBase(Joystick joystick) {
        this.state = "total reverse";
        this.execute(joystick);
        
    }

    private void execute(Joystick joystick) {

        double speed = -joystick.getRawAxis(1);
        double rotation = joystick.getRawAxis(0);

        switch(this.state) {
            case "fullspeed":
                differentialDrive.arcadeDrive(speed, rotation);
            break;

            case "straight forward":
                differentialDrive.arcadeDrive(0.66, 0);
            break;

            case "straight backward":
                differentialDrive.arcadeDrive(-0.66, 0);
            break;

            case "total reverse":
                differentialDrive.arcadeDrive(-((speed) * 0.66), ((rotation) * 0.66));
            break;

            case "66 percent":                
                differentialDrive.arcadeDrive((speed) * 0.66, (rotation) * 0.66);
            break;

            default:
                differentialDrive.arcadeDrive((speed) * 0.66, (rotation) * 0.66);
            break;
        }
    }

}