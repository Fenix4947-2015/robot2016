package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.commands.DriveArcade;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	private final static double ROTATE_OFFSET = 0.08;
	
	private AnalogGyro gyro = new AnalogGyro(0);
    
	public CANTalon rearRightMotor = new CANTalon(6);		// Encoder
	public CANTalon frontRightMotor = new CANTalon(2);
	public CANTalon rearLeftMotor = new CANTalon(1);		// Encoder
	public CANTalon frontLeftMotor = new CANTalon(8);
		
	private RobotDrive robotDrive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

    public DriveTrain(){
    	robotDrive.setSafetyEnabled(false);
    	//robotDrive.setExpiration(0.250);
    	
    	// TODO See if this works to calibrate the gyro
    	gyro.initGyro();
    	gyro.calibrate();
    	
    	robotDrive.setInvertedMotor(MotorType.kRearRight, true);
    	robotDrive.setInvertedMotor(MotorType.kFrontRight, true);
    	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
    	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);
    	
		// Configure encoders
    	setPosition(0);
		rearLeftMotor.configEncoderCodesPerRev(10);
    	
    	LiveWindow.addActuator("DriveTrain", "RearLeft", rearLeftMotor);
    	LiveWindow.addActuator("DriveTrain", "FrontLeft", frontLeftMotor);
    	LiveWindow.addActuator("DriveTrain", "RearRight", rearRightMotor);
    	LiveWindow.addActuator("DriveTrain", "FrontRight", frontRightMotor);
		LiveWindow.addSensor("Drive Train", "Gyro", gyro);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveArcade());
    }
    
    public void arcadeDrive(double moveValue, double rotateValue){
    	// Adjust the rotateValue to compensate the right motor being too strong
    	if(moveValue > 0.1){
    		rotateValue = rotateValue - ROTATE_OFFSET;
    	}
    	else if(moveValue < 0.1){
    		rotateValue = rotateValue + ROTATE_OFFSET;
    	}
    	
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
    
    public void calibGyro(){
    	gyro.initGyro();
    	gyro.calibrate();
    }
    
	public double getPosition() {
		return -rearLeftMotor.getPosition();
	}
	
	public void setPosition(double position) {
		rearLeftMotor.setPosition(position);
	}

    public void log(){
    	SmartDashboard.putNumber("GyroAngle", gyro.getAngle());
    	SmartDashboard.putNumber("LeftEncoder", getPosition());
    }
}

