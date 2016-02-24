package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.OI.XBoxButton;
import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTarget extends Command {
	private final static double TOLERANCE = 0.5;
	private final static double SPEED = 0.4;
	
    double rotateValue = 0;
	
    public DriveTarget() {
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		if(Robot.camera.targetAngle < 0){
			rotateValue = -SPEED;
		}
		else if(Robot.camera.targetAngle > 0){
			rotateValue = SPEED;
		}
		else{
			rotateValue = 0;
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.driveTrain.tankDrive(-rotateValue, rotateValue);
    }
 
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double actualAngle = Robot.driveTrain.getAngle();
    	boolean isOnTarget = false;

    	if(Robot.camera.targetFound){
	    	if(rotateValue > 0){
	    		isOnTarget = Math.abs(actualAngle - Robot.camera.targetAngle) <= TOLERANCE || actualAngle >= Robot.camera.targetAngle;
	    	}
	    	else{
	    		isOnTarget = Math.abs(actualAngle - Robot.camera.targetAngle) <= TOLERANCE || actualAngle <= Robot.camera.targetAngle;	
	    	}
    	}
    	
    	return isOnTarget || Robot.oi.getJoystickDriverButton(XBoxButton.Back);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
