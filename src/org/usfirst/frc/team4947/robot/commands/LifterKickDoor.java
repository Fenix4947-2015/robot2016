package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LifterKickDoor extends CommandGroup {
    
	private final static int NB_KICK = 2;
	
    public  LifterKickDoor() {
    	for(int i = 0; i < NB_KICK; i++){
            addSequential(new LifterUpDown(-1.0));
            addSequential(new LifterUpDown(1.0), 0.25);
    	}

    	addSequential(new LifterUpDown(-1.0));        
    }
}
