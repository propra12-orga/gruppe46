
public class Fallenfeld extends Feld{
	private String fallensteller="";
	
	public Fallenfeld(String name){
		fallensteller=name;
	}
	
	public Fallenfeld(){
		
	}
	
	public void draw(int x, int y){
	}
	
	public String getFallensteller(){
		return fallensteller;
	}
	
}
