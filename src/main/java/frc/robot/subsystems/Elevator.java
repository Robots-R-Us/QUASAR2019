
package frc.robot.subsystems;

//#region Imports
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.*;

import util.Log;
import util.MessageType;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
//#endregion

public class Elevator {

	private WPI_TalonSRX elevatorMotor;
	public String current_preset;
	private String state;
	private int targetPosition;

	public Elevator(int _port) {

	    elevatorMotor = new WPI_TalonSRX(_port);

	    elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PID_ID, Constants.TIMEOUT_MS);

		elevatorMotor.setSensorPhase(Constants.SENSOR_PHASE);
		elevatorMotor.setInverted(Constants.SENSOR_PHASE);

		Timer.delay(0.01);

		elevatorMotor.configNominalOutputForward(0, Constants.TIMEOUT_MS);
		elevatorMotor.configNominalOutputReverse(0, Constants.TIMEOUT_MS);

		Timer.delay(0.01);

		elevatorMotor.configPeakOutputForward(1, Constants.TIMEOUT_MS);
		elevatorMotor.configPeakOutputReverse(-1, Constants.TIMEOUT_MS);

		Timer.delay(0.01);

		elevatorMotor.configAllowableClosedloopError(0, Constants.PID_ID, Constants.TIMEOUT_MS);

		Timer.delay(0.01);

		elevatorMotor.config_kF(Constants.PID_ID, Constants.PID_Gains._F, Constants.TIMEOUT_MS);
		elevatorMotor.config_kP(Constants.PID_ID, Constants.PID_Gains._P, Constants.TIMEOUT_MS);
		elevatorMotor.config_kI(Constants.PID_ID, Constants.PID_Gains._I, Constants.TIMEOUT_MS);
		elevatorMotor.config_kD(Constants.PID_ID, Constants.PID_Gains._D, Constants.TIMEOUT_MS);
		
		Timer.delay(0.01);

		int absolutePosition = elevatorMotor.getSensorCollection().getPulseWidthPosition();

       	absolutePosition &= 0xFFF; // mask out overflows, keep bottom 12 bits
       
		if (Constants.SENSOR_PHASE) { absolutePosition *= -1; }
		if (Constants.PID_INVERT) { absolutePosition *= -1; }
		
		elevatorMotor.setSelectedSensorPosition(absolutePosition, Constants.PID_ID, Constants.TIMEOUT_MS);

		Timer.delay(0.01);

		this.state = "stop";
		this.execute();

	}

	public String getPresetPosition() {
		return current_preset;
	}

	public double getElevatorPosition() {
	    return elevatorMotor.getSelectedSensorPosition();
	}

	public void analogControl(Joystick joystick) {
		elevatorMotor.set(joystick.getRawAxis(5));
	
		Log.WriteLine(MessageType.INFO, "[ANALOG CONTROL] Encoder Value: " + this.getElevatorPosition());
	}

	public void setPreset(int _preset) {

		this.targetPosition = _preset;

		this.state = "preset";
		this.execute();
		
	}

	public void zero() {
		this.state = "zero";
		this.execute();
	}

	public void up() {
		this.state = "up";
		this.execute();
	}

	public void down() {
		this.state = "down";
		this.execute();
	}

	public void stop() {
		this.state = "stop";
		this.execute();
	}

	private void execute() {
		switch(this.state) {
			case "up":
				elevatorMotor.set(1);
			break;

			case "down":
				elevatorMotor.set(-1);
			break;

			case "stop":
				elevatorMotor.set(0);
			break;

			case "zero":
				elevatorMotor.setSelectedSensorPosition(0);
			break;

			case "preset":
				elevatorMotor.set(ControlMode.Position, targetPosition);
			break;
		}
	}
}