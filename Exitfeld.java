
public class Exitfeld extends Feld{
	/**
	 * Gibt an, ob der Ausgang versteckt ist
	 */
	private boolean covered=true;
	/**
	 * Zeichnet die Grafik
	 */
	public void draw  (int x, int y){
		if (covered==true){
			Renderer.Tile_Break.draw(x*size, y*size);
			//Renderer.Tile_Explosion.draw(x*size, y*size);
		}
		else {
			Renderer.Tile_Empty.draw(x*size, y*size);
			Renderer.Tile_Explosion.draw(x*size, y*size);
		}
		
	}
	
	public void setUncovered(){
		covered=false;
	}
	
	public boolean isCovered(){
		return covered;
	}
}
