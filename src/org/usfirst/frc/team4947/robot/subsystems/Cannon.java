package org.usfirst.frc.team4947.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Cannon extends Subsystem {
    
    private Solenoid positionSolenoid = new Solenoid(1);
    private Solenoid releaseSolenoid = new Solenoid(2);
    
    public Cannon(){
    	LiveWindow.addActuator("Cannon", "PositionSolenoid", positionSolenoid);
    	LiveWindow.addActuator("Cannon", "ReleaseSolenoid", releaseSolenoid);
    }

    public void initDefaultCommand() {        
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

