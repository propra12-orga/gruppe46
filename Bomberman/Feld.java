//Oberklasse von der saemtliche Felder des Spielfeldes
//abgeleitet werden koennen

import java.io.Serializable; 
//Serializable zum Speichern des Objektes

public abstract class Feld implements Serializable {
	
	public void draw(int x, int y){
	}
	
	public void setUncovered(){
		
	}
	
}
