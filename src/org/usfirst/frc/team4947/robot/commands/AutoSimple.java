package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSimple extends CommandGroup {
    public  AutoSimple(double forwardSpeed, double forwardTime, double rotateSpeed, double rotateAngle, boolean isLifterUp, boolean isCannonUp, boolean isBallAlign, boolean isBallShoot) {
    	if(isLifterUp){
    		addSequential(new LifterUpDown(1.0));
    	}
    	else{
    		addSequential(new LifterUpDown(-1.0));
    	}
    	
   		addSequential(new CannonPosition(isCannonUp));
   		
   		addSequential(new DriveForward(forwardSpeed), forwardTime);
   		addSequential(new DriveRotateAngle(rotateAngle, rotateSpeed, false));
   		
   		if(isBallAlign && isBallShoot){
   			addSequential(new BallAlignShoot());
   		}
   		else if(isBallAlign){
   			addSequential(new BallAlign());
   		}
    }
}
