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
//    private USBCamera USBCamera2;
    
    public double targetAngle = 0;
    public boolean targetFound = false;
    public boolean targetInRange = false;
    
    public Camera(){
    	try{
	        USBCamera = new USBCamera("cam0");
	        USBCamera.startCapture();
//
//			USBCamera.setExposureManual(0);
//			USBCamera.setBrightness(0);
    	}
    	catch(Exception e){}
    	
//    	try{
//	        USBCamera2 = new USBCamera("cam1");
//	        USBCamera2.startCapture();
//
//       	USBCamera2.setExposureAuto();
//    		USBCamera2.setBrightness(50);
//    	}
//    	catch(Exception e){}
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new CameraExposureDefault());
    }
    
    public void setDefaultExposure(){
    	try{
	       	USBCamera.setExposureAuto();
	    	USBCamera.setBrightness(50);
    	}
    	catch(Exception e){
    		
    	}
    }
    
    public void setTargetExposure(){
    	try{
			USBCamera.setExposureManual(0);
			USBCamera.setBrightness(0);
    	}
    	catch(Exception e){
    		
    	}
    }
    
    public USBCamera getCamera(){
    	return USBCamera;
    }

//    public USBCamera getCamera2(){
//    	return USBCamera2;
//    }
    
    public boolean isOnTarget(boolean canOvershoot){
    	double actualAngle = Robot.driveTrain.getAngle();
    	boolean isOnTarget = false;
    	boolean isOvershootTarget = false;

    	if(targetFound && targetInRange){
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

