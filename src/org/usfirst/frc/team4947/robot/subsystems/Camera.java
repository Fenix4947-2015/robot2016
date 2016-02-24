package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.CameraDefault;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem {
    
    private USBCamera USBCamera;
    
    public double targetAngle = 0;
    public boolean targetFound = false;
    
    public Camera(){
        USBCamera = new USBCamera("cam0");
        USBCamera.startCapture();
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new CameraDefault());
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
}

