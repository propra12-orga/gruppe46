import org.lwjgl.Sys;


public class GameTime {
	
	/* time at last frame */ 
	private static long lastFrame;
	
	/* frames per second  */
	private static int fps, frames;
	
	/* last fps time */
	private static long lastFPS;
	
	private static int delta;
	
	/* initialize delta time and frame time */
	public static void init() {
		updateDelta();
		lastFPS = getTime();
	}
	
	/* update delta timer and FPS counter. Call once per loop. */
	public static void update() {
		updateDelta();
		updateFPS();
	}
	
	/* returns current FPS value */
	public static int getFPS() {
		return fps;
	}
	
	
	/* Get the accurate system time */
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	
	/* return the delta value for this frame */
	public static int getDelta() {
		return delta;
	}
	
	
	/* Calculate how many milliseconds have passed since last frame and update delta */
	private static void updateDelta() {
	    long time = getTime();
	    delta = (int) (time - lastFrame);
	    lastFrame = time;
	}
	
	
	/* increment or reset FPS counter */
	private static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			fps = frames;
			frames = 0;
			lastFPS += 1000;
		}
		frames++;
	}
	

}
