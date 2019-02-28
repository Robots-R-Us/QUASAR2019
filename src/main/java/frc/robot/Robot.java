
package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  
  // code components
  Drivetrain driveTrain;
  Camera camera;
  Elevator elevator;
  Arms arms;
  Climber climber;
  Sensors sensors;

  // other components
  Compressor compressor;
  Joystick controller, operator;

  @Override
  public void robotInit() {

    compressor = new Compressor(Constants.COMPRESSOR_PORT);

    controller = new Joystick(Constants.XBOX_CONTROLLER);
    operator = new Joystick(Constants.OPERATOR_CONTROLLER);

    driveTrain = new Drivetrain(); // constants are assembled in the constructor
    camera = new Camera();
    elevator = new Elevator(Constants.ELEVATOR_MOTOR);
    arms = new Arms(Constants.ARMS_SOLENOID);
    sensors = new Sensors();
    climber = new Climber(Constants.CLIMBER_FRONT,Constants.CLIMBER_REAR);
    elevator.current_preset = "START";

    //Starts compressor
    compressor.start();
  }

  @Override
  public void robotPeriodic() {

    // we need to routinely update the network tables
    camera.updateNetworkTables();
    SmartDashboard.putData("DifferentialDrivebase", driveTrain.differentialDrive);
    SmartDashboard.putNumber("ElevatorEncoder", elevator.ElevatorPosition());
    SmartDashboard.putBoolean("ElevatorLimitSwitch", sensors.elevatorBottom());
    SmartDashboard.putString("ElevatorPresetPosition", elevator.getPresetPosition());
    SmartDashboard.putBoolean("CompressorAirFull", compressor.getPressureSwitchValue());
    SmartDashboard.putBoolean("IsEnabled", this.isEnabled());
    SmartDashboard.putBoolean("FrontStingers", climber.get_front());
    SmartDashboard.putBoolean("RearStingers", climber.get_rear());
    SmartDashboard.putBoolean("ArmsOpen", arms.get_arms());

  }

  @Override
  public void teleopInit() {
    driveTrain.differentialDrive.setSafetyEnabled(true);
    driveTrain.differentialDrive.setExpiration(.1);
  }

  @Override
  public void teleopPeriodic() {

    // LT - drive at 100% speed
    if (controller.getRawAxis(2) > 0.1) {
      driveTrain.FullSpeedEnabled = true;
      driveTrain.drive(controller);
    } else {
      driveTrain.FullSpeedEnabled = false;
      driveTrain.drive_66(controller);
    }

    // Y - camera tracking on, B - camera tracking off
    if(controller.getRawButton(4)) {
      camera.cameraOn();
    } else if(controller.getRawButton(2)) {
      camera.cameraOff();
    }
    
    // A - elevator down, X - elevator up
    if(controller.getRawButton(1) && !sensors.elevatorBottom()) {
      elevator.down();
    } else if(controller.getRawButton(3)) {
      elevator.up();
    } else {
      elevator.stop();
    }


    // LB - open arms, RB - close arms
    if(controller.getRawButton(5)) {
      if(arms.isClosed)
        arms.open();
    } else if(controller.getRawButton(6)) {
      if(!arms.isClosed)
        arms.close();
    }

    // START - lower the elevator all the way down
    //         then set the encoder position to zero
    if(controller.getRawButton(8)) {

      elevator.down();

      if(sensors.elevatorBottom()) {
        elevator.setPositionToZero();
        elevator.stop();
      }
    }

    // Operator D-PAD Up
    if(operator.getPOV() == 0 && elevator.ElevatorPosition() <= -2200) {
      climber.both_extended();
    }

    // Operator D-PAD Down
    if(operator.getPOV() == 180) {
      climber.both_retracted();
    }
    
    // LT - rear stingers
    if (operator.getRawAxis(2) > 0.1) {
      climber.rear_extended();
    } else {
      climber.rear_retracted();
    }

    // RT - front stingers
    if (operator.getRawAxis(3) > 0.1) {
      climber.front_extended();
    } else {
      climber.front_retracted();
    }

    // A - low hatch
    if(operator.getRawButton(1)) {
      elevator.current_preset = "LOW HATCH";
      elevator.set_preset(Constants.PRESET_HATCH_LOW);
    }

    // B/X - med hatch
    if(operator.getRawButton(2)) {
      elevator.current_preset = "MED HATCH";
      elevator.set_preset(Constants.PRESET_HATCH_MED);
    }

    // B/X - med hatch
    if(operator.getRawButton(3)) {
      elevator.current_preset = "MED HATCH";
      elevator.set_preset(Constants.PRESET_HATCH_MED);
    }

    // Y - high hatch
    if(operator.getRawButton(4)) {
      elevator.current_preset = "HI HATCH";
      elevator.set_preset(Constants.PRESET_HATCH_HIGH);
    }

    // A+RB - low ball
    if(operator.getRawButton(1) && operator.getRawButton(6)) {
      elevator.current_preset = "LOW CARGO";
      elevator.set_preset(Constants.PRESET_BALL_LOW);
    }

    // B+RB/X+RB - med ball
    if(operator.getRawButton(2) && operator.getRawButton(6)) {
        elevator.current_preset = "MED CARGO";
        elevator.set_preset(Constants.PRESET_BALL_MED);
    }

    // B+RB/X+RB - med ball
    if(operator.getRawButton(3) && operator.getRawButton(6)) {
      elevator.current_preset = "MED CARGO";
      elevator.set_preset(Constants.PRESET_BALL_MED);
  }

    // Y+RB - high ball
    if(operator.getRawButton(4) && operator.getRawButton(6)) {
      elevator.current_preset = "HI CARGO";
      elevator.set_preset(Constants.PRESET_BALL_HIGH);
    }

    // R3 - auto drive button
    if(controller.getRawButton(10)){
      
      camera.adjustSteering(driveTrain);

    }

    driveTrain.differentialDrive.feedWatchdog();
    
  }
  
}