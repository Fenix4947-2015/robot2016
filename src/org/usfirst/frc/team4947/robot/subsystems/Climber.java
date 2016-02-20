package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.ClimberStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Climber extends PIDSubsystem {
	
	private CANTalon motor = new CANTalon(5);
	
	public Climber(){
		super(4, 0, 0);
		
		LiveWindow.addActuator("Climber", "Motor", motor);
		LiveWindow.addActuator("Climber", "PID", getPIDController());
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ClimberStop());
    }

    public void setSpeed(double speed){
    	motor.set(speed);
    }

    public void log(){
    	
    }

	@Override
	protected double returnPIDInput() {
		return motor.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		motor.set(output);
	}
}

