package org.usfirst.frc.team4947.robot.commands;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team4947.robot.OI.XBoxButton;
import org.usfirst.frc.team4947.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveCamera extends Command {

	public class ParticleInfo implements Comparable<ParticleInfo>{
		double Area;
		double ConvexHullArea;
		double AreaRatio;
		double Left;
		double Top;
		double Right;
		double Bottom;
		double Width;
		double Height;
		double AspectRatio;
		
		public int compareTo(ParticleInfo r)
		{
			return (int)(r.ConvexHullArea - this.ConvexHullArea);
		}
	};
	
	private NIVision.Range hueRange = new NIVision.Range(100, 160);
	private NIVision.Range satRange = new NIVision.Range(0, 255);
	private NIVision.Range valRange = new NIVision.Range(80, 255);
	private float areaMin = 200;
	private float areaMax = 2000; 

	private NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	private NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);

	private Image frame;
	private Image binaryFrame;
	
	private double areaRatio = 3;		// AreaRatio is  240 / 80 = 3
	private double aspectRatio = 1.667;	// AspectRation is 20 x 12, so 1.667

	private int numParticles;
	private int imageWidth = 320;
	private int imageHeight = 240;
    int imageCenterX = imageWidth / 2;
    int imageCenterY = imageHeight / 2;
    boolean targetFound = false;
    double targetAngle;
    double rotateValue = 0;
	
    public DriveCamera() {
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.camera.setExposureManual(0);
		Robot.camera.setBrightness(0);
	
    	frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_CONVEX_HULL_AREA, areaMin, areaMax, 0, 0);
		
		targetFound = false;

		// Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Hue min", hueRange.minValue);
		SmartDashboard.putNumber("Hue max", hueRange.maxValue);
		SmartDashboard.putNumber("Sat min", satRange.minValue);
		SmartDashboard.putNumber("Sat max", satRange.maxValue);
		SmartDashboard.putNumber("Val min", valRange.minValue);
		SmartDashboard.putNumber("Val max", valRange.maxValue);
		SmartDashboard.putNumber("Area min", areaMin);
		SmartDashboard.putNumber("Area max", areaMax);
		SmartDashboard.putBoolean("BinaryImage", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	Robot.camera.getImage(frame);
		
		boolean binaryImage = SmartDashboard.getBoolean("BinaryImage");
		if(binaryImage){
			CameraServer.getInstance().setImage(binaryFrame);
			
			// Update threshold values from SmartDashboard. 
			// For performance reasons it is recommended to remove this after calibration is finished.
			hueRange.minValue = (int)SmartDashboard.getNumber("Hue min", hueRange.minValue);
			hueRange.maxValue = (int)SmartDashboard.getNumber("Hue max", hueRange.maxValue);
			satRange.minValue = (int)SmartDashboard.getNumber("Sat min", satRange.minValue);
			satRange.maxValue = (int)SmartDashboard.getNumber("Sat max", satRange.maxValue);
			valRange.minValue = (int)SmartDashboard.getNumber("Val min", valRange.minValue);
			valRange.maxValue = (int)SmartDashboard.getNumber("Val max", valRange.maxValue);
			areaMin = (float)SmartDashboard.getNumber("Area min", areaMin);
			areaMax = (float)SmartDashboard.getNumber("Area max", areaMax);
			criteria[0].lower = areaMin;
			criteria[0].upper = areaMax;
		}
		else{
			CameraServer.getInstance().setImage(frame);
		}
		
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, hueRange, satRange, valRange);
		
		if(targetFound && !Robot.testMode){
			Robot.driveTrain.arcadeDrive(rotateValue, 0);
    	}
		else{
			targetFound = false;
			
			//Send particle count to dashboard
			numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Masked particles", numParticles);
	
			//filter out small particles
			NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
			
			//Send particle count after filtering to dashboard
			numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Filtered particles", numParticles);
	
			if(numParticles > 0)
			{
				//Measure particles and sort by particle size
				List<ParticleInfo> particles = new ArrayList<ParticleInfo>();
				for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
				{
					ParticleInfo par = new ParticleInfo();
					par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
					par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
					par.AreaRatio = par.ConvexHullArea / par.Area;
					par.Top = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.Left = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.Bottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.Right = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
					par.Width = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
					par.Height = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
					par.AspectRatio = par.Width / par.Height;
					
					// Make sure it looks like a target
					if(Math.abs(par.AreaRatio - areaRatio) < 0.5 && Math.abs(par.AspectRatio - aspectRatio) < 0.5){
						particles.add(par);
					}
				}
				particles.sort(null);
				
				if(particles.size() > 0){
					ParticleInfo bestParticule = particles.get(0);
		
					SmartDashboard.putNumber("Area", bestParticule.Area);
					SmartDashboard.putNumber("ConvexHullArea", bestParticule.ConvexHullArea);
					SmartDashboard.putNumber("TargetWidth", bestParticule.Width);
					SmartDashboard.putNumber("TargetHeight", bestParticule.Height);
					SmartDashboard.putNumber("AreaRatio", bestParticule.AreaRatio);
					SmartDashboard.putNumber("AspectRatio", bestParticule.AspectRatio);
		
					int top = (int)bestParticule.Top;
					int left = (int)bestParticule.Left;
					int height = (int)(bestParticule.Bottom - bestParticule.Top);
					int width = (int)(bestParticule.Right - bestParticule.Left);
		
			        int rectCenterX = left + (width / 2);
			        int rectCenterY = top + (height / 2);
			        
			        int offsetX = rectCenterX - imageCenterX;
			        int offsetY = rectCenterY - imageCenterY;
			        double targetDistance = computeDistance(bestParticule);
			        targetAngle = computeAngle(bestParticule, targetDistance, offsetX);
			        Robot.driveTrain.resetAngle();
			        
			        targetFound = true;

					if(offsetX < 0){
						rotateValue = -0.4;
					}
					else if(offsetX > 0){
						rotateValue = 0.4;
					}
					else{
						rotateValue = 0;
					}
					
					SmartDashboard.putNumber("OffsetX", offsetX);
					SmartDashboard.putNumber("OffsetY", offsetY);
					SmartDashboard.putNumber("TargetDistance", targetDistance);
					SmartDashboard.putNumber("TargetAngle", targetAngle);
					SmartDashboard.putNumber("RotateValue", rotateValue);
				}
			}
    	}
		
		SmartDashboard.putBoolean("TargetFound", targetFound);
    }
    
    private double computeDistance(ParticleInfo partInfo) {
		double viewAngle = 43.0;
		
		double targetWidthPixel = partInfo.Width;
		double targetWidthInch = 20.0;
		
		double imageWidthPixel = imageWidth;
		
		double distance = targetWidthInch * imageWidthPixel / (targetWidthPixel * Math.tan(Math.toRadians(viewAngle)));
    	
		return distance;
	}
    
    private double computeAngle(ParticleInfo partInfo, double distanceInch, double offsetPixel){
		double targetWidthPixel = partInfo.Width;
		double targetWidthInch = 20.0;
		
		// consider distance between camera and robot center line
		double offsetCamInch = -10.0;
		double offsetInch = offsetPixel * targetWidthInch / targetWidthPixel - offsetCamInch;
		
		// offset distanceInch by distance between shooter and center of rotation
		distanceInch = distanceInch + 0.0; 
		double angle = Math.toDegrees(Math.atan(offsetInch / distanceInch));
		
		return angle;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double actualAngle = Robot.driveTrain.getAngle();
    	boolean isOnTarget = false;

    	if(targetFound && !Robot.testMode){
	    	if(rotateValue > 0){
	    		isOnTarget = Math.abs(actualAngle - targetAngle) <= 1 || actualAngle >= targetAngle;
	    	}
	    	else{
	    		isOnTarget = Math.abs(actualAngle - targetAngle) <= 1 || actualAngle <= targetAngle;	
	    	}
    	}
    	
    	return isOnTarget || Robot.oi.getJoystickDriverButton(XBoxButton.Back);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
