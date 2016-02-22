package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.ClimberStop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Climber extends PIDSubsystem {
	
	public final static int POSITION_IN = 100;
	public final static int POSITION_OUT = 200000;
	public final static int POSITION_CLIMB = 100000;
	
	private CANTalon climberMotor = new CANTalon(5);
	
	public Climber(){
		super(4, 0, 0);
		
		setAbsoluteTolerance(100);
		setPosition(0);
		
		LiveWindow.addActuator("Climber", "Climber", climberMotor);
		LiveWindow.addActuator("Climber", "PID", getPIDController());
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ClimberStop());
    }

    public void setSpeed(double speed){
    	climberMotor.set(speed);
    }
    
	public double getPosition() {
		return climberMotor.getPosition();
	}
	
	public void setPosition(double position) {
		climberMotor.setPosition(position);
	}

	@Override
	protected double returnPIDInput() {
		return climberMotor.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		climberMotor.set(output);
	}
	
    public void log(){
    	
    }
    
    // Workaround to get the subsystem to show on the SmartDashBoard
    public String getSmartDashboardType() {
        return "Subsystem";
    }
    
}

