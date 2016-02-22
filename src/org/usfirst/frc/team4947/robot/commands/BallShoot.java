package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallShoot extends CommandGroup {
    
    public  BallShoot() {
    	addSequential(new ShooterInOut(-1), 5);
    	//addParallel(new ShooterInOut(-1), 3);
        addSequential(new CannonRelease(true));
    }
}
