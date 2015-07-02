package planets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import GL.GLUT;
import math.Vector3;

public class SpaceObject implements ISpaceObject 
{
	protected double myRotationAngle;
	protected double myRotationSpeed;
	protected double myDistance;
	protected ISpaceObject myOrbitCenter;
	protected Vector3 myRotationAxis;
	protected double mySize;
	protected ArrayList<ISpaceObject> mySatellites;
	protected String myName;
	protected Vector3 myOrbitAxis;
	protected double myOrbitTilt;
	protected double myRotationTilt;
	protected double myOrbitAngle;
	protected double myOrbitSpeed;
	protected boolean myShowOrbit;
	
	public SpaceObject()
	{
		myRotationAngle = 0;
		myRotationSpeed = 0;
		myDistance = 0;
		myOrbitCenter = null;
		myRotationAxis = null;
		mySize = 0;
		mySatellites = new ArrayList<ISpaceObject>();
		myName = null;
		myOrbitAxis = null;
		myOrbitTilt = 0;
		myRotationTilt = 0;
		myOrbitAngle = 0;
		myOrbitSpeed = 0;
		myShowOrbit = true;
	}
		
	public void setParameters(double rot, double dist, ISpaceObject oCenter, Vector3 rAxis, double size, String name, Vector3 oAxis, double oTilt, double rTilt, double oSpeed)
	{
		myRotationSpeed = rot;
		myDistance = dist;
		myOrbitCenter = oCenter;
		myRotationAxis = rAxis;
		mySize = size;
		mySatellites = new ArrayList<ISpaceObject>();
		myName = name;
		myOrbitAxis = oAxis;
		myOrbitTilt = oTilt;
		myRotationTilt = rTilt;
		myOrbitSpeed = oSpeed;
	}
	
	public void add(ISpaceObject obj)
	{
		mySatellites.add(obj);
	}

	public void draw() 
	{	
		if(myShowOrbit)
		{
			for(ISpaceObject sat: mySatellites)
			{
				GL11.glPushMatrix();
				
				sat.drawOrbit();
				GL11.glPopMatrix();
			}
		}
		
		GL11.glPushMatrix();
		transform();
		GL11.glRotated(myRotationAngle, myRotationAxis.x, myRotationAxis.y, myRotationAxis.z);
		GLUT.WireSpearD(mySize, 20, 20);	//radius, slices, stacks
		GL11.glPopMatrix();
		
		for(ISpaceObject sat: mySatellites)
		{
			GL11.glPushMatrix();
			sat.draw();
			GL11.glPopMatrix();
		}
	}
	
	public void drawOrbit()
	{
		myOrbitCenter.transform();
		colorOrbit();
		GL11.glRotated(myOrbitTilt, myOrbitAxis.x, myOrbitAxis.y, myOrbitAxis.z);
		GL11.glTranslated(-myDistance, 0, 0);
		
		
		
		
		// TODO: GLUT
		//GLUT.glutWireTorus(myDistance, myDistance, 100, 1);
		
	}
	
	public void transform()
	{
		myOrbitCenter.transform();
		GL11.glRotated(myOrbitTilt, myOrbitAxis.x, myOrbitAxis.y, myOrbitAxis.z);
		GL11.glRotated(myOrbitAngle, 0, 1, 0);
		GL11.glTranslated(myDistance, 0, 0);
		colorObject();
	}
	
	public void animate()
	{
		myRotationAngle += myRotationSpeed;
		myOrbitAngle += myOrbitSpeed;
		for(ISpaceObject sat: mySatellites)
		{
			sat.animate();
		}
	}

	public String getName() 
	{
		return myName;
	}

	public String getParentName() 
	{
		return myOrbitCenter.getName();
	}

	public Vector3 getOrbitAxis() 
	{
		return myOrbitAxis;
	}

	public Vector3 getRotationAxis() 
	{
		return myRotationAxis;
	}

	public double getRotationSpeed() 
	{
		return myRotationSpeed;
	}
	
	public double getRotationAngle()
	{
		return myRotationAngle;
	}

	public double getOrbitAngle() 
	{
		return myOrbitAngle;
	}

	public void toggleOrbit(boolean toggle) 
	{
		myShowOrbit = toggle;
	}

	public void colorObject() 
	{
		
	}

	public void colorOrbit() 
	{
		GL11.glColor3d(255, 255, 255);
	}
}
