package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoChevalDeFrise extends CommandGroup {
    public  AutoChevalDeFrise(double rotateSpeed, double rotateAngle, boolean isShort, boolean isBallAlign, boolean isBallShoot) {
   		addSequential(new AllUp());
   		
   		// Move to the Cheval
   		addSequential(new DriveForward(0.5), 2.0);
   		// Lower the Cheval
   		addSequential(new LifterUpDown(-1.0));
   		// Move a little bit on the Cheval
   		addSequential(new DriveForward(0.5), 1);
   		// Raise the lifter to avoid hitting the ground
   		addSequential(new LifterUpDown(1.0));
   		
   		// Compltely move over the Cheval
   		if(isShort){
   			addSequential(new DriveForward(0.5), 2.0);
   		}
   		else{
   			addSequential(new DriveForward(0.5), 4.0);
   		}
   		
   		addSequential(new DriveRotateAngle(rotateAngle, rotateSpeed, false));
   		
   		if(isBallAlign && isBallShoot){
   			addSequential(new BallAlignShoot());
   		}
   		else if(isBallAlign){
   			addSequential(new BallAlign());
   		}
    }
}
