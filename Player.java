
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
	public boolean kicker=true;
	
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
 