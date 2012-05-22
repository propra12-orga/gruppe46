import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Renderer {
	
	private int width, height, fps;
	private float clearR, clearG, clearB, clearA;
	
	public Renderer(int width, int height, int fps) {
		this.width = width;
		this.height = height;
		this.fps = fps;
		setClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	public Renderer(int width, int height) {
		this(width, height, 60);
	}
	
	public Renderer(int fps) {
		this(640, 480, fps);
	}
	
	public Renderer() {
		this(640, 480, 60);
	}
	
	public void initDisplay() {
		/* set up display mode */
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void initDisplay(int width, int height) {
		this.width = width;
		this.height = height;
		
		/* set up display mode */
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	

	public void initGL() {
		
		/* set up 'camera' as Ortho */
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_REPLACE);
        
        /* enable backface culling */
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        /* enable transparency */
        GL11.glEnable (GL11.GL_BLEND); 
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
	}
	
	public void setClearColor(float r, float g, float b, float a) {
		clearR = r;
		clearG = g;
		clearB = b;
		clearA = a;
	}
	
	
	public void clearGL() {
		// Clear The Screen And The Depth Buffer
		GL11.glClearColor(clearR, clearG, clearB, clearA);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void sync() {
		Display.update();
		Display.sync(fps);
	}
	
	public void destroy() {
		Display.destroy();
	}
}
