package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveKickDoor extends CommandGroup {
    
    public  DriveKickDoor() {
        addSequential(new DriveRotate(25, 0.75));
        addSequential(new DriveRotate(-25, 0.75));
    }
}
