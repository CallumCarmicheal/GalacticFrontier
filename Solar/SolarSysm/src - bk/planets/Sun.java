package planets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import math.Color;
import math.Vector3;


public class Sun extends SpaceObject
{
	private final Color SUN_COLOR = new Color(255, 255, 0);
	public Sun()
	{
		super();
	}
	
	public void drawOrbit()
	{
		//no axis
	}
	
	public void transform()
	{
		GL11.glRotated(myOrbitTilt, 1, 0, 0);
		GL11.glRotated(myOrbitAngle, myOrbitAxis.x, myOrbitAxis.y, myOrbitAxis.z);
		GL11.glTranslated(myDistance, 0, 0);
		GL11.glColor3d(SUN_COLOR.r, SUN_COLOR.g, SUN_COLOR.b);
	}
	
	public void animate()
	{
		myRotationAngle += myRotationSpeed;
		for(ISpaceObject sat: mySatellites)
		{
			sat.animate();
		}
	}

	public String getParentName() 
	{
		return null;
	}
	
	public void colorObject()
	{
		GL11.glColor3d(255, 255, 0);
	}
}