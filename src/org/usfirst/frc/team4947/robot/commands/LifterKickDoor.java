package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LifterKickDoor extends CommandGroup {
    
    public  LifterKickDoor() {
        addSequential(new LifterUpDown(-1.0));
        addSequential(new LifterUpDown(1.0), 0.25);
        addSequential(new LifterUpDown(-1.0));
        addSequential(new LifterUpDown(1.0), 0.25);
        addSequential(new LifterUpDown(-1.0));
        addSequential(new LifterUpDown(1.0), 0.25);
        addSequential(new LifterUpDown(-1.0));        
    }
}
