package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Intake extends Subsystem {
    
    private CANTalon rollerMotor = new CANTalon(3);

    public Intake(){
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

