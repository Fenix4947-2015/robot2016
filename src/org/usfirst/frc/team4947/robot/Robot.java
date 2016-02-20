
package org.usfirst.frc.team4947.robot;

import org.usfirst.frc.team4947.robot.commands.DoNothing;
import org.usfirst.frc.team4947.robot.subsystems.Climber;
import org.usfirst.frc.team4947.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4947.robot.subsystems.Intake;
import org.usfirst.frc.team4947.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;
    SendableChooser autonomousChooser = new SendableChooser();

    public static USBCamera camera;
    
    public static OI oi;

    public static DriveTrain driveTrain;
    public static Intake intake;
    public static Shooter shooter;
    public static Climber climber;
    
    public static boolean testMode = false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	driveTrain = new DriveTrain();
    	intake = new Intake();
    	shooter = new Shooter();
    	climber = new Climber();
    	
    	oi = new OI();
    	
        camera = new USBCamera("cam0");
        camera.startCapture();
    	
        // Instantiate the command used for the autonomous period
        autonomousChooser.addDefault("DoNothing", new DoNothing());
        //autonomousChooser.addObject("AutonomousSimple", new AutonomousSimple());
        SmartDashboard.putData("AutoMode", autonomousChooser);
       
        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(intake);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(climber);
        
		SmartDashboard.putBoolean("TestMode", false);
    }
    
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
    	autonomousCommand = (Command)autonomousChooser.getSelected();
    	
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        log();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        log();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    private void log() {
    	testMode = SmartDashboard.getBoolean("TestMode", false);
    	
        driveTrain.log();
        intake.log();
        shooter.log();
        climber.log();
    }
}
