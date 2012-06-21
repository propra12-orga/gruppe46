import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

class Bombe extends Thread{
		//private static int Bombs;
		private static final List<Bombe> Bombs = new ArrayList<Bombe>();
		
		private int x;
		private int y;
		private int num;
		private boolean exploding;
		private boolean exploded=false;
		private boolean time1=false;
		protected int range;
		private String player;
		
		public Bombe(int a, int b, String p){
			x=a;
			y=b;
			exploding = false;
			num = Bombs.size();
			//Bombs++;
			Bombs.add(this);
			range = 0;
			player = p;
		}
		
	private void setExploded(){
		this.exploded=true;
	}
		
	private void explosion(){
		exploding = true;
		Game.spielfeld[x][y]= new Explosionsfeld(this);
		for (int i=1;i<(Main.m.getRange()+1);i++){		//Explosion nach links
			if(Bombe.getBomb(x-i, y) != -1) {
				Bombs.get(Bombe.getBomb(x-i, y)).setExploded();
			}
			if (!(Game.spielfeld[x-i][y] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x-i][y] instanceof Exitfeld)){
					Game.spielfeld[x-i][y]= new Explosionsfeld(this);}}
			else {break;}
		}
		for (int i=1;i<(Main.m.getRange()+1);i++){		//Explosion nach rechts
			if(Bombe.getBomb(x+i, y) != -1) {
				Bombs.get(Bombe.getBomb(x+i, y)).setExploded();
			}
			if (!(Game.spielfeld[x+i][y] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x+i][y] instanceof Exitfeld)){
					Game.spielfeld[x+i][y]= new Explosionsfeld(this);}
				}
			else {break;}
		}
		for (int i=1;i<(Main.m.getRange()+1);i++){		//Explosion nach oben
			if(Bombe.getBomb(x, y-i) != -1) {
				Bombs.get(Bombe.getBomb(x, y-i)).setExploded();
			}
			if (!(Game.spielfeld[x][y-i] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x][y-i] instanceof Exitfeld)){
					Game.spielfeld[x][y-i]= new Explosionsfeld(this);}
				}
			else {break;}
		}
		for (int i=1;i<(Main.m.getRange())+1;i++){		//Explosion nach unten
			if(Bombe.getBomb(x, y+i) != -1) {
				Bombs.get(Bombe.getBomb(x, y+i)).setExploded();
			}
			if (!(Game.spielfeld[x][y+i] instanceof Steinfeld)) {
				if (!(Game.spielfeld[x][y+i] instanceof Exitfeld)){
					Game.spielfeld[x][y+i]= new Explosionsfeld(this);}
				}
			else {break;}
		}
	}
		

	private void clean(){ //macht aus den Explosionsfeldern Leerfelder
		if(Game.spielfeld[x][y] instanceof Explosionsfeld) {
			if(((Explosionsfeld)(Game.spielfeld[x][y])).getBomb() == this) Game.spielfeld[x][y] = new Leerfeld();
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //links
			if (!(Game.spielfeld[x-i][y] instanceof Explosionsfeld) && !(Game.spielfeld[x-i][y] instanceof Leerfeld)) {
				if(!(Game.spielfeld[x-i][y] instanceof Exitfeld))break;
			}
			if (Game.spielfeld[x-i][y] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x-i][y]).getBomb() == this) Game.spielfeld[x-i][y] = new Leerfeld();
			}
			if (Game.spielfeld[x-i][y] instanceof Exitfeld){
			Game.spielfeld[x-i][y].setUncovered();}
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //rechts
			if (!(Game.spielfeld[x+i][y] instanceof Explosionsfeld) && !(Game.spielfeld[x+i][y] instanceof Leerfeld)) {
				if (!(Game.spielfeld[x+i][y] instanceof Exitfeld)) break;
			}
			if (Game.spielfeld[x+i][y] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x+i][y]).getBomb() == this) Game.spielfeld[x+i][y] = new Leerfeld();
			}
			if (Game.spielfeld[x+i][y] instanceof Exitfeld){
				Game.spielfeld[x+i][y].setUncovered();}
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //oben
			if (!(Game.spielfeld[x][y-i] instanceof Explosionsfeld) && !(Game.spielfeld[x][y-i] instanceof Leerfeld)) {
				if(!(Game.spielfeld[x][y-i] instanceof Exitfeld))break;
			}
			if (Game.spielfeld[x][y-i] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x][y-i]).getBomb() == this) Game.spielfeld[x][y-i] = new Leerfeld();
			}
			if (Game.spielfeld[x][y-i] instanceof Exitfeld){
				Game.spielfeld[x][y-i].setUncovered();}
		}
		
		for (int i=1;i<(Main.m.getRange()+1);i++){ //unten
			if (!(Game.spielfeld[x][y+i] instanceof Explosionsfeld) && !(Game.spielfeld[x][y+i] instanceof Leerfeld)) {
				if (!(Game.spielfeld[x][y+i] instanceof Exitfeld))break;
			}
			if (Game.spielfeld[x][y+i] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x][y+i]).getBomb() == this) Game.spielfeld[x][y+i] = new Leerfeld();
			}
			if (Game.spielfeld[x][y+i] instanceof Exitfeld){
				 Game.spielfeld[x][y+i].setUncovered();}
			
		}
		Bombs.remove(Bombe.getBomb(num));
	}
	
		
	
		public void run(){
			int i=0;
			while (!((exploded )||(i==3000)))
			{try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {};
			i++;
			}
			
			Game.lock1.lock();
			explosion();
			Game.lock1.unlock();			 
			try {
				TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {};			
			Game.lock1.lock();
			clean();
			Game.lock1.unlock();
		} 
		
		
		
		public static int getBombs(String p) {
			//return Bombs.size();
			int n = 0;
			for(int i = 0; i < Bombs.size();i++)
			{
				if(Bombs.get(i).player.equals(p)) n++;
			}
			return n;
		}
		
		public static int getBomb(int x, int y) {
			for(int i = 0; i < Bombs.size(); i++){
				if((Bombs.get(i).x == x)&&(Bombs.get(i).y == y)&&(Bombs.get(i).exploding==false)) return i;
			}
			return -1;
		}
		
		public static int getBomb(int n) {
			for(int i = 0; i < Bombs.size(); i++){
				if((Bombs.get(i).num == n)) return i;
			}
			return -1;
		}
	}