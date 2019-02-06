
package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Constants;
import frc.robot.Drivetrain;

public class Robot extends TimedRobot {
  Compressor compressor;
  Joystick controller;
  Drivetrain driveTrain;

  @Override
  public void robotInit() {
    compressor = new Compressor(Constants.COMPRESSOR_PORT);
    controller = new Joystick(Constants.XBOX_CONTROLLER);
    driveTrain = new Drivetrain();

    //Starts compressor
    compressor.start();
  }

  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void teleopPeriodic() {

    //will use Drivetrain.Java to drive
    while(isOperatorControl() && isEnabled()) {
      if (controller.getRawAxis(2) > 0) {
        driveTrain.drive(controller);
      } else {
        driveTrain.drive_66(controller);
      }
    }
  }

  @Override
  public void testPeriodic() {

  }
}