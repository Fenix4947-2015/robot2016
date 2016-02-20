package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CannonPosition extends Command {

	private boolean isUpPosition;
	
    public CannonPosition(boolean isUpPosition) {
        requires(Robot.cannon);
        
        this.isUpPosition = isUpPosition;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.cannon.setShootingPosition(isUpPosition);
    	setTimeout(0.3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
