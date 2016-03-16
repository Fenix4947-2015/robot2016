package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.OI.XBoxButton;
import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTarget extends Command {
	double rotateValue = 0;
	double speed = 0.35;
    
    public DriveTarget() {
    	requires(Robot.driveTrain);
    	SmartDashboard.putNumber("DriveTargetSpeed", speed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = SmartDashboard.getNumber("DriveTargetSpeed", speed);
    	
		if(Robot.camera.targetAngle < 0){
			rotateValue = -speed;
		}
		else if(Robot.camera.targetAngle > 0){
			rotateValue = speed;
		}
		else{
			rotateValue = 0;
		}
		
		// For test on mecanum base
		//Robot.driveTrain.tankDrive(rotateValue, -rotateValue);
		Robot.driveTrain.tankDrive(-rotateValue, rotateValue);
    }
 
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.camera.isOnTarget(true) || Robot.oi.getJoystickDriverButton(XBoxButton.Back);
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
