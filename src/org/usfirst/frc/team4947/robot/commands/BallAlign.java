package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallAlign extends CommandGroup {
	private final static int MAX_RETRY = 3;
	
	public BallAlign() {
		//addSequential(new CameraExposureTarget());
		addSequential(new CameraTarget(), 1.0);
		addSequential(new DriveTarget());
		
    	for(int i = 0; i < MAX_RETRY; i++){
			addSequential(new CameraTarget());
			addSequential(new DriveTarget());
		}
    }
	
	@Override
	protected boolean isFinished() {
		return super.isFinished() || Robot.camera.isOnTarget(false);
	}
}
