package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LifterUp extends Command {

	private double speed;
	
    public LifterUp(double speed) {
        requires(Robot.lifter);
        
        this.speed = speed;
        if(speed > 0){
        	speed = -speed;
        }
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.lifter.setLifterSpeed(-speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lifter.getDownLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lifter.setLifterSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
