package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AllDown extends CommandGroup {
	public AllDown() {
		addParallel(new CannonPosition(false));
		addParallel(new LifterUpDown(-1.0));
    }
}
