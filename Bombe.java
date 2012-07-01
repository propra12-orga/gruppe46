import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

class Bombe extends Thread{
		/**
		 * Liste aller Bomben
		 */
		public static final List<Bombe> Bombs = new ArrayList<Bombe>();
		/**
		 * x-Koordinate
		 */
		private int x;
		/**
		 * y-Koordinate
		 */
		private int y;
		/**
		 * Boolean zur Feststellung, ob gerade explodiert
		 */
		private boolean exploding;
		/**
		 * Variable zur Festellung, ob bereits explodiert
		 */
		private boolean exploded=false;
		/**
		 * Aktuelle Reichweite der Bomben
		 */
		protected int range;
		/**
		 * Name des Spielers
		 */
		private String player;
		
		/**
		 * Bombe nach oben gekickt
		 */
		private boolean kickedUp=false;
		/**
		 * Bombe nach unten gekickt
		 */
		private boolean kickedDown=false;
		/**
		 * Bombe nach rechts gekickt
		 */
		private boolean kickedRight=false;
		/**
		 * Bombe nach links gekickt
		 */
		private boolean kickedLeft=false;
		
		/**
		 * Konstruktor
		 * @param a: uebergebene x-Koordinate
		 * @param b: uebergebene y-Koordinate
		 * @param p: uebergebener Spieler
		 */
		public Bombe(int a, int b, String p){
			x=a;
			y=b;
			exploding = false;
			//Bombs++;
			Bombs.add(this);
			range = 0;
			player = p;
		}
		/**
		 * Legt fest, dass eine Bombe explodiert
		 */
	private void setExploded(){
		this.exploded=true;
	}
	/**
	 * Grafische Zeichnung des Explosion	
	 */
	private void explosion(){
		exploding = true;
		Game.spielfeld[x][y]= new Explosionsfeld(this);
		for (int i=1;i<(Main.m.getRange()+1);i++){		//Explosion nach links
			if(Bombe.getBomb(x-i, y) != -1) {
				Bombs.get(Bombe.getBomb(x-i, y)).setExploded();
			}
			if (!(Game.spielfeld[x-i][y] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x-i][y] instanceof Exitfeld)){
					if (!(Game.spielfeld[x-i][y] instanceof Extrasfeld)){
						if (!(Game.spielfeld[x-i][y] instanceof Fallenfeld)){
							Game.spielfeld[x-i][y]= new Explosionsfeld(this);
						}
					} else{
						if (((Extrasfeld)Game.spielfeld[x-i][y]).isCovered()==false){
							Game.spielfeld[x-i][y]= new Explosionsfeld(this);
						}
					  }
				}
			}
			else {break;}
		}
		for (int i=1;i<(Main.m.getRange()+1);i++){		//Explosion nach rechts
			if(Bombe.getBomb(x+i, y) != -1) {
				Bombs.get(Bombe.getBomb(x+i, y)).setExploded();
			}
			if (!(Game.spielfeld[x+i][y] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x+i][y] instanceof Exitfeld)){
					if (!(Game.spielfeld[x+i][y] instanceof Extrasfeld )){
						if (!(Game.spielfeld[x+i][y] instanceof Fallenfeld)){
							Game.spielfeld[x+i][y]= new Explosionsfeld(this);
						}
					} else{
						if (((Extrasfeld)Game.spielfeld[x+i][y]).isCovered()==false){
							Game.spielfeld[x+i][y]= new Explosionsfeld(this);
						}
					  }
				}
			}
			else {break;}
		}
		for (int i=1;i<(Main.m.getRange()+1);i++){		//Explosion nach oben
			if(Bombe.getBomb(x, y-i) != -1) {
				Bombs.get(Bombe.getBomb(x, y-i)).setExploded();
			}
			if (!(Game.spielfeld[x][y-i] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x][y-i] instanceof Exitfeld)){
					if (!(Game.spielfeld[x][y-i] instanceof Extrasfeld )){
						if (!(Game.spielfeld[x][y-i] instanceof Fallenfeld)){
							Game.spielfeld[x][y-i]= new Explosionsfeld(this);
						}
					} else{
						if (((Extrasfeld)Game.spielfeld[x][y-i]).isCovered()==false){
							Game.spielfeld[x][y-i]= new Explosionsfeld(this);
						}
					  }
				}
			}
			else {break;}
		}
		for (int i=1;i<(Main.m.getRange())+1;i++){		//Explosion nach unten
			if(Bombe.getBomb(x, y+i) != -1) {
				Bombs.get(Bombe.getBomb(x, y+i)).setExploded();
			}
			if (!(Game.spielfeld[x][y+i] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x][y+i] instanceof Exitfeld)){
					if (!(Game.spielfeld[x][y+i] instanceof Extrasfeld)){
						if (!(Game.spielfeld[x][y+i] instanceof Fallenfeld))
							Game.spielfeld[x][y+i]= new Explosionsfeld(this);
					} else{
						if (((Extrasfeld)Game.spielfeld[x][y+i]).isCovered()==false){
							Game.spielfeld[x][y+i]= new Explosionsfeld(this);
						}
					  }
				}
			}
			else {break;}
		}
	}
		
/**
 * Reinigung des Spielfelds
 */
	private void clean(){
		if(Game.spielfeld[x][y] instanceof Explosionsfeld) {
			if(((Explosionsfeld)(Game.spielfeld[x][y])).getBomb() == this) Game.spielfeld[x][y] = new Leerfeld();
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //links
			if (!(Game.spielfeld[x-i][y] instanceof Explosionsfeld) && !(Game.spielfeld[x-i][y] instanceof Leerfeld)) {
				if(!((Game.spielfeld[x-i][y] instanceof Exitfeld) || (Game.spielfeld[x-i][y] instanceof Extrasfeld) || (Game.spielfeld[x-i][y] instanceof Fallenfeld) )) break;
			}
			if (Game.spielfeld[x-i][y] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x-i][y]).getBomb() == this) Game.spielfeld[x-i][y] = new Leerfeld();
			}
			if (Game.spielfeld[x-i][y] instanceof Exitfeld){
				Game.spielfeld[x-i][y].setUncovered();
			}
			if (Game.spielfeld[x-i][y] instanceof Extrasfeld){
				Game.spielfeld[x-i][y].setUncovered();
			}			
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //rechts
			if (!(Game.spielfeld[x+i][y] instanceof Explosionsfeld) && !(Game.spielfeld[x+i][y] instanceof Leerfeld)) {
				if (!((Game.spielfeld[x+i][y] instanceof Exitfeld) || (Game.spielfeld[x+i][y] instanceof Extrasfeld)|| (Game.spielfeld[x+i][y] instanceof Fallenfeld))) break;
			}
			if (Game.spielfeld[x+i][y] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x+i][y]).getBomb() == this) Game.spielfeld[x+i][y] = new Leerfeld();
			}
			if (Game.spielfeld[x+i][y] instanceof Exitfeld){
				Game.spielfeld[x+i][y].setUncovered();
			}
			if (Game.spielfeld[x+i][y] instanceof Extrasfeld){
				Game.spielfeld[x+i][y].setUncovered();
			}
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //oben
			if (!(Game.spielfeld[x][y-i] instanceof Explosionsfeld) && !(Game.spielfeld[x][y-i] instanceof Leerfeld)) {
				if (!((Game.spielfeld[x][y-i] instanceof Exitfeld) || (Game.spielfeld[x][y-i] instanceof Extrasfeld)|| (Game.spielfeld[x][y-i] instanceof Extrasfeld))) break;
			}
			if (Game.spielfeld[x][y-i] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x][y-i]).getBomb() == this) Game.spielfeld[x][y-i] = new Leerfeld();
			}
			if (Game.spielfeld[x][y-i] instanceof Exitfeld){
				Game.spielfeld[x][y-i].setUncovered();
			}
			if (Game.spielfeld[x][y-i] instanceof Extrasfeld){
				Game.spielfeld[x][y-i].setUncovered();
			}
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //unten
			if (!(Game.spielfeld[x][y+i] instanceof Explosionsfeld) && !(Game.spielfeld[x][y+i] instanceof Leerfeld)) {
				if (!((Game.spielfeld[x][y+i] instanceof Exitfeld) || (Game.spielfeld[x][y+i] instanceof Extrasfeld)|| (Game.spielfeld[x][y+i] instanceof Extrasfeld))) break;
			}
			if (Game.spielfeld[x][y+i] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x][y+i]).getBomb() == this) Game.spielfeld[x][y+i] = new Leerfeld();
			}
			if (Game.spielfeld[x][y+i] instanceof Exitfeld){
				Game.spielfeld[x][y+i].setUncovered();
			}
			if (Game.spielfeld[x][y+i] instanceof Extrasfeld){
				Game.spielfeld[x][y+i].setUncovered();
			}
		}

		//Bombs.remove(Bombe.getBomb(num));
		Bombs.remove(Bombs.indexOf(this));
	}
	
		
	/**
	 * Spiel weiterlaufen lassen
	 */
		public void run(){
			int i=0;
			while (!((exploded )||(i==24)))
			{try {
				TimeUnit.MILLISECONDS.sleep(125);
				if ((kickedDown==true) && (kickedUp==true)){
					kickedDown=false;
					kickedUp=false;
				}
				if ((kickedLeft==true) && (kickedRight==true)){
					kickedLeft=false;
					kickedRight=false;
				}
				heaven:
				if (kickedUp == true){
					
					if ( ((Game.getPlayer("One")).getx()==x) && ((Game.getPlayer("One")).gety()==(y-1)) ){
						kickedUp=false;
						break heaven;
					}
					if ( ((Game.getPlayer("Two")).getx()==x) && ((Game.getPlayer("Two")).gety()==(y-1)) ){		
						kickedUp=false;
						break heaven;
					}
					
					if (Game.spielfeld[x][y-1] instanceof Leerfeld){
						Game.spielfeld[x][y]= new Leerfeld();
						y=y-1;
						Game.spielfeld[x][y]= new Bombenfeld();
					}
					if ((Game.spielfeld[x][y-1] instanceof Steinfeld) || (Game.spielfeld[x][y-1] instanceof Mauerfeld) || (Game.spielfeld[x][y-1] instanceof Bombenfeld)){
						kickedUp=false;
					}
					if (Game.spielfeld[x][y-1] instanceof Explosionsfeld){
						Game.spielfeld[x][y]= new Explosionsfeld(this);
						y=y-1;
						exploded=true;
					}
				}
				hell:
				if (kickedDown == true){
					
					if ( ((Game.getPlayer("One")).getx()==x) && ((Game.getPlayer("One")).gety()==(y+1)) ){
						kickedDown=false;
						break hell;
					}
					if ( ((Game.getPlayer("Two")).getx()==x) && ((Game.getPlayer("Two")).gety()==(y+1)) ){		
						kickedDown=false;
						break hell;
					}
					if (Game.spielfeld[x][y+1] instanceof Leerfeld){
						Game.spielfeld[x][y]= new Leerfeld();
						y=y+1;
						Game.spielfeld[x][y]= new Bombenfeld();
					}
					if ((Game.spielfeld[x][y+1] instanceof Steinfeld) || (Game.spielfeld[x][y+1] instanceof Mauerfeld) || (Game.spielfeld[x][y+1] instanceof Bombenfeld)){
						kickedDown=false;
					}
					if (Game.spielfeld[x][y+1] instanceof Explosionsfeld){
						Game.spielfeld[x][y]= new Explosionsfeld(this);
						y=y+1;
						exploded=true;
					}
				}
				
				america:
				if (kickedLeft == true){
					if ( ((Game.getPlayer("One")).getx()==x-1) && ((Game.getPlayer("One")).gety()==(y)) ){
						kickedLeft=false;
						break america;
					}
					if ( ((Game.getPlayer("Two")).getx()==x-1) && ((Game.getPlayer("Two")).gety()==(y)) ){		
						kickedLeft=false;
						break america;
					}
					if (Game.spielfeld[x-1][y] instanceof Leerfeld){
						Game.spielfeld[x][y]= new Leerfeld();
						x=x-1;
						Game.spielfeld[x][y]= new Bombenfeld();
					}
					if ((Game.spielfeld[x-1][y] instanceof Steinfeld) || (Game.spielfeld[x-1][y] instanceof Mauerfeld) || (Game.spielfeld[x-1][y] instanceof Bombenfeld)){
						kickedLeft=false;
					}
					if (Game.spielfeld[x-1][y] instanceof Explosionsfeld){
						Game.spielfeld[x][y]= new Explosionsfeld(this);
						x=x-1;
						exploded=true;
					}
				}
				asia:
				if (kickedRight == true){
					if ( ((Game.getPlayer("One")).getx()==x+1) && ((Game.getPlayer("One")).gety()==(y)) ){
						kickedRight=false;
						break asia;
					}
					if ( ((Game.getPlayer("Two")).getx()==x+1) && ((Game.getPlayer("Two")).gety()==(y)) ){		
						kickedRight=false;
						break asia;
					}
					if (Game.spielfeld[x+1][y] instanceof Leerfeld){
						Game.spielfeld[x][y]= new Leerfeld();
						x=x+1;
						Game.spielfeld[x][y]= new Bombenfeld();
					}
					if ((Game.spielfeld[x+1][y] instanceof Steinfeld) || (Game.spielfeld[x+1][y] instanceof Mauerfeld) || (Game.spielfeld[x+1][y] instanceof Bombenfeld)){
						kickedRight=false;
					}
					if (Game.spielfeld[x+1][y] instanceof Explosionsfeld){
						Game.spielfeld[x][y]= new Explosionsfeld(this);
						x=x+1;
						exploded=true;
					}
				}
				
			} catch (InterruptedException e) {};
			i++;
			}
			
			Game.lock1.lock();
			explosion();
			Game.lock1.unlock();			 
			//Renderer.Bomb_Explode.playAsSoundEffect(1.0f , 1.0f, true);
			try {
				TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {};			
			Game.lock1.lock();
			clean();
			Game.lock1.unlock();
		} 
		
		
		/**
		 * Nach oben gekickte Bombe ist wahr
		 */
		public void setkickedUp(){
			this.kickedUp=true;
		}
		/**
		 * Nach unten gekickte Bombe ist wahr
		 */
		public void setkickedDown(){
			this.kickedDown=true;
		}
		/**
		 * Nach rechts gekickte Bombe ist wahr
		 */
		public void setkickedRight(){
			this.kickedRight=true;
		}
		/**
		 * Nach links gekickte Bombe ist wahr
		 */
		public void setkickedLeft(){
			this.kickedLeft=true;
		}
		/**
		 * Gibt Anzahl der Bomben pro Spieler zurueck
		 * @param p: Zu ueberpruefende Spieler
		 * @return: Anzahl der Bomben
		 */
		public static int getBombs(String p) {
			//return Bombs.size();
			int n = 0;
			for(int i = 0; i < Bombs.size();i++)
			{
				if(Bombs.get(i).player.equals(p)) n++;
			}
			return n;
		}
		/**
		 * Ueberpruefung der Bombe
		 */
		public static int getBomb(int x, int y) {
			for(int i = 0; i < Bombs.size(); i++){
				if((Bombs.get(i).x == x)&&(Bombs.get(i).y == y)&&(Bombs.get(i).exploding==false)) return i;
			}
			return -1;
		}
	}