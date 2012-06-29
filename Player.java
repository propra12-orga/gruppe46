
public class Player{
	private String name;
	private boolean alive;
	private int posx, posy;
	private int Bombs;
	protected int lives;
	private Bombe last_hit_by;	
	private int key_left, key_right, key_up, key_down, key_bomb;
	private boolean press_bomb;	
	private LWJGL_Sprite Sprite;
	private long lastMove;
	public boolean kicker=false;
	
	public Player (String name, int x, int y){
		this.name=name;
		this.posx=x;
		this.posy=y;
		this.alive=true;
		lastMove = 0;
		lives = Main.m.getLives();
		Bombs = 1;
	}
	
	public boolean isAlive(){
		return alive; // ES LEEEEEEEBT!
	}
	
	public void hit(Bombe Bomb) {
		if(last_hit_by == Bomb) { return; }
		last_hit_by = Bomb;
		lives--;
		if(lives<=0) alive=false;
	}
	
	public int getx(){
		return posx;
	}
	public int getLives(){
		return lives;
	}
	
	public int gety(){
		return posy;
	}
	
	public void die(){
		alive=false;
	}
	
	public void move(int addx, int addy){
		if((GameTime.getTime() - lastMove) > 300)
		{
			posx+=addx;
			posy+=addy;
			lastMove = GameTime.getTime();
		}
	}
	
	public void collect(Extrasfeld field){
		int art=field.art;
		switch(art){
			case 1: {this.lives++;}//Dieses Feld generiert ein zusätzliches Leben um einen Bombentreffer zu überleben}
			case 2: {this.Bombs++;}//Dieses Feld generiert ein Upgrade, um die Anzahl der Bomben, die man gleichzeitig legen kann, um 1 zu erhöhen
			case 3: {this.kicker=true;}//Dieses Feld generiert ein Upgrade, wodurch der Spieler dazu befaehigt wird, die Bomben linear zu treten
			case 4: {}//Dieses Feld generiert ein Upgrade um (fuer die naechste Bombe) eine Super-Bombe anstatt einer normalen Bombe zu legen. Die Super-Bombe hat <spezifikation>
			case 5: {}//Dieses Feld generiert eine temporäre Steuerungsbehinderung für alle feindlichen/anderen Spieler. Alle außer der Spieler, der das Feld betritt, unterliegen einer umgekehrten Steuerung (Ausnahme: Bombe legen)
			case 6: {}//Dieses Feld generiert ein Teleportationsfeld. Der Spieler der auf dieses Feld tritt wird mit sofortiger Wirkung zum entsprechenden Feld teleportiert
			case 7: {}//Dieses Feld generiert ein Upgrade, welches einen temporären Geschwindigkeitsbonus für den Spieler gibt, der es aufsammelt
			case 8: {}//Dieses Feld generiert ein temporaere Spielerbehinderung. Alle gegnerischen/anderen Spieler sind nur noch 50-75% so schnell
			case 9: {}//Dieses Feld generiert bei Kontakt auf dem gesammten Spielfeld Bomben (5-10 Bomben? [Abhaengig davon, wie viele freie Felder es gibt]
			case 10: {}//Dieses Feld generiert ein Loch. Der Spieler, der dieses Loch betritt faellt darin hinein und stirbt (egal wie viele Leben dieser noch hatte = Instant Death). Weitere Spieler können dieses Feld gefahrlos ueberqueren
			case 11: {}//Dieses Feld generiert ein Uprade, welches temporaere Unverwundbarkeit verleiht. Dem Spieler wird kein Bombentreffer angerechnet. Fraglich: Würde er bei ART == 10 sterben?
			case 12: {}//Dieses Feld generiert einen FROST-SCHOCK. Der Spieler der es aufsammelt, darf sich temporaer nicht bewegen. Wenn der Frost-Schock vorbei ist, erhält der Spieler seine komplette Bewegungsfreiheit.
			case 13: {}//Dieses Feld generiert ein Upgrade zur Verbesserung der Bombenreichweite.
		}
	}
	
	public void loadSprite(String file) {
		Sprite = new LWJGL_Sprite(file);
		Sprite.init();
		Sprite.setScaleX(Feld.getSize()/(float)Sprite.getWidth());
		Sprite.setScaleY(Feld.getSize()/(float)Sprite.getHeight());
		
	}
	
	public void draw(){
		Sprite.draw(posx * Feld.getSize(), posy * Feld.getSize());
	}
	
	public void setBombs(int n) {
		Bombs = n;
	}
	
	public int getBombs() {
		return Bombs;
	}
	
	public void setKeys(int l, int r, int u, int d, int b) {
		key_left = l;
		key_right = r;
		key_down = d;
		key_up = u;
		key_bomb = b;
	}
	
	public int getKeyUp() {
		return key_up;
	}

	public int getKeyDown() {
		return key_down;
	}

	public int getKeyLeft() {
		return key_left;
	}

	public int getKeyRight() {
		return key_right;
	}

	public int getKeyBomb() {
		return key_bomb;
	}

	public boolean isPress_bomb() {
		return press_bomb;
	}

	public void setPress_bomb(boolean press) {
		press_bomb = press;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getKicker(){
		return kicker;
	}
	
	public void setKicker(){
		this.kicker=true;
	}
	
	
}
 