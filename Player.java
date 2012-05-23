
public class Player{
	private String name;
	private boolean alive;
	private int posx, posy;
	
	private LWJGL_Sprite Sprite;
	
	private long lastMove;
	
	public Player (String name, int x, int y){
		this.name=name;
		this.posx=x;
		this.posy=y;
		this.alive=true;
		lastMove = 0;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public int getx(){
		return posx;
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
			posy+=addy;											//taste f�rs bombe legen fehlt
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
		/*
		glBegin(GL_QUADS);
		glVertex2i(10*(posx-1),10*(posy-1));
		glVertex2i(10*posx, 10*(posy-1));
		glVertex2i(10*posx,10*posy);
		glVertex2i(10*(posx-1),10*posy);
		glEnd(); // Zum Test: Bomberman=wei�er Kasten*/
		Sprite.draw(posx * 128 * 0.33f, posy * 128 * 0.33f);
	}
	
}
 