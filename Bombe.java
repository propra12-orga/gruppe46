import java.util.concurrent.TimeUnit;

class Bombe extends Thread{
		private int x;
		private int y;
		
		public Bombe(int a, int b){
			x=a;
			y=b;
		}
		
	private void explosion(int x, int y){
		Game.spielfeld[x][y]= new Explosionsfeld();
		for (int i=1;i<4;i++){		//Explosion nach links
			if (!(Game.spielfeld[x-i][y] instanceof Steinfeld)) {Game.spielfeld[x-i][y]= new Explosionsfeld();}
			else {break;}
		}
		for (int i=1;i<4;i++){		//Explosion nach rechts
			if (!(Game.spielfeld[x+i][y] instanceof Steinfeld)) {Game.spielfeld[x+i][y]= new Explosionsfeld();}
			else {break;}
		}
		for (int i=1;i<4;i++){		//Explosion nach oben
			if (!(Game.spielfeld[x][y-i] instanceof Steinfeld)) {Game.spielfeld[x][y-i]= new Explosionsfeld();}
			else {break;}
		}
		for (int i=1;i<4;i++){		//Explosion nach unten
			if (!(Game.spielfeld[x][y+i] instanceof Steinfeld)) {Game.spielfeld[x][y+i]= new Explosionsfeld();}
			else {break;}
		}
		Game.bombeVerwendbar = true;
	}
		

	private void clean(int x, int y){ //macht aus den Explosionsfeldern Leerfelder
		Game.spielfeld[x][y]=new Leerfeld();
		for (int i=1;i<4;i++){		//links
			if (Game.spielfeld[x-i][y] instanceof Explosionsfeld | Game.spielfeld[x-i][y] instanceof Leerfeld) {Game.spielfeld[x-i][y]= new Leerfeld();}
			else {break;}
		}
		for (int i=1;i<4;i++){		//rechts
			if (Game.spielfeld[x+i][y] instanceof Explosionsfeld | Game.spielfeld[x+i][y] instanceof Leerfeld) {Game.spielfeld[x+i][y]= new Leerfeld();}
			else {break;}
		}
		for (int i=1;i<4;i++){		//oben
			if (Game.spielfeld[x][y-i] instanceof Explosionsfeld | Game.spielfeld[x][y-i] instanceof Leerfeld) {Game.spielfeld[x][y-i]= new Leerfeld();}
			else {break;}
		}
		for (int i=1;i<4;i++){		//unten
			if (Game.spielfeld[x][y+i] instanceof Explosionsfeld | Game.spielfeld[x][y+i] instanceof Leerfeld) {Game.spielfeld[x][y+i]= new Leerfeld();}
			else {break;}
		}
	}
		public void run(){
			try{
			TimeUnit.SECONDS.sleep(3);
			} catch(InterruptedException e){};
			Game.lock1.lock();
			explosion(x,y);
			Game.lock1.unlock();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
			Game.lock1.lock();
			clean(x,y);
			Game.lock1.unlock();
		}
	}