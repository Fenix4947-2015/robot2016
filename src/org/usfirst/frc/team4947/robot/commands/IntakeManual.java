package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;
import org.usfirst.frc.team4947.robot.OI.XBoxAxis;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeManual extends Command {

    public IntakeManual() {
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.setLifterSpeed(Robot.oi.getJoystickDriverAxis(XBoxAxis.LeftStickY, 0.15));
    	Robot.intake.setRollerSpeed(Robot.oi.getJoystickDriverAxis(XBoxAxis.RightStickY, 0.15));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.setLifterSpeed(0);
    	Robot.intake.setRollerSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
