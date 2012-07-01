//Serializable zum Speichern des Objektes
//import java.io.Serializable; 

/**
 * Oberklasse von der saemtliche Felder des Spielfeldes 
 * abgeleitet werden koennen
 * @author simon
 */
public abstract class Feld {
	/**
	 * Legt Groesse des Feldes fest
	 */
	protected static int size = 40;
	/**
	 * Zeichnen
	 * @param x: x-Koordinate
	 * @param y: y-Koordinate
	 */
	public void draw(int x, int y){
	}
	/**
	 * Setzt Feld zu sichtbar
	 */
	public void setUncovered(){
		
	}
	/**
	 * Gibt Groesse des Feldes zurueck
	 * @return: Groesse des Feldes
	 */
	public static int getSize(){
		return size;
	}
	
}
