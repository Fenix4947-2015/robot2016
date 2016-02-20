package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Intake extends PIDSubsystem {
    
	public final static double POSITION_LOW = 0.198;
	public final static double POSITION_HIGH = 3.28;
	public final static double POSITION_CLIMB = 2.5;
	
    private CANTalon rollerMotor = new CANTalon(3);
    private CANTalon lifterMotor = new CANTalon(4);
    //private AnalogPotentiometer liftPot = new AnalogPotentiometer(1);

    public Intake(){
    	super(4, 0, 0);
    	
    	lifterMotor.setInverted(true);
    	    	
    	setAbsoluteTolerance(0.002);
    	
    	LiveWindow.addActuator("Intake", "Roller", rollerMotor);
    	LiveWindow.addActuator("Intake", "Lifter", lifterMotor);
        LiveWindow.addActuator("Intake", "PID", getPIDController());
        
        //LiveWindow.addSensor("Intake", "Pot", liftPot);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new IntakeStop());
    }
    
    public void setRollerSpeed(double speed){
    	rollerMotor.set(speed);
    }
    
    public void setLifterSpeed(double speed){
    	lifterMotor.set(speed);
    }

    public void log(){
    	//SmartDashboard.putData("Lift Pot", liftPot);
    }

	@Override
	protected double returnPIDInput() {
		return lifterMotor.get();
		//return liftPot.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		lifterMotor.set(output);
	}
}

