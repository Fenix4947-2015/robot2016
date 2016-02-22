package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallPickUp extends CommandGroup {
    
    public  BallPickUp() {
    	addParallel(new CannonRelease(false));
    	addParallel(new IntakeInOut(0.5));
    	//addParallel(new LifterPosition(Lifter.POSITION_LOW));
    	addParallel(new ShooterInOut(0.5));
    }
}
