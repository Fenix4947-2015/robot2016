package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallPickUp extends CommandGroup {
    
    public  BallPickUp() {
    	addParallel(new LifterDown(1.0));
    	addParallel(new CannonPosition(false));
    	addParallel(new IntakeInOut(0.75));
    	addParallel(new ShooterInOut(0.75));
    }
}
