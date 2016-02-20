package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LifterPosition extends Command {

	private double setPoint;
	
    public LifterPosition(double setPoint) {
        requires(Robot.lifter);
        
        this.setPoint = setPoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lifter.enable();
    	Robot.lifter.setSetpoint(setPoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.lifter.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lifter.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
