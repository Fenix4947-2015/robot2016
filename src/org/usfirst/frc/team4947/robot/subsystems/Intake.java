package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Intake extends Subsystem {
    
    private CANTalon rollerMotor = new CANTalon(3);

    public Intake(){
    	rollerMotor.setInverted(true);
    	
    	LiveWindow.addActuator("Intake", "Roller", rollerMotor);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new IntakeStop());
    }
    
    public void setRollerSpeed(double speed){
    	rollerMotor.set(speed);
    }

    public void log(){
    }
}

