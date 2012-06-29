//Serializable zum Speichern des Objektes
//import java.io.Serializable; 

/**
 * Oberklasse von der saemtliche Felder des Spielfeldes 
 * abgeleitet werden koennen
 * @author simon
 */
public abstract class Feld {
	
	protected static int size = 40;
	
	public void draw(int x, int y){
	}
	
	public void setUncovered(){
		
	}
	
	public static int getSize(){
		return size;
	}
	
}
