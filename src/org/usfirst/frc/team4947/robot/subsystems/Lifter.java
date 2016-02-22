package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.LifterManual;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lifter extends PIDSubsystem {
    
	private final static double MAX_SPEED = 0.2;
	
	public final static int POSITION_HIGH = 0;
	public final static int POSITION_LOW = 580000;
	public final static int POSITION_CLIMB = 400000;
	
    private CANTalon lifterMotor = new CANTalon(4);

    public Lifter(){
    	super(4, 0, 0);
    	
    	lifterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	setAbsoluteTolerance(1000);
    	setPosition(0);    	
    	
    	LiveWindow.addActuator("Lifter", "Lifter", lifterMotor);
        LiveWindow.addActuator("Lifter", "PID", getPIDController());
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new LifterManual());
    }
    
    public void setLifterSpeed(double speed){
    	speed = limitSpeed(speed);

    	lifterMotor.set(speed);
    }
    
	public double getPosition() {
		return lifterMotor.getPosition();
	}
	
	public void setPosition(double position) {
		lifterMotor.setPosition(position);
	}

	@Override
	protected double returnPIDInput() {
		return lifterMotor.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		output = limitSpeed(output);
		lifterMotor.set(output);
	}
	
	private double limitSpeed(double speed){
    	if(speed > MAX_SPEED){
    		speed = MAX_SPEED;
    	}
    	else if(speed < -MAX_SPEED){
    		speed = -MAX_SPEED;
    	}
    	
    	return speed;
	}
	
    public void log(){
    	SmartDashboard.putNumber("Lifter Encoder", getPosition());
    }
}

