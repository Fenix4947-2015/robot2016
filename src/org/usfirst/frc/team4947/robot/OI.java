package org.usfirst.frc.team4947.robot;

import org.usfirst.frc.team4947.robot.commands.BallAlign;
import org.usfirst.frc.team4947.robot.commands.BallAlignShoot;
import org.usfirst.frc.team4947.robot.commands.BallPickUp;
import org.usfirst.frc.team4947.robot.commands.BallShoot;
import org.usfirst.frc.team4947.robot.commands.CameraExposureDefault;
import org.usfirst.frc.team4947.robot.commands.CameraExposureTarget;
import org.usfirst.frc.team4947.robot.commands.CameraTarget;
import org.usfirst.frc.team4947.robot.commands.CannonPosition;
import org.usfirst.frc.team4947.robot.commands.CannonRelease;
import org.usfirst.frc.team4947.robot.commands.ClimberInOut;
import org.usfirst.frc.team4947.robot.commands.ClimberManual;
import org.usfirst.frc.team4947.robot.commands.ClimberPosition;
import org.usfirst.frc.team4947.robot.commands.ClimberResetEncoder;
import org.usfirst.frc.team4947.robot.commands.ClimberStop;
import org.usfirst.frc.team4947.robot.commands.DriveArcade;
import org.usfirst.frc.team4947.robot.commands.DriveCalibGyro;
import org.usfirst.frc.team4947.robot.commands.DriveDistance;
import org.usfirst.frc.team4947.robot.commands.DriveForward;
import org.usfirst.frc.team4947.robot.commands.DriveKickDoor;
import org.usfirst.frc.team4947.robot.commands.DriveResetEncoder;
import org.usfirst.frc.team4947.robot.commands.DriveRotate;
import org.usfirst.frc.team4947.robot.commands.DriveRotateAngle;
import org.usfirst.frc.team4947.robot.commands.DriveStop;
import org.usfirst.frc.team4947.robot.commands.DriveTank;
import org.usfirst.frc.team4947.robot.commands.DriveTarget;
import org.usfirst.frc.team4947.robot.commands.IntakeInOut;
import org.usfirst.frc.team4947.robot.commands.IntakeManual;
import org.usfirst.frc.team4947.robot.commands.IntakeStop;
import org.usfirst.frc.team4947.robot.commands.LifterKickDoor;
import org.usfirst.frc.team4947.robot.commands.LifterManual;
import org.usfirst.frc.team4947.robot.commands.LifterResetEncoder;
import org.usfirst.frc.team4947.robot.commands.LifterStop;
import org.usfirst.frc.team4947.robot.commands.LifterUpDown;
import org.usfirst.frc.team4947.robot.commands.ShooterEnableCompressor;
import org.usfirst.frc.team4947.robot.commands.ShooterInOut;
import org.usfirst.frc.team4947.robot.commands.ShooterManual;
import org.usfirst.frc.team4947.robot.commands.ShooterStart;
import org.usfirst.frc.team4947.robot.commands.ShooterStop;
import org.usfirst.frc.team4947.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public enum XBoxAxis{
		LeftStickX(0),
		LeftStickY(1),
		LeftTrigger(2),
		RightTrigger(3),
		RightStickX(4),
		RightStickY(5);		

		private int value;
		XBoxAxis(int value){
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public enum XBoxButton{
		A(1),
		B(2),
		X(3),
		Y(4),
		LB(5),
		RB(6),
		Back(7),
		Start(8),
		LeftStick(9),
		RightStick(10);

		private int value;
		XBoxButton(int value){
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}	
	
    private Joystick joystickDriver = new Joystick(0);
    private Joystick joystickHelper = new Joystick(1);

    @SuppressWarnings("unused")
	public OI() {
        // Create all required button in case we need them
        JoystickButton driverA = new JoystickButton(joystickDriver, XBoxButton.A.getValue());
        JoystickButton driverB = new JoystickButton(joystickDriver, XBoxButton.B.getValue());
        JoystickButton driverX = new JoystickButton(joystickDriver, XBoxButton.X.getValue());
        JoystickButton driverY = new JoystickButton(joystickDriver, XBoxButton.Y.getValue());
        JoystickButton driverLB = new JoystickButton(joystickDriver, XBoxButton.LB.getValue());
        JoystickButton driverRB = new JoystickButton(joystickDriver, XBoxButton.RB.getValue());
        JoystickButton driverBack = new JoystickButton(joystickDriver, XBoxButton.Back.getValue());
        JoystickButton driverStart = new JoystickButton(joystickDriver, XBoxButton.Start.getValue());
        JoystickButton driverLeftStick = new JoystickButton(joystickDriver, XBoxButton.LeftStick.getValue());
        JoystickButton driverRightStick = new JoystickButton(joystickDriver, XBoxButton.RightStick.getValue());
        
        JoystickButton helperA = new JoystickButton(joystickHelper, XBoxButton.A.getValue());
        JoystickButton helperB = new JoystickButton(joystickHelper, XBoxButton.B.getValue());
        JoystickButton helperX = new JoystickButton(joystickHelper, XBoxButton.X.getValue());
        JoystickButton helperY = new JoystickButton(joystickHelper, XBoxButton.Y.getValue());
        JoystickButton helperLB = new JoystickButton(joystickHelper, XBoxButton.LB.getValue());
        JoystickButton helperRB = new JoystickButton(joystickHelper, XBoxButton.RB.getValue());
        JoystickButton helperBack = new JoystickButton(joystickHelper, XBoxButton.Back.getValue());
        JoystickButton helperStart = new JoystickButton(joystickHelper, XBoxButton.Start.getValue());
        JoystickButton helperLeftStick = new JoystickButton(joystickHelper, XBoxButton.LeftStick.getValue());
        JoystickButton helperRightStick = new JoystickButton(joystickHelper, XBoxButton.RightStick.getValue());

        // TODO Link button state to execute commands
        driverA.whileHeld(new BallPickUp());
        //driverB.whileHeld(new BallShoot());
        //driverX.whenPressed(new BallAlign());
        driverX.whileHeld(new BallAlignShoot());
        driverY.whenPressed(new LifterUpDown(1.0));
        driverB.whenReleased(new CannonRelease(false));
        driverLB.whenPressed(new CannonPosition(false));
        driverRB.whenPressed(new CannonPosition(true));
        
        driverLeftStick.whenPressed(new DriveKickDoor());
        driverRightStick.whenPressed(new LifterKickDoor());
        
        SmartDashboard.putData("BallPickUp", new BallPickUp());
        SmartDashboard.putData("BallShoot", new BallShoot());
        SmartDashboard.putData("BallAlign", new BallAlign());
        SmartDashboard.putData("BallAlignShoot", new BallAlignShoot());
        
        SmartDashboard.putData("CameraExposureDefault", new CameraExposureDefault());
        SmartDashboard.putData("CameraExposureTarget", new CameraExposureTarget());
        SmartDashboard.putData("CameraTarget", new CameraTarget());
        
        SmartDashboard.putData("DriveStop", new DriveStop());
        SmartDashboard.putData("DriveResetEncoder", new DriveResetEncoder());
        SmartDashboard.putData("DriveArcade", new DriveArcade());
        SmartDashboard.putData("DriveTank", new DriveTank());
        SmartDashboard.putData("DriveTarget", new DriveTarget());
        SmartDashboard.putData("DriveForward", new DriveForward(0.5));
        SmartDashboard.putData("DriveBackward", new DriveForward(-0.5));
        SmartDashboard.putData("DriveDistance", new DriveDistance(1));
        SmartDashboard.putData("DriveRotateAngle+25", new DriveRotateAngle(25, 0.5));
        SmartDashboard.putData("DriveRotateAngle-25", new DriveRotateAngle(-25, 0.5));
        SmartDashboard.putData("DriveRotate+0.5", new DriveRotate(0.5, 0.5));
        SmartDashboard.putData("DriveRotate-0.5", new DriveRotate(-0.5, 0.5));
        SmartDashboard.putData("DriveCalibGyro", new DriveCalibGyro());
        SmartDashboard.putData("DriveKickDoor", new DriveKickDoor());
        
        SmartDashboard.putData("IntakeStop", new IntakeStop());
        SmartDashboard.putData("IntakeIn", new IntakeInOut(0.5));
        SmartDashboard.putData("IntakeOut", new IntakeInOut(-0.5));
        SmartDashboard.putData("IntakeManual", new IntakeManual());
        
        SmartDashboard.putData("LifterStop", new LifterStop());
        SmartDashboard.putData("LifterResetEncoder", new LifterResetEncoder());
        SmartDashboard.putData("LifterManual", new LifterManual());
        //SmartDashboard.putData("LifterPositionLow", new LifterPosition(Lifter.POSITION_LOW));
        //SmartDashboard.putData("LifterPositionHigh", new LifterPosition(Lifter.POSITION_HIGH));
        //SmartDashboard.putData("LifterPositionClimb", new LifterPosition(Lifter.POSITION_CLIMB));
        SmartDashboard.putData("LifterUp", new LifterUpDown(1.0));
        SmartDashboard.putData("LifterDown", new LifterUpDown(-1.0));
        SmartDashboard.putData("LifterKickDoor", new LifterKickDoor());
        
        SmartDashboard.putData("ShooterStart", new ShooterStart());
        SmartDashboard.putData("ShooterStop", new ShooterStop());
        SmartDashboard.putData("ShooterIn", new ShooterInOut(0.5));
        SmartDashboard.putData("ShooterOut", new ShooterInOut(-0.5));
        SmartDashboard.putData("ShooterManual", new ShooterManual());
        SmartDashboard.putData("CompressorEnabled", new ShooterEnableCompressor(true));
        SmartDashboard.putData("CompressorDisabled", new ShooterEnableCompressor(false));
        
        SmartDashboard.putData("CannonReleaseOn", new CannonRelease(true));
        SmartDashboard.putData("CannonReleaseOff", new CannonRelease(false));
        SmartDashboard.putData("CannonPositionUp", new CannonPosition(true));
        SmartDashboard.putData("CannonPositionDown", new CannonPosition(false));
        
        SmartDashboard.putData("ClimberStop", new ClimberStop());
        SmartDashboard.putData("ClimberResetEncoder", new ClimberResetEncoder());
        SmartDashboard.putData("ClimberIn", new ClimberInOut(0.5));
        SmartDashboard.putData("ClimberOut", new ClimberInOut(-0.5));
        SmartDashboard.putData("ClimberManual", new ClimberManual());
        SmartDashboard.putData("ClimberPositionIn", new ClimberPosition(Climber.POSITION_IN));
        SmartDashboard.putData("ClimberPositionOut", new ClimberPosition(Climber.POSITION_OUT));
        SmartDashboard.putData("ClimberPositionClimb", new ClimberPosition(Climber.POSITION_CLIMB));
    }
    
    public double getJoystickDriverAxis(XBoxAxis axis) {
        return joystickDriver.getRawAxis(axis.getValue());
    }

    public double getJoystickDriverAxis(XBoxAxis axis, double deadBand) {
    	double axisValue = joystickDriver.getRawAxis(axis.getValue());
    	
    	if(Math.abs(axisValue) <= deadBand){
    		axisValue = 0;
    	}
    	
    	return axisValue;
    }
    
    public void setJoystickDriverRumble(RumbleType rumbleType, float value){
    	joystickDriver.setRumble(rumbleType, value);
    }
    
    public boolean getJoystickDriverButton(XBoxButton button) {
        return joystickDriver.getRawButton(button.getValue());
    }
    
    public double getJoystickHelperAxis(XBoxAxis axis) {
        return joystickHelper.getRawAxis(axis.getValue());
    }

    public double getJoystickHelperAxis(XBoxAxis axis, double deadBand) {
    	double axisValue = joystickHelper.getRawAxis(axis.getValue());
    	
    	if(Math.abs(axisValue) <= deadBand){
    		axisValue = 0;
    	}
    	
    	return axisValue;
    }
    
    public boolean getJoystickHelperButton(XBoxButton button) {
        return joystickHelper.getRawButton(button.getValue());
    }
}

