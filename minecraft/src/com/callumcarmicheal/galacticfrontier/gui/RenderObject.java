package com.callumcarmicheal.galacticfrontier.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public abstract class RenderObject {

	float x = 0;
	float y = 0;
	float z = 0;

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getZ() {
		return this.z;
	}

	public void setX(float X) {
		this.x = X;
	}

	public void setY(float Y) {
		this.y = Y;
	}

	public void setZ(float Z) {
		this.z = Z;
	}

	public RenderObject() {
		Init();
	}

	public RenderObject(float x, float y) {
		this.x = y;
		this.y = y;

		Init();
	}

	public RenderObject(int x, int y) {
		this.x = (float) x;
		this.y = (float) y;

		Init();
	}

	public void Update() {
		Render();
	}

	protected abstract void Render();

	public abstract void Init();

	public abstract void Pop();

}
