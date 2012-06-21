/**
 * Klasse fuer Feld mit Bombe
 * @author simon
 *
 */
public class Bombenfeld extends Feld{
	public void draw(int x, int y){
		Renderer.Tile_Empty.draw(x*128 * 0.33f, y*128 * 0.33f);
		Renderer.Tile_Bomb.draw(x*128 * 0.33f, y*128 * 0.33f);
	}
}
