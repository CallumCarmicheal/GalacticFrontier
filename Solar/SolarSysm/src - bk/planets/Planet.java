package planets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import math.Color;
import math.Vector3;

public class Planet extends SpaceObject
{
	private final Color PLANET_COLOR = new Color(0, 0, 255);
	public Planet()
	{
		super();
	}
	
	public void colorObject() {
		GL11.glColor3d(0, 0, 255);
	}
}
