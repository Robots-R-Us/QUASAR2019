
package frc.robot;

import java.util.Map;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  
  // code components
  Drivetrain driveTrain;
  Camera camera;
  Elevator elevator;
  Arms arms;
  Climber climber;
  Sensors sensors;
  BackHatch backHatch;

  // other components
  Compressor compressor;
  Joystick controller, operator;

  HttpCamera limelightFeed;

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
    backHatch = new BackHatch(Constants.BACKHATCH_FLOWER, Constants.BACKHATCH_KICKER);
    elevator.current_preset = "START";

    //Starts compressor
    compressor.start();

    ShuffleboardTab visionTab = Shuffleboard.getTab("Backup Vision");

    limelightFeed = new HttpCamera("limelight", "http://limelight.local:5800/stream.mjpg");
    visionTab.add("limelight", limelightFeed).withPosition(0, 0).withSize(15, 8).withProperties(Map.of("Show Crosshair", true, "Show Controls", false));

  }

  @Override
  public void robotPeriodic() {

    // we need to routinely update the network tables
    camera.updateNetworkTables();

    SmartDashboard.putNumber("ElevatorEncoder", elevator.getElevatorPosition());
    SmartDashboard.putBoolean("ElevatorLimitSwitch", sensors.getElevatorBottom());
    SmartDashboard.putString("ElevatorPresetPosition", elevator.getPresetPosition());
    SmartDashboard.putBoolean("CompressorAirFull", compressor.getPressureSwitchValue());
    SmartDashboard.putBoolean("IsEnabled", this.isEnabled());
    SmartDashboard.putBoolean("FrontStingers", climber.getFrontClimber());
    SmartDashboard.putBoolean("RearStingers", climber.getRearClimber());
    SmartDashboard.putBoolean("ArmsClosed", arms.getArmsClosed());
    SmartDashboard.putBoolean("FloorF", sensors.floorSensorF.get());
    SmartDashboard.putBoolean("FloorR", sensors.floorSensorR.get());
    SmartDashboard.putBoolean("FloorGet", sensors.getFloorTape());

  }

  @Override
  public void autonomousInit() {
    this.teleopInit();
  }

  @Override
  public void autonomousPeriodic() {
    this.teleopPeriodic();
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
      driveTrain.fullSpeedDrive(controller);
    // L3 - drive straight reverse
    } else if (controller.getRawButton(9)) {
      driveTrain.driveStraightBackward(controller);
    // Operator ? - reverse drive base as long as the button is held
    } else if(operator.getRawButton(7)) {
      driveTrain.reverseDriveBase(controller);
    // default drive
    } else {
      driveTrain.regularDrive(controller);
    }

    // driver D-pad Left - scan for floor tape
    if(operator.getPOV() == 270) {
      if(sensors.floorSensorR.get() || sensors.floorSensorF.get()) {
        this.scan_area();
      } else {
        // do nothing cause there's no tape detected on either sensor
      }
    }

    // Y - camera tracking on, B - camera tracking off
    if(controller.getRawButton(4))  {
      camera.cameraOn();
    } else if(controller.getRawButton(2)) {
      camera.cameraOff();
    }
    
    // A - elevator down, X - elevator up
    // don't allow the elevator to run downwards if the sensor is flipped
    if(controller.getRawButton(1) && !sensors.getElevatorBottom()) {
      elevator.down();
    } else if(controller.getRawButton(3)) {
      elevator.up();
    } else {
      elevator.stop();
    }


    // LB - open arms, RB - close arms
    if(controller.getRawButton(5)) {
      if(arms.getArmsClosed())
        arms.open();
    } else if(controller.getRawButton(6)) {
      if(!arms.getArmsClosed())
        arms.close();
    }

    // START - lower the elevator all the way down
    //         then set the encoder position to zero
    if(controller.getRawButton(8)) {

      elevator.down();

      // stop once it hits the sensor
      if(sensors.getElevatorBottom()) {
        // and zero the position
        elevator.zero();
        elevator.stop();
      }
    }

    if(operator.getRawButton(5)) {
      backHatch.open();

      if(operator.getRawButton(6)) {
        backHatch.kick();
      }

    } else {
      backHatch.close();
      backHatch.unkick();
    }

    if(operator.getRawButton(8)) {
      backHatch.kick();
    } else {
      backHatch.unkick();
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
      elevator.setPreset(Constants.PRESET_HATCH_LOW);
    }

    // B/X - med hatch
    if(operator.getRawButton(2)) {
      elevator.current_preset = "MED HATCH";
      elevator.setPreset(Constants.PRESET_HATCH_MED);
    }

    // B/X - med hatch
    if(operator.getRawButton(3)) {
      elevator.current_preset = "MED HATCH";
      elevator.setPreset(Constants.PRESET_HATCH_MED);
    }

    // Y - high hatch
    if(operator.getRawButton(4)) {
      elevator.current_preset = "HI HATCH";
      elevator.setPreset(Constants.PRESET_HATCH_HIGH);
    }

    // A+RB - low ball
    if(operator.getRawButton(1) && operator.getRawButton(6)) {
      elevator.current_preset = "LOW CARGO";
      elevator.setPreset(Constants.PRESET_BALL_LOW);
    }

    // B+RB/X+RB - med ball
    if(operator.getRawButton(2) && operator.getRawButton(6)) {
        elevator.current_preset = "MED CARGO";
        elevator.setPreset(Constants.PRESET_BALL_MED);
    }

    // B+RB/X+RB - med ball
    if(operator.getRawButton(3) && operator.getRawButton(6)) {
      elevator.current_preset = "MED CARGO";
      elevator.setPreset(Constants.PRESET_BALL_MED);
  }

    // Y+RB - high ball
    if(operator.getRawButton(4) && operator.getRawButton(6)) {
      elevator.current_preset = "HI CARGO";
      elevator.setPreset(Constants.PRESET_BALL_HIGH);
    }

    // R3 - auto drive button
    if(controller.getRawButton(10)){
      
      camera.adjustSteering(driveTrain);

    }

    driveTrain.differentialDrive.feedWatchdog();
    
  }

  private void scan_area() {

    do {
      driveTrain.differentialDrive.arcadeDrive(0, 0.33);
      
      // driver d-pad DOWN - this is the override switch
      //                     for stopping the scan
      if(operator.getPOV() == 90) {
        break;
      }

    } while(!sensors.getFloorTape());

    driveTrain.differentialDrive.arcadeDrive(0, 0);
  }
  
}