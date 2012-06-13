import java.awt.image.*;
import java.io.*;
import java.nio.*;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class LWJGL_Texture {
	
	/* IntBuffer will later hold the texture for use in OpenGL */
	private final IntBuffer texture;

	/* store image width and height internally */
	private final int width, height;
	
	/* OpenGL texture name */
	private int gl_Id;
	
	public LWJGL_Texture(InputStream input ) throws IOException
	{
		/* load the image */
        BufferedImage image = ImageIO.read(input);
        
        /* retrieve image dimensions */
        width = image.getWidth();
        height = image.getHeight();
        
        /* store image data in an integer array */
        final int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        
        /* create the IntBuffer for OpenGL and copy the image data */
        texture = BufferUtils.createIntBuffer(pixels.length);
        texture.put(pixels);
        texture.rewind();
		
	}
	

    public void init() {
    	
    	/* enable 2D textures in openGL */
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        
        /* generate an openGL texture name via an IntBuffer */
        final IntBuffer buffer = BufferUtils.createIntBuffer(1);
        GL11.glGenTextures(buffer);
        gl_Id = buffer.get(0);
        
        /* bind texture name in GL to edit texture settings */
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, gl_Id);
        
        /* set texture filtering to nearest */
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        
        /* apply texture data to texture name */
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, texture);
        
        /* unbind texture */
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
 
    }
 
    
    /* bind the texture */
    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, gl_Id);
    }
 
    
    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
    
	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}
    
}
