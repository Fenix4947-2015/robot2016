package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;
import org.usfirst.frc.team4947.robot.OI.XBoxButton;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallAlignShoot extends CommandGroup {
	public BallAlignShoot() {
		addSequential(new CannonPosition(true));
		addSequential(new LifterUpDown(1.0));
		
		addSequential(new ShooterEnableCompressor(false));
    	addParallel(new ShooterStart(), 10.0);
    	addSequential(new BallAlign());
    	addSequential(new CannonRelease(true));
    	addSequential(new ShooterStart(), 0.250);
        addSequential(new ShooterStop());
        
//      addSequential(new BallAlign());
//		addSequential(new BallShoot());
    }
	
	@Override
	protected boolean isFinished() {
		return super.isFinished() || Robot.camera.isOnTarget(false) || Robot.oi.getJoystickDriverButton(XBoxButton.Back);
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
