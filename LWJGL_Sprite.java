import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.opengl.GL11;


public class LWJGL_Sprite {
	
	
	private LWJGL_Texture texture;
	
	private float u1, v1, u2, v2, scalex, scaley;
	
	private float x, y, offsetx, offsety;
	
	public LWJGL_Sprite(String filename) {
		try {
			texture = new LWJGL_Texture(new FileInputStream(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		offsetx = 0;
		offsety = 0;
		scalex = 1.0f;
		scaley = 1.0f;
	}
	
	public void init(){
		this.setU1(0.0f); this.setV1(0.0f);
		this.setU2(1.0f); this.setV2(1.0f);
		texture.init();
	}
	
	public void init(float u1, float v1, float u2, float v2){
		this.setU1(u1); this.setV1(v1);
		this.setU2(u2); this.setV2(v2);
		texture.init();
	}
	
	public void draw(float x, float y) {
		
		this.x = x;
		this.y = y;
		
		texture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(u1, v1); GL11.glVertex2f(x - offsetx                    , y - offsety                                );
		GL11.glTexCoord2f(u1, v2); GL11.glVertex2f(x - offsetx                    , y - offsety + texture.getHeight() * scaley * (v2-v1) );
		GL11.glTexCoord2f(u2, v2); GL11.glVertex2f(x - offsetx + texture.getWidth() * scalex * (u2-u1), y - offsety + texture.getHeight() * scaley * (v2-v1) );
		GL11.glTexCoord2f(u2, v1); GL11.glVertex2f(x - offsetx + texture.getWidth() * scalex * (u2-u1), y - offsety                                );
		
		GL11.glEnd();
		
		GL11.glPopMatrix();
		
		texture.unbind();
	}
	
	public void draw() {
		
		texture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(u1, v1); GL11.glVertex2f(x - offsetx                    , y - offsety                                );
		GL11.glTexCoord2f(u1, v2); GL11.glVertex2f(x - offsetx                    , y - offsety + texture.getHeight() * scaley * (v2-v1) );
		GL11.glTexCoord2f(u2, v2); GL11.glVertex2f(x - offsetx + texture.getWidth() * scalex * (u2-u1), y - offsety + texture.getHeight() * scaley * (v2-v1) );
		GL11.glTexCoord2f(u2, v1); GL11.glVertex2f(x - offsetx + texture.getWidth() * scalex * (u2-u1), y - offsety                                );
		
		GL11.glEnd();
		
		GL11.glPopMatrix();
		
		texture.unbind();
	}

	public float getU1() {
		return u1;
	}

	public void setU1(float u) {
		this.u1 = u;
	}

	public float getV1() {
		return v1;
	}

	public void setV1(float v) {
		this.v1 = v;
	}

	public float getU2() {
		return u2;
	}

	public void setU2(float u) {
		this.u2 = u;
	}

	public float getV2() {
		return v2;
	}

	public void setV2(float v) {
		this.v2 = v;
	}

	public float getScaleX() {
		return scalex;
	}

	public void setScaleX(float scalex) {
		this.scalex = scalex;
	}

	public float getScaleY() {
		return scaley;
	}

	public void setScaleY(float scaley) {
		this.scaley = scaley;
	}

	public float getOffsetX() {
		return offsetx;
	}

	public void setOffsetX(float offsetx) {
		this.offsetx = offsetx;
	}

	public float getOffsetY() {
		return offsety;
	}

	public void setOffsetY(float offsety) {
		this.offsety = offsety;
	}
	
	public int getWidth(){
		return texture.getWidth();
	}
	
	public int getHeight(){
		return texture.getHeight();
	}
}
