package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Intake extends Subsystem {
    
    private CANTalon rollerMotor = new CANTalon(0);
    private CANTalon liftMotor = new CANTalon(11);

    public Intake(){
    	LiveWindow.addActuator("Intake", "Roller", rollerMotor);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new IntakeStop());
    }
    
    public void setSpeed(double speed){
    	rollerMotor.set(speed);
    }
    
    public void log(){
    	
    }
}

