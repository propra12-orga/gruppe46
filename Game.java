import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Game implements Runnable {

	public static Feld[][] spielfeld;
	//public Player one, two;
	private final List<Player> players = new ArrayList<Player>();
	static Lock lock1 = new ReentrantLock();
	private LWJGL_Font lucida;
	private boolean[] arr;
	
	public Game(int spielerzahl) {
		//Spielfeld laden
		try {
			initialfeld("Level2.xml", spielerzahl); 
		} catch (FileNotFoundException e) {
			// Level nicht gefunden
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		//
		
		
		
		
	}
	/**
	 * Veraendert die Zustaende des Arrays bzgl. der Anzahl der Power-Ups
	 */
	public void setArray(int i)
	{
		if(arr[i])
		{
			
		}
	}
	
	public void pollInput(){
		lock1.lock();
		
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
				
			if (spielfeld[p.getx()][p.gety()] instanceof Explosionsfeld) {
				p.hit(((Explosionsfeld)spielfeld[p.getx()][p.gety()]).getBomb());
//				lucida.setScale(0.33f);
//				lucida.print(10, 5, "Spieler "+i+" hat noch "+p.lives+" Leben.");
				}
//			System.out.println(p.lives); //Ueberpruefung der Leben
			if(spielfeld[p.getx()][p.gety()] instanceof Exitfeld){
				Main.m.gameover.setText("Spieler "+(i+1)+" hat gewonnen!");
				p.die();
				}
			
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
				if((Bombe.getBombs(p.getName()) < p.getBombs()) && (!p.isPress_bomb())) {
					new Bombe(p.getx(),p.gety(), p.getName()).start();
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
				Main.m.gameover.setVisible(true);
			}
			Main.m.setVisible(true);
			Keyboard.destroy();
			try {
				Keyboard.create();
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
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			
			p.loadSprite("player.png");
    		p.setBombs(Main.m.getBombs());
    		
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
				lucida.print(5, 5, "Player 1 is " + (players.get(0).isAlive()?"alive":"dead") + " (" + players.get(0).getLives() + ") , Player 2 is " + (players.get(1).isAlive()?"alive":"dead")  + " (" + players.get(1).getLives() + ") ... (" + players.size() + ") " + GameTime.getFPS() + " ");
			}
			
		    GameTime.update();
		    Renderer.sync();
		}   
        
        for(int i = 0; i < players.size(); i++) {
        	if(players.get(i).isAlive() == false) Main.m.gameover.setText("Spieler "+players.get(i).getName()+" ist tot!" + " (" + players.get(0).getLives() + ")");
        }
        
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}    
        for(int i = 0; i < 4; i++){
			Main.m.hauptButtons[i].setVisible(true);
			Main.m.gameover.setVisible(true);
			Menue.conti = false;
		}
		Main.m.setVisible(true);
		Keyboard.destroy(); //Keyboard wird zerstört...
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
    
/*    public boolean playersAlive() {
    	
		if(players.size() <= 0) return false;
		
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if(p.isAlive() && p.getLives()!=0) {return true;}
			else{Main.m.gameover.setText("Spieler "+(i+1)+" ist tot!");}
		}
    	return false;
    } */
    /**
     * Sterbebedingungen fuer Einzel- und Mehrspieler
     * @param p: Uebergabe des zu ueberpruefenden Spielers
     * @return: true, falls betreffender Spieler lebt; false, falls betreffender Spieler tot
     */
    public boolean playersAlive(){
    	for(int i = 0; i < players.size(); i++) {
    		if(players.get(i).isAlive() == false) return false;
    	}	
    	return true;
    }

/**
 * Laed das gewuenschte Level
 * @param name: Uebergabe des Levelnamens
 * @throws FileNotFoundException
 * @throws XMLStreamException
 */
	protected void initialfeld(String name, int spielerzahl) throws FileNotFoundException, XMLStreamException{
		InputStream in = new FileInputStream(name);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(in);	
		int breit=0, hoch=0;
		int y = -1;		//2mal Start_Element bevor die Attribute kommen, somit y=0 beim ersten Attribut
		
		while( parser.hasNext() ) {
		    int event = parser.next();
		   
		    switch (event) {
		        case XMLStreamConstants.END_DOCUMENT:
		            parser.close();
		            break;
		        case XMLStreamConstants.START_ELEMENT:
		        	if(parser.getLocalName().equals("Groesse")){
		        		breit= Integer.parseInt(parser.getAttributeValue(0));
		        		hoch= Integer.parseInt(parser.getAttributeValue(1));
		        		spielfeld= new Feld[breit][hoch];
		        	}
		        	else if(parser.getLocalName().equals("Startpositionen")){
		        		int x1 = Integer.parseInt(parser.getAttributeValue(0));
		        		int y1 = Integer.parseInt(parser.getAttributeValue(1));
		        		int x2 = Integer.parseInt(parser.getAttributeValue(2));
		        		int y2 = Integer.parseInt(parser.getAttributeValue(3));
		        		
		        		if(spielerzahl==1) {
		        			players.add(new Player("One",x1,y1));
		        		}
		        		if(spielerzahl==2) {
		        			players.add(new Player("One",x1,y1));
		        			players.add(new Player("Two",x2,y2));
		        		}
		        	} else if(parser.getLocalName().startsWith("Zeile")){
		        		y++;
		        		for( int x = 0; x < parser.getAttributeCount(); x++ ){
		        			String test = parser.getAttributeValue(x);
		            	
		            		if (test.equals("S")){spielfeld[x][y]= new Steinfeld();}
		            		else if (test.equals("M")){spielfeld[x][y]= new Mauerfeld();}
		            		else if (test.equals("L")){spielfeld[x][y]= new Leerfeld();}
		        		}//for
		        	}//if 
		            break;
		        
		       
		        default:
		            break;
		    }
		}
		
		

		//Ausgangsfeld mit zufaelligen Variablen
		int a = 1;
		int b = 1;
		do {
			a = (int) ((Math.random()*100)%(breit-2))+1;
			b = (int) ((Math.random()*100)%(hoch-2))+1;
		}
		while(spielfeld[a][b] instanceof Steinfeld);

		spielfeld[a][b]=new Exitfeld();
		a = 1;
		b = 1;
		do {
			a = (int) ((Math.random()*100)%(breit-2))+1;
			b = (int) ((Math.random()*100)%(hoch-2))+1;
		}
		while(spielfeld[a][b] instanceof Steinfeld || spielfeld[a][b] instanceof Exitfeld || spielfeld[a][b] instanceof Extrasfeld);
		/*
		int art = 1;
		arr = new boolean[13];
		for(int i=0;i<arr.length;i++)
		{
			arr[i] = true;
		}
		
		if(Main.m.getPowerUps())
		{
			for(int i=0;i<5;i++)
			{
				int zahl = (int)((Math.random()*100)%100)+1;
				
					if(zahl>=1 && zahl<10 && arr[0] == true)
					{ 	art = 1;
						arr[0] = false;
					}
					//if(arr[0] == false) i--;
					
					if(zahl>=10 && zahl<26 && arr[1] == true)
					{ 	art = 2;
					arr[1] = false;
					}
					//if(arr[1] == false) i--;
					
					if(zahl>=26 && zahl<35 && arr[2] == true)
					{ 	art = 3;
					arr[2] = false;
					}
					//if(arr[2] == false) i--;
					
					if(zahl>=35 && zahl<40 && arr[3] == true)
					{ 	art = 4;
					arr[3] = false;
					}
					//if(arr[3] == false) i--;
					
					if(zahl>=40 && zahl<49 && arr[4] == true){ art = 5;}
					if(zahl>=49 && zahl<53 && arr[5] == true){ art = 6;}
					if(zahl>=53 && zahl<62 && arr[6] == true){ art = 7;}
					if(zahl>=62 && zahl<66 && arr[7] == true){ art = 8;}
					if(zahl>=66 && zahl<70 && arr[8] == true){ art = 9;}
					if(zahl>=70 && zahl<73 && arr[9] == true){ art = 10;}
					if(zahl>=73 && zahl<77 && arr[10] == true){ art = 11;}
					if(zahl>=77 && zahl<86 && arr[11] == true){ art = 12;}
					if(zahl>=86 && zahl<100 && arr[12] == true){ art = 13;}
				
			}
		}
		// art = Algorithmus... For-Schleife inklusive boolean um erneut zu generieren um mehrfach Extrafeld zu vermeiden
		spielfeld[x][y]=new Extrasfeld(art);
		GameTime.init();*/
		
		
	}
	
	
}//end class