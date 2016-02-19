package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.OI.XBoxAxis;
import org.usfirst.frc.team4947.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveArcade extends Command {

	private Image frame;
	
    public DriveArcade() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.camera.setExposureAuto();
    	Robot.camera.setBrightness(50);
    	frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.camera.getImage(frame);
    	
    	double moveValue = Robot.oi.getJoystickDriverAxis(XBoxAxis.RightTrigger) - Robot.oi.getJoystickDriverAxis(XBoxAxis.LeftTrigger);
    	double rotateValue = Robot.oi.getJoystickDriverAxis(XBoxAxis.LeftStickX, 0.1);
    	
    	// TODO
    	//Robot.driveTrain.arcadeDrive(moveValue, -rotateValue);
    	Robot.driveTrain.arcadeDrive(rotateValue, -moveValue);
    	
    	CameraServer.getInstance().setImage(frame);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// Execute this command until interrupted
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	// Stop the robot when interrupted
    	Robot.driveTrain.tankdrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
