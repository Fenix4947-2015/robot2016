package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallAlign extends CommandGroup {
    
    public  BallAlign() {
        addSequential(new DriveTarget());
    }
}
