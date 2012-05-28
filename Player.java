
public class Player{
	private String name;
	private boolean alive;
	private int posx, posy;
	private int Bombs;
	
	private LWJGL_Sprite Sprite;
	
	private long lastMove;
	
	public Player (String name, int x, int y){
		this.name=name;
		this.posx=x;
		this.posy=y;
		this.alive=true;
		lastMove = 0;
		Bombs = 1;
	}
	
	public boolean isAlive(){
		return alive; // ES LEEEEEEEBT!
	}
	
	public int getx(){
		return posx;
	}
	
	public int gety(){
		return posy;
	}
	
	public void die(){
		alive=false; // Sterben kann er auch :D so richtig gut
	}
	
	public void move(int addx, int addy){
		if((GameTime.getTime() - lastMove) > 300)
		{
			posx+=addx;
			posy+=addy;											//taste f�rs bombe legen fehlt NICHT
			lastMove = GameTime.getTime();
		}
	}
	
	public void loadSprite(String file) {
		Sprite = new LWJGL_Sprite(file);
		Sprite.init();
		Sprite.setScaleX(0.33f);
		Sprite.setScaleY(0.33f);
		
	}
	
	public void draw(){
		Sprite.draw(posx * 128 * 0.33f, posy * 128 * 0.33f);
	}
	
	public void setBombs(int n) {
		Bombs = n;
	}
	
	public int getBombs() {
		return Bombs;
	}
}
 