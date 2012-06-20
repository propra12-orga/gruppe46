/**
 * Steinfelder, die nicht durch Bomber zerstoerbar sind
 * @author simon
 *
 */
public class Steinfeld extends Feld{
	
	
	public void draw(int x, int y){
		Renderer.Tile_Wall.draw(x * 128 * 0.33f, y * 128 * 0.33f);
	}
}
