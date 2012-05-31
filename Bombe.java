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
		
		public Bombe(int a, int b){
			x=a;
			y=b;
			exploding = false;
			num = Bombs.size();
			//Bombs++;
			Bombs.add(this);
		}
		
	private void explosion(){
		exploding = true;
		Game.spielfeld[x][y]= new Explosionsfeld(num);
		for (int i=1;i<4;i++){		//Explosion nach links
			if(Bombe.getBomb(x-i, y) != -1) Bombs.get(Bombe.getBomb(x-i, y)).explosion();
			if (!(Game.spielfeld[x-i][y] instanceof Steinfeld)) {Game.spielfeld[x-i][y]= new Explosionsfeld(num);}
			else {break;}
		}
		for (int i=1;i<4;i++){		//Explosion nach rechts
			if(Bombe.getBomb(x+i, y) != -1) Bombs.get(Bombe.getBomb(x+i, y)).explosion();
			if (!(Game.spielfeld[x+i][y] instanceof Steinfeld)) {Game.spielfeld[x+i][y]= new Explosionsfeld(num);}
			else {break;}
		}
		for (int i=1;i<4;i++){		//Explosion nach oben
			if(Bombe.getBomb(x, y-i) != -1) Bombs.get(Bombe.getBomb(x, y-i)).explosion();
			if (!(Game.spielfeld[x][y-i] instanceof Steinfeld)) {Game.spielfeld[x][y-i]= new Explosionsfeld(num);}
			else {break;}
		}
		for (int i=1;i<4;i++){		//Explosion nach unten
			if(Bombe.getBomb(x, y+i) != -1) Bombs.get(Bombe.getBomb(x, y+i)).explosion();
			if (!(Game.spielfeld[x][y+i] instanceof Steinfeld)) {Game.spielfeld[x][y+i]= new Explosionsfeld(num);}
			else {break;}
		}
	}
		

	private void clean(){ //macht aus den Explosionsfeldern Leerfelder
		if(Game.spielfeld[x][y] instanceof Explosionsfeld) {
			if(((Explosionsfeld)(Game.spielfeld[x][y])).getBomb() == num) Game.spielfeld[x][y] = new Leerfeld();
		}
		
		for (int i=1;i<4;i++){ //links
			if (!(Game.spielfeld[x-i][y] instanceof Explosionsfeld) && !(Game.spielfeld[x-i][y] instanceof Leerfeld)) {
				break;
			}
			if (Game.spielfeld[x-i][y] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x-i][y]).getBomb() == num) Game.spielfeld[x-i][y] = new Leerfeld();
			}
		}
		
		for (int i=1;i<4;i++){ //rechts
			if (!(Game.spielfeld[x+i][y] instanceof Explosionsfeld) && !(Game.spielfeld[x+i][y] instanceof Leerfeld)) {
				break;
			}
			if (Game.spielfeld[x+i][y] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x+i][y]).getBomb() == num) Game.spielfeld[x+i][y] = new Leerfeld();
			}
		}
		
		for (int i=1;i<4;i++){ //oben
			if (!(Game.spielfeld[x][y-i] instanceof Explosionsfeld) && !(Game.spielfeld[x][y-i] instanceof Leerfeld)) {
				break;
			}
			if (Game.spielfeld[x][y-i] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x][y-i]).getBomb() == num) Game.spielfeld[x][y-i] = new Leerfeld();
			}
		}
		
		for (int i=1;i<4;i++){ //unten
			if (!(Game.spielfeld[x][y+i] instanceof Explosionsfeld) && !(Game.spielfeld[x][y+i] instanceof Leerfeld)) {
				break;
			}
			if (Game.spielfeld[x][y+i] instanceof Explosionsfeld) {
				if(((Explosionsfeld)Game.spielfeld[x][y+i]).getBomb() == num) Game.spielfeld[x][y+i] = new Leerfeld();
			}
		}
		Bombs.remove(Bombe.getBomb(num));
	}
		public void run(){
			try{
			TimeUnit.SECONDS.sleep(3);
			} catch(InterruptedException e){};
			Game.lock1.lock();
			explosion();
			Game.lock1.unlock();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
			Game.lock1.lock();
			clean();
			Game.lock1.unlock();
		}
		
		public static int getBombs() {
			return Bombs.size();
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