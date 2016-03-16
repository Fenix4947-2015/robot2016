package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.Robot;
import org.usfirst.frc.team4947.robot.commands.CameraExposureDefault;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem {
	private final static double TARGET_ANGLE_TOLERANCE = 0.5;
	
    private USBCamera USBCamera;
    
    public double targetAngle = 0;
    public boolean targetFound = false;
    
    public Camera(){
        USBCamera = new USBCamera("cam0");
        USBCamera.startCapture();
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new CameraExposureDefault());
    }
    
    public void setDefaultExposure(){
       	USBCamera.setExposureAuto();
    	USBCamera.setBrightness(50); 
    }
    
    public void setTargetExposure(){
		USBCamera.setExposureManual(0);
		USBCamera.setBrightness(0);
    }
    
    public USBCamera getCamera(){
    	return USBCamera;
    }
    
    public boolean isOnTarget(boolean canOvershoot){
    	double actualAngle = Robot.driveTrain.getAngle();
    	boolean isOnTarget = true;
    	boolean isOvershootTarget = true;

    	if(targetFound){
	    	if(targetAngle > 0){
	    		isOnTarget = Math.abs(actualAngle - targetAngle) <= TARGET_ANGLE_TOLERANCE;
	    		isOvershootTarget = actualAngle >= targetAngle;
	    	}
	    	else{
	    		isOnTarget = Math.abs(actualAngle - targetAngle) <= TARGET_ANGLE_TOLERANCE;
	    		isOvershootTarget = actualAngle <= targetAngle;	
	    	}
    	}
    	
    	return isOnTarget || (canOvershoot && isOvershootTarget);
    }
}

