package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSimple extends CommandGroup {
    public  AutoSimple(double forwardSpeed, double forwardTime, double rotateSpeed, double rotateAngle, boolean isAllUp, boolean isBallAlign, boolean isBallShoot) {
    	if(isAllUp){
    		addSequential(new AllUp());
    	}
    	else{
    		addSequential(new AllDown());
    	}
   		
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
