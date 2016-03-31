package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraExposureDefault extends Command {

	private Image frame;
	
    public CameraExposureDefault() {
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
    	Robot.camera.setDefaultExposure();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try{
	    	Robot.camera.getCamera().getImage(frame);
//	    	Robot.camera.getCamera2().getImage(frame);
	    	
	        NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(140, 120), new Point(180,  120), 250.0f);
	        NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(160, 100), new Point(160,  140), 250.0f);
	    	
	    	CameraServer.getInstance().setImage(frame);
    	}
    	catch(Exception e){
    		
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
