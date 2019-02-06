
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;


public class Drivetrain {

    WPI_TalonSRX Fl, Fr, Rl, Rr;
    SpeedControllerGroup LeftTank, RightTank;
    DifferentialDrive differentialDrive;

    public Drivetrain(){

        Fl = new WPI_TalonSRX(Constants.LEFT_FRONT_MOTOR);
        Fr = new WPI_TalonSRX(Constants.RIGHT_FRONT_MOTOR);
        Rl = new WPI_TalonSRX(Constants.LEFT_REAR_MOTOR);
        Rr = new WPI_TalonSRX(Constants.RIGHT_REAR_MOTOR);
        LeftTank = new SpeedControllerGroup(Fl, Rl);
        RightTank = new SpeedControllerGroup(Fr, Rr);
        differentialDrive = new DifferentialDrive(LeftTank, RightTank);

    }
    public void drive(Joystick joystick){
        differentialDrive.arcadeDrive(joystick.getRawAxis(0), joystick.getRawAxis(1));
    }

    public void drive_66(Joystick joystick){
        differentialDrive.arcadeDrive((joystick.getRawAxis(0)*.66), (joystick.getRawAxis(1)*.66));
    }




    
}