
public class Bombenfeld extends Feld{
	public void draw(int x, int y){
		Renderer.Tile_Empty.draw(x*32, y*32);
		Renderer.Tile_Bomb.draw(x*32, y*32);
	}
}
