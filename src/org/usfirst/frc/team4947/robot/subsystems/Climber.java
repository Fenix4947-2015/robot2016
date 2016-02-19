package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.ClimberStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Climber extends Subsystem {
	
	private CANTalon motor = new CANTalon(10);
	
	public Climber(){
		LiveWindow.addActuator("Intake", "Motor", motor);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ClimberStop());
    }
    
    public void setSpeed(double speed){
    	motor.set(speed);
    }
    
    public void log(){
    	
    }
}

