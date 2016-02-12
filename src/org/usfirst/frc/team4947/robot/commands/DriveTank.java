package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.OI.XBoxAxis;
import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTank extends Command {

    public DriveTank() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.tankdrive(Robot.oi.getJoystickDriverAxis(XBoxAxis.LeftStickY), Robot.oi.getJoystickDriverAxis(XBoxAxis.RightStickY));
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
