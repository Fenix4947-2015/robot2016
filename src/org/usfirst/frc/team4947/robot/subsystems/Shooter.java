package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.ShooterStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Shooter extends Subsystem {
    
    private CANTalon leftMotor = new CANTalon(9);
    private CANTalon rightMotor = new CANTalon(7);
    private Solenoid positionSolenoid = new Solenoid(1);
    private Solenoid releaseSolenoid = new Solenoid(2);
    
    public Shooter(){
    	rightMotor.setInverted(true);
    	
    	LiveWindow.addActuator("Shooter", "LeftMotor", leftMotor);
    	LiveWindow.addActuator("Shooter", "RightMotor", rightMotor);
    	LiveWindow.addActuator("Shooter", "PositionSolenoid", positionSolenoid);
    	LiveWindow.addActuator("Shooter", "ReleaseSolenoid", releaseSolenoid);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ShooterStop());
    }
    
    public void setSpeed(double speed){
    	leftMotor.set(speed);
    	rightMotor.set(speed);
    }
    
    public void setShootingPosition(boolean isUpPosition){
    	positionSolenoid.set(isUpPosition);
    }

    public void setReleasePosition(boolean isReleasePosition){
    	releaseSolenoid.set(isReleasePosition);
    }
    
    public void log(){
    	
    }
}

