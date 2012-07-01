
public class Fallenfeld extends Feld{
	/**
	 * Name des Fallenstellers
	 */
	private String fallensteller="";
	/**
	 * Art der Falle
	 */
	private int art=0;
	/**
	 * Konstrukor
	 * @param name: Name des Fallenstellers
	 * @param art: Art der Falle
	 */
	public Fallenfeld(String name, int value){
		fallensteller=name;
		this.art=value;
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
	
	public int getArt(){
		return art;
	}
	
	
}
