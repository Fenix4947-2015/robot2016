package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.ShooterStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {
    
    private CANTalon leftMotor = new CANTalon(9);
    private CANTalon rightMotor = new CANTalon(7);
    
    private DigitalInput ballPresentSwitch = new DigitalInput(0);
    
    public double speed;
    
    public Shooter(){
    	leftMotor.setInverted(true);
    	
    	LiveWindow.addActuator("Shooter", "LeftMotor", leftMotor);
    	LiveWindow.addActuator("Shooter", "RightMotor", rightMotor);
    	
    	SmartDashboard.putNumber("ShooterSpeed",  -1.0);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ShooterStop());
    }
    
    public void setSpeed(double speed){
    	leftMotor.set(speed);
    	rightMotor.set(speed);
    }

    public boolean isBallPresent(){
    	return !ballPresentSwitch.get();
    }
    
    public void enableCompressor(boolean isEnabled){
    	
    }
    
    public void log(){
    	speed = SmartDashboard.getNumber("ShooterSpeed",  -1.0);
    }
}

