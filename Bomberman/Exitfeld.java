
public class Exitfeld extends Feld{
	
	private boolean covered=true;

	public void draw  (int x, int y){
		if (covered==true){
			
		}
		else {
			Renderer.Tile_Empty.draw(x*128 * 0.33f, y*128* 0.33f);
			Renderer.Tile_Explosion.draw(x*128* 0.33f, y*128* 0.33f);
		}
		
	}
	
	public void setUncovered(){
		this.covered=false;
	}
}
