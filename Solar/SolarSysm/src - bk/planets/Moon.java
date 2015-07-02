package planets;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import math.Vector3;

public class Moon extends SpaceObject
{
	public Moon()
	{
		super();
	}
	
	public void colorObject()
	{
		GL11.glColor3d(190, 190, 190);
	}
	
	
}