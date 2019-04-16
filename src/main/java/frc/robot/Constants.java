
package frc.robot;

public class Constants {

    // Elevator Presets
    public static int PRESET_BALL_LOW = 8000;
    public static int PRESET_BALL_MED = 17900;
    public static int PRESET_BALL_HIGH = 24200;

    public static int PRESET_HATCH_LOW = 5500;
    public static int PRESET_HATCH_MED = 15500;
    public static int PRESET_HATCH_HIGH = 24100;

    // Drive Train
    public static int LEFT_FRONT_MOTOR = 3;
    public static int LEFT_REAR_MOTOR = 4;
    public static  int RIGHT_FRONT_MOTOR = 0;
    public static int RIGHT_REAR_MOTOR = 1;

    // Elevator
    public static int ELEVATOR_MOTOR = 2;

    // Controllers
    public static int XBOX_CONTROLLER = 0;
    public static int OPERATOR_CONTROLLER = 1;
    
    // Pneumatics
    public static int COMPRESSOR_PORT = 0;
    public static int CLIMBER_REAR = 0;
    public static int CLIMBER_FRONT = 1;
    public static int ARMS_SOLENOID = 2;
    public static int BACKHATCH_KICKER = 4;
    public static int BACKHATCH_FLOWER = 5;

    // Camera
    public static String CAMERA_NAME = "limelight";

    // PID
    public static int PID_ID = 0;
    public static int SLOT_ID = 0;
    public static int TIMEOUT_MS = 30;
    public static boolean SENSOR_PHASE = true;
    public static boolean PID_INVERT = false;
    public static final PIDGains PID_Gains = new PIDGains(0.15, 0.0, 1.0, 0.0, 0, 1.0);

}