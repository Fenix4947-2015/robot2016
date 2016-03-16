package org.usfirst.frc.team4947.robot.commands;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team4947.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CameraTarget extends Command {

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
	
	private final static double TOLERANCE = 2.75;
	
	private NIVision.Range hueRange = new NIVision.Range(100, 160);
	private NIVision.Range satRange = new NIVision.Range(0, 255);
	private NIVision.Range valRange = new NIVision.Range(80, 255);
	// For mecanum base test
//	private NIVision.Range hueRange = new NIVision.Range(220, 255);
//	private NIVision.Range satRange = new NIVision.Range(0, 255);
//	private NIVision.Range valRange = new NIVision.Range(80, 255);
	private float areaMin = 200;
	private float areaMax = 5000; 
	private int particleIndex = -1;

	private NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	private NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);

	private Image frame;
	private Image binaryFrame;

	private double areaRatio = 3;		// AreaRatio is  240 / 80 = 3
	private double aspectRatio = 1.667;	// AspectRation is 20 x 12, so 1.42

	private int numParticles;
	private int imageWidth = 320;
	private int imageHeight = 240;
    int imageCenterX = imageWidth / 2;
    int imageCenterY = imageHeight / 2;
	
    public CameraTarget() {
    	requires(Robot.camera);
    	
		// Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Hue min", hueRange.minValue);
		SmartDashboard.putNumber("Hue max", hueRange.maxValue);
		SmartDashboard.putNumber("Sat min", satRange.minValue);
		SmartDashboard.putNumber("Sat max", satRange.maxValue);
		SmartDashboard.putNumber("Val min", valRange.minValue);
		SmartDashboard.putNumber("Val max", valRange.maxValue);
		SmartDashboard.putNumber("Area min", areaMin);
		SmartDashboard.putNumber("Area max", areaMax);
		SmartDashboard.putNumber("Particle index", particleIndex);
		SmartDashboard.putBoolean("BinaryImage", false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.camera.setTargetExposure();
    	
    	frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_CONVEX_HULL_AREA, areaMin, areaMax, 0, 0);
		
		Robot.camera.targetFound = false;
    	Robot.camera.targetAngle = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.camera.targetFound = false;
    	Robot.camera.getCamera().getImage(frame);
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, hueRange, satRange, valRange);
		
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
		
		//Send particle count to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Unfiltered particles", numParticles);

		//filter out small particles
		NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
		
		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);
		particleIndex = (int)SmartDashboard.getNumber("Particle index", particleIndex);
		
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			List<ParticleInfo> particles = new ArrayList<ParticleInfo>();
			for(int i = 0; i < numParticles; i++)
			{
				ParticleInfo par = new ParticleInfo();
				par.Area = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_AREA);
				par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
				par.AreaRatio = par.ConvexHullArea / par.Area;
				par.Top = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.Left = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.Bottom = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				par.Right = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				par.Width = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				par.Height = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				par.AspectRatio = par.Width / par.Height;
				
				if(i == particleIndex){
					SmartDashboard.putNumber("Area", par.Area);
					SmartDashboard.putNumber("ConvexHullArea", par.ConvexHullArea);
					SmartDashboard.putNumber("TargetWidth", par.Width);
					SmartDashboard.putNumber("TargetHeight", par.Height);
					SmartDashboard.putNumber("AreaRatio", par.AreaRatio);
					SmartDashboard.putNumber("AspectRatio", par.AspectRatio);
				}
				
				// Make sure it looks like a target
				if(Math.abs(par.AreaRatio - areaRatio) < TOLERANCE && Math.abs(par.AspectRatio - aspectRatio) < TOLERANCE){
					particles.add(par);
				}
			}
			particles.sort(null);
			SmartDashboard.putNumber("Target particles", particles.size());
			
			if(particles.size() > 0){
				ParticleInfo bestParticule = particles.get(0);
	
				if(particleIndex < 0){
					SmartDashboard.putNumber("Area", bestParticule.Area);
					SmartDashboard.putNumber("ConvexHullArea", bestParticule.ConvexHullArea);
					SmartDashboard.putNumber("TargetWidth", bestParticule.Width);
					SmartDashboard.putNumber("TargetHeight", bestParticule.Height);
					SmartDashboard.putNumber("AreaRatio", bestParticule.AreaRatio);
					SmartDashboard.putNumber("AspectRatio", bestParticule.AspectRatio);
				}
	
				int top = (int)bestParticule.Top;
				int left = (int)bestParticule.Left;
				int height = (int)(bestParticule.Bottom - bestParticule.Top);
				int width = (int)(bestParticule.Right - bestParticule.Left);
	
		        NIVision.Rect rect = new NIVision.Rect(top, left, height, width);
		        NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_INVERT, ShapeMode.SHAPE_RECT, 0.0f);
		        
		        int rectCenterX = left + (width / 2);
		        int rectCenterY = top + (height / 2);
		        
		        int offsetX = rectCenterX - imageCenterX;
		        int offsetY = rectCenterY - imageCenterY;
		        double targetDistance = computeDistance(bestParticule);
		        Robot.camera.targetAngle = computeAngle(bestParticule, targetDistance, offsetX);
		        Robot.driveTrain.resetAngle();
		        
		        Robot.camera.targetFound = true;

				SmartDashboard.putNumber("OffsetX", offsetX);
				SmartDashboard.putNumber("OffsetY", offsetY);
				SmartDashboard.putNumber("TargetDistance", targetDistance);
				SmartDashboard.putNumber("TargetAngle", Robot.camera.targetAngle);
			}
    	}
		
		if(!binaryImage){
			CameraServer.getInstance().setImage(frame);
		}
		
		if(Robot.camera.targetFound){
			Robot.oi.setJoystickDriverRumble(RumbleType.kLeftRumble, 0.5f);
			Robot.oi.setJoystickDriverRumble(RumbleType.kRightRumble, 0.5f);
		}
		else{
			Robot.oi.setJoystickDriverRumble(RumbleType.kLeftRumble, 0.0f);
			Robot.oi.setJoystickDriverRumble(RumbleType.kRightRumble, 0.0f);
		}
		
		SmartDashboard.putBoolean("TargetFound", Robot.camera.targetFound);
    }
    
    private double computeDistance(ParticleInfo partInfo) {
		//double viewAngle = 41.8;
    	//double viewAngle = 34.0;
    	double viewAngle = 32.0;
    	
		//double targetWidthPixel = partInfo.Width;
		double targetHeightPixel = partInfo.Height;
		double targetWidthInch = 20.0;
		double targetHeightInch = 12.0;
		
		//double imageWidthPixel = imageWidth;
		double imageHeightPixel = imageHeight;
		
		//double distance = targetWidthInch * imageWidthPixel / (targetWidthPixel * Math.tan(Math.toRadians(viewAngle)));
		double distance = targetHeightInch * imageHeightPixel / (targetHeightPixel * Math.tan(Math.toRadians(viewAngle)));

		// We adjust the distance to the middle of the target according to the actual skew angle of the target
    	// At 0 degree the ratio is 1.66 and at 45 degree the ratio is 1.18 So m = 0.010666
    	double targetAngle = (partInfo.AspectRatio - aspectRatio) / -0.010666;
    	double distanceAdjust = Math.sin(Math.toRadians(targetAngle)) * targetWidthInch / 2;
    	
    	//SmartDashboard.putNumber("TargetDistanceAdjust", distanceAdjust);
    	
    	distance = distance + distanceAdjust;
    	
    	// The target is 7'1'' from the floor so we use Pythagore to compute the projected distance on the floor
    	double targetToFloorInch = 73.0;
    	//double targetToFloorInch = 33.0;
    	distance = Math.sqrt(Math.pow(distance,  2) - Math.pow(targetToFloorInch, 2));
		return distance;
	}
    
    private double computeAngle(ParticleInfo partInfo, double distanceInch, double offsetPixel){
		double targetWidthPixel = partInfo.Width;
		double targetWidthInch = 20.0;
		
		// consider distance between camera and robot center line
		double offsetCamInch = 6.25;
		//double offsetCamInch = 7.5;
		double offsetInch = offsetPixel * targetWidthInch / targetWidthPixel - offsetCamInch;
		
		// offset distanceInch by distance between shooter and center of rotation
		distanceInch = distanceInch + 9.0; 
		//distanceInch = distanceInch + 11.0;
		double angle = Math.toDegrees(Math.atan(offsetInch / distanceInch));
		
		return angle;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.testMode && Robot.camera.targetFound;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.oi.setJoystickDriverRumble(RumbleType.kLeftRumble, 0.0f);
		Robot.oi.setJoystickDriverRumble(RumbleType.kRightRumble, 0.0f);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
