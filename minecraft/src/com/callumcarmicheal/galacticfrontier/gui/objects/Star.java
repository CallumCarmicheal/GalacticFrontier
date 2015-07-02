package com.callumcarmicheal.galacticfrontier.gui.objects;

import java.util.Random;

import com.callumcarmicheal.galacticfrontier.GalacticFrontierMOD;
import com.callumcarmicheal.galacticfrontier.gui.*;
import com.callumcarmicheal.galacticfrontier.render.Util;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;



public class Star extends RenderObject {

	float rotation;
	double size;
	Random random = new Random();
	
	
	public Star() {
		int min = 0;
		int max = 6;
		setX(min + (max - min) * random.nextFloat());
		setY(min + (max - min) * random.nextFloat());
		setZ(min + (max - min) * random.nextFloat());
		
		size = (0.02 + (0.09 - 0.02) * random.nextDouble());
		
		Init();
	}

	public Star(float x, float y) {
		super(x, y);
	}

	public Star(int x, int y) {
		super(x, y);
	}

	@Override
	public void Update() {
		rotation = rotation + .5F;
		
		Render();
	}

	@Override
	protected void Render() {
		Util.make3D();

		float x = getX();
		float y = getY();
		float z = getZ();
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		//GL11.glLoadIdentity();
		GLU.gluPerspective(60.0F, (float) Display.getWidth() / (float) Display.getHeight(), 0.01f, 100.0f);
		GLU.gluLookAt(-5, 5, -5, 0, 0, 0, 0, 1, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glLoadIdentity();
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glClearColor(0, 0, 0, 255);
		GL11.glTranslatef(x, y, z);

		
		GL11.glPushMatrix(); {
			
			Util.drawSphere(255, 25, 255, 6, size);
			//Util.drawRing(390);
			
		} GL11.glPopMatrix();
		
		Util.make2D();
	}

	@Override
	public void Init() {
		GalacticFrontierMOD.getLog().INFO("STARTED STAR SYSTEM");
	}

	@Override
	public void Pop() {

	}

}
