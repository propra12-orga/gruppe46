/**
 * Klasse fuer Felder mit Explosion
 * @author simon
 *
 */
public class Explosionsfeld extends Feld {
	private Bombe Bomb;
	
	public Explosionsfeld(Bombe B) {
		Bomb = B;
	}
	/**
	 * Zeichnen
	 */
	public void draw(int x, int y){
		Renderer.Tile_Empty.draw(x*size, y*size);
		Renderer.Tile_Explosion.draw(x*size, y*size);
	}
	/**
	 * Rueckgabe der Bombe
	 * @return: Rueckgabe der Bombe
	 */
	public Bombe getBomb() {
		return Bomb;
	}
}
