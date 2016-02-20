package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.LifterStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lifter extends PIDSubsystem {
    
	public final static double POSITION_LOW = 0.198;
	public final static double POSITION_HIGH = 3.28;
	public final static double POSITION_CLIMB = 2.5;
	
    private CANTalon lifterMotor = new CANTalon(4);

    public Lifter(){
    	super(4, 0, 0);
    	
    	lifterMotor.setInverted(true);
    	    	
    	setAbsoluteTolerance(10);
    	
    	LiveWindow.addActuator("Lifter", "Lifter", lifterMotor);
        LiveWindow.addActuator("Lifter", "PID", getPIDController());
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new LifterStop());
    }
    
    public void setLifterSpeed(double speed){
    	if(speed > 0.4){
    		speed = 0.4;
    	}
    	else if(speed < -0.4){
    		speed = -0.4;
    	}
    	
    	lifterMotor.set(speed);
    }

    public void log(){
    	SmartDashboard.putNumber("Lifter Encoder", lifterMotor.getPosition());
    }

	@Override
	protected double returnPIDInput() {
		return lifterMotor.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		lifterMotor.set(output);
	}
}

