
public class Explosionsfeld extends Feld {

	public void draw(int x, int y){
		Renderer.Tile_Empty.draw(x * 128 * 0.33f, y*128 * 0.33f);
		Renderer.Tile_Explosion.draw(x * 128 * 0.33f, y*128 * 0.33f);
	}
}
