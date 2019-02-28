
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.Joystick;

public class Elevator {

	private WPI_TalonSRX elevatorMotor;
	public String current_preset;

	// this constructor is for accessing info inside the elevator
	public Elevator() {

	}

	public Elevator(int _port) {

	    elevatorMotor = new WPI_TalonSRX(_port);

	    elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
        	Constants.PID_ID,
        	Constants.TIMEOUT_MS);

			elevatorMotor.setSensorPhase(Constants.SENSOR_PHASE);
			elevatorMotor.setInverted(Constants.SENSOR_PHASE);

			elevatorMotor.configNominalOutputForward(0, Constants.TIMEOUT_MS);
			elevatorMotor.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
			elevatorMotor.configPeakOutputForward(1, Constants.TIMEOUT_MS);
			elevatorMotor.configPeakOutputReverse(-1, Constants.TIMEOUT_MS);

			elevatorMotor.configAllowableClosedloopError(0, Constants.PID_ID, Constants.TIMEOUT_MS);

			elevatorMotor.config_kF(Constants.PID_ID, Constants.PID_Gains._F, Constants.TIMEOUT_MS);
			elevatorMotor.config_kP(Constants.PID_ID, Constants.PID_Gains._P, Constants.TIMEOUT_MS);
			elevatorMotor.config_kI(Constants.PID_ID, Constants.PID_Gains._I, Constants.TIMEOUT_MS);
			elevatorMotor.config_kD(Constants.PID_ID, Constants.PID_Gains._D, Constants.TIMEOUT_MS);

			int absolutePosition = elevatorMotor.getSensorCollection().getPulseWidthPosition();

        	absolutePosition &= 0xFFF; // mask out overflows, keep bottom 12 bits
        
			if (Constants.SENSOR_PHASE) { absolutePosition *= -1; }
			if (Constants.PID_INVERT) { absolutePosition *= -1; }
		
			elevatorMotor.setSelectedSensorPosition(absolutePosition, Constants.PID_ID, Constants.TIMEOUT_MS);

	}

	// ControlMode.Position changes at a rate of position / 100ms
	public void set_preset(int _preset) {

		int targetPosition = _preset;
		elevatorMotor.set(ControlMode.Position, targetPosition);
		
	}

	public String getPresetPosition() {
		return current_preset;
	}

	public void analogControl(Joystick joystick) {
		elevatorMotor.set(joystick.getRawAxis(5));
	
		System.out.println("Elevator Val: " + this.ElevatorPosition());
	}

	public void setPositionToZero() {
		elevatorMotor.setSelectedSensorPosition(0);
	}

	public double ElevatorPosition() {
	    return elevatorMotor.getSelectedSensorPosition();
	}

	public void up(){
		elevatorMotor.set(1);
	}

	public void down(){
		elevatorMotor.set(-1);
	}

	public void stop(){
	    elevatorMotor.set(0);

	}
}