/**
 * Klasse fuer leeres Feld
 * @author simon
 *
 */
public class Leerfeld extends Feld{
	/**
	 * Zeichen
	 */
	public void draw(int x, int y){
		Renderer.Tile_Empty.draw(x*size, y*size);
		
	}
}
