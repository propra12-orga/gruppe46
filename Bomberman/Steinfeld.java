
//Steinfelder sind nicht durch Bomben zerstoerbar
public class Steinfeld extends Feld{
	
	
	public void draw(int x, int y){
		Renderer.Tile_Wall.draw(x * 128 * 0.33f, y * 128 * 0.33f);
	}
}
