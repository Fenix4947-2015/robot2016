package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.DriveArcade;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	private AnalogGyro gyro = new AnalogGyro(0);
    
	private CANTalon rearLeftMotor = new CANTalon(1);
	private CANTalon frontLeftMotor = new CANTalon(0);
	private CANTalon rearRightMotor = new CANTalon(2);
	private CANTalon frontRightMotor = new CANTalon(3);
    
    RobotDrive robotDrive = new RobotDrive(rearLeftMotor, frontLeftMotor, rearRightMotor, frontRightMotor);

    public DriveTrain(){
    	robotDrive.setSafetyEnabled(true);
    	
    	LiveWindow.addActuator("DriveTrain", "RearLeft", rearLeftMotor);
    	LiveWindow.addActuator("DriveTrain", "FrontLeft", frontLeftMotor);
    	LiveWindow.addActuator("DriveTrain", "RearRight", rearRightMotor);
    	LiveWindow.addActuator("DriveTrain", "FrontRight", frontRightMotor);
    	
		LiveWindow.addSensor("DriveTrain", "Gyro", gyro);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveArcade());
    }
    
    public void arcadeDrive(double moveValue, double rotateValue){
		robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void tankdrive(double leftValue, double rightValue){
    	robotDrive.tankDrive(leftValue, rightValue);
    }
    
    public void resetAngle(){
    	gyro.reset();
    }
    
    public double getAngle(){
    	return gyro.getAngle();
    }
    
    public void log(){
    	// TODO See if LiveWindow.addSensor does the same job as the following line 
    	SmartDashboard.putNumber("GyroAngle", gyro.getAngle());
    }
}

