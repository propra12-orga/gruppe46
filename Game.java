import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Game implements Runnable {

	public static Feld[][] spielfeld;
	//public Player one, two;
	private final List<Player> players = new ArrayList<Player>();
	static Lock lock1 = new ReentrantLock();
	private LWJGL_Font lucida;
	
	public Game(int spielerzahl) {
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
		if(spielerzahl==1) {
			players.add(new Player("One",1,1));
		}
		if(spielerzahl==2) {
			players.add(new Player("One",1,1));
			players.add(new Player("Two",3,3));
		}		
		
		spielfeld[13][11]=new Exitfeld();//Ausgang f�r Pr�sentation
		GameTime.init();
	}
	
	public void pollInput(){
		lock1.lock();
		
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
				
			if (spielfeld[p.getx()][p.gety()] instanceof Explosionsfeld | spielfeld[p.getx()][p.gety()] instanceof Exitfeld) {p.die();};
			
			if(!p.isAlive()) { continue; }
					 
			if (Keyboard.isKeyDown(p.getKeyRight())) {
				if (spielfeld[p.getx()+1][p.gety()] instanceof Leerfeld | 
					spielfeld[p.getx()+1][p.gety()] instanceof Exitfeld |
					spielfeld[p.getx()+1][p.gety()] instanceof Explosionsfeld){
				
					 p.move(1,0);
				}
			}
			if (Keyboard.isKeyDown(p.getKeyLeft())) {
				if (spielfeld[p.getx()-1][p.gety()] instanceof Leerfeld | 
					spielfeld[p.getx()-1][p.gety()] instanceof Exitfeld |
					spielfeld[p.getx()-1][p.gety()] instanceof Explosionsfeld){
				
					p.move(-1,0);
				}
			}
			if (Keyboard.isKeyDown(p.getKeyDown())) {
				if (spielfeld[p.getx()][p.gety()+1] instanceof Leerfeld | 
					spielfeld[p.getx()][p.gety()+1] instanceof Exitfeld |
					spielfeld[p.getx()][p.gety()+1] instanceof Explosionsfeld){
				 
					p.move(0,1);
				}
			}
			if (Keyboard.isKeyDown(p.getKeyUp())) {
				if (spielfeld[p.getx()][p.gety()-1] instanceof Leerfeld | 
					spielfeld[p.getx()][p.gety()-1] instanceof Exitfeld |
					spielfeld[p.getx()][p.gety()-1] instanceof Explosionsfeld){
					p.move(0,-1);
				}
			}
			if (Keyboard.isKeyDown(p.getKeyBomb()) ) {
				if((Bombe.getBombs() < p.getBombs()) && (!p.isPress_bomb())) {
					new Bombe(p.getx(),p.gety()).start();
					spielfeld[p.getx()][p.gety()]= new Bombenfeld();
				}
				p.setPress_bomb(true);
			} else {
				p.setPress_bomb(false);
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			for(int i = 0; i < 4; i++){
				Main.m.hauptButtons[i].setVisible(false);
				if(i!=2){
					Main.m.spielButtons[i].setVisible(true);
				}
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
    	
		try {
			lucida = new LWJGL_Font("lucida_console2.png");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		}
    	System.out.println(players.size());
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			
			p.loadSprite("player.png");
    		p.setBombs(4);
    		
    		if(i == 0) {
    			p.setKeys(Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_SPACE);
    		} else {
    			p.setKeys(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_F);
    		}
		}
    	
    	Menue.conti = true;
    	
        while (!Display.isCloseRequested() && playersAlive() && (Menue.conti == true)) {
        	// conti bezeichnet den Unterschied zwischen Spiel- und Hauptmenu
        	Renderer.clearGL();
        	
			pollInput();
		    
		    for(int x = 0; x < spielfeld.length; x++) {
		    	for(int y = 0; y < spielfeld[0].length; y++) {
		    		spielfeld[x][y].draw(x, y);
		    	}
		    }
		    
		    lucida.setScale(0.33f);
			for(int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				if(p.isAlive()) p.draw();
				lucida.print((int)(p.getx()*128*0.33f),(int)(p.gety()*128*0.33f) - 6, p.getName());
			}
			
			lucida.setScale(0.50f);
			if(players.size()==1) {
				lucida.print(5, 5, "Player 1 is " + (players.get(0).isAlive()?"alive":"dead"));
			}
			else {
				lucida.print(5, 5, "Player 1 is " + (players.get(0).isAlive()?"alive":"dead") + ", Player 2 is " + (players.get(1).isAlive()?"alive":"dead")  + " (" + players.size() + ")");
			}
			
		    GameTime.update();
		    Renderer.sync();
		}
	    //ggf. Game-Over Behandlung    
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}    
        for(int i = 0; i < 4; i++){
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
    
    public boolean playersAlive() {
    	
		if(players.size() <= 0) return false;
		
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if(p.isAlive()) return true;
		}
    	return false;
    }
}

