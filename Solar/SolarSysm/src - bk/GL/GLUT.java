package GL;

import org.lwjgl.opengl.GL11;

public class GLUT {
	
	public static void WireSpearF(float r, int nParal, int nMerid){
	    float x,y,z,i,j;
	    for (j=0;j<Math.PI; j+=Math.PI/(nParal+1)){
	        GL11.glBegin(GL11.GL_LINE_LOOP);
	        y=(float) (r*Math.cos(j));
	        for(i=0; i<2*Math.PI; i+=Math.PI/60){
	            x=(float) (r*Math.cos(i)*Math.sin(j));
	            z=(float) (r*Math.sin(i)*Math.sin(j));
	            GL11.glVertex3f(x,y,z);
	        }
	        GL11.glEnd();
	    }

	    for(j=0; j<Math.PI; j+=Math.PI/nMerid){
	    	GL11.glBegin(GL11.GL_LINE_LOOP);
	        for(i=0; i<2*Math.PI; i+=Math.PI/60){
	            x=(float) (r*Math.sin(i)*Math.cos(j));
	            y=(float) (r*Math.cos(i));
	            z=(float) (r*Math.sin(j)*Math.sin(i));
	            GL11.glVertex3f(x,y,z);
	        }
	        GL11.glEnd();
	    }
	}
	
	public static void WireSpearD(double r, int nParal, int nMerid){
		WireSpearF((float)r, nParal, nMerid);
	}
}
