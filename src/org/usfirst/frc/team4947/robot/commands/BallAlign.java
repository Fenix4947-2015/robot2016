package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.Robot;
import org.usfirst.frc.team4947.robot.OI.XBoxButton;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallAlign extends CommandGroup {
	private final static int MAX_RETRY = 2;
	
	public BallAlign() {
		// TODO Maybe use DriveRotateAngle with the angle return by CameraTarget
		addSequential(new CameraExposureTarget(), 0.5);
		addSequential(new CameraTarget());
		addSequential(new DriveTarget());
		
    	for(int i = 0; i < MAX_RETRY; i++){
			addSequential(new CameraTarget());
			addSequential(new DriveTarget());
		}
    }
	
	@Override
	protected boolean isFinished() {
		return super.isFinished() || Robot.camera.isOnTarget(false) || Robot.oi.getJoystickDriverButton(XBoxButton.Back);
	}
}
