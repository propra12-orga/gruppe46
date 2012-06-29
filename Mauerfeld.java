/**
* Einfaches Mauerfeld
* @author simon
*
*/
public class Mauerfeld extends Feld{

public void draw(int x, int y){
	Renderer.Tile_Break.draw(x*size, y*size);
}
}