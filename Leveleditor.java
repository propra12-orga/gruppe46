import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class Leveleditor {
	private static LWJGL_Font lucida;
	private static int type = 1;
	
	//Main-Methode
	public static void main(String[] args){
		GameTime.init();
		
    	Renderer.initDisplay(800,600,60);
    	Renderer.initGL();
    	Renderer.setClearColor(1.0f, 1.0f, 1.0f, 1.0f); //white
    	
		try {
			lucida = new LWJGL_Font("lucida_console2.png");
			lucida.setScale(0.45f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		}

		int width = Integer.parseInt(getInput("Level Width: ", "0123456789"));
		int height = Integer.parseInt(getInput("Level Height: ", "0123456789"));
		
		int px = 0, py = 0;
		int p2x = 1, p2y = 1;
		Feld[][] spielfeld = new Feld[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				spielfeld[x][y] = new Leerfeld();
			}
		}
		
        while (!Display.isCloseRequested()) {
        	// conti bezeichnet den Unterschied zwischen Spiel- und Hauptmenu
        	Renderer.clearGL();
        	
        	pollInput();
        	
		    for(int x = 0; x < spielfeld.length; x++) {
		    	for(int y = 0; y < spielfeld[0].length; y++) {
		    		spielfeld[x][y].draw(x, y);
		    	}
		    }
		    
		    int x = MouseFeldX(Mouse.getX());
		    int y = MouseFeldY(Mouse.getY());
		    
		    if(Mouse.isButtonDown(0) && (x < width) && (y < height)) {
		    	switch(type) {
		    		case 1: 
		    			spielfeld[x][y] = new Steinfeld();
		    			 break;
		    		case 2: 
		    			spielfeld[x][y] = new Mauerfeld();
		    			 break;
		    		case 3: 
		    			px = x; py = y;
		    			 break;
		    		case 4: 
		    			p2x = x; p2y = y;
		    			 break;
		    	}
		    }
		    
		    if(Mouse.isButtonDown(1) && (x < width) && (y < height)) {
		    	spielfeld[x][y] = new Leerfeld();
		    }
		    
	    	switch(type) {
	    		case 1: 
	    			Renderer.Tile_Wall.draw(Mouse.getX(), Display.getHeight() - Mouse.getY()); 
	    			lucida.print(0, 0, "   LMB: Steinfeld setzen (1 = Mauer, 2 = P1, 3 = P2)");
	    			break;
	    		case 2: 
	    			Renderer.Tile_Break.draw(Mouse.getX(), Display.getHeight() - Mouse.getY());
	    			lucida.print(0, 0, "   LMB: Mauer (zerstoerbar) setzen (0 = Steinfeld, 2 = P1, 3 = P2)");
	    			 break;
	    		case 3: 
	    			lucida.print(Mouse.getX(), Display.getHeight() - Mouse.getY(), "P1");
	    			lucida.print(0, 0, "   LMB: Player 1 setzen (0 = Steinfeld, 1 = Mauer, 3 = P2)");
	    			 break;
	    		case 4: 
	    			lucida.print(Mouse.getX(), Display.getHeight() - Mouse.getY(), "P2");
	    			lucida.print(0, 0, "   LMB: Player 2 setzen (0 = Steinfeld, 1 = Mauer, 2 = P1)");
	    			 break;
	    	}
	    	lucida.print(0, (int)(lucida.getScale() * 24), "   RMB: Feld loeschen"); 
	    			
	    	lucida.print(Feld.getSize()*px, Feld.getSize()*py, "P1");
	    	lucida.print(Feld.getSize()*p2x, Feld.getSize()*p2y, "P2");
	    	
        	
		    GameTime.update();
		    Renderer.sync();
        }
        Renderer.destroy();
	}
	
	public static void pollInput(){
		Keyboard.poll();
		while (Keyboard.getNumKeyboardEvents() > 0) {
			if(Keyboard.getEventKeyState() == true) {
				switch(Keyboard.getEventKey()) {
					case Keyboard.KEY_ESCAPE: 
						break;
					case Keyboard.KEY_1: type = 1;
						break;
					case Keyboard.KEY_2: type = 2;
						break;
					case Keyboard.KEY_3: type = 3;
						break;
					case Keyboard.KEY_4: type = 4;
					break;
				}
			}
			Keyboard.next();
		}
		type = (type + Mouse.getDWheel()/120 - 1)%4 + 1;
	}
	
	private static String getInput(String label, String allowed) {
		boolean done = false;
		String input = "";
		
		while (!Display.isCloseRequested() && !done) {
			Renderer.clearGL();
			lucida.print(0, 0, label + input);
			Keyboard.poll();
			while (Keyboard.getNumKeyboardEvents() > 0) {
				
				if(Keyboard.getEventKeyState() == true) {
				
					String c = ""+Keyboard.getEventCharacter();
					
					if(allowed.contains(c)) input += c;
					
					switch(Keyboard.getEventKey()) {
						case Keyboard.KEY_BACK:
							input = input.length() > 0 ? "" : input.substring(0, input.length()-1);
							break;
						case Keyboard.KEY_RETURN: 
							done = true;
							break;
						case Keyboard.KEY_ESCAPE: 
							return "";
					}
				}
				Keyboard.next();
			}
		    GameTime.update();
		    Renderer.sync();
		}
		return input;
	}
	
	private static int MouseFeldX(int x) {
		return x/Feld.getSize();
	}
	
	private static int MouseFeldY(int y) {
		return (Display.getHeight() - y)/Feld.getSize();
	}
	
}


/*
import java.io.*;

public class Leveleditor {

	public static Feld[][] einlesen(Feld[][] spielfeld) throws IOException{
		int j=0;
		int i=0;
		while(i<13){
			while(j<15){
				System.out.println("Position X:"+ (j+1) +" Y:"+ (i+1) + ". Welche Art von Feld soll an dieser Position sein? ('S'= nicht zerstoerbarer Stein, 'L'= leeres Feld)");
				BufferedReader eing = new BufferedReader(new InputStreamReader(System.in));
				String zeile = eing.readLine();
				char auswahl = zeile.charAt(0);
				System.out.println(auswahl);
				switch(auswahl){
					case 'S': spielfeld[j][i]=new Steinfeld(); i++; j++;
								break;
					case 'L': spielfeld[j][i]=new Leerfeld(); i++; j++;
								break;
					case 'M': spielfeld[j][i]=new Mauerfeld(); i++; j++;
								break;
								}
			}
		}
		return spielfeld;
	}
	
	
	//Main-Methode
	public static void main(String[] args) throws IOException{
	BufferedReader eingabe = new BufferedReader(new InputStreamReader(System.in));
	
	System.out.println("Wie soll das neue Level heissen?");
	String name= eingabe.readLine();
	
	Feld[][] spielfeld = new Feld[15][13];
	
	try{
	ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(name + ".map",true));
	Feld[][] inhalt = einlesen(spielfeld);
	o.writeObject(inhalt);
	o.close();
	} catch (Exception e){}
	System.out.println("Level erfolgreich erstellt!");

}
}
*/