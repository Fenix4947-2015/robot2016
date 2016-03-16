package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterInOut extends Command {

	private double speed;
	
    public ShooterInOut(double speed) {
    	requires(Robot.shooter);
        
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.setSpeed(speed);
    	
    	// Rumble when have a ball and shooter is running to get a ball in
    	if(speed > 0 && Robot.shooter.isBallPresent()){
    		Robot.oi.setJoystickDriverRumble(RumbleType.kLeftRumble, 0.5f);
    		Robot.oi.setJoystickDriverRumble(RumbleType.kRightRumble, 0.5f);
    	}
    	else{
    		Robot.oi.setJoystickDriverRumble(RumbleType.kLeftRumble, 0.0f);
    		Robot.oi.setJoystickDriverRumble(RumbleType.kRightRumble, 0.0f);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.setSpeed(0);
		Robot.oi.setJoystickDriverRumble(RumbleType.kLeftRumble, 0.0f);
		Robot.oi.setJoystickDriverRumble(RumbleType.kRightRumble, 0.0f);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
