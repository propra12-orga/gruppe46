import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.opengl.GL11;


public class LWJGL_Font {
	
	private LWJGL_Texture texture;
	
	private float scale = 1.0f;
	
	public LWJGL_Font(final String filename) throws IOException {
		texture = new LWJGL_Texture(new FileInputStream(filename));
		texture.init();
	}
	
	public void print(int x, int y, String text){
		texture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glBegin(GL11.GL_QUADS);
		for(int i = 0; i < text.length();i++) {
			char c = text.charAt(i);
			float u = getCharU(c);
			float v = getCharV(c);
			GL11.glTexCoord2f(u,v);                      GL11.glVertex2f(x + i * 18 *scale,y);
			GL11.glTexCoord2f(u,v+1.0f/8.0f);            GL11.glVertex2f(x + i * 18 *scale,y + 32*scale);
			GL11.glTexCoord2f(u+1.0f/14.0f,v+1.0f/8.0f); GL11.glVertex2f(x + (i+1) * 18 *scale,y + 32*scale);
			GL11.glTexCoord2f(u+1.0f/14.0f,v);           GL11.glVertex2f(x + (i+1) * 18 *scale,y);
			
		}
		
		GL11.glEnd();
		
		GL11.glPopMatrix();
		
		texture.unbind();
	}
	
	
	private float getCharU(char C){
		
		if((C<32)||(C>126)) return 0.0f;
		
		return (((int)C-32)%14)/14.0f;
	}
	
	private float getCharV(char C){
		
		if((C<32)||(C>126)) return 0.0f;
		
		return (float)(((int)C-32)/14)/8.0f;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
