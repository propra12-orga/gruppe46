
//Steinfelder sind nicht durch Bomben zerstörbar
public class Steinfeld extends Feld{
	
	
	public void draw(int x, int y){
		Renderer.Tile_Wall.draw(x * 32, y * 32);
	}
}
