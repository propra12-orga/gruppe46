import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Renderer {
	
	private static int width, height, fps;
	private static float clearR, clearG, clearB, clearA;
	
	public static final LWJGL_Sprite Tile_Empty = new LWJGL_Sprite("empty.png");
	public static final LWJGL_Sprite Tile_Wall = new LWJGL_Sprite("wall.png");
	public static final LWJGL_Sprite Tile_Bomb = new LWJGL_Sprite("bomb.png");
	public static final LWJGL_Sprite Tile_Explosion = new LWJGL_Sprite("explosion.png");
	
	public static void initDisplay() {
		initDisplay(640,480,60);
	}
	
	public static void initDisplay(int w, int h) {
		initDisplay(w,h,60);
	}
	
	public static void initDisplay(int fps) {
		initDisplay(640,480,fps);
	}
	
	public static void initDisplay(int w, int h, int fps) {
		width = w;
		height = h;
		Renderer.fps = fps;
		
		/* set up display mode */
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	

	public static void initGL() {
		
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
        
        Tile_Empty.init();
        Tile_Empty.setScaleX(0.25f); Tile_Empty.setScaleY(0.25f);
        Tile_Wall.init();
        Tile_Wall.setScaleX(0.25f); Tile_Wall.setScaleY(0.25f);
        Tile_Bomb.init();
        Tile_Bomb.setScaleX(0.25f); Tile_Bomb.setScaleY(0.25f);
        Tile_Explosion.init();
        Tile_Explosion.setScaleX(0.25f); Tile_Explosion.setScaleY(0.25f);
	}
	
	public static void setClearColor(float r, float g, float b, float a) {
		clearR = r;
		clearG = g;
		clearB = b;
		clearA = a;
	}
	
	
	public static void clearGL() {
		// Clear The Screen And The Depth Buffer
		GL11.glClearColor(clearR, clearG, clearB, clearA);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void sync() {
		Display.update();
		Display.sync(fps);
	}
	
	public static void destroy() {
		Display.destroy();
	}
}
