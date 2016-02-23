package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallShoot extends CommandGroup {
    
    public  BallShoot() {
    	//addSequential(new CannonRelease(false));
    	addSequential(new ShooterStart(), 2);
    	addSequential(new CannonRelease(true));
    	addSequential(new ShooterStart(), 1);
        addSequential(new ShooterStop());
    }
}
