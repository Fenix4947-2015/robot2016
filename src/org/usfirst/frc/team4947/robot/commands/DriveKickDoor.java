package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveKickDoor extends CommandGroup {
    
    public  DriveKickDoor() {
        addSequential(new DriveRotateAngle(-25, 0.5));
        addSequential(new DriveRotateAngle(25, 0.5));
    }
}
