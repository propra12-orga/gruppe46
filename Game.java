import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import java.io.*;

public class Game implements Runnable {

	public Feld[][] spielfeld;
	public Player one;
	
	//public GameTime Timer;
	public Renderer Render;
	
	public Game() {
		try {
			ObjectInputStream o = new ObjectInputStream(new FileInputStream("default.map")); //hier muss der Pfad der default.map angegeben werden sonst Error
			spielfeld = (Feld[][]) o.readObject();
			o.close();
		} 
		catch (IOException i) {
			i.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			//Fehlerbehandlung "Level nicht gefunden"
			e.printStackTrace();
		} 
		
		one = new Player("One",1,1);
		
		//Timer = new GameTime();
		GameTime.init();
		Render = new Renderer(640, 480, 60);
	}
	
	public void pollInput(){
		//Zeichnen des Feldes durch spielfeld[x][y].draw();?
				 
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (spielfeld[one.getx()+1][one.gety()] instanceof Leerfeld){
			
				 one.move(1,0);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (spielfeld[one.getx()-1][one.gety()] instanceof Leerfeld){
			
				one.move(-1,0);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (spielfeld[one.getx()][one.gety()+1] instanceof Leerfeld){
			 
				one.move(0,1);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (spielfeld[one.getx()][one.gety()-1] instanceof Leerfeld){
				
			
				one.move(0,-1);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
		
		 //Bombenthread?
		}
	 
	}

    public void run() {
    	
    	Render.initDisplay();
    	Render.initGL();
    	
    	one.loadSprite("player.png"); // kann erst nach initGL benutzt werden, alternativ initGL und so mit im konstruktor?
        
        while (!Display.isCloseRequested()) {
        	
        	Render.clearGL();
        	
		    pollInput();
		    
		    one.draw();
		    
		    GameTime.update();
		    Render.sync();
		}
	    //ggf. Game-Over Behandlung    
	        
		Render.destroy();
    }
    
}

