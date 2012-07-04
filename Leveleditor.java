import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class Leveleditor implements Runnable{
	/**
	 * Die verwendete Schriftart
	 */
	private static LWJGL_Font lucida;
	/**
	 * Art des ausgewaehlten Feldes
	 */
	private static int type = 1;
	
	//run-Methode
	/**
	 * Ausfuehrung des Leveleditors
	 */
	public void run(){
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
		String name = "";
		do{
			name = getInput("Level Name: ", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		}while(!isValidName(name));
		
		name = name + ".xml";
		
		int width = 0;
		int height = 0;
		do{
			width = Integer.parseInt(getInput("Level Width (5 - 15): ", "0123456789"));
		} while((width < 5)||(width > 15));
		
		do{
			height = Integer.parseInt(getInput("Level Height (5 - 15): ", "0123456789"));
		} while((height < 5)||(height > 15));
		int px = 1, py = 1;
		int p2x = 1, p2y = 2;
		int exitx=1, exity=3;
		Feld[][] spielfeld = new Feld[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if((x < width-1) && (y < height-1) && (x > 0) && (y > 0)) {
					spielfeld[x][y] = new Leerfeld();
				} else {
					spielfeld[x][y] = new Steinfeld();
				}
			}
		}
		boolean finish=false;
		/**
		 * Solange das Fenster nicht geschlossen wird bzw. ESC gedrueckt wird, erstellt der Benutzer das Level, danach wird es gespeichert.
		 */
        while (!Display.isCloseRequested() && !finish) {
        	
        	Renderer.clearGL();
        	if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) finish=true;//Beim druecken von esc wird ueberprueft ob LVL gueltig
        	pollInput();
        	
		    for(int x = 0; x < spielfeld.length; x++) {
		    	for(int y = 0; y < spielfeld[0].length; y++) {
		    		spielfeld[x][y].draw(x, y);
		    	}
		    }
		    
		    int x = MouseFeldX(Mouse.getX());
		    int y = MouseFeldY(Mouse.getY());
		    
		    if(Mouse.isButtonDown(0) && (x < width-1) && (y < height-1) && (x > 0) && (y > 0)) {
		    	switch(type) {
		    		case 1: 
		    			if(((x!=px)||(y!=py))&&((x!=p2x)||(y!=p2y))&&((x!=exitx)||(y!=exity))) {
		    				spielfeld[x][y] = new Steinfeld();
		    			}
		    			 break;
		    		case 2: 
		    			if(((x!=px)||(y!=py))&&((x!=p2x)||(y!=p2y))) {
		    				spielfeld[x][y] = new Mauerfeld();
		    			}
		    			 break;
		    		case 3: 
		    			if (!(spielfeld[x][y] instanceof Leerfeld)){
		    				lucida.print(0, 580, "Spieler koennen nur auf Leerfeldern starten!");
		    			} else {
		    				px = x; py = y;
		    			}
		    			 break;
		    		case 4: 
		    			if (!(spielfeld[x][y] instanceof Leerfeld)){
		    				lucida.print(0, 580, "Spieler koennen nur auf Leerfeldern starten!");
		    			} else {
		    				p2x = x; p2y = y;
		    			}
		    			 break;
		    		case 5: 
		    			if ((spielfeld[x][y] instanceof Steinfeld)){
		    				lucida.print(0, 580, "Den Ausgang nicht auf ein Steinfeld legen!");
		    			} else {
		    				exitx = x; exity = y;
		    			}
		    			 break;
		    	}
		    }
		    
		    if(Mouse.isButtonDown(1) && (x < width-1) && (y < height-1) && (x > 0) && (y > 0)) {
		    	spielfeld[x][y] = new Leerfeld();
		    }
		    
	    	switch(type) {
	    		case 1: 
	    			Renderer.Tile_Wall.draw(Mouse.getX(), Display.getHeight() - Mouse.getY()); 
	    			lucida.print(0, 0, "   LMB: Steinfeld setzen (2 = Mauer, 3 = P1, 4 = P2, 5 = Ausgang)");
	    			break;
	    		case 2: 
	    			Renderer.Tile_Break.draw(Mouse.getX(), Display.getHeight() - Mouse.getY());
	    			lucida.print(0, 0, "   LMB: Mauer (zerstoerbar) setzen (1 = Steinfeld, 3 = P1, 4 = P2, 5 = Ausgang)");
	    			 break;
	    		case 3: 
	    			lucida.print(Mouse.getX(), Display.getHeight() - Mouse.getY(), "P1");
	    			lucida.print(0, 0, "   LMB: Player 1 setzen (1 = Steinfeld, 2 = Mauer, 4 = P2, 5 = Ausgang)");
	    			 break;
	    		case 4: 
	    			lucida.print(Mouse.getX(), Display.getHeight() - Mouse.getY(), "P2");
	    			lucida.print(0, 0, "   LMB: Player 2 setzen (1 = Steinfeld, 2 = Mauer, 3 = P1, 5 = Ausgang)");
	    			 break;
	    		case 5: 
	    			lucida.print(Mouse.getX(), Display.getHeight() - Mouse.getY(), "EXIT");
	    			lucida.print(0, 0, "   LMB: Ausgang setzen (1 = Steinfeld, 2 = Mauer, 3 = P1, 4 = P2)");
   			 		 break;
	    	}
	    	lucida.print(0, (int)(lucida.getScale() * 24), "   RMB: Feld loeschen"); 
	    			
	    	lucida.print(Feld.getSize()*px, Feld.getSize()*py, "P1");
	    	lucida.print(Feld.getSize()*p2x, Feld.getSize()*p2y, "P2");
	    	lucida.print(Feld.getSize()*exitx, Feld.getSize()*exity, "EXIT");
	    	
	    	if (finish){
        	if (!startpruefung(px,py,p2x,p2y,width,height)){finish = false;}
        	if (!aufbaupruefung(spielfeld, width, height)){ 
        		lucida.print(0, 560, "Level ungueltig, da nicht nach au�en abgeschlossen!");
        		finish=false;
        		} else if (!sperrpruefung(spielfeld,width, height, px, py, p2x, p2y, exitx, exity)) {
        			lucida.print(0, 560, "Spieler koennen sich nicht erreichen oder Ausgang ist versperrt!");
        			finish=false;
        			}
        	}
		    GameTime.update();
		    Renderer.sync();
        }
        //abspeichern
       try {
			speichern(spielfeld,width,height,px,py,p2x,p2y,name,exitx,exity);
		} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Renderer.destroy();
	}
	
	

	public static void pollInput(){
		Keyboard.poll();
		while (Keyboard.getNumKeyboardEvents() > 0) {
			if(Keyboard.getEventKeyState() == true) {
				switch(Keyboard.getEventKey()) {
					case Keyboard.KEY_ESCAPE:;
						break;
					case Keyboard.KEY_1: type = 1;
						break;
					case Keyboard.KEY_2: type = 2;
						break;
					case Keyboard.KEY_3: type = 3;
						break;
					case Keyboard.KEY_4: type = 4;
						break;
					case Keyboard.KEY_5: type = 5;
						break;
				}
			}
			Keyboard.next();
		}
		type = (type + Mouse.getDWheel()/120 - 1)%5 + 1;
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
							input = input.length() > 0 ? input.substring(0, input.length()-1) : "";
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

	/**
	 * Abspeichern des erstellten Levels als .xml
	 * @param spielfeld: Erstelltes Spielfeld
	 * @param width: Breite des Spielfelds
	 * @param height: Hoehe des Spielfelds
	 * @param px: x-Teil der Startposition von Spieler 1
	 * @param py: y-Teil der Startposition von Spieler 1
	 * @param p2x: x-Teil der Startposition von Spieler 2
	 * @param p2y: y-Teil der Startposition von Spieler 2
	 * @param name: Name des Levels
	 * @param exitx: x-Position des Ausgangs
	 * @param exity: y-Position des Ausgangs
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	private static void speichern(Feld[][] spielfeld, int width, int height, int px, int py, int p2x, int p2y, String name, int exitx, int exity) throws FileNotFoundException, XMLStreamException {
		//erstellen der xml-Datei
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter schreiber = factory.createXMLStreamWriter( new FileOutputStream(name));
		
		
		//schreiben der xml-Datei
		//zunaechst die Groesse des Spielfeldes
		schreiber.writeStartDocument();
		schreiber.writeStartElement("Level");
		schreiber.writeStartElement("Groesse");
		schreiber.writeAttribute("Breite", String.valueOf(width));
		schreiber.writeAttribute("Hoehe", String.valueOf(height));
		schreiber.writeEndElement();
		
		//hier die Beschaffenheit der einzelnen Felder
		schreiber.writeStartElement("Spielfeld");
		for (int i=0;i<height;i++){
			schreiber.writeStartElement("Zeile"+String.valueOf(i));
			for (int j=0;j<width;j++){		
				if (spielfeld[j][i] instanceof Steinfeld) {schreiber.writeAttribute("X"+String.valueOf(j),"S");}
				else if (spielfeld[j][i] instanceof Leerfeld) {schreiber.writeAttribute("X"+String.valueOf(j),"L");}
				else if (spielfeld[j][i] instanceof Mauerfeld) {schreiber.writeAttribute("X"+String.valueOf(j),"M");}
			}
			schreiber.writeEndElement();
		}//end for(i)
		schreiber.writeEndElement();
		
		//festlegen der Startpositionen
		schreiber.writeStartElement("Startpositionen");
			schreiber.writeAttribute("p1x", String.valueOf(px));
			schreiber.writeAttribute("p1y", String.valueOf(py));
			schreiber.writeAttribute("p2x", String.valueOf(p2x));
			schreiber.writeAttribute("p2y", String.valueOf(p2y));
		schreiber.writeEndElement();
		
		//Ausgang festlegen
		schreiber.writeStartElement("Ausgang");
			schreiber.writeAttribute("exitx", String.valueOf(exitx));
			schreiber.writeAttribute("exity", String.valueOf(exity));
		schreiber.writeEndElement();
		
		schreiber.writeEndElement();
		schreiber.writeEndDocument();
		schreiber.close();
	}
		
		
		
		//Konsistenzpruefungen
	private static boolean sperrpruefung(Feld[][] spielfeld,int breit, int hoch, int x1, int y1, int x2, int y2, int exitx, int exity) {
		Feld[][] kopie = spielfeld;
		for (int i=0;i<breit;i++){
			for (int j=0;j<hoch;j++){
				if (!(kopie[i][j] instanceof Steinfeld)) kopie[i][j].sperrtest=false;
			}
		}
		
		kopie=rekursion(kopie, x1,y1);
		if (kopie[x2][y2].sperrtest!=true) return false;
		if (kopie[exitx][exity].sperrtest!=true) return false;
		return true;
		}


private static Feld[][] rekursion(Feld[][] kopie, int x, int y) {
	kopie[x][y].sperrtest=true;	
		if ((!(kopie[x-1][y] instanceof Steinfeld)) && (kopie[x-1][y].sperrtest==false)) kopie=rekursion(kopie, x-1, y);
		if ((!(kopie[x+1][y] instanceof Steinfeld)) && (kopie[x+1][y].sperrtest==false)) kopie=rekursion(kopie, x+1, y);
		if ((!(kopie[x][y-1] instanceof Steinfeld)) && (kopie[x][y-1].sperrtest==false)) kopie=rekursion(kopie, x, y-1);
		if ((!(kopie[x][y+1] instanceof Steinfeld)) && (kopie[x][y+1].sperrtest==false)) kopie=rekursion(kopie, x, y+1);
	return kopie;	
	}



/**
 * Ueberprueft, ob beide Spieler die gleiche Entfernung zu einer Ecke des Levels haben
 * @param x1: x-Teil der Startposition von Spieler 1
 * @param y1: y-Teil der Startposition von Spieler 1
 * @param x2: x-Teil der Startposition von Spieler 2
 * @param y2: y-Teil der Startposition von Spieler 2
 * @param breit: Breite des Levels
 * @param hoch: Hoehe des Levels
 * @return: Gibt wahr zurueck, wenn Entfernung gleich
 */
	private static boolean startpruefung(int x1, int y1, int x2, int y2, int breit, int hoch) {
		boolean gefunden=false; //Startposition von Spieler 2 gefunden
		
		//position[0]= x-Koordinate Spieler 1, [1] y-S1, [2] x-S2, [3] y-S2
		if (x2==breit-1-x1)	//vergleicht den Abstand von Spieler 1 zur linken oberen Ecke mit dem Abstand von Spieler 2 mit den anderen Ecken
			if (y2==hoch-1-y1) gefunden=true;
		if (x2==breit-1-x1)
			if (y2==y1) gefunden=true;
		if (x2==x1)
			if (y2==hoch-1-y1) gefunden=true;
		
		if (gefunden) return true;
		else {
			lucida.print(0, 580, "Ein Spieler hat einen Vorteil durch seine Startposition. Bitte Startpositionen korrigieren");
			return false;
		}
		
	}

/**
 * Ueberprueft, ob der Rand des Spielfeldes aus Steinfeldern besteht
 * @param spielfeld: 2D-Array des Spielfeldes
 * @param breit: Breite des Spielfeldes
 * @param hoch: Hoehe des Spielfeldes
 * @return Gibt wahr zurueck, wenn der Rand aus Steinen besteht
 */
	private static boolean aufbaupruefung(Feld[][] spielfeld, int breit, int hoch) {	//testet ob die map aussen aus steinfeldern besteht
		for (int i=0;i<breit;i++){		
			if (!(spielfeld[i][0] instanceof Steinfeld)) return false;
			if (!(spielfeld[i][hoch-1] instanceof Steinfeld)) return false;
		}
		for (int j=0;j<hoch;j++){
			if (!(spielfeld[0][j] instanceof Steinfeld)) return false;
			if (!(spielfeld[breit-1][j] instanceof Steinfeld)) return false;
		}
		
		return true;
	}
	
	private static boolean isValidName(String name) {
		if(name.equals("")) return false;
		if(name.length()>20) return false;
		return true;
	}
	
	
}

