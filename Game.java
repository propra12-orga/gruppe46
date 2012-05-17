import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.io.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;



public class Game implements Runnable {

public Feld[][] spielfeld;
public Player one;



public Game(){
	 try{
		  ObjectInputStream o = new ObjectInputStream(new FileInputStream("/home/philip/ProPra/gruppe46/src/default.map")); //hier muss der Pfad der default.map angegeben werden sonst Error
		  spielfeld = (Feld[][]) o.readObject();
		  o.close();
		 } catch (IOException i){
		  } 
		  catch (ClassNotFoundException e) {
			//Fehlerbehandlung "Level nicht gefunden"
		  
		 }
	one = new Player("One",1,1);
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
	 one.print();
	 
	}

    public void run() {
        try {
	    Display.setDisplayMode(new DisplayMode(640, 480));
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
	// init OpenGL here
      glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0,640,480,0,1,-1);
        glMatrixMode(GL_MODELVIEW);
        while (!Display.isCloseRequested()) {
	    // render OpenGL here
        glClear(GL_COLOR_BUFFER_BIT);		     //zu Testzwecken
        
       
        
	    pollInput();
	    Display.update();
	    Display.sync(60);
	}
    //ggf. Game-Over Behandlung    
        
	Display.destroy();
    }
      
}

