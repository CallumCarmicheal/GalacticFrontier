package planets;

import math.Vector3;

public interface ISpaceObject 
{	
	public void setParameters(double rot, double dist, ISpaceObject oCenter, Vector3 rAxis, double size, String name, Vector3 oAxis, double oTilt, double rTilt, double oSpeed);
	
	public void add(ISpaceObject obj);
	
	public void draw();

	public void animate();

	public String getName();
	
	public String getParentName();

	public void drawOrbit();

	public void transform();
	
	public double getRotationSpeed();
	
	public double getRotationAngle();
	
	public Vector3 getRotationAxis();
		
	public Vector3 getOrbitAxis();
	
	public double getOrbitAngle();

	public void toggleOrbit(boolean toggle);
	
	public void colorOrbit();
	
	public void colorObject();
	
	
}
