package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRotateAngle extends Command {

	private final double TOLERANCE = 1.0;

	private double angle;
	private double speed;
	
    public DriveRotateAngle(double angle, double speed) {
        requires(Robot.driveTrain);
        
        this.angle = angle;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetAngle();
    	
    	// Reverse the speed if not in the direction of the angle
    	if((angle < 0 && speed > 0) || (angle > 0 && speed < 0)){
    		speed = -speed;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.driveTrain.arcadeDrive(0, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean isFinished = false;
    	double actualAngle = Robot.driveTrain.getAngle();
    	
    	if(angle >= 0){
    		isFinished = Math.abs(actualAngle - angle) <= TOLERANCE || actualAngle >= angle;
    	}
    	else{
    		isFinished = Math.abs(actualAngle - angle) <= TOLERANCE || actualAngle <= angle;
    	}
    	
    	return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
