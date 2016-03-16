package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import com.ni.vision.NIVision;
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
    	Robot.camera.getCamera().getImage(frame);
    	CameraServer.getInstance().setImage(frame);
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
