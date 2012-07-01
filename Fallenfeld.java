
public class Fallenfeld extends Feld{
	/**
	 * Name des Fallenstellers
	 */
	private String fallensteller="";
	/**
	 * Konstrukor
	 * @param name: Name des Fallenstellers
	 */
	public Fallenfeld(String name){
		fallensteller=name;
	}
	
	public Fallenfeld(){
		
	}
	/**
	 * Zeichnen
	 */
	public void draw(int x, int y){
	}
	/**
	 * Names des Fallenstellers
	 * @return: Name des Fallenstellers
	 */
	public String getFallensteller(){
		return fallensteller;
	}
	
}
