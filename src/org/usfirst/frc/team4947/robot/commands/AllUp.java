package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AllUp extends CommandGroup {
	public AllUp() {
		addParallel(new CannonPosition(true));
		addParallel(new LifterUpDown(1.0));
    }
}
