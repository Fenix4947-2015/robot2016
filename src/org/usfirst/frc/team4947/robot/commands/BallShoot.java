package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallShoot extends CommandGroup {
    
    public BallShoot() {
    	addSequential(new ShooterEnableCompressor(false));
    	//addSequential(new CannonRelease(false));
    	addSequential(new ShooterStart(), 1.0);
    	addSequential(new CannonRelease(true));
    	addSequential(new ShooterStart(), 0.5);
        addSequential(new ShooterStop());
    }
    
    @Override
    protected void end() {
    	Robot.shooter.enableCompressor(true);
    }
    
    @Override
    protected void interrupted() {
    	end();
    }
}
