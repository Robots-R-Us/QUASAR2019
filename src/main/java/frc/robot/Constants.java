
package frc.robot;

public class Constants {

    // Elevator Presets
    static int PRESET_BALL_LOW = -11500;
    static int PRESET_BALL_MED = -23500;
    static int PRESET_BALL_HIGH = -26000;

    static int PRESET_HATCH_LOW = 6990;
    static int PRESET_HATCH_MED = 17200;
    static int PRESET_HATCH_HIGH = 28000;

    // Drive Train
    static int LEFT_FRONT_MOTOR = 3;
    static int LEFT_REAR_MOTOR = 4;
    static  int RIGHT_FRONT_MOTOR = 0;
    static int RIGHT_REAR_MOTOR = 1;

    // Elevator
    static int ELEVATOR_MOTOR = 2;

    // Controllers
    static int XBOX_CONTROLLER = 0;
    static int OPERATOR_CONTROLLER = 1;
    
    // Pneumatics
    static int COMPRESSOR_PORT = 0;
    static int CLIMBER_REAR = 0;
    static int CLIMBER_FRONT = 1;
    static int ARMS_SOLENOID = 2;
    static int KICKER_SOLENOID = 3;

    // Camera
    static String CAMERA_NAME = "limelight";
    static int TARGET_SENSITIVITY = 10;

    // PID
    static int PID_ID = 0;
    static int SLOT_ID = 0;
    static int TIMEOUT_MS = 30;
    static boolean SENSOR_PHASE = true;
    static boolean PID_INVERT = false;
    static final PIDGains PID_Gains = new PIDGains(0.15, 0.0, 1.0, 0.0, 0, 1.0);

}