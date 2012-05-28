import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Game implements Runnable {

	public static Feld[][] spielfeld;
	public Player one;
	static Lock lock1 = new ReentrantLock();
	private boolean Space = false;
	
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
		spielfeld[13][11]=new Exitfeld();//Ausgang f�r Pr�sentation
		GameTime.init();
	}
	
	public void pollInput(){
		lock1.lock();
		if (spielfeld[one.getx()][one.gety()] instanceof Explosionsfeld | spielfeld[one.getx()][one.gety()] instanceof Exitfeld) {one.die();};
		
				 
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (spielfeld[one.getx()+1][one.gety()] instanceof Leerfeld | 
				spielfeld[one.getx()+1][one.gety()] instanceof Exitfeld |
				spielfeld[one.getx()+1][one.gety()] instanceof Explosionsfeld){
			
				 one.move(1,0);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (spielfeld[one.getx()-1][one.gety()] instanceof Leerfeld | 
				spielfeld[one.getx()-1][one.gety()] instanceof Exitfeld |
				spielfeld[one.getx()-1][one.gety()] instanceof Explosionsfeld){
			
				one.move(-1,0);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (spielfeld[one.getx()][one.gety()+1] instanceof Leerfeld | 
				spielfeld[one.getx()][one.gety()+1] instanceof Exitfeld |
				spielfeld[one.getx()][one.gety()+1] instanceof Explosionsfeld){
			 
				one.move(0,1);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (spielfeld[one.getx()][one.gety()-1] instanceof Leerfeld | 
				spielfeld[one.getx()][one.gety()-1] instanceof Exitfeld |
				spielfeld[one.getx()][one.gety()-1] instanceof Explosionsfeld){
				one.move(0,-1);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) ) {
			if((Bombe.getBombs() < one.getBombs()) && (!Space)) {
				new Bombe(one.getx(),one.gety()).start();
				spielfeld[one.getx()][one.gety()]= new Bombenfeld();
			}
			Space = true;
		} else {
			Space = false;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			for(int i=0;i<4;i++){
				Main.m.hauptButtons[i].setVisible(false);
				Main.m.spielButtons[i].setVisible(true);
			}
			Main.m.setVisible(true);
			Keyboard.destroy(); //Ab hier neu. Keyboard wird zerstört...
			try {
				Keyboard.create(); // ...und hier wieder neu erstellt.
			} catch (LWJGLException e){
				e.printStackTrace();
			}
			Main.t1.suspend();
		}
			lock1.unlock();
	}
	


	
	
	
	
    public void run() {
    	
    	Renderer.initDisplay(800,600,60);
    	Renderer.initGL();
    	Renderer.setClearColor(1.0f, 1.0f, 1.0f, 1.0f); //white
    	
    	one.loadSprite("player.png");
    	one.setBombs(4);
    	
    	Menue.conti = true;
    	
        while (!Display.isCloseRequested() && one.isAlive() && Menue.conti == true) {
        	// conti bezeichnet den Unterschied zwischen Spiel- und Hauptmenu
        	Renderer.clearGL();
        	

				pollInput();
		    
		    for(int x = 0; x < spielfeld.length; x++) {
		    	for(int y = 0; y < spielfeld[0].length; y++) {
		    		spielfeld[x][y].draw(x, y);
		    	}
		    }
		    
		    one.draw();
		    
		    GameTime.update();
		    Renderer.sync();
		}
	    //ggf. Game-Over Behandlung    
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}    
        for(int i=0;i<4;i++){
			Main.m.hauptButtons[i].setVisible(true);
			
		}
		Main.m.setVisible(true);
		Keyboard.destroy(); //Ab hier neu. Keyboard wird zerstört...
		try {
			Keyboard.create(); // ...und hier wieder neu erstellt.
		} catch (LWJGLException e){
			e.printStackTrace();
		}
		Renderer.destroy();
    }
    public static void destroy()
    {
    	Renderer.destroy();
    }
}

