/**
 * Klasse fuer Feld mit Bombe
 * @author simon
 *
 */
public class Bombenfeld extends Feld{
	public void draw(int x, int y){
		Renderer.Tile_Empty.draw(x*size, y*size);
		Renderer.Tile_Bomb.draw(x*size, y*size);
	}
}
