package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.DriveArcade;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	private AnalogGyro gyro = new AnalogGyro(0);
    
	public Talon rearRightMotor = new Talon(8);
	public Talon frontRightMotor = new Talon(9);
	public Talon rearLeftMotor = new Talon(7);
	public Talon frontLeftMotor = new Talon(6);
	
	public Encoder leftEncoder = new Encoder(1,  2, false, EncodingType.k4X);
	public Encoder rightEncoder = new Encoder(3, 4, true, EncodingType.k4X);
		
	private RobotDrive robotDrive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

    public DriveTrain(){
    	robotDrive.setSafetyEnabled(true);
    	
    	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);
    	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
    	
		// Configure encoders
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);

		// TODO Find the right value for the distance per pulse
		rightEncoder.setDistancePerPulse(0.0785398);
		leftEncoder.setDistancePerPulse(0.0785398);
    	
    	LiveWindow.addActuator("DriveTrain", "RearLeft", rearLeftMotor);
    	LiveWindow.addActuator("DriveTrain", "FrontLeft", frontLeftMotor);
    	LiveWindow.addActuator("DriveTrain", "RearRight", rearRightMotor);
    	LiveWindow.addActuator("DriveTrain", "FrontRight", frontRightMotor);
		LiveWindow.addSensor("DriveTrain", "Right Encoder", rightEncoder);
		LiveWindow.addSensor("DriveTrain", "Left Encoder", leftEncoder);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveArcade());
    }
    
    public void arcadeDrive(double moveValue, double rotateValue){
		robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void tankDrive(double leftValue, double rightValue){
    	robotDrive.tankDrive(leftValue, rightValue);
    }
    
    public void resetAngle(){
    	gyro.reset();
    }
    
    public double getAngle(){
    	return gyro.getAngle();
    }
    
	public Encoder getLeftEncoder() {
		return leftEncoder;
	}

	public Encoder getRightEncoder() {
		return rightEncoder;
	}    
    
    public void log(){
    	SmartDashboard.putNumber("GyroAngle", gyro.getAngle());
    }
}

